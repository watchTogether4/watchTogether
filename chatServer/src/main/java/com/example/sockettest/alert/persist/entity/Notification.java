package com.example.sockettest.alert.persist.entity;


import com.example.sockettest.domain.ChatMessage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Notification {

    @Id @GeneratedValue
    @Column(name = "notification_id")
    private Long id;

    @Column(name = "user_email")
    private String email;

    @Column(name = "party_id")
    private String partyId;

    @Column(name = "notification_type")
    private String type;

    @Column(name = "notification_text")
    private String message;

    @Column(name = "notification_created_dt")
    private LocalDateTime createdDt;

    @Column(name = "notification_expired_dt")
    private LocalDateTime expiredDt;

    @Column(name = "notification_open")
    private boolean checkAlert;

    @Column(name = "notification_invite_uuid")
    private String inviteId;

    public Notification(String email, String partyId, String inviteId, String message, String type) {
        this.email = email;
        this.partyId = partyId;
        this.type = type;
        this.message = message;
        this.createdDt = LocalDateTime.now();
        this.expiredDt = LocalDateTime.now().plusDays(2); // TODO: 2022/11/05 만료일 내부적으로 상의 필요
        this.checkAlert = false;
        this.inviteId = inviteId;
    }
}
