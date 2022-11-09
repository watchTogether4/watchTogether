package com.watchtogether.server.party.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LeavePartyResponseForm {
    private String nickname;

    private Long partyId;

    private boolean leader;
}
