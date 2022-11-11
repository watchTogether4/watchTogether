package com.watchtogether.server.party.domain.entitiy;

import com.watchtogether.server.party.domain.model.CreatePartyForm;
import lombok.*;
import org.hibernate.envers.AuditOverride;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@Builder
@AuditOverride(forClass = BaseEntity.class)
@AllArgsConstructor
@NoArgsConstructor
public class Party extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "party_id")
    private Long id;

    private Long ottId;

    private String leaderNickname;

    private String title;

    private String body;

    private Integer people;

    private String partyOttId;

    private String partyOttPassword;

    private boolean partyFull;

    private LocalDateTime invisibleDt;

    private boolean leaderVerify;

    private LocalDateTime payDt;



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
                .partyFull(false)
                .leaderVerify(false)
                .leaderNickname(form.getLeaderNickName())
                .invisibleDt(LocalDateTime.now().plusDays(1).plusMinutes(1))
                .build();
    }
    public static Party fromNicknameIsNull(CreatePartyForm form){
        return Party.builder()
                .ottId(form.getOttId())
                .title(form.getTitle())
                .body(form.getBody())
                .people(1)
                .partyOttId(form.getPartyOttId())
                .partyOttPassword(form.getPartyOttPassword())
                .partyFull(false)
                .leaderVerify(false)
                .leaderNickname(form.getLeaderNickName())
                .invisibleDt(LocalDateTime.now())
                .build();
    }
}
