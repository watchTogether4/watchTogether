package com.watchtogether.server.users.domain.model;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class SignInUser {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(name = "SignInRequest", description = "사용자 로그인 요청")
    public static class Request {

        @Email
        @NotBlank(message = "이메일을 입력해 주세요.")
        @Schema(description = "아이디", example = "abc@ikdmd.kg.lr")
        private String email;


        @NotBlank(message = "비밀번호를 입력해 주세요.")
        @Schema(description = "비밀번호")
        private String password;

    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(name = "SignInResponse", description = "사용자 로그인 응답")
    public static class Response {

        @Schema(description = "아이디", example = "abc@ikdmd.kg.lr")
        private String email;

        @Schema(description = "토큰 값")
        private String token;

        @Schema(description = "메시지")
        private String message;
    }
}
