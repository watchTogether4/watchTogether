package com.watchtogether.server.party.domain.entitiy;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.watchtogether.server.party.domain.model.InvitePartyForm;
import com.watchtogether.server.users.domain.entitiy.BaseEntity;
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
public class PartyMember extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String NickName;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "party_id")
    private Party party;
    public static PartyMember from(InvitePartyForm form){
        return PartyMember.builder()
                .NickName(form.getReceiverNickName())
                .party(form.getParty())
                .build();
    }
}
