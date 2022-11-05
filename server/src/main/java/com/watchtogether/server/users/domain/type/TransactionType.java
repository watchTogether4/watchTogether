package com.watchtogether.server.users.domain.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TransactionType {

    CHARGE("충전"),
    DEPOSIT("입금"),
    WITHDRAW("출금");

    private String description;
}
