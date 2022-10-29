package com.watchtogether.server.users.controller;


import static com.watchtogether.server.users.domain.type.UserSuccess.SUCCESS_MY_PAGE;
import static com.watchtogether.server.users.domain.type.UserSuccess.SUCCESS_SEARCH_NICKNAME;
import static com.watchtogether.server.users.domain.type.UserSuccess.SUCCESS_SIGNIN;
import static com.watchtogether.server.users.domain.type.UserSuccess.SUCCESS_SIGNUP;
import static com.watchtogether.server.users.domain.type.UserSuccess.SUCCESS_VERIFY_EMAIL;

import com.watchtogether.server.components.jwt.TokenProvider;
import com.watchtogether.server.users.domain.dto.UserDto;
import com.watchtogether.server.users.domain.entitiy.User;
import com.watchtogether.server.users.domain.model.MyPageUser;
import com.watchtogether.server.users.domain.model.SearchUser;
import com.watchtogether.server.users.domain.model.SignInUser;
import com.watchtogether.server.users.domain.model.SignUpUser;
import com.watchtogether.server.users.domain.model.VerifyEmail;
import com.watchtogether.server.users.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;
    private final TokenProvider tokenProvider;

    @PostMapping("/sign-up")
    @Operation(summary = "사용자 회원가입 신청", description = "회원가입 정보를 받고 해당 이메일로 인증 메일 전송.")
    public ResponseEntity<SignUpUser.Response> signUp(
        @Validated @RequestBody SignUpUser.Request request) {

        UserDto userDto = userService.singUpUser(
            request.getEmail()
            , request.getNickname()
            , request.getPassword()
            , request.getBirth());

        return ResponseEntity.ok(
            new SignUpUser.Response(
                userDto.getEmail(),
                SUCCESS_SIGNUP.getMessage()));
    }

    @GetMapping("/sign-up/verify")
    @Operation(summary = "인증 메일 검증", description = "회원가입시 보낸 인증 메일 코드를 검사 후 승인 처리")
    public ResponseEntity<VerifyEmail.Response> verify(@RequestParam(value = "email") String email
        , @RequestParam(value = "code") String code) {

        userService.verifyUser(email, code);

        return ResponseEntity.ok(
            new VerifyEmail.Response(
                SUCCESS_VERIFY_EMAIL.getMessage()));
    }

    @PostMapping("/sign-in")
    @Operation(summary = "사용자 로그인", description = "아이디와, 비밀번호를 입력받고 인증된 사용자인지 확인 후 로그인 처리")
    public ResponseEntity<SignInUser.Response> signIn(
        @Validated @RequestBody SignInUser.Request request) {

        UserDto userDto = userService.signInUser(request.getEmail(), request.getPassword());

        // token 발행
        String token = tokenProvider.generateToken(userDto.getEmail(), userDto.getRoles());

        return ResponseEntity.ok(
            new SignInUser.Response(
                userDto.getEmail(),
                token,
                SUCCESS_SIGNIN.getMessage()));
    }

    @GetMapping("/my-page")
    public ResponseEntity<MyPageUser.Response> myPage(@AuthenticationPrincipal User user) {

        UserDto userDto = userService.myPageUser(user.getEmail());

        return ResponseEntity.ok(
            new MyPageUser.Response(
                userDto.getEmail(),
                userDto.getNickname(),
                userDto.getCash(),
                userDto.getBirth(),
                SUCCESS_MY_PAGE.getMessage()));

    }

    @GetMapping("/search-user")
    public ResponseEntity<SearchUser.Response> searchUser(@RequestParam String nickname) {

        userService.searchNickname(nickname);

        return ResponseEntity.ok(
            new SearchUser.Response(
                nickname,
                SUCCESS_SEARCH_NICKNAME.getMessage()));
    }
}
