package com.watchtogether.server.party.domain.model;

import com.watchtogether.server.party.domain.entitiy.CreateParty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvitePartyForm {
    private String receiverNickName;
    private CreateParty createParty;

}
