package com.watchtogether.server.users.service.Application;

import com.watchtogether.server.ott.domain.dto.OttDto;
import com.watchtogether.server.ott.service.OttService;
import com.watchtogether.server.party.domain.repository.PartyRepository;
import com.watchtogether.server.users.domain.dto.TransactionDto;
import com.watchtogether.server.users.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionApplicaion {

    private final TransactionService transactionService;
    private final OttService ottService;

    private final PartyRepository partyRepository;


    public TransactionDto userCashWithdraw(String leaderNickname, Long partyId, Long ottId,
        String email) {

        OttDto ottDto = ottService.searchOtt(ottId);

        return transactionService.userCashWithdraw(
            partyId,
            leaderNickname,
            email,
            ottDto.getCommissionMember(),
            ottDto.getFee());
    }

    public TransactionDto userCashWithdrawCancel(String leaderEmail, Long partyId, Long ottId,
        String email) {

        OttDto ottDto = ottService.searchOtt(ottId);

        return transactionService.userCashWithdrawCancel(
            partyId,
            leaderEmail,
            email,
            ottDto.getCommissionMember(),
            ottDto.getFee());

    }

//    public TransactionDto test() {
//
//        List<Party> partyList = partyRepository.findByPayDt(LocalDate.now());
//        for (Party party : partyList) {
//            // 결제 진행부분 삽입
//
//            OttDto ottDto = ottService.searchOtt(party.getOttId());
//
//            transactionService.userCashDeposit(
//                party.getMembers(),
//                party.getLeaderNickname(),
//                party.getId(),
//                ottDto.getCommissionLeader(),
//                ottDto.getFee());
//        }
//
//        return null;
//    }
}
