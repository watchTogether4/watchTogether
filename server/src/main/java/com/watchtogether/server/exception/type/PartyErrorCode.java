package com.watchtogether.server.exception.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum PartyErrorCode {
    NOT_FOUND_USER(HttpStatus.BAD_REQUEST.value(), "NOT_FOUND_USER", "일치하는 사용자가 없습니다."),
    NOT_FOUND_PARTY(HttpStatus.BAD_REQUEST.value(),"NOT_FOUND_PARTY", "일치하는 파티가 없습니다."),
    EXPIRE_CODE(HttpStatus.BAD_REQUEST.value(),"EXPIRE_CODE", "인증시간이 만료되었습니다."),
    WRONG_VERIFICATION(HttpStatus.BAD_REQUEST.value(),"WRONG_VERIFICATION", "잘못된 인증 시도입니다.");

    private final int errorStatus;
    private final String errorCode;
    private final String detail;
}
