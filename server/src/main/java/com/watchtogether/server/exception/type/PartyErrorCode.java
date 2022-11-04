package com.watchtogether.server.exception.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum PartyErrorCode {
    NOT_FOUND_USER(HttpStatus.BAD_REQUEST.value(), "NOT_FOUND_USER", "일치하는 사용자가 없습니다."),
    NOT_FOUND_PARTY(HttpStatus.BAD_REQUEST.value(), "NOT_FOUND_PARTY", "일치하는 파티가 없습니다."),

    PARTY_IS_FULL(HttpStatus.BAD_REQUEST.value(), "PARTY_IS_FULL", "파티인원이 이미 4명입니다."),
    EXPIRE_CODE(HttpStatus.BAD_REQUEST.value(), "EXPIRE_CODE", "인증시간이 만료되었습니다."),
    ALREADY_JOIN_PARTY(HttpStatus.BAD_REQUEST.value(),"ALREADY_JOIN_PARTY", "이미 해당파티에 가입되었습니다."),
    WRONG_VERIFICATION(HttpStatus.BAD_REQUEST.value(), "WRONG_VERIFICATION", "잘못된 인증 시도입니다."),

    // todo 사용자 회원 탈퇴 전 파티 그룹에 속해있을 경우 에러
    FOUND_USER_BEFORE_DELETE(HttpStatus.BAD_REQUEST.value(), "FOUND_USER_BEFORE_DELETE",
            "파티 그룹에 참여하고 있는 중 입니다. 파티 탈퇴 후 다시 시도해주세요.");

    private final int errorStatus;
    private final String errorCode;
    private final String detail;
}
