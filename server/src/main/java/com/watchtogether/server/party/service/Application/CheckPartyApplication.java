package com.watchtogether.server.party.service.Application;


import com.watchtogether.server.alert.service.AlertService;
import com.watchtogether.server.exception.PartyException;
import com.watchtogether.server.exception.type.PartyErrorCode;
import com.watchtogether.server.ott.domain.dto.OttDto;
import com.watchtogether.server.ott.service.OttService;
import com.watchtogether.server.party.domain.entitiy.Party;
import com.watchtogether.server.party.domain.entitiy.PartyMember;
import com.watchtogether.server.party.domain.model.LeavePartyForm;
import com.watchtogether.server.party.domain.model.LeavePartyResponseForm;
import com.watchtogether.server.party.domain.repository.PartyMemberRepository;
import com.watchtogether.server.party.domain.repository.PartyRepository;
import com.watchtogether.server.party.domain.type.AlertType;
import com.watchtogether.server.party.service.impl.PartyServiceImpl;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.watchtogether.server.users.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.watchtogether.server.party.domain.type.AlertType.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class CheckPartyApplication {
        private final PartyRepository partyRepository;
        private final PartyMemberRepository partyMemberRepository;
        private final PartyServiceImpl partyService;

        private final OttService ottService;
        private final TransactionService transactionService;

        private final AlertService alertService;



    @Transactional
    @Scheduled(cron = "0 0 1 * * *")
    public void autoCheckParty() {
        checkParty();
        checkSendMessage();
        checkLeave();
    }

    public void selfCheckParty() {
        checkParty();
    }


    private void checkParty() {
        List<Party> partyList = new ArrayList<>();
        partyList = partyRepository.findByPayDt(LocalDate.now());
        if (partyList.isEmpty()) {
            log.info("오늘 결제할 파티가 존재하지 않습니다.");
            return;

        } else {
            for (Party party : partyList) {
                OttDto ottDto = ottService.searchOtt(party.getOttId());

                transactionService.userCashDeposit(
                    party.getMembers(),
                    party.getLeaderNickname(),
                    party.getId(),
                    ottDto.getCommissionLeader(),
                    ottDto.getFee());


                party.setPayDt(party.getPayDt().plusMonths(1));
                partyRepository.save(party);
                log.info("오늘 결제한 파티는" + party.getId() + "번 " + party.getTitle() + "입니다");

                // 결제 진행되었다고 메시지 전송
                sendAlert(party, TRANSACTION);



            }
        }
    }

    private void checkSendMessage() {
        // 1주일전 확인 메시지 발송
        List<Party> partyList = new ArrayList<>();
        partyList = partyRepository.findByPayDt(LocalDate.now().minusWeeks(1));
        if (partyList.isEmpty()) {
            log.info("오늘 확인 메시지를 전송할 파티가 존재하지 않습니다.");
            return;
        } else {
            for (Party party : partyList) {
                // 있다면 partyMember의 check를 false로 바꿔준다.
                for (int i = 0; i < party.getMembers().size(); i++) {
                    String nickname = party.getMembers().get(i).getNickName();
                    Optional<PartyMember> partyMember = partyMemberRepository.findByNickNameAndParty(nickname, party);
                    if (partyMember.isEmpty()) {
                        throw new PartyException(PartyErrorCode.NOT_FOUND_USER);
                    }else {
                        partyMember.get().setAlertCheck(false);
                        partyMemberRepository.save(partyMember.get());
                        // 파티 갱신할껀지 말지 메시지 전송
                        sendAlert(party, CONTINUE);
                    }
                }
            }
        }
    }
    private void checkLeave(){
        List<Party> partyList = new ArrayList<>();
        partyList = partyRepository.findByPayDt(LocalDate.now().minusDays(4));
        if (partyList.isEmpty()) {
            log.info("오늘 파티를 나갈 인원이 존재하지 않습니다.");
            return;
    }else {
            for (Party party : partyList) {
                for (int i = 0; i < party.getMembers().size(); i++) {
                    String nickname = party.getMembers().get(i).getNickName();
                    PartyMember partyMember = partyMemberRepository.findByNickNameAndParty(nickname, party)
                            .orElseThrow(()-> new PartyException(PartyErrorCode.NOT_FOUND_USER));
                        if (!partyMember.isAlertCheck()){
                            LeavePartyForm leavePartyForm = LeavePartyForm.builder()
                                    .partyId(party.getId())
                                    .nickName(nickname)
                                    .build();
                            LeavePartyResponseForm form = partyService.IgnoreContinueMessage(leavePartyForm);
                            if (form.isLeader()){
                                // TODO: 2022-11-08 파티장이 탈퇴하여 삭제된다는 메세지 발송 및 파티 삭제
                                sendAlert(party, LEAVE_LEADER);
                                partyService.deleteParty(party.getId());
                            }else {
                                sendAlert(party, LEAVE_MEMBER);
                            }
                        }

                }
            }
        }
    }

    private void sendAlert(Party party, AlertType type) {
        List<PartyMember> partyMemberList = party.getMembers();
        for (int i = 0; i < party.getMembers().size(); i++) {
            List<String> nickname = new ArrayList<>();
            nickname.add(partyMemberList.get(i).getNickName());
            String message = "";
            switch (type){
                case INVITE: message= "초대메세지";
                    break;
                case LEAVE_LEADER:message= "리더가 떠났다는 메세지 및 파티 삭제 메세지";
                    break;
                case LEAVE_MEMBER:message= "파티원이 떠났다는 메세지";
                    break;
                case CHANGE_PASSWORD:message= " 비밀번호 변경 메세지";
                    break;
                case CONTINUE: message= "파티 갱신 메세지";
                    break;
                case TRANSACTION:message= "거래 발생 메세지";
                    break;

            }
            // todo 메시지 형식 작성되면 그 형식에 따라서 메시지 작성
            alertService.createAlert(nickname, party.getId(), null, message, type);

        }
    }
    // TODO: 2022-11-08 파티객체 삭제 메서드 구현 필요!! (파티원을 다 채우지 못한경우)

}
