package com.watchtogether.server.party.service.impl;

import static com.watchtogether.server.exception.type.UserErrorCode.NOT_FOUND_USER;
import static com.watchtogether.server.party.domain.type.AlertType.CHANGE_PASSWORD;

import com.watchtogether.server.exception.PartyException;
import com.watchtogether.server.exception.UserException;
import com.watchtogether.server.exception.type.PartyErrorCode;
import com.watchtogether.server.ott.service.OttService;
import com.watchtogether.server.party.domain.entitiy.InviteParty;
import com.watchtogether.server.party.domain.entitiy.Party;
import com.watchtogether.server.party.domain.entitiy.PartyMember;
import com.watchtogether.server.party.domain.model.*;
import com.watchtogether.server.party.domain.repository.InvitePartyRepository;
import com.watchtogether.server.party.domain.repository.PartyMemberRepository;
import com.watchtogether.server.party.domain.repository.PartyRepository;
import com.watchtogether.server.party.domain.type.AlertType;
import com.watchtogether.server.party.service.PartyService;
import com.watchtogether.server.users.domain.entitiy.User;
import com.watchtogether.server.users.domain.repository.UserRepository;
import com.watchtogether.server.users.service.TransactionService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PartyServiceImpl implements PartyService {

    private final PartyRepository partyRepository;
    private final InvitePartyRepository invitePartyRepository;
    private final PartyMemberRepository partyMemberRepository;
    private final UserRepository userRepository;

    private final TransactionService transactionService;
    private final OttService ottService;
    private final EntityManagerFactory emf;

    // 파티장이 파티 생성 클릭
    @Override
    public Party createParty(CreatePartyForm form) {
        LocalDateTime limitDt = LocalDateTime.now().plusDays(1);
        Party party;
        if (form.getOttId() == null){
            throw new PartyException(PartyErrorCode.NOT_FOUND_OTT_ID);
        }
        if (form.getReceiversNickName() != null) {
            party = Party.from(form);
            buildLeaderForm(form, limitDt, party);

            String[] receiverNickName = form.getReceiversNickName().split(",");
            for (String s : receiverNickName) {
                InvitePartyForm invitePartyForm = InvitePartyForm.builder()
                        .nickname(s)
                        .party(party)
                        .limitDt(limitDt)
                        .build();
                invitePartyRepository.save(InviteParty.from(invitePartyForm));
            }
        } else {

            party = Party.fromNicknameIsNull(form);
            buildLeaderForm(form, limitDt, party);
        }
        return partyRepository.save(party);
    }

    @Override
    public List<SendAlertForm> sendInviteAlert(Party party, String leader) {
        List<SendAlertForm> inviteAlertList = new ArrayList<>();
        List<InviteParty> invitePartyList = invitePartyRepository.findByPartyAndLeaderIsFalse(party);
        for (int i = 0; i < invitePartyList.size(); i++) {
            SendAlertForm sendAlertForm = SendAlertForm.builder()
                    .nickName(invitePartyList.get(i).getReceiverNickName())
                    .uuid(invitePartyList.get(i).getReceiverUUID())
                    .partyId(party.getId())
                    .sender(leader)
                    .ottId(party.getOttId())
                    .alertType(AlertType.INVITE)
                    .build();
            inviteAlertList.add(sendAlertForm);
        }
        return inviteAlertList;
    }

    @Override
    public ResponseEntity<Object> checkInviteMessage(CheckInviteMessageForm form) {
        Optional<Party> party = partyRepository.findById(form.getPartyId());
        if (party.isEmpty()) {
            throw new PartyException(PartyErrorCode.NOT_FOUND_PARTY);
        } else {
            Optional<PartyMember> partyMember = partyMemberRepository.findByNickNameAndParty(form.getNickname(), party.get());
            if (partyMember.isPresent()) {
                partyMember.get().setAlertCheck(true);
                partyMemberRepository.save(partyMember.get());
            } else {
                throw new PartyException(PartyErrorCode.NOT_FOUND_USER);
            }
        }
        return ResponseEntity.ok().build();
    }

    @Override
    public List<SendAlertForm> changePassword(ChangePasswordForm form) {

        Optional<Party> optionalParty = partyRepository.findById(form.getPartyId());

        if (optionalParty.isPresent()) {
            Party party = optionalParty.get();
            if (Objects.equals(party.getLeaderNickname(), form.getNickname())) {
                if (Objects.equals(party.getPartyOttPassword(), form.getPassword())) {
                    if (party.getPartyOttPassword().equals(form.getNewPassword())) {
                        throw new PartyException(PartyErrorCode.SAME_PASSWORD_OTT);
                    }
                    List<SendAlertForm> sendAlertFormList = new ArrayList<>();
                    party.setPartyOttPassword(form.getNewPassword());
                    partyRepository.save(party);
                    for (int i = 0; i < party.getMembers().size(); i++) {
                        SendAlertForm sendAlertForm = SendAlertForm.builder()
                                .alertType(CHANGE_PASSWORD)
                                .sender(party.getLeaderNickname())
                                .partyId(party.getId())
                                .ottId(party.getOttId())
                                .nickName(party.getMembers().get(i).getNickName())
                                .build();
                        sendAlertFormList.add(sendAlertForm);
                    }

                    return sendAlertFormList;
                } else {
                    throw new PartyException(PartyErrorCode.WRONG_PASSWORD_OTT);
                }
            } else {
                throw new PartyException(PartyErrorCode.NOT_LEADER);
            }

        } else {
            throw new PartyException(PartyErrorCode.NOT_FOUND_PARTY);
        }

        // todo 이후 메시지 전송api호출필요
    }


    public List<SendAlertForm> createPartyAndSendInviteAlert(CreatePartyForm form) {
        List<SendAlertForm> list = new ArrayList<>();
        if (form.getReceiversNickName() != null) {
            list = sendInviteAlert(createParty(form), form.getLeaderNickName());
        } else {
            createParty(form);
        }
        return list;
    }


    private void buildLeaderForm(CreatePartyForm form, LocalDateTime limitDt, Party party) {
        InvitePartyForm leaderForm = InvitePartyForm.builder()
                .nickname(form.getLeaderNickName())
                .party(party)
                .limitDt(LocalDateTime.now())
                .build();
        invitePartyRepository.save(InviteParty.leaderFrom(leaderForm));
    }


    @Override
    public ResponseEntity<Object> joinParty(JoinPartyForm form) {
        Optional<Party> optionalParty = partyRepository.findById(form.getPartyId());
        if (optionalParty.isPresent()) {
            if (invitePartyRepository.findByReceiverNickNameAndParty(form.getNickName(), optionalParty.get()).isPresent()
                    || partyMemberRepository.findByNickNameAndParty(form.getNickName(), optionalParty.get()).isPresent()) {
                throw new PartyException(PartyErrorCode.ALREADY_JOIN_PARTY);
            }
            if (optionalParty.get().isPartyFull()) {
                throw new PartyException(PartyErrorCode.PARTY_IS_FULL);
            }
            Party party = optionalParty.get();
            InvitePartyForm invitePartyForm = InvitePartyForm.builder()
                    .party(optionalParty.get())
                    .nickname(form.getNickName())
                    .build();
            invitePartyRepository.save(InviteParty.joinPartyFrom(invitePartyForm));
            party.setPeople(party.getPeople() + 1);
            return ResponseEntity.ok(partyRepository.save(party));
        }

        throw new PartyException(PartyErrorCode.NOT_FOUND_PARTY);

    }

    @Override
    public List<Party> showPartyList() {
        LocalDateTime now = LocalDateTime.now();
        List<Party> partyList;
        partyList = partyRepository.findByPartyFullIsFalseAndInvisibleDtBefore(now);
        return partyList;
    }

    @Override
    public ResponseEntity<Object> leaveParty(LeavePartyForm form) {
        Party party = partyRepository.findById(form.getPartyId())
                .orElseThrow(() -> new PartyException(PartyErrorCode.NOT_FOUND_PARTY));

        InviteParty inviteParty = invitePartyRepository.
                findByReceiverNickNameAndPartyAndAcceptIsTrue(form.getNickName(), party)
                .orElseThrow(() -> new PartyException(PartyErrorCode.NOT_FOUND_USER_IN_INVITE_PARTY));
        // TODO: 2022-11-08 리더일때 경우 추가 필요

        invitePartyRepository.delete(inviteParty);


        party.setPartyFull(false);
        party.setPeople(party.getPeople() - 1);
        partyRepository.save(party);

        return ResponseEntity.ok().build();
    }

    public LeavePartyResponseForm IgnoreContinueMessage(LeavePartyForm form) {
        Party party = partyRepository.findById(form.getPartyId())
                .orElseThrow(() -> new PartyException(PartyErrorCode.NOT_FOUND_PARTY));

        PartyMember partyMember = partyMemberRepository.findByNickNameAndParty(form.getNickName(), party)
                .orElseThrow(() -> new PartyException(PartyErrorCode.NOT_FOUND_USER_IN_PARTY_MEMBER));
        partyMemberRepository.delete(partyMember);

        party.setPartyFull(false);
        party.setPeople(party.getPeople() - 1);
        partyRepository.save(party);

        if (!partyMember.isLeader()) {
            partyMemberRepository.delete(partyMember);
            return LeavePartyResponseForm.builder()
                    .partyId(party.getId())
                    .nickname(form.getNickName())
                    .leader(false)
                    .build();
        } else {
            return LeavePartyResponseForm.builder()
                    .partyId(party.getId())
                    .nickname(form.getNickName())
                    .leader(true)
                    .build();
        }

    }

    @Override
    public Party addMember(AcceptPartyForm form) {
        InviteParty inviteParty = findUser(form);
        //validateAndFindPartyWithPartyIdBeforeJoin(inviteParty.getParty().getId(), form.getNick());
        Optional<Party> optionalParty = partyRepository.findById(inviteParty.getParty().getId());
        if (optionalParty.isPresent()) {
            inviteParty.setAccept(true);
            Party party = optionalParty.get();
            if (party.getPeople() < 4) {
                party.setPeople(party.getPeople() + 1);
                return partyRepository.save(party);
            } else {
                throw new PartyException(PartyErrorCode.PARTY_IS_FULL);
            }


        }
        throw new PartyException(PartyErrorCode.NOT_FOUND_PARTY);

    }


    public TransactionForm checkPartyFull(Long partyId) {
        // 리더 아이디, 다른멤버 아이디
        Optional<Party> optionalParty = partyRepository.findById(partyId);

        if (optionalParty.isPresent()) {
            Party party = optionalParty.get();
            if (party.getPeople() == 4) {
                party.setPartyFull(true);
                party.setPayDt(LocalDate.now().plusMonths(1));
                partyRepository.save(party);
                List<InviteParty> list = invitePartyRepository.findByParty(party);
                savePartyMember(party.getId());
                invitePartyRepository.deleteAll(list);

                return TransactionForm.builder()
                        .party(party)
                        .build();
            } else {
                return null;
            }
        }
        throw new PartyException(PartyErrorCode.NOT_FOUND_PARTY);

    }

    @Override
    public ResponseEntity<Object> savePartyMember(Long partyId) {
        List<Object[]> list = findAddPartyMember(partyId);
        LocalDateTime limitDt = LocalDateTime.now().plusDays(1);
        for (Object[] object : list) {
            InvitePartyForm invitePartyForm = InvitePartyForm.builder()
                    .nickname((String) object[0])
                    .leader((Boolean) object[1])
                    .party((Party) object[2])
                    .limitDt(limitDt)
                    .build();
            partyMemberRepository.save(PartyMember.from(invitePartyForm));
        }
        return ResponseEntity.ok().build();
    }

    @Override
    public List<Object[]> findAddPartyMember(Long partyId) {
        EntityManager entityManager = emf.createEntityManager();

        Query query = entityManager
                .createQuery(" select i.receiverNickName,i.leader ,i.party " +
                        " from InviteParty i " +
                        " where i.accept = true and i.party.id=:partyId ");
        query.setParameter("partyId", partyId);
        return query.getResultList();
    }

    @Override
    public InviteParty findUser(AcceptPartyForm form) {
        InviteParty inviteParty = invitePartyRepository.findByReceiverNickNameAndReceiverUUID(form.getNick(), form.getUuid())
                .orElseThrow(() -> new PartyException(PartyErrorCode.NOT_FOUND_USER));
        if (inviteParty.getLimitDt().isBefore(LocalDateTime.now())) {
            throw new PartyException(PartyErrorCode.EXPIRE_CODE);
        } else if (!inviteParty.getReceiverUUID().equals(form.getUuid())) {
            throw new PartyException(PartyErrorCode.WRONG_VERIFICATION);
        }
        return inviteParty;
    }

    @Override
    public List<String> myPartyMembers(List<Optional<Party>> list) {
        if (!list.isEmpty()) {
            for (int i = 0; i < list.size(); i++) {
                list.get(i).get().getMembers().get(i).getNickName();
            }
        }
        throw new PartyException(PartyErrorCode.NOT_FOUND_PARTY);
    }

    @Override
    public List<List<Optional<Party>>> findMyParties(FindMyPartiesForm form) {
        List<List<Optional<Party>>> myPartyList = new ArrayList<>();
        List<Optional<Party>> myPartyList1 = new ArrayList<>();
        List<Optional<Party>> myPartyList2 = new ArrayList<>();


        List<PartyMember> myPartyListId = partyMemberRepository.findByNickName(form.getNickName());
        if (!myPartyListId.isEmpty()) {
            for (PartyMember partyMember : myPartyListId) {
                Optional<Party> party = partyRepository.findById(partyMember.getParty().getId());
                myPartyList1.add(party);
            }
            myPartyList.add(0,myPartyList1);
        }else {
            myPartyList.add(0, null);
        }
        List<InviteParty> myInvitePartyListId = invitePartyRepository.findByReceiverNickName(form.getNickName());
        if (!myInvitePartyListId.isEmpty()) {
            for (InviteParty inviteParty : myInvitePartyListId) {
                Optional<Party> party = partyRepository.findById(inviteParty.getParty().getId());
                myPartyList2.add(party);
            }
            myPartyList.add(1,myPartyList2);
        }else {
            myPartyList.add(1, null);
        }
        return myPartyList;
    }

    // todo 파티 탈퇴 전 자신이 속한 파티 존재 여부 확인
    @Override
    public void findMyPartiesBeforeDeleteUser(String nickName) {

        List<PartyMember> myPartyListId = partyMemberRepository.findByNickName(nickName);
        if (!myPartyListId.isEmpty()) {
            throw new PartyException(PartyErrorCode.FOUND_USER_BEFORE_DELETE);
        }
    }

    // todo 파티 참가 전 유효성 검사
    @Override
    public Party validateAndFindPartyWithPartyIdBeforeJoin(Long partyId, String nickname) {

        Party party = partyRepository.findById(partyId)
                .orElseThrow(() -> new PartyException(PartyErrorCode.NOT_FOUND_PARTY));


        Optional<InviteParty> optionalInviteParty =
                invitePartyRepository.findByReceiverNickNameAndPartyAndAcceptIsFalse(nickname, party);
        if (optionalInviteParty.isPresent()) {
            System.out.println((optionalInviteParty.get().getCreatedDt().plusDays(1)));
            System.out.println( LocalDateTime.now());
            if (optionalInviteParty.get().getCreatedDt().plusDays(1).isBefore(
                    LocalDateTime.now())) {
                invitePartyRepository.delete(optionalInviteParty.get());
                return party;
            }
        }



        if (invitePartyRepository.findByReceiverNickNameAndParty(nickname, party).isPresent()
                || partyMemberRepository.findByNickNameAndParty(nickname, party).isPresent()) {
            throw new PartyException(PartyErrorCode.ALREADY_JOIN_PARTY);
        }
        return party;
    }

    // todo 파티 탈퇴 전 유효성 검사
    @Override
    public Party validateAndFindPartyWithPartyIdBeforeLeave(Long partyId, String nickname) {

        Party party = partyRepository.findById(partyId)
                .orElseThrow(() -> new PartyException(PartyErrorCode.NOT_FOUND_PARTY));

        if (invitePartyRepository.findByReceiverNickNameAndParty(nickname, party).isEmpty()) {
            throw new PartyException(PartyErrorCode.NOT_JOIN_PARTY);
        }

        return party;
    }

    @Override
    public ResponseEntity<Object> deleteParty(Long partyId) {
        Party party = partyRepository.findById(partyId)
                .orElseThrow(() -> new PartyException(PartyErrorCode.NOT_FOUND_PARTY));
        partyRepository.delete(party);
        return ResponseEntity.ok().build();
    }
    public ResponseEntity<Object> deleteInviteParty(Party party){
        List<InviteParty> list = invitePartyRepository.findByParty(party);
        if (!list.isEmpty()){
            invitePartyRepository.deleteAll(list);
        }else {
            throw new PartyException(PartyErrorCode.NOT_FOUND_PARTY_IN_INVITE_PARTY);
        }
        return ResponseEntity.ok().build();
    }
    public ResponseEntity<Object> deletePartyMember(Party party){
        List<PartyMember> list = partyMemberRepository.findByParty(party);
        if (!list.isEmpty()){
            partyMemberRepository.deleteAll(list);
        }else {
            throw new PartyException(PartyErrorCode.NOT_FOUND_PARTY_IN_PARTY_MEMBER);
        }
        return ResponseEntity.ok().build();
    }
    public ResponseEntity<Object> deleteInviteParty(LocalDateTime now){
        List<InviteParty> list = invitePartyRepository.findByAcceptIsFalseAndLimitDtAfter(now);
        if (!list.isEmpty()){
            invitePartyRepository.deleteAll(list);
        }else {
            log.info("삭제할 데이터를 찾지 못했습니다");

        }
        return ResponseEntity.ok().build();
    }


    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(NOT_FOUND_USER));
        return user;
    }
}
