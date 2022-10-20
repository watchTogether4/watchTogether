package com.watchtogether.server.exception.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    ALREADY_SIGNUP_EMAIL(HttpStatus.BAD_REQUEST, "이미 사용중인 이메일입니다."),
    ALREADY_SIGNUP_NICKNAME(HttpStatus.BAD_REQUEST, "이미 사용중인 닉네임입니다."),
    FAILURE_SEND_AUTH_EMAIL(HttpStatus.BAD_REQUEST, "이미 사용중인 닉네임입니다.");

    private final HttpStatus httpStatus;
    private final String detail;
}
