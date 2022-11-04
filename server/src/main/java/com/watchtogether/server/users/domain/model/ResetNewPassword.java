package com.watchtogether.server.users.domain.model;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ResetNewPassword {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(name = "ResetNewPasswordRequest", description = "새로운 비밀번호로 변경 요청")
    public static class Request {

        @NotBlank(message = "패스워드 초기화 인증 코드를 입력해주세요.")
        @Schema(description = "패스워드 초기화 인증 코드")
        private String code;

        @NotBlank(message = "비밀번호를 입력해 주세요.")
        @Schema(description = "비밀번호")
        private String password;

    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(name = "ResetNewPasswordResponse", description = "새로운 비밀번호로 변경 응답")
    public static class Response {

        @Schema(description = "성공 응답 메시지")
        private String message;

    }
}
