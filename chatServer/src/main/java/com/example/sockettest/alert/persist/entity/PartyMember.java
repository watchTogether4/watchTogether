package com.example.sockettest.alert.persist.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;


import javax.persistence.*;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PartyMember{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nickName;

    private boolean isLeader;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "party_id")
    private Party party;

}
