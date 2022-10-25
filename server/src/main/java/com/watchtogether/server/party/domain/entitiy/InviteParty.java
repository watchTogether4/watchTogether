package com.watchtogether.server.party.domain.entitiy;


import com.watchtogether.server.party.domain.model.InvitePartyForm;
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
public class InviteParty {
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
    private LocalDateTime limitDt;
    private LocalDateTime createDt;
    private LocalDateTime updateDt;

    public void setCreatePartyId(Party party){
        this.party = party;
    }


    public static InviteParty from(InvitePartyForm form){
        return InviteParty.builder()
                .receiverNickName(form.getReceiverNickName())
                .receiverUUID(UUID.randomUUID().toString())
                .accept(false)
                .limitDt(LocalDateTime.now().plusDays(1))
                .createDt(LocalDateTime.now())
                .party(form.getParty())
                .build();
    }
    public static InviteParty leaderFrom(InvitePartyForm form){
        return InviteParty.builder()
                .receiverNickName(form.getReceiverNickName())
                .receiverUUID(UUID.randomUUID().toString())
                .accept(true)
                .limitDt(LocalDateTime.now().plusDays(1))
                .createDt(LocalDateTime.now())
                .party(form.getParty())
                .build();
    }
}
