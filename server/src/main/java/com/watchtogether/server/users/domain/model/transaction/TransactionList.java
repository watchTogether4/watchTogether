package com.watchtogether.server.users.domain.model.transaction;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class TransactionList {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(name = "TransactionListResponse", description = "사용자 거래 내역 리스트")
    public static class Response {

        @Schema(description = "사용자 거래 내역 리스트")
        private Object list;

        @Schema(description = "성공 응답 메시지")
        private String message;

    }


}
