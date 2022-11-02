package com.watchtogether.server.exception.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum TokenErrorCode {

    UNAUTHORIZED(HttpStatus.UNAUTHORIZED.value(), "UNAUTHORIZED", "인증 정보가 없습니다."),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED.value(), "EXPIRED_TOKEN", "사용 가능 시간이 만료된 토큰 값입니다."),
    EXPIRED_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED.value(), "EXPIRED_REFRESH_TOKEN", "사용 가능 시간이 만료된 refresh 토큰 값입니다."),
    EXPIRED_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED.value(), "EXPIRED_ACCESS_TOKEN", "사용 가능 시간이 만료된 access 토큰 값입니다."),
    INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED.value(), "INVALID_REFRESH_TOKEN", "refresh 토큰 서명 값이 일치하지 않습니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED.value(), "INVALID_TOKEN", "토큰 서명 값이 일치하지 않습니다."),
    INVALID_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED.value(), "INVALID_ACCESS_TOKEN", "access 토큰 서명 값이 일치하지 않습니다."),
    INVALID_TOKEN_PREFIX(HttpStatus.UNAUTHORIZED.value(), "INVALID_TOKEN_PREFIX", "Bearer 값을 확인해주세요."),
    IS_EMPTY_TOKEN(HttpStatus.UNAUTHORIZED.value(), "IS_EMPTY_TOKEN", "토큰 값이 비어있습니다."),
    IS_EMPTY_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED.value(), "IS_EMPTY_ACCESS_TOKEN", "access 토큰 값이 비어있습니다."),
    IS_EMPTY_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED.value(), "IS_EMPTY_REFRESH_TOKEN", "refresh 토큰 값이 비어있습니다."),
    WRONG_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED.value(), "WRONG_REFRESH_TOKEN", "fresh 토큰 값이 일치하지 않습니다.");

    private final int errorStatus;
    private final String errorCode;
    private final String detail;
}
