package com.watchtogether.server.users.domain.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class AuthResetPassword {


    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(name = "AuthResetPasswordResponse", description = "패스워드 초기화 메일 인증 응답")
    public static class Response {

        @Schema(description = "성공 응답 메시지")
        private String message;

    }
}
