package com.watchtogether.server.users.domain.model;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class TransactionCharge {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(name = "TransactionChargeRequest", description = "캐쉬 충전 요청")
    public static class Request {

        @Email
        @NotBlank(message = "이메일을 입력해 주세요.")
        @Schema(description = "아이디", example = "abc@ikdmd.kg.lr")
        private String email;

        @NotBlank(message = "비밀번호를 입력해 주세요.")
        @Schema(description = "비밀번호")
        private String password;

        @NotNull(message = "생년월일을 입력해 주세요.")
        @Schema(description = "충전 금액")
        private Long amount;

    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(name = "SignUpResponse", description = "사용자 회원가입 응답")
    public static class Response {

        @Schema(description = "아이디", example = "abc@ikdmd.kg.lr")
        private String email;

        @Schema(description = "거래 종류")
        private String transactionType;

        @Schema(description = "거래 결과 종류")
        private String transactionResultType;

        @Schema(description = "거래 금액")
        private Long amount;

        @Schema(description = "거래 후 잔액")
        private Long balanceSnapshot;

        @Schema(description = "거래자 닉네임")
        private String traderEmail;

        @Schema(description = "거래 일시")
        private LocalDateTime transactionDt;

        @Schema(description = "성공 응답 메시지")
        private String message;

    }

}
