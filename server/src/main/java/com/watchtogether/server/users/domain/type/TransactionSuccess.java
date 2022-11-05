package com.watchtogether.server.users.domain.type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum TransactionSuccess {

    SUCCESS_CHARGE("캐쉬 충전에 성공하였습니다."),
    SUCCESS_DEPOSIT("입금에 성공하였습니다."),
    SUCCESS_WITHDRAW("출금에 성공하였습니다."),
    SUCCESS_TRANSACTION_LIST("사용자 거래 내역 출력에 성공하였습니다.");

    private String message;
}
