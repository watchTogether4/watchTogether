package com.watchtogether.server.users.domain.model;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ResetPassword {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(name = "ResetPasswordRequest", description = "비밀번호 초기화 요청")
    public static class Request {

        @Email
        @NotBlank(message = "이메일을 입력해 주세요.")
        @Schema(description = "아이디", example = "abc@ikdmd.kg.lr")
        private String email;

        @NotBlank(message = "닉네임을 입력해 주세요.")
        @Schema(description = "닉네임")
        private String nickname;

    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(name = "ResetPasswordResponse", description = "비밀번호 초기화 응답")
    public static class Response {

        @Schema(description = "성공 응답 메시지")
        private String message;

    }
}
