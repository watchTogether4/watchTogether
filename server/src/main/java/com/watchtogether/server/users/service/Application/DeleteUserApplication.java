package com.watchtogether.server.users.service.Application;

import com.watchtogether.server.alert.service.AlertService;
import com.watchtogether.server.party.domain.entitiy.Party;
import com.watchtogether.server.party.service.PartyService;
import com.watchtogether.server.users.domain.dto.UserDto;
import com.watchtogether.server.users.service.TransactionService;
import com.watchtogether.server.users.service.UserService;
import java.util.List;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteUserApplication {

    private final UserService userService;
    private final TransactionService transactionService;
    private final PartyService partyService;
    private final AlertService alertService;

    @Transactional
    public void deleteUser(String email, String password) {

        // 회원 아이디, 비밀번호
        UserDto user = userService.checkUser(email, password);

        // 자신이 속한 파티 그룹 검사
        partyService.findMyPartiesBeforeDeleteUser(user.getNickname());

        // 파티장인지 유효성 검사
        List<Party> parties = partyService.checkLeaderOrMemberBeforeUserLeave(user.getNickname());

        if (parties.isEmpty()) {
            // 파티원인 경우
            // notification 내역 삭제
            alertService.deleteAlertMemberBeforeUserLeave(user.getEmail());

            // invite_party 내역 삭제
            partyService.deleteInvitePartyMemberBeforeUserLeave(user.getNickname());
        } else {
            // 파티장인 경우
            for (Party party : parties) {
                // 해당 파티 모든 notification 내역 삭제
                alertService.deleteAlertLeaderBeforeUserLeave(party);

                // 해당 파티 모든 invite_party 내역 삭제
                partyService.deleteInvitePartyLeaderBeforeUserLeave(party);

                // 해당 파티 삭제
                partyService.deletePartyBeforeUserLeave(party);
            }
        }

        // 사용자 거래 내역 삭제
        transactionService.deleteTransaction(user.getEmail());

        // 사용자 파티 회원 탈퇴
        userService.deleteUser(user.getEmail());

    }
}
