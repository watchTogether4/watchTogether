package com.watchtogether.server.party.domain.entitiy;


import lombok.*;

import javax.persistence.*;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PartyMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String NickName;

    @ManyToOne
    @JoinColumn(name = "party_id")
    private Party party;
}
