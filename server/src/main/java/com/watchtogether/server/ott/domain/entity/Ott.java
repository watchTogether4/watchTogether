package com.watchtogether.server.ott.domain.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.envers.AuditOverride;


@Getter
@Builder
@AuditOverride(forClass = BaseEntity.class)
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="ott")
public class Ott extends BaseEntity{
    @Id
    @Column(name = "ott_id")
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
    private  String imagePath;
    // 이미지 경로
}
