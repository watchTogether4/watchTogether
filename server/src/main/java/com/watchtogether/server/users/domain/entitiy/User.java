package com.watchtogether.server.users.domain.entitiy;

import com.watchtogether.server.users.domain.type.UserStatus;
import java.util.Date;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.AuditOverride;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@AuditOverride(forClass = BaseEntity.class)
@Entity(name = "user")
public class User extends BaseEntity {

    @Id
    @Column(name = "email")
    private String email;

    @Column(name = "nickname", unique = true)
    private String nickname;

    @Column(name = "password")
    private String password;

    @Column(name = "cash", nullable = false, columnDefinition = "INT DEFAULT 0")
    private Long cash;

    @Column(name = "birth")
    private Date birth;

    @Column(name = "email_verify", nullable = false, columnDefinition = "BIT DEFAULT 0")
    private boolean emailVerify;

    @Column(name = "email_verify_code")
    private String emailVerifyCode;

    @Column(name = "email_verify_expired_dt")
    private LocalDateTime emailVerifyExpiredDt;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private UserStatus status;


}
