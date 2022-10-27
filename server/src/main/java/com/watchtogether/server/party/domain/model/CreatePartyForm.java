package com.watchtogether.server.party.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePartyForm {
    private Long ottId;
    private String title;

    private String body;

    private String partyOttId;

    private String partyOttPassword;

    private String leaderNickName;

    private String receiversNickName;


}
