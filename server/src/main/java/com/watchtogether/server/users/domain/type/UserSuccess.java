package com.watchtogether.server.users.domain.type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum UserSuccess {
    SUCCESS_SIGNUP("회원가입에 성공하였습니다. 인증 메일을 확인해주세요."),
    SUCCESS_VERIFY_EMAIL("이메일 인증에 성공하였습니다."),
    SUCCESS_SIGNIN("로그인에 성공하였습니다."),
    SUCCESS_MY_PAGE("메인페이지 출력에 성공하였습니다.");

    private String message;
}
