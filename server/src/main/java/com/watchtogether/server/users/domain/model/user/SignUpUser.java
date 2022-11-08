package com.watchtogether.server.users.domain.model.user;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

public class SignUpUser {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(name = "SignUpRequest", description = "사용자 회원가입 요청")
    public static class Request {

        @Email
        @NotBlank(message = "이메일을 입력해 주세요.")
        @Schema(description = "아이디", example = "abc@ikdmd.kg.lr")
        private String email;

        @NotBlank(message = "닉네임을 입력해 주세요.")
        @Schema(description = "닉네임")
        private String nickname;

        @NotBlank(message = "비밀번호를 입력해 주세요.")
        @Schema(description = "비밀번호")
        private String password;

        @NotNull(message = "생년월일을 입력해 주세요.")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        @Schema(description = "생년월일", example = "yyyy-MM-dd")
        private LocalDate birth;

    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(name = "SignUpResponse", description = "사용자 회원가입 응답")
    public static class Response {

        @Schema(description = "아이디", example = "abc@ikdmd.kg.lr")
        private String email;

        @Schema(description = "성공 응답 메시지")
        private String message;

    }
}
