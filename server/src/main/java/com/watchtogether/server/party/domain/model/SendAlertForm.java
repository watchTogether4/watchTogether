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

    private String nickName; // 받는사람

    private String uuid; // 초대 일경우   uuid

    private Long partyId; // 파티 아이디

    private String sender; // 보내는 사람

    private AlertType alertType; // 알림 타입

    private Long ottId; // ott 아이디
}

