package com.watchtogether.server.party.service;

import com.watchtogether.server.exception.PartyException;
import com.watchtogether.server.exception.type.PartyErrorCode;
import com.watchtogether.server.party.domain.entitiy.InviteParty;
import com.watchtogether.server.party.domain.model.AcceptPartyForm;
import com.watchtogether.server.party.domain.model.CreatePartyForm;
import com.watchtogether.server.party.domain.entitiy.Party;
import com.watchtogether.server.party.domain.model.InvitePartyForm;
import com.watchtogether.server.party.domain.repository.PartyRepository;
import com.watchtogether.server.party.domain.repository.InvitePartyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class partyService {

    private final PartyRepository partyRepository;
    private final InvitePartyRepository invitePartyRepository;

    // 파티장이 파티 생성 클릭
    public Party createParty(CreatePartyForm form) {

        Party party = Party.from(form);
        if (form.getReceiversNickName() != null) {
            String[] receiverNickName = form.getReceiversNickName().split(",");

            for (String s : receiverNickName) {
                InvitePartyForm invitePartyForm = InvitePartyForm.builder()
                        .receiverNickName(s)
                        .party(party)
                        .build();
                invitePartyRepository.save(InviteParty.from(invitePartyForm));
            }
        }

        return partyRepository.save(party);
    }

    //파티 초대링크 눌렀을때
    public Party addMember(AcceptPartyForm form) {
        InviteParty inviteParty = invitePartyRepository.findByReceiverNickNameAndReceiverUUID(form.getNick(), form.getUuid())
                .orElseThrow(() -> new PartyException(PartyErrorCode.NOT_FOUND_USER));
        if (inviteParty.getLimitDt().isBefore(LocalDateTime.now())) {
            throw new PartyException(PartyErrorCode.EXPIRE_CODE);
        } else if (!inviteParty.getReceiverUUID().equals(form.getUuid())) {
            throw new PartyException(PartyErrorCode.WRONG_VERIFICATION);
        }
        Optional<Party> optionalParty = partyRepository.findById(inviteParty.getParty().getId());
        if (optionalParty.isPresent()) {
            Party party = optionalParty.get();
            party.setPeople(party.getPeople() + 1);
            party.setUpdatedDt(LocalDateTime.now());
            return partyRepository.save(party);
        }
        throw new PartyException(PartyErrorCode.NOT_FOUND_PARTY);

    }

}
