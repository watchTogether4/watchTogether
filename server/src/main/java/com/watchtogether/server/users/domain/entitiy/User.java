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
    private String email;

    @Column(unique = true)
    private String nickname;

    private String password;

    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    private Long cash;

    private LocalDate birth;

    @Column(nullable = false, columnDefinition = "BIT DEFAULT 0")
    private boolean emailVerify;

    private String emailVerifyCode;

    private LocalDateTime emailVerifyExpiredDt;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    private String roles;

    private LocalDateTime lastLoginDt;

    private String resetPasswordKey;

    private LocalDateTime resetPasswordLimitDt;

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
