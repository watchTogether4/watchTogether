package com.watchtogether.server.users.domain.dto;

import com.watchtogether.server.users.domain.entitiy.User;
import com.watchtogether.server.users.domain.type.UserStatus;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private String email;
    private String nickname;
    private String password;
    private Long cash;
    private Date birth;
    private boolean emailVerify;
    private String emailVerifyCode;
    private LocalDateTime emailVerifyExpiredDt;
    private UserStatus status;
    private LocalDateTime lastLoginDt;

    private LocalDateTime createdDt;
    private LocalDateTime updatedDt;

    public static UserDto fromEntity(User user) {
        return UserDto.builder()
            .email(user.getEmail())
            .nickname(user.getNickname())
            .password(user.getPassword())
            .cash(user.getCash())
            .birth(user.getBirth())
            .emailVerify(user.isEmailVerify())
            .emailVerifyCode(user.getEmailVerifyCode())
            .emailVerifyExpiredDt(user.getEmailVerifyExpiredDt())
            .status(user.getStatus())
            .lastLoginDt(user.getLastLoginDt())
            .createdDt(user.getCreatedDt())
            .updatedDt(user.getUpdatedDt())
            .build();
    }


}
