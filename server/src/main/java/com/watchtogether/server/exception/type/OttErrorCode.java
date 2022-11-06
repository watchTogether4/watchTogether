package com.watchtogether.server.exception.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum OttErrorCode {

    NOT_FOUND_OTT(HttpStatus.UNAUTHORIZED.value(), "NOT_FOUND_OTT", "Ott 서비스를 찾을 수 없습니다.");

    private final int errorStatus;
    private final String errorCode;
    private final String detail;
}
