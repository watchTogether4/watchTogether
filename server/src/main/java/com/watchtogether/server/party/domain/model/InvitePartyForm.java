package com.watchtogether.server.party.domain.model;

import com.watchtogether.server.party.domain.entitiy.Party;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvitePartyForm {
    private String nickname;

    private Boolean leader;

    private Party party;

    private LocalDateTime limitDt;


}
