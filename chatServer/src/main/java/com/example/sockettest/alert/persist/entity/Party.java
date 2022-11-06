package com.example.sockettest.alert.persist.entity;


import lombok.*;


import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Party{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "party_id")
    private Long id;

    private Long ottId;

    private String title;

    private String body;

    private Integer people;

    private String partyOttId;

    private String partyOttPassword;

    private boolean partyFull;

    private LocalDateTime invisibleDt;

    private boolean leaderVerify;

    private LocalDate payDt;

    @OneToMany(mappedBy = "party")
    private List<PartyMember> members = new ArrayList<>();


}
