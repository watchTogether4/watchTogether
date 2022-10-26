package com.watchtogether.server.party.domain.entitiy;


import com.watchtogether.server.party.domain.model.InvitePartyForm;
import com.watchtogether.server.users.domain.entitiy.BaseEntity;
import lombok.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.envers.AuditOverride;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Setter
@Getter
@Builder
@AuditOverride(forClass = BaseEntity.class)
@AllArgsConstructor
@NoArgsConstructor
public class InviteParty extends BaseEntity{
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


    public void setCreatePartyId(Party party){
        this.party = party;
    }


    public static InviteParty from(InvitePartyForm form){
        String uuid = UUID.randomUUID().toString()
                .replaceAll("-", "").substring(0, 15);
        return InviteParty.builder()
                .receiverNickName(form.getReceiverNickName())
                .receiverUUID(uuid)
                .accept(false)
                .limitDt(LocalDateTime.now().plusDays(1))
                .party(form.getParty())
                .build();
    }
    public static InviteParty leaderFrom(InvitePartyForm form){
        String uuid = UUID.randomUUID().toString()
                .replaceAll("-", "").substring(0, 15);
        return InviteParty.builder()
                .receiverNickName(form.getReceiverNickName())
                .receiverUUID(uuid)
                .accept(true)
                .limitDt(LocalDateTime.now().plusDays(1))
                .party(form.getParty())
                .build();
    }
}
