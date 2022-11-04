package com.watchtogether.server.exception.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode {

    NOT_FOUND_USER(HttpStatus.BAD_REQUEST.value(), "NOT_FOUND_USER", "일치하는 사용자가 없습니다."),
    LEAVE_USER(HttpStatus.BAD_REQUEST.value(), "LEAVE_USER","탈퇴한 사용자입니다."),
    NEED_VERIFY_EMAIL(HttpStatus.BAD_REQUEST.value(), "NEED_VERIFY_EMAIL","이메일 인증을 완료해주세요."),
    ALREADY_SIGNUP_EMAIL(HttpStatus.BAD_REQUEST.value(), "ALREADY_SIGNUP_EMAIL","이미 사용중인 이메일입니다."),
    ALREADY_SIGNUP_NICKNAME(HttpStatus.BAD_REQUEST.value(), "ALREADY_SIGNUP_NICKNAME","이미 사용중인 닉네임입니다."),
    FAILURE_SEND_AUTH_EMAIL(HttpStatus.BAD_REQUEST.value(), "FAILURE_SEND_AUTH_EMAIL","인증 메일 전송에 실패했습니다."),
    ALREADY_VERIFY_EMAIL(HttpStatus.BAD_REQUEST.value(), "ALREADY_VERIFY_EMAIL","이미 인증된 이메일입니다."),
    WRONG_VERIFY_EMAIL_CODE(HttpStatus.BAD_REQUEST.value(), "WRONG_VERIFY_EMAIL_CODE","인증 메일 코드가 일치하지 않습니다."),
    EXPIRED_VERIFY_EMAIL_CODE(HttpStatus.BAD_REQUEST.value(), "EXPIRED_VERIFY_EMAIL_CODE","인증 기간이 만료되었습니다."),
    WRONG_PASSWORD_USER(HttpStatus.BAD_REQUEST.value(), "WRONG_PASSWORD_USER","패스워드가 일치하지 않습니다."),
    SAME_PASSWORD(HttpStatus.BAD_REQUEST.value(), "SAME_PASSWORD","기존의 패스워드와 일치합니다."),
    NOT_FOUND_NICKNAME(HttpStatus.BAD_REQUEST.value(), "NOT_FOUND_NICKNAME","일치하는 닉네임이 없습니다."),
    IS_EXIST_BALANCE(HttpStatus.BAD_REQUEST.value(), "IS_EXIST_BALANCE","잔액이 남아있습니다.");


    private final int errorStatus;
    private final String errorCode;
    private final String detail;
}
