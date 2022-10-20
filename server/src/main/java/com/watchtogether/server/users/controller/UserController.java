package com.watchtogether.server.users.controller;


import static com.watchtogether.server.users.domain.type.UserSuccess.SUCCESS_SIGNUP;
import static com.watchtogether.server.users.domain.type.UserSuccess.SUCCESS_VERIFY_EMAIL;

import com.watchtogether.server.users.domain.dto.UserDto;
import com.watchtogether.server.users.domain.model.SignUpUser;
import com.watchtogether.server.users.domain.model.VerifyEmail;
import com.watchtogether.server.users.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "사용자(UserController)", description = "사용자 API")
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/signUp")
    @Operation(summary = "사용자 회원가입 신청", description = "회원가입 정보를 받고 해당 이메일로 인증 메일 전송.")
    public ResponseEntity<SignUpUser.Response> signUp(
        @Validated @RequestBody SignUpUser.Request request) {

        UserDto userDto = userService.singUpUser(
            request.getEmail()
            , request.getNickname()
            , request.getPassword()
            , request.getBirth());

        return ResponseEntity.ok(
            new SignUpUser.Response(userDto.getEmail(), SUCCESS_SIGNUP.getMessage()));
    }

    @GetMapping("/signUp/verify")
    @Operation(summary = "인증 메일 검증", description = "회원가입시 보낸 인증 메일 코드를 검사 후 승인 처리")
    public ResponseEntity<VerifyEmail.Response> verify(@RequestParam(value = "email") String email
        , @RequestParam(value = "code") String code) {

        userService.verifyUser(email, code);

        return ResponseEntity.ok(
            new VerifyEmail.Response(SUCCESS_VERIFY_EMAIL.getMessage()));
    }
}
