package com.watchtogether.server.exception.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum PartyErrorCode {
    NOT_FOUND_USER(HttpStatus.BAD_REQUEST.value(), "NOT_FOUND_USER", "일치하는 사용자가 없습니다."),
    NOT_FOUND_USER_IN_INVITE_PARTY(HttpStatus.BAD_REQUEST.value(), "NOT_FOUND_USER_IN_INVITE_PARTY", "대기자 명단에서 해당 유저를 찾을수 없습니다."),
    NOT_FOUND_PARTY_IN_INVITE_PARTY(HttpStatus.BAD_REQUEST.value(), "NOT_FOUND_PARTY_IN_INVITE_PARTY", "대기자 명단에서 해당 파티를 찾을수 없습니다."),
    NOT_FOUND_DATA_IN_INVITE_PARTY(HttpStatus.BAD_REQUEST.value(), "NOT_FOUND_DATA_IN_INVITE_PARTY", "대기자 명단에서 해당 데이터를 찾을수 없습니다."),
    NOT_FOUND_PARTY_IN_PARTY_MEMBER(HttpStatus.BAD_REQUEST.value(), "NOT_FOUND_PARTY_IN_PARTY_MEMBER", "파티멤버 명단에서 해당 유저를 찾을수 없습니다."),
    NOT_FOUND_USER_IN_PARTY_MEMBER(HttpStatus.BAD_REQUEST.value(), "NOT_FOUND_USER_IN_PARTY_MEMBER", "파티완료 명단에서 해당 유저를 찾을수 없습니다."),
    NOT_FOUND_PARTY(HttpStatus.BAD_REQUEST.value(), "NOT_FOUND_PARTY", "일치하는 파티가 없습니다."),
    NOT_FOUND_OTT_ID(HttpStatus.BAD_REQUEST.value(), "NOT_FOUND_OTT_ID", "일치하는 OTT_ID가 없습니다."),

    PARTY_IS_FULL(HttpStatus.BAD_REQUEST.value(), "PARTY_IS_FULL", "파티인원이 이미 4명입니다."),
    EXPIRE_CODE(HttpStatus.BAD_REQUEST.value(), "EXPIRE_CODE", "인증시간이 만료되었습니다."),
    ALREADY_JOIN_PARTY(HttpStatus.BAD_REQUEST.value(),"ALREADY_JOIN_PARTY", "이미 해당파티에 가입되었습니다."),
    NOT_JOIN_PARTY(HttpStatus.BAD_REQUEST.value(),"NOT_JOIN_PARTY", "해당파티에 가입되어있지않습니다"),
    NOT_LEADER(HttpStatus.BAD_REQUEST.value(),"NOT_LEADER", "파티장이 아닙니다."),
    WRONG_PASSWORD_OTT(HttpStatus.BAD_REQUEST.value(), "WRONG_PASSWORD_USER","패스워드가 일치하지 않습니다."),
    SAME_PASSWORD_OTT(HttpStatus.BAD_REQUEST.value(), "SAME_PASSWORD_OTT","패스워드가 같습니다."),
    WRONG_VERIFICATION(HttpStatus.BAD_REQUEST.value(), "WRONG_VERIFICATION", "잘못된 인증 시도입니다."),

    // todo 사용자 회원 탈퇴 전 파티 그룹에 속해있을 경우 에러
    FOUND_USER_BEFORE_DELETE(HttpStatus.BAD_REQUEST.value(), "FOUND_USER_BEFORE_DELETE",
            "파티 그룹에 참여하고 있는 중 입니다. 파티 탈퇴 후 다시 시도해주세요.");

    private final int errorStatus;
    private final String errorCode;
    private final String detail;
}
