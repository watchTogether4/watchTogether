package com.example.sockettest.alert.persist.entity;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Setter
@Getter
@Builder

@AllArgsConstructor
@NoArgsConstructor
public class InviteParty{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invite_id")
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "party_id")
    private Party party;

    private String receiverNickName;
    private String receiverUUID;
    private boolean accept;
    private boolean leader;
    private LocalDateTime limitDt;


    public void setCreatePartyId(Party party){
        this.party = party;
    }



}
