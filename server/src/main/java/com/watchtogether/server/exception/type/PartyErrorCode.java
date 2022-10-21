package com.watchtogether.server.exception.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum PartyErrorCode {
    NOT_FOUND_USER(HttpStatus.BAD_REQUEST, "일치하는 사용자가 없습니다."),
    NOT_FOUND_PARTY(HttpStatus.BAD_REQUEST, "일치하는 파티가 없습니다."),
    EXPIRE_CODE(HttpStatus.BAD_REQUEST, "인증시간이 만료되었습니다."),
    WRONG_VERIFICATION(HttpStatus.BAD_REQUEST, "잘못된 인증 시도입니다.");

    private final HttpStatus httpStatus;
    private final String detail;
}
