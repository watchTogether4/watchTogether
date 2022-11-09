package com.watchtogether.server.party.service.Application;


import com.watchtogether.server.alert.service.AlertService;
import com.watchtogether.server.exception.PartyException;
import com.watchtogether.server.exception.type.PartyErrorCode;
import com.watchtogether.server.ott.domain.dto.OttDto;
import com.watchtogether.server.ott.service.OttService;
import com.watchtogether.server.party.domain.entitiy.InviteParty;
import com.watchtogether.server.party.domain.entitiy.Party;
import com.watchtogether.server.party.domain.entitiy.PartyMember;
import com.watchtogether.server.party.domain.model.*;
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
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.watchtogether.server.party.domain.type.AlertType.*;
import static com.watchtogether.server.party.domain.type.AlertType.IS_NOT_FULL;

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
            // 파티 결제일까지 파티인원이 다 차지 않으면 파티 삭제
            for (Party party : partyList) {
                if (!party.isPartyFull()){
                    sendAlert(party, IS_NOT_FULL);
                    partyService.deleteParty(party.getId());
                }


                transaction(party);
                party.setPayDt(party.getPayDt().plusMonths(1));
                partyRepository.save(party);
                log.info("오늘 결제한 파티는" + party.getId() + "번 " + party.getTitle() + "입니다");

                // 결제 진행되었다고 메시지 전송
                sendAlert(party, TRANSACTION);
            }
        }
        // TODO: 2022-11-08 일정기간동안 파티가 다 차지 않았을경우 메세지 작성 및 파티 삭제

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
                case IS_NOT_FULL:message= "파티 인원 부족 메세지";
                    break;

            }
            // todo 메시지 형식 작성되면 그 형식에 따라서 메시지 작성
            alertService.createAlert(nickname, party.getId(), null, message, type);

        }
    }
    public ResponseEntity<Object> acceptParty(AcceptPartyForm form) {


        InviteParty inviteParty = partyService.findUser(form);
        TransactionForm transactionForm = TransactionForm.builder()
                .party(inviteParty.getParty())
                .nickname(form.getNick())
                .build();
        if (transactionForm != null){

            transaction1(transactionForm);
            sendAlert(transactionForm.getParty(), TRANSACTION);
        }
        partyService.addMember(form);
        partyService.checkPartyFull(inviteParty.getParty().getId());

        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Object> joinPartyAndCheckFull(JoinPartyForm form) {

        partyService.joinParty(form);
        TransactionForm transactionForm = partyService.checkPartyFull(form.getPartyId());
        if (transactionForm != null){

            transaction(transactionForm.getParty());
            sendAlert(transactionForm.getParty(), TRANSACTION);
        }
        return ResponseEntity.ok().build();
    }
    private void transaction(Party transactionForm) {
        OttDto ottDto = ottService.searchOtt(transactionForm.getOttId());
        transactionService.userCashDeposit(
                transactionForm.getMembers(),
                transactionForm.getLeaderNickname(),
                transactionForm.getId(),
                ottDto.getCommissionLeader(),
                ottDto.getFee());
    }
    private void transaction1(TransactionForm form) {
        OttDto ottDto = ottService.searchOtt(form.getParty().getOttId());
        transactionService.userCashWithdraw(
                form.getParty().getId(),
                form.getParty().getLeaderNickname(),
                form.getNickname(),
                ottDto.getCommissionLeader(),
                ottDto.getFee());
    }

}
