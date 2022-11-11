package com.watchtogether.server.users.service.Application;

import com.watchtogether.server.ott.domain.dto.OttDto;
import com.watchtogether.server.ott.service.OttService;
import com.watchtogether.server.party.domain.entitiy.Party;
import com.watchtogether.server.party.service.PartyService;
import com.watchtogether.server.users.domain.dto.TransactionDto;
import com.watchtogether.server.users.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionApplicaion {

    private final TransactionService transactionService;
    private final OttService ottService;
    private final PartyService partyService;


    public TransactionDto userCashWithdraw(Long partyId, String nickname) {

        // party_id로 해당 Party 객체 정보 가져오기
        Party party = partyService.validateAndFindPartyWithPartyIdBeforeJoin(partyId, nickname);

        // party.getOttId로 해당 Ott 정보 가져오기
        OttDto ottDto = ottService.searchOtt(party.getOttId());

        return transactionService.userCashWithdraw(
            party.getId(),
            party.getLeaderNickname(),
            nickname,
            ottDto.getCommissionMember(),
            ottDto.getFee());
    }

    public TransactionDto userCashWithdrawCancel(Long partyId, String nickname) {

        // party_id로 해당 Party 객체 정보 가져오기
        Party party = partyService.validateAndFindPartyWithPartyIdBeforeLeave(partyId, nickname);

        // party.getOttId로 해당 Ott 정보 가져오기
        OttDto ottDto = ottService.searchOtt(party.getOttId());

        return transactionService.userCashWithdrawCancel(
            party.getId(),
            party.getLeaderNickname(),
            nickname,
            ottDto.getCommissionMember(),
            ottDto.getFee());

    }
}
