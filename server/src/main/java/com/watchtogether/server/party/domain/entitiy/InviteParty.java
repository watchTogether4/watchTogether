package com.watchtogether.server.party.domain.entitiy;


import com.watchtogether.server.party.domain.model.InvitePartyForm;
import lombok.*;
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
    private boolean leader;
    private LocalDateTime limitDt;


    public void setCreatePartyId(Party party){
        this.party = party;
    }

    public static InviteParty leaderFrom(InvitePartyForm form){
        String uuid = UUID.randomUUID().toString()
                .replaceAll("-", "").substring(0, 15);
        return InviteParty.builder()
                .receiverNickName(form.getNickname())
                .accept(true)
                .leader(true)
                .party(form.getParty())
                .build();
    }
    public static InviteParty from(InvitePartyForm form){
        String uuid = UUID.randomUUID().toString()
                .replaceAll("-", "").substring(0, 15);
        return InviteParty.builder()
                .receiverNickName(form.getNickname())
                .receiverUUID(uuid)
                .accept(false)
                .leader(false)
                .limitDt(form.getLimitDt())
                .party(form.getParty())
                .build();
    }

    public static InviteParty joinPartyFrom(InvitePartyForm form){
        String uuid = UUID.randomUUID().toString()
                .replaceAll("-", "").substring(0, 15);
        return InviteParty.builder()
                .receiverNickName(form.getNickname())
                .accept(true)
                .leader(false)
                .party(form.getParty())
                .build();
    }

}
