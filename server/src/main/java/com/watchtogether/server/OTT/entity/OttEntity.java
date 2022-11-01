package com.watchtogether.server.OTT.entity;

import com.watchtogether.server.users.domain.entitiy.BaseEntity;
import lombok.*;
import org.hibernate.envers.AuditOverride;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AuditOverride(forClass = BaseEntity.class)
@AllArgsConstructor
@NoArgsConstructor
public class OttEntity extends BaseEntity{
    @Id
    @Column(name = "invite_id")
    private Long id;
    // 넷플릭스     : 1
    // 왓챠        : 2
    // 디즈니      : 3
    // 웨이브      : 4
    // 티빙       : 5
    // 애플       : 6
    private String name;

    private int commissionLeader;
    // 900원으로 통일
    private int commissionMember;
    // 500원으로 통일
    private Long fee;
    // 전체금액 / 4
    private  String ImagePath;
    // 이미지 경로
}
