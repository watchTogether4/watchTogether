package com.watchtogether.server.exception.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum TransactionErrorCode {

    INSUFFICIENT_CASH(HttpStatus.UNAUTHORIZED.value(), "INSUFFICIENT_CASH", "캐쉬가 부족합니다."),
    INVALID_REQUEST(HttpStatus.UNAUTHORIZED.value(), "INVALID_REQUEST", "잘못된 요청입니다.");

    private final int errorStatus;
    private final String errorCode;
    private final String detail;
}
