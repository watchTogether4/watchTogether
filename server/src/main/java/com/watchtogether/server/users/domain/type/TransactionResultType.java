package com.watchtogether.server.users.domain.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TransactionResultType {

    ACCEPT("완료"),
    WAIT("대기"),
    CANCEL("취소");

    private String description;


}
