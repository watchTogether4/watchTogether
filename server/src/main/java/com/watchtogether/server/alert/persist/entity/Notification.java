package com.watchtogether.server.alert.persist.entity;


import com.watchtogether.server.party.domain.entitiy.Party;
import com.watchtogether.server.party.domain.type.AlertType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
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

    @ManyToOne
    @JoinColumn(name = "party_id")
    private Party party;

    @Column(name = "notification_type")
    private AlertType type;

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

    public Notification(String email, Party party, String inviteId, String message, AlertType type) {
        this.email = email;
        this.party = party;
        this.type = type;
        this.message = message;
        this.createdDt = LocalDateTime.now();
        if(type.equals("INVITE")) {
            this.expiredDt = LocalDateTime.now().plusDays(1);
        } else this.expiredDt = LocalDateTime.now().plusDays(3);
        this.checkAlert = false;
        this.inviteId = inviteId;
    }
}
