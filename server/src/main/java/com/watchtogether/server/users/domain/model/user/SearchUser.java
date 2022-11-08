package com.watchtogether.server.users.domain.model.user;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class SearchUser {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {

        @Schema(description = "닉네임")
        private String nickname;

        @Schema(description = "성공 메시지")
        private String message;

    }

}
