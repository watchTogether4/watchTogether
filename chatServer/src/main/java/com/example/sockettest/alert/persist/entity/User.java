package com.example.sockettest.alert.persist.entity;


import lombok.*;


import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "users")
public class User{

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



}
