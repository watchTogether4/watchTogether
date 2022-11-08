package com.watchtogether.server.users.domain.model.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class SignOutUser {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(name = "SignOutResponse", description = "사용자 로그아웃 응답")
    public static class Response {

        @Schema(description = "메시지")
        private String message;
    }
}
