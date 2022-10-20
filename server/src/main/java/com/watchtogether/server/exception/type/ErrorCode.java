package com.watchtogether.server.exception.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    NOT_FOUND_USER(HttpStatus.BAD_REQUEST, "일치하는 사용자가 없습니다."),
    ALREADY_SIGNUP_EMAIL(HttpStatus.BAD_REQUEST, "이미 사용중인 이메일입니다."),
    ALREADY_SIGNUP_NICKNAME(HttpStatus.BAD_REQUEST, "이미 사용중인 닉네임입니다."),
    FAILURE_SEND_AUTH_EMAIL(HttpStatus.BAD_REQUEST, "인증 메일 전송에 실패했습니다."),
    ALREADY_VERIFY_EMAIL(HttpStatus.BAD_REQUEST, "이미 인증된 이메일입니다."),
    WRONG_VERIFY_EMAIL_CODE(HttpStatus.BAD_REQUEST, "인증 메일 코드가 일치하지 않습니다."),
    EXPIRED_VERIFY_EMAIL_CODE(HttpStatus.BAD_REQUEST, "인증 기간이 만료되었습니다.");



    private final HttpStatus httpStatus;
    private final String detail;
}
