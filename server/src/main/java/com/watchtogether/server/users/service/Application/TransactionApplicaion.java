package com.watchtogether.server.users.service.Application;

import com.watchtogether.server.ott.domain.dto.OttDto;
import com.watchtogether.server.ott.service.OttService;
import com.watchtogether.server.users.domain.dto.TransactionDto;
import com.watchtogether.server.users.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionApplicaion {

    private final TransactionService transactionService;
    private final OttService ottService;


    public TransactionDto userCashWithdraw(String leaderNickname, Long ottId, String email) {

        OttDto ottDto = ottService.searchOtt(ottId);

        return transactionService.userCashWithdraw(
            leaderNickname,
            email,
            ottDto.getCommissionMember(),
            ottDto.getFee());
    }
}
