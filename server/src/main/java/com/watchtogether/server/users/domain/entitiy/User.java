package com.watchtogether.server.users.domain.entitiy;

import com.watchtogether.server.users.domain.type.UserStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@AuditOverride(forClass = BaseEntity.class)
@Entity(name = "users")
public class User extends BaseEntity implements UserDetails {

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
    private LocalDate birth;

    @Column(name = "email_verify", nullable = false, columnDefinition = "BIT DEFAULT 0")
    private boolean emailVerify;

    @Column(name = "email_verify_code")
    private String emailVerifyCode;

    @Column(name = "email_verify_expired_dt")
    private LocalDateTime emailVerifyExpiredDt;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @Column(name = "roles")
    private String roles;

    @Column(name = "last_login_dt")
    private LocalDateTime lastLoginDt;

    @Column(name = "reset_password_key")
    private String resetPasswordKey;

    @Column(name = "reset_password_limit_dt")
    private LocalDateTime resetPasswordLimitDt;

    @Column(name = "refresh_token")
    private String refreshToken;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(this.roles));
        return grantedAuthorities;
    }

    @Override
    public String getUsername() {
        return this.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
