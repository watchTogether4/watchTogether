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
    SUCCESS_MY_PAGE("메인페이지 출력에 성공하였습니다."),
    SUCCESS_SEARCH_NICKNAME("닉네임 검색에 성공하였습니다."),
    SUCCESS_CHECK_PASSWORD("패스워드 검사에 성공하였습니다."),
    SUCCESS_CHANGE_PASSWORD("패스워드 변경에 성공하였습니다."),
    SUCCESS_SEND_RESET_PASSWORD("패스워드 초기화 메일 전송에 성공하였습니다. 메일을 확인하여 코드를 입력해주세요."),
    SUCCESS_VERIFY_RESET_PASSWORD("패스워드 초기화 코드 인증에 성공하였습니다."),
    SUCCESS_RESET_PASSWORD("패스워드 변경에 성공하였습니다."),
    SUCCESS_REFRESH_TOKEN("토큰 재발급에 성공하였습니다."),
    SUCCESS_SIGN_OUT("로그아웃에 성공하였습니다.");

    private String message;
}
