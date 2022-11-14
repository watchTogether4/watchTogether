package com.watchtogether.server.exception.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AlertErrorCode {

    NOT_FOUND_NOTIFICATION(HttpStatus.NOT_FOUND.value(), "NOT_FOUND_NOTIFICATION", "알림을 찾을 수 없습니다.");
    private final int errorStatus;
    private final String errorCode;
    private final String detail;
}
