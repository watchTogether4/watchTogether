package com.watchtogether.server.party.domain.model;

import com.watchtogether.server.party.domain.type.AlertType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SendAlertForm {

    private String nickName;

    private String uuid;

    private Long partyId;

    private String sender;

    private AlertType alertType;
}

