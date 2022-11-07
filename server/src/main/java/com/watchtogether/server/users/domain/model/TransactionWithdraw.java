package com.watchtogether.server.users.domain.model;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class TransactionWithdraw {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(name = "TransactionWithdrawRequest", description = "파티 참가시 선결제 요청")
    public static class Request {

        @Schema(description = "party 아이디")
        private Long partyId;

    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(name = "TransactionWithdrawResponse", description = "파티 참가시 선결제 응답")
    public static class Response {

        @Schema(description = "닉네임", example = "abc@ikdmd.kg.lr")
        private String nickname;

        @Schema(description = "거래 종류")
        private String transactionType;

        @Schema(description = "거래 결과 종류")
        private String transactionResultType;

        @Schema(description = "거래 금액")
        private Long amount;

        @Schema(description = "거래 후 잔액")
        private Long balanceSnapshot;

        @Schema(description = "거래자 이메일")
        private String traderNickname;

        @Schema(description = "거래 일시")
        private LocalDateTime transactionDt;

        @Schema(description = "성공 응답 메시지")
        private String message;

    }

}
