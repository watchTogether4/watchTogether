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
            log.info("?????? ??????????????? ???????????? ????????? ???????????? ????????????.");
            return ResponseEntity.ok().build();
        } else {
            // ?????? ??????????????? ??????????????? ??? ?????? ????????? ?????? ??????
            for (Party party : partyList) {
                if (!party.isPartyFull()) {
                    sendAlert(party, IS_NOT_FULL);
                    log.info("?????? ?????? ???????????? ????????? ???????????? ????????? Party Id : " + party.getId() + "???  ???????????? : " + party.getTitle() + "?????????");
                    partyService.deleteInviteParty(party);
                    partyService.deletePartyMember(party);
                    alertService.deleteAlert(party);
                    partyService.deleteParty(party.getId());
                } else {
                    log.info("?????? ???????????? ????????? ???????????? ??? ???????????? ???????????? ????????? ????????????!!");
                    log.info(party.getId().toString());
                    log.info(party.getMembers().size() + "");
                    // ??????
                    transaction(party);
                    // ?????? ?????????????????? ????????? ??????
                    sendAlert(party, TRANSACTION);
                    party.setPayDt(party.getPayDt().plusMonths(1));
                    partyRepository.save(party);
                    log.info("?????? ????????? ?????????" + party.getId() + "??? " + party.getTitle() + "?????????");
                }
            }
        }
        // TODO: 2022-11-08 ?????????????????? ????????? ??? ?????? ??????????????? ????????? ?????? ??? ?????? ??????
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Object> checkCreatedParty() {
        List<Party> partyList = new ArrayList<>();
        partyList = partyRepository.findByCreatedDtBefore(LocalDateTime.now().minusWeeks(1));
        if (partyList.isEmpty()) {
            log.info("?????? ?????? ??? ???????????? ????????? ????????????");
            return ResponseEntity.ok().build();
        } else {
            // ?????? ????????? 1???????????? ??????????????? ??? ?????? ????????? ?????? ??????
            for (Party party : partyList) {
                if (party.getMembers() == null || party.getMembers().isEmpty()) {
                    sendAlert(party, IS_NOT_FULL);
                    log.info("?????? ?????? ???????????? ????????? ???????????? ?????????" + party.getId() + "??? " + party.getTitle() + "?????????");
                    partyService.deleteInviteParty(party);
                    alertService.deleteAlert(party);
                    partyService.deleteParty(party.getId());
                } else {
                    log.info("?????? ???????????? ????????? ???????????? ??? ???????????? ???????????? ????????? ????????????!!");
                }

            }

        }
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Object> checkSendMessage() {
        // 1????????? ?????? ????????? ??????
        List<Party> partyList = new ArrayList<>();
        // 6 ~ 7 ???      11 - 11 - 00 - 00  ::::  11 - 4 - 00 - 00 ~ 11 - 5 - 00 - 00
        LocalDateTime checkDay = LocalDateTime.now().plusDays(6);
        LocalDateTime startCheckDay = LocalDateTime.of(checkDay.getYear(), checkDay.getMonthValue(), checkDay.getDayOfMonth(), 0, 0);
        LocalDateTime endCheckDay = startCheckDay.plusDays(1);

        partyList = partyRepository.findByPayDtBetween(startCheckDay, endCheckDay);
        if (partyList.isEmpty()) {
            log.info("?????? ?????? ???????????? ????????? ????????? ???????????? ????????????.");
            return ResponseEntity.ok().build();
        } else {
            for (Party party : partyList) {
                // ????????? partyMember??? check??? false??? ????????????.
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
                // ?????? ??????????????? ?????? ????????? ??????
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
            log.info("?????? ????????? ?????? ????????? ???????????? ????????????.");
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
                    // TODO: 2022-11-08 ???????????? ???????????? ??????????????? ????????? ?????? ??? ?????? ??????
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
                    message = "???????????????";
                    // sender ?????? invitenickname ?????? ott name????????? ?????????????????????.
                    alertService.createAlert(nickname, party.getId(), null, message, type);
                    break;
                case LEAVE_LEADER:
                    message = party.getTitle() + "??? ???????????? ????????? ????????? ???????????? ???????????????";
                    alertService.createAlert(nickname, party.getId(), null, message, type);
                    break;
                case LEAVE_MEMBER:
                    if (!partyMember.isAlertCheck()) {
                        continue;
                    }
                    if(partyMember.isLeader()){
                        message = party.getTitle()+"??? ???????????? ???????????????. ??????????????? ??????????????????";
                        alertService.createAlert(nickname, party.getId(), null, message, type);
                    }else {
                        message = party.getTitle()+"??? ???????????? ???????????????.";
                        alertService.createAlert(nickname, party.getId(), null, message, type);
                    }
                    break;
                case CHANGE_PASSWORD:
                    message = party.getTitle()+"??? ???????????? ??? ????????????????????? ??????????????????!";
                    alertService.createAlert(nickname, party.getId(), null, message, type);
                    break;
                case CONTINUE:
                    message = party.getTitle()+" ?????? ????????? 7???????????????. ?????? ?????????????????????????";
                    alertService.createAlert(nickname, party.getId(), null, message, type);
                    break;
                case TRANSACTION:
                    message =  party.getTitle()+" ????????? ????????? ??????????????????. ??????????????????";
                    alertService.createAlert(nickname, party.getId(), null, message, type);
                    break;
                case IS_NOT_FULL:
                    message = party.getTitle()+" ????????? ????????? ???????????? ????????? ???????????? ???????????????";
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
