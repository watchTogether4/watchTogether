package com.watchtogether.server.users.domain.model.user;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class DeleteUser {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(name = "DeleteUserRequest", description = "사용자 회원 탈퇴 요청")
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
    @Schema(name = "DeleteUserResponse", description = "사용자 회원 탈퇴 응답")
    public static class Response {

        @Schema(description = "성공 응답 메시지")
        private String message;

    }

}
