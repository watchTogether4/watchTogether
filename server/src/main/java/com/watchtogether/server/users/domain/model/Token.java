package com.watchtogether.server.users.domain.model;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class Token {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(name = "TokenRequest", description = "토큰 값 갱신을 위한 요청")
    public static class Request {

        @NotBlank(message = "access 토큰을 입력해주세요.")
        @Schema(description = "access_token")
        private String accessToken;


        @NotBlank(message = "refresh 토큰을 입력해주세요.")
        @Schema(description = "refresh_token")
        private String refreshToken;

    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(name = "TokenResponse", description = "토큰 값 갱신 요청에 대한 응답")
    public static class Response {

        @Schema(description = "access_token")
        private String accessToken;

        @Schema(description = "refresh_token")
        private String refreshToken;

        @Schema(description = "성공 응답 메시지")
        private String message;

    }
}
