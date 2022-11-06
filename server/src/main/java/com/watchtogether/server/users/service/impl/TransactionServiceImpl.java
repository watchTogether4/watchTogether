package com.watchtogether.server.users.service.impl;


import static com.watchtogether.server.exception.type.UserErrorCode.NOT_FOUND_LEADER;
import static com.watchtogether.server.exception.type.UserErrorCode.NOT_FOUND_USER;
import static com.watchtogether.server.exception.type.UserErrorCode.WRONG_PASSWORD_USER;
import static com.watchtogether.server.users.domain.type.TransactionResultType.ACCEPT;
import static com.watchtogether.server.users.domain.type.TransactionResultType.CANCEL;
import static com.watchtogether.server.users.domain.type.TransactionResultType.WAIT;
import static com.watchtogether.server.users.domain.type.TransactionType.CHARGE;
import static com.watchtogether.server.users.domain.type.TransactionType.WITHDRAW;

import com.watchtogether.server.exception.UserException;
import com.watchtogether.server.users.domain.dto.TransactionDto;
import com.watchtogether.server.users.domain.entitiy.Transaction;
import com.watchtogether.server.users.domain.entitiy.User;
import com.watchtogether.server.users.domain.repository.TransactionRepository;
import com.watchtogether.server.users.domain.repository.UserRepository;
import com.watchtogether.server.users.service.TransactionService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
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
    public List<TransactionDto> userCashList(String email) {

        User user = userRepository.findById(email)
            .orElseThrow(() -> new UserException(NOT_FOUND_USER));

        List<Transaction> transactionList = transactionRepository.findByUserOrderByIdDesc(user);

        return transactionList.stream()
            .map(TransactionDto::fromEntity)
            .collect(Collectors.toList());
    }


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
                .transactionType(CHARGE.getDescription())
                .transactionResultType(ACCEPT.getDescription())
                .user(user)
                .amount(amount)
                .balanceSnapshot(user.getCash())
                .traderEmail("")
                .transactionDt(LocalDateTime.now())
                .build()));

    }

    @Override
    public TransactionDto userCashWithdraw(String leaderEmail, String email,
        int commissionMember, Long fee) {

        // 파티장 아이디 유효성 검사
        userRepository.findById(leaderEmail)
            .orElseThrow(() -> new UserException(NOT_FOUND_LEADER));

        // 사용자 아이디 유효성 검사
        User user = userRepository.findById(email)
            .orElseThrow(() -> new UserException(NOT_FOUND_USER));

        // 서비스 이용료 총 합 : 전체 요금 / 4 + 파티원 수수료
        Long totalAmount = fee + commissionMember;

        user.minusCash(totalAmount);

        return TransactionDto.fromEntity(
            transactionRepository.save(Transaction.builder()
                .transactionType(WITHDRAW.getDescription())
                .transactionResultType(WAIT.getDescription())
                .user(user)
                .amount(totalAmount)
                .balanceSnapshot(user.getCash())
                .traderEmail(leaderEmail)
                .transactionDt(LocalDateTime.now())
                .build()));
    }

    @Override
    public TransactionDto userCashWithdrawCancel(String leaderEmail, String email,
        int commissionMember, Long fee) {

        // 파티장 아이디 유효성 검사
        userRepository.findById(leaderEmail)
            .orElseThrow(() -> new UserException(NOT_FOUND_LEADER));

        // 사용자 아이디 유효성 검사
        User user = userRepository.findById(email)
            .orElseThrow(() -> new UserException(NOT_FOUND_USER));

        // 서비스 이용료 총 합 : 전체 요금 / 4 + 파티원 수수료
        Long totalAmount = fee + commissionMember;

        user.plusCash(totalAmount);

        return TransactionDto.fromEntity(
            transactionRepository.save(Transaction.builder()
                .transactionType(WITHDRAW.getDescription())
                .transactionResultType(CANCEL.getDescription())
                .user(user)
                .amount(totalAmount)
                .balanceSnapshot(user.getCash())
                .traderEmail(leaderEmail)
                .transactionDt(LocalDateTime.now())
                .build()));
    }

}
