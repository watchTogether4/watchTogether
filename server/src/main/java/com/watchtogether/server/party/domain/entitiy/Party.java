package com.watchtogether.server.party.domain.entitiy;

import com.watchtogether.server.party.domain.model.CreatePartyForm;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Party {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "party_id")
    private Long id;

    private Long ottId;

    private LocalDateTime createdDt;
    private LocalDateTime updatedDt;

    private String title;

    private String body;

    private Integer people;

    private String partyOttId;

    private String partyOttPassword;

    private boolean leaderVerify;

    @OneToMany(mappedBy = "party")
    private List<PartyMember> members = new ArrayList<>();

    public static Party from(CreatePartyForm form){
        return Party.builder()
                .ottId(form.getOttId())
                .title(form.getTitle())
                .body(form.getBody())
                .people(1)
                .partyOttId(form.getPartyOttId())
                .partyOttPassword(form.getPartyOttPassword())
                .leaderVerify(false)
                .createdDt(LocalDateTime.now())
                .build();
    }
}
