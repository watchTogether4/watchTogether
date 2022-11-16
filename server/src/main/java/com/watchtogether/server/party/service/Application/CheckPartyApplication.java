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
import com.watchtogether.server.users.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.asm.Advice;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.watchtogether.server.party.domain.type.AlertType.*;

@Service
@Slf4j
@Transactional

@RequiredArgsConstructor
public class CheckPartyApplication {
    private final PartyRepository partyRepository;
    private final PartyMemberRepository partyMemberRepository;
    private final PartyServiceImpl partyService;

    private final OttService ottService;
    private final TransactionService transactionService;

    private final AlertService alertService;


    @Transactional
    @Scheduled(cron = "0 0 0 * * *")
    public void autoCheckParty() {
        checkSendMessage();
        checkLeave();
        checkParty();
        checkCreatedParty();
        checkInviteParty();
    }

    public ResponseEntity<Object> checkParty() {
        List<Party> partyList = new ArrayList<>();
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime startToday = LocalDateTime.of(today.getYear(), today.getMonthValue(), today.getDayOfMonth(), 0, 0);
        LocalDateTime endToday = startToday.plusDays(1);
        partyList = partyRepository.findByPayDtBetween(startToday, endToday);
        if (partyList.isEmpty()) {
            log.info("오늘 결제하거나 해체되는 파티가 존재하지 않습니다.");
            return ResponseEntity.ok().build();
        } else {
            // 파티 결제일까지 파티인원이 다 차지 않으면 파티 삭제
            for (Party party : partyList) {
                if (!party.isPartyFull()) {
                    sendAlert(party, IS_NOT_FULL);
                    log.info("오늘 기존 활동중인 파티중 해체되는 파티는 Party Id : " + party.getId() + "번  파티제목 : " + party.getTitle() + "입니다");
                    partyService.deleteInviteParty(party);
                    partyService.deletePartyMember(party);
                    alertService.deleteAlert(party);
                    partyService.deleteParty(party.getId());
                } else {
                    log.info("기존 활동중인 파티중 파티원이 다 차지않아 해체되는 파티가 없습니다!!");
                    log.info(party.getId().toString());
                    log.info(party.getMembers().size() + "");
                    // 결제
                    transaction(party);
                    // 결제 진행되었다고 메시지 전송
                    sendAlert(party, TRANSACTION);
                    party.setPayDt(party.getPayDt().plusMonths(1));
                    partyRepository.save(party);
                    log.info("오늘 결제한 파티는" + party.getId() + "번 " + party.getTitle() + "입니다");
                }
            }
        }
        // TODO: 2022-11-08 일정기간동안 파티가 다 차지 않았을경우 메세지 작성 및 파티 삭제
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Object> checkCreatedParty() {
        List<Party> partyList = new ArrayList<>();
        partyList = partyRepository.findByCreatedDtBefore(LocalDateTime.now().minusWeeks(1));
        if (partyList.isEmpty()) {
            log.info("파티 생성 중 해체되는 파티가 없습니다");
            return ResponseEntity.ok().build();
        } else {
            // 파티 생성후 1주일까지 파티인원이 다 차지 않으면 파티 삭제
            for (Party party : partyList) {
                if (party.getMembers() == null || party.getMembers().isEmpty()) {
                    sendAlert(party, IS_NOT_FULL);
                    log.info("오늘 처음 생성하는 파티중 해체되는 파티는" + party.getId() + "번 " + party.getTitle() + "입니다");
                    partyService.deleteInviteParty(party);
                    alertService.deleteAlert(party);
                    partyService.deleteParty(party.getId());
                } else {
                    log.info("처음 생성하는 파티중 파티원이 다 차지않아 해체되는 파티가 없습니다!!");
                }

            }

        }
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Object> checkSendMessage() {
        // 1주일전 확인 메시지 발송
        List<Party> partyList = new ArrayList<>();
        // 6 ~ 7 전      11 - 11 - 00 - 00  ::::  11 - 4 - 00 - 00 ~ 11 - 5 - 00 - 00
        LocalDateTime checkDay = LocalDateTime.now().plusDays(6);
        LocalDateTime startCheckDay = LocalDateTime.of(checkDay.getYear(), checkDay.getMonthValue(), checkDay.getDayOfMonth(), 0, 0);
        LocalDateTime endCheckDay = startCheckDay.plusDays(1);

        partyList = partyRepository.findByPayDtBetween(startCheckDay, endCheckDay);
        if (partyList.isEmpty()) {
            log.info("오늘 확인 메시지를 전송할 파티가 존재하지 않습니다.");
            return ResponseEntity.ok().build();
        } else {
            for (Party party : partyList) {
                // 있다면 partyMember의 check를 false로 바꿔준다.
                for (int i = 0; i < party.getMembers().size(); i++) {
                    String nickname = party.getMembers().get(i).getNickName();
                    Optional<PartyMember> partyMember = partyMemberRepository.findByNickNameAndParty(nickname, party);
                    if (partyMember.isEmpty()) {
                        throw new PartyException(PartyErrorCode.NOT_FOUND_USER);
                    } else {
                        partyMember.get().setAlertCheck(false);
                        partyMemberRepository.save(partyMember.get());


                    }
                }
                // 파티 갱신할껀지 말지 메시지 전송
                sendAlert(party, CONTINUE);
            }
        }
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Object> checkLeave() {
        List<Party> partyList = new ArrayList<>();
        boolean isLeader = false;
        boolean checked = false;
        LocalDateTime checkDay = LocalDateTime.now().plusDays(3);
        LocalDateTime startCheckDay = LocalDateTime.of(checkDay.getYear(), checkDay.getMonthValue(), checkDay.getDayOfMonth(), 0, 0);
        LocalDateTime endCheckDay = startCheckDay.plusDays(1);
        partyList = partyRepository.findByPayDtBetween(startCheckDay, endCheckDay);

        if (partyList.isEmpty()) {
            log.info("오늘 파티를 나갈 인원이 존재하지 않습니다.");
            return ResponseEntity.ok().build();
        } else {
            for (Party party : partyList) {
                checked = false;
                for (int i = 0; i < party.getMembers().size(); i++) {
                    String nickname = party.getMembers().get(i).getNickName();
                    PartyMember partyMember = partyMemberRepository.findByNickNameAndParty(nickname, party)
                            .orElseThrow(() -> new PartyException(PartyErrorCode.NOT_FOUND_USER));
                    if (!partyMember.isAlertCheck()) {
                        LeavePartyForm leavePartyForm = LeavePartyForm.builder()
                                .partyId(party.getId())
                                .nickName(nickname)
                                .build();
                        LeavePartyResponseForm form = partyService.IgnoreContinueMessage(leavePartyForm);
                        checked = true;
                        if (form.isLeader()) {
                            isLeader = true;
                        }
                    }
                }
                if (!checked) {
                    break;
                }
                if (isLeader) {
                    // TODO: 2022-11-08 파티장이 탈퇴하여 삭제된다는 메세지 발송 및 파티 삭제
                    sendAlert(party, LEAVE_LEADER);
                    partyService.deleteInviteParty(party);
                    partyService.deletePartyMember(party);
                    alertService.deleteAlert(party);
                    partyService.deleteParty(party.getId());
                } else {
                    sendAlert(party, LEAVE_MEMBER);
                }
            }
        }
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Object> checkInviteParty() {
        partyService.deleteInviteParty(LocalDateTime.now());
        return ResponseEntity.ok().build();
    }


    private void sendAlert(Party party, AlertType type) {
        List<PartyMember> partyMemberList = party.getMembers();
        for (int i = 0; i < party.getMembers().size(); i++) {
            List<String> nickname = new ArrayList<>();
            nickname.add(partyMemberList.get(i).getNickName());
            String message = "";
            PartyMember partyMember = partyMemberList.get(i);
            switch (type) {
                case INVITE:
                    message = "초대메세지";
                    // sender 님이 invitenickname 님을 ott name파티에 초대하셨습니다.
                    alertService.createAlert(nickname, party.getId(), null, message, type);
                    break;
                case LEAVE_LEADER:
                    message = party.getTitle() + "의 파티장이 나가서 파티가 자동으로 해체됩니다";
                    alertService.createAlert(nickname, party.getId(), null, message, type);
                    break;
                case LEAVE_MEMBER:
                    if (!partyMember.isAlertCheck()) {
                        continue;
                    }
                    if(partyMember.isLeader()){
                        message = party.getTitle()+"의 파티원이 나갔습니다. 비밀번호를 변경해주세요";
                        alertService.createAlert(nickname, party.getId(), null, message, type);
                    }else {
                        message = party.getTitle()+"의 파티원이 나갔습니다.";
                        alertService.createAlert(nickname, party.getId(), null, message, type);
                    }
                    break;
                case CHANGE_PASSWORD:
                    message = party.getTitle()+"의 비밀번호 가 변경되었습니다 확인해주세요!";
                    alertService.createAlert(nickname, party.getId(), null, message, type);
                    break;
                case CONTINUE:
                    message = party.getTitle()+" 파티 결제일 7일전입니다. 계속 이용하시겠습니까?";
                    alertService.createAlert(nickname, party.getId(), null, message, type);
                    break;
                case TRANSACTION:
                    message =  party.getTitle()+" 파티의 거래가 발생했습니다. 확인해주세요";
                    alertService.createAlert(nickname, party.getId(), null, message, type);
                    break;
                case IS_NOT_FULL:
                    message = party.getTitle()+" 파티의 인원이 부족하여 파티가 자동으로 해체됩니다";
                    alertService.createAlert(nickname, party.getId(), null, message, type);
                    break;

            }

        }
    }

    public ResponseEntity<Object> acceptParty(AcceptPartyForm form) {


        InviteParty inviteParty = partyService.findUser(form);
        TransactionForm transactionForm = TransactionForm.builder()
                .party(inviteParty.getParty())
                .nickname(form.getNick())
                .build();
        if (transactionForm != null) {

            transaction1(transactionForm);
            sendAlert(transactionForm.getParty(), TRANSACTION);
        }
        partyService.addMember(form);
        partyService.checkPartyFull(inviteParty.getParty().getId());

        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Object> joinPartyAndCheckFull(JoinPartyForm form) {
        transactionService.checkTransaction(form.getPartyId(), form.getNickName());
        partyService.joinParty(form);

        TransactionForm transactionForm = partyService.checkPartyFull(form.getPartyId());
        if (transactionForm != null) {

            transaction(transactionForm.getParty());
            sendAlert(transactionForm.getParty(), TRANSACTION);
        }
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Object> checkContinueMessage(CheckContinueMessageForm form) {
        Party party = partyRepository.findById(form.getPartyId()).orElseThrow(() -> new PartyException(PartyErrorCode.NOT_FOUND_PARTY));
        TransactionForm transactionForm = TransactionForm.builder()
                .party(party)
                .nickname(form.getNickname())
                .build();
        transaction1(transactionForm);

        PartyMember partyMember = partyMemberRepository.findByNickNameAndParty(form.getNickname(), party)
                .orElseThrow(() -> new PartyException(PartyErrorCode.NOT_FOUND_USER));

        partyMember.setAlertCheck(true);
        partyMemberRepository.save(partyMember);


        return ResponseEntity.ok().build();
    }


    public void transaction(Party transactionForm) {
        OttDto ottDto = ottService.searchOtt(transactionForm.getOttId());
        transactionService.userCashDeposit(
                transactionForm.getMembers(),
                transactionForm.getLeaderNickname(),
                transactionForm.getId(),
                ottDto.getCommissionLeader(),
                ottDto.getFee());
    }

    public void transaction1(TransactionForm form) {
        OttDto ottDto = ottService.searchOtt(form.getParty().getOttId());
        transactionService.userCashWithdraw(
                form.getParty().getId(),
                form.getParty().getLeaderNickname(),
                form.getNickname(),
                ottDto.getCommissionMember(),
                ottDto.getFee());
    }

}
