package com.watchtogether.server.party.service;

import com.watchtogether.server.party.domain.entitiy.InviteParty;
import com.watchtogether.server.party.domain.model.CreatePartyForm;
import com.watchtogether.server.party.domain.entitiy.CreateParty;
import com.watchtogether.server.party.domain.model.InvitePartyForm;
import com.watchtogether.server.party.domain.repository.CreatePartyRepository;
import com.watchtogether.server.party.domain.repository.InvitePartyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class partyService {

    private final CreatePartyRepository createPartyRepository;
    private final InvitePartyRepository invitePartyRepository;

    // 파티장이 파티 생성 클릭
    public CreateParty createParty(CreatePartyForm form){

        CreateParty createParty = CreateParty.from(form);
        if (form.getReceiversNickName() != null){
            String[] receiverNickName = form.getReceiversNickName().split(",");

            for (String s : receiverNickName) {
                InvitePartyForm invitePartyForm = InvitePartyForm.builder()
                        .receiverNickName(s)
                        .createParty(createParty)
                        .build();
                invitePartyRepository.save(InviteParty.from(invitePartyForm));
            }
        }

        return createPartyRepository.save(createParty);
    }

}
