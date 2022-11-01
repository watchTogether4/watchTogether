package com.watchtogether.server.exception.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AuthErrorCode {

    UNAUTHORIZED(HttpStatus.UNAUTHORIZED.value(), "UNAUTHORIZED", "인증 정보가 없습니다."),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED.value(), "EXPIRED_TOKEN", "사용 가능 시간이 만료된 토큰 값입니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED.value(), "INVALID_TOKEN", "토큰 서명 값이 일치하지 않습니다."),
    INVALID_TOKEN_PREFIX(HttpStatus.UNAUTHORIZED.value(), "INVALID_TOKEN_PREFIX", "Bearer 값을 확인해주세요."),
    IS_EMPTY_TOKEN(HttpStatus.UNAUTHORIZED.value(), "IS_EMPTY_TOKEN", "토큰 값이 비어있습니다."),
    IS_SIGN_OUT_TOKEN(HttpStatus.UNAUTHORIZED.value(), "IS_SIGN_OUT_TOKEN", "로그아웃 처리된 토큰 값입니다.");

    private final int errorStatus;
    private final String errorCode;
    private final String detail;
}
