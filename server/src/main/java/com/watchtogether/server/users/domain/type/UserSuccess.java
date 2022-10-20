package com.watchtogether.server.users.domain.type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum UserSuccess {
    SUCCESS_SIGNUP("회원가입에 성공하였습니다.");

    private String message;
}
