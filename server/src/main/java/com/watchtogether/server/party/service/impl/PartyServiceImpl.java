package com.watchtogether.server.party.service.impl;

import com.watchtogether.server.exception.PartyException;
import com.watchtogether.server.exception.UserException;
import com.watchtogether.server.exception.type.PartyErrorCode;
import com.watchtogether.server.party.domain.entitiy.InviteParty;
import com.watchtogether.server.party.domain.entitiy.Party;
import com.watchtogether.server.party.domain.entitiy.PartyMember;
import com.watchtogether.server.party.domain.model.*;
import com.watchtogether.server.party.domain.repository.InvitePartyRepository;
import com.watchtogether.server.party.domain.repository.PartyMemberRepository;
import com.watchtogether.server.party.domain.repository.PartyRepository;
import com.watchtogether.server.party.service.PartyService;
import com.watchtogether.server.users.domain.entitiy.User;
import com.watchtogether.server.users.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import static com.watchtogether.server.exception.type.UserErrorCode.NOT_FOUND_USER;

@Service
@RequiredArgsConstructor
public class PartyServiceImpl implements PartyService {

    private final PartyRepository partyRepository;
    private final InvitePartyRepository invitePartyRepository;
    private final PartyMemberRepository partyMemberRepository;
    private final UserRepository userRepository;
    private final EntityManagerFactory emf;
    private final EntityManager em;

    // 파티장이 파티 생성 클릭
    @Override
    public Party createParty(CreatePartyForm form) {
        LocalDateTime limitDt = LocalDateTime.now().plusDays(1);


        Party party;
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
        }else {
            party = Party.fromNicknameIsNull(form);

            buildLeaderForm(form, limitDt, party);
        }
        return partyRepository.save(party);

    }

    private void buildLeaderForm(CreatePartyForm form, LocalDateTime limitDt, Party party) {
        InvitePartyForm leaderForm = InvitePartyForm.builder()
                .nickname(form.getLeaderNickName())
                .party(party)
                .limitDt(limitDt)
                .build();
        invitePartyRepository.save(InviteParty.leaderFrom(leaderForm));
    }

    //파티 초대링크 눌렀을때
    public ResponseEntity<Object> acceptParty(AcceptPartyForm form) {
        addMember(form);
        addPartyMember(form);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Object> joinPartyAndCheckFull(JoinPartyForm form) {
        joinParty(form);
        checkPartyFull(form.getPartyId());
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Object> joinParty(JoinPartyForm form) {
        // 1. 파티 아이디와 유저 닉네임을 받고
        // 2. 그것을 토대로 invite party 테이블에 accept을 true 상태로 저장 및 파티인원 증가 +1;
        LocalDateTime limitDt = LocalDateTime.now().plusDays(1);
        Optional<Party> optionalParty = partyRepository.findById(form.getPartyId());
        if (optionalParty.isPresent()) {
            Party party = optionalParty.get();
            InvitePartyForm invitePartyForm = InvitePartyForm.builder()
                    .party(optionalParty.get())
                    .nickname(form.getNickName())
                    .limitDt(limitDt)
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
        List<Party> partyList = new ArrayList<>();
        partyList = partyRepository.findByPartyFullIsFalseAndInvisibleDtBefore(now);
        return partyList;
    }

    @Override
    @Transactional
    public Party addMember(AcceptPartyForm form) {

        InviteParty inviteParty = findUser(form);
        Optional<Party> optionalParty = partyRepository.findById(inviteParty.getParty().getId());
        if (optionalParty.isPresent()) {
            inviteParty.setAccept(true);
            Party party = optionalParty.get();
            if (party.getPeople() < 4){
                party.setPeople(party.getPeople() + 1);
                return partyRepository.save(party);
            }else {
                throw new PartyException(PartyErrorCode.PARTY_IS_FULL);
            }


        }
        throw new PartyException(PartyErrorCode.NOT_FOUND_PARTY);

    }

    @Override
    public ResponseEntity<Object> addPartyMember(AcceptPartyForm form) {
        InviteParty inviteParty = findUser(form);
        checkPartyFull(inviteParty.getParty().getId());

        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Object> checkPartyFull(Long partyId) {

        Optional<Party> optionalParty = partyRepository.findById(partyId);

        if (optionalParty.isPresent()) {
            Party party = optionalParty.get();
            if (party.getPeople() == 4) {
                party.setPartyFull(true);
                return savePartyMember(party.getId());
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
                    .isLeader((Boolean) object[1])
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
                .createQuery(" select i.receiverNickName,i.isLeader ,i.party " +
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
        List<String> membersList = new ArrayList<>();
        if (!list.isEmpty()) {
            for (int i = 0; i < list.size(); i++) {
                list.get(i).get().getMembers().get(i).getNickName();
            }
        }
        throw new PartyException(PartyErrorCode.NOT_FOUND_PARTY);
    }

    @Override
    public List<Optional<Party>> findMyParties(FindMyPartiesForm form) {
        List<Optional<Party>> myPartyList = new ArrayList<>();
        List<PartyMember> myPartyListId = partyMemberRepository.findByNickName(form.getNickName());
        if (!myPartyListId.isEmpty()) {
            for (PartyMember partyMember : myPartyListId) {
                Optional<Party> party = partyRepository.findById(partyMember.getParty().getId());
                myPartyList.add(party);
            }
            return myPartyList;
        }
        throw new PartyException(PartyErrorCode.NOT_FOUND_PARTY);
    }


    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(NOT_FOUND_USER));
        return user;
    }
}
