package com.watchtogether.server.users.domain.model.user;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class MyPageUser {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(name = "SignUpResponse", description = "사용자 회원가입 응답")
    public static class Response {

        @Schema(description = "아이디", example = "abc@ikdmd.kg.lr")
        private String email;

        @Schema(description = "닉네임")
        private String nickname;

        @Schema(description = "캐쉬")
        private Long cash;

        @Schema(description = "생년월일")
        private LocalDate birth;

        @Schema(description = "성공 응답 메시지")
        private String message;

    }
}
