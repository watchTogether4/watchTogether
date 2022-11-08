package com.watchtogether.server.users.domain.entitiy;

import static com.watchtogether.server.exception.type.TransactionErrorCode.INSUFFICIENT_CASH;
import static com.watchtogether.server.exception.type.TransactionErrorCode.INVALID_REQUEST;

import com.watchtogether.server.exception.TransactionException;
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
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.envers.AuditOverride;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@AuditOverride(forClass = BaseEntity.class)
@Entity(name = "users")
public class User extends BaseEntity implements UserDetails {

    @Id
    private String email;

    @Column(unique = true)
    @NotNull
    private String nickname;

    @NotNull
    private String password;

    @ColumnDefault("'0'")
    private Long cash;

    @NotNull
    private LocalDate birth;

    @ColumnDefault("0")
    private boolean emailVerify;

    private String emailVerifyCode;

    private LocalDateTime emailVerifyExpiredDt;

    @Enumerated(EnumType.STRING)
    @NotNull
    private UserStatus status;

    @NotNull
    private String roles;

    private LocalDateTime lastLoginDt;

    private String resetPasswordKey;

    private LocalDateTime resetPasswordLimitDt;

    private String refreshToken;

    public void plusCash(Long amount) {
        if (amount <= 0) {
            throw new TransactionException(INVALID_REQUEST);
        }

        this.cash += amount;
    }

    public void minusCash(Long amount) {
        if (amount > this.cash) {
            throw new TransactionException(INSUFFICIENT_CASH);
        }
        this.cash -= amount;
    }


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
