package com.example.sockettest.persist.entity;


import com.example.sockettest.domain.ChatMessage;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Dialog {

    @Id @GeneratedValue
    @Column(name = "dialog_id")
    private Long id;

    @Column(name = "party_id")
    private String partyId;

    @Column(name = "dialog_sender")
    private String sender;

    @Column(name = "dialog_message")
    private String message;

    @Column(name = "dialog_dt")
    private LocalDateTime dt;

    public Dialog(ChatMessage msg) {
        this.partyId = msg.getRoomId();
        this.sender = msg.getSender();
        this.message = msg.getMessage();
        this.dt = LocalDateTime.now();

    }

}
