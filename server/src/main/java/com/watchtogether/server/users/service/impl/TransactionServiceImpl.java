package com.watchtogether.server.users.service.impl;


import static com.watchtogether.server.exception.type.UserErrorCode.NOT_FOUND_USER;
import static com.watchtogether.server.exception.type.UserErrorCode.WRONG_PASSWORD_USER;
import static com.watchtogether.server.users.domain.type.TransactionResultType.ACCEPT;
import static com.watchtogether.server.users.domain.type.TransactionType.CHARGE;

import com.watchtogether.server.exception.UserException;
import com.watchtogether.server.users.domain.dto.TransactionDto;
import com.watchtogether.server.users.domain.entitiy.Transaction;
import com.watchtogether.server.users.domain.entitiy.User;
import com.watchtogether.server.users.domain.repository.TransactionRepository;
import com.watchtogether.server.users.domain.repository.UserRepository;
import com.watchtogether.server.users.service.TransactionService;
import java.time.LocalDateTime;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public TransactionDto userCashCharge(String email, String password, Long amount) {

        User user = userRepository.findById(email)
            .orElseThrow(() -> new UserException(NOT_FOUND_USER));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new UserException(WRONG_PASSWORD_USER);
        }

        user.plusCash(amount);

        return TransactionDto.fromEntity(
            transactionRepository.save(Transaction.builder()
                .transactionType(CHARGE)
                .transactionResultType(ACCEPT)
                .user(user)
                .amount(amount)
                .balanceSnapshot(user.getCash())
                .traderNickname("")
                .transactionDt(LocalDateTime.now())
                .build()));

    }
}
