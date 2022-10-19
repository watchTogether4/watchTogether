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
    private CreateParty createParty;

    private String receiverNickName;
    private String receiverUUID;
    private LocalDateTime limitDt;
    private LocalDateTime acceptDt;
    private LocalDateTime createDt;
    private LocalDateTime updateDt;

    public void setCreatePartyId(CreateParty createParty){
        this.createParty = createParty;
    }


    public static InviteParty from(InvitePartyForm form){
        return InviteParty.builder()
                .receiverNickName(form.getReceiverNickName())
                .receiverUUID(UUID.randomUUID().toString())
                .limitDt(LocalDateTime.now().plusDays(1))
                .createDt(LocalDateTime.now())
                .createParty(form.getCreateParty())
                .build();
    }
}
