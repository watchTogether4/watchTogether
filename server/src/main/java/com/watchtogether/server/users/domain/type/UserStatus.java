package com.watchtogether.server.users.domain.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserStatus {
    REQ("유저가 현재 가입 요청 중"),
    ING("유저가 현재 이용 중"),
    LEAVE("유저가 현재 탈퇴한 상태");

    private final String description;
}
