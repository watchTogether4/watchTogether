package com.watchtogether.server.users.controller;


import static com.watchtogether.server.users.domain.type.UserSuccess.SUCCESS_CHANGE_PASSWORD;
import static com.watchtogether.server.users.domain.type.UserSuccess.SUCCESS_CHECK_PASSWORD;
import static com.watchtogether.server.users.domain.type.UserSuccess.SUCCESS_MY_PAGE;
import static com.watchtogether.server.users.domain.type.UserSuccess.SUCCESS_RESET_PASSWORD;
import static com.watchtogether.server.users.domain.type.UserSuccess.SUCCESS_SEARCH_NICKNAME;
import static com.watchtogether.server.users.domain.type.UserSuccess.SUCCESS_SEND_RESET_PASSWORD;
import static com.watchtogether.server.users.domain.type.UserSuccess.SUCCESS_SIGNIN;
import static com.watchtogether.server.users.domain.type.UserSuccess.SUCCESS_SIGNUP;
import static com.watchtogether.server.users.domain.type.UserSuccess.SUCCESS_VERIFY_EMAIL;
import static com.watchtogether.server.users.domain.type.UserSuccess.SUCCESS_VERIFY_RESET_PASSWORD;

import com.watchtogether.server.components.jwt.TokenProvider;
import com.watchtogether.server.users.domain.dto.UserDto;
import com.watchtogether.server.users.domain.entitiy.User;
import com.watchtogether.server.users.domain.model.AuthResetPassword;
import com.watchtogether.server.users.domain.model.ChangePassword;
import com.watchtogether.server.users.domain.model.CheckPassword;
import com.watchtogether.server.users.domain.model.MyPageUser;
import com.watchtogether.server.users.domain.model.ResetNewPassword;
import com.watchtogether.server.users.domain.model.ResetPassword;
import com.watchtogether.server.users.domain.model.SearchUser;
import com.watchtogether.server.users.domain.model.SignInUser;
import com.watchtogether.server.users.domain.model.SignUpUser;
import com.watchtogether.server.users.domain.model.VerifyEmail;
import com.watchtogether.server.users.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    public ResponseEntity<VerifyEmail.Response> verify(
        @RequestParam(value = "email") String email
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
        String accessToken = tokenProvider.generateAccessToken(userDto.getEmail(),
            userDto.getRoles());
        String refreshToken = tokenProvider.generateRefreshToken(userDto.getRoles( ));

        // refresh_token 값 , 유효기간 저장
        userService.saveRefreshToken(userDto.getEmail(), refreshToken);

        return ResponseEntity.ok(
            new SignInUser.Response(
                userDto.getEmail(),
                accessToken,
                refreshToken,
                SUCCESS_SIGNIN.getMessage()));
    }

    @GetMapping("/my-page")
    @Operation(summary = "사용자 마이페이지", description = "로그인한 사용자 정보를 조회한다.")
    public ResponseEntity<MyPageUser.Response> myPage(
        @Parameter(hidden = true) @AuthenticationPrincipal User user) {

        UserDto userDto = userService.InfoUser(user.getEmail());

        return ResponseEntity.ok(
            new MyPageUser.Response(
                userDto.getEmail(),
                userDto.getNickname(),
                userDto.getCash(),
                userDto.getBirth(),
                SUCCESS_MY_PAGE.getMessage()));

    }

    @GetMapping("/search-user")
    @Operation(summary = "초대할 사용자 닉네임 검색", description = "파티 모집 글 작성시, 초대할 사용자 닉네임을 검색한다.")
    public ResponseEntity<SearchUser.Response> searchUser(
        @RequestParam String nickname) {

        userService.searchNickname(nickname);

        return ResponseEntity.ok(
            new SearchUser.Response(
                nickname,
                SUCCESS_SEARCH_NICKNAME.getMessage()));
    }

    @PostMapping("/reset-password")
    @Operation(summary = "사용자 비밀번호 초기화 메일 전송", description = "사용자 비밀번호 초기화 메일 전송을 한다.")
    public ResponseEntity<ResetPassword.Response> sendResetPassword(
        @Validated @RequestBody ResetPassword.Request request) {

        userService.resetPassword(request.getEmail(), request.getNickname());

        return ResponseEntity.ok(
            new ResetPassword.Response(
                SUCCESS_SEND_RESET_PASSWORD.getMessage()));
    }

    @GetMapping("/reset-password")
    @Operation(summary = "사용자 비밀번호 초기화 메일 인증", description = "사용자 비밀번호 초기화 메일 인증을 한다.")
    public ResponseEntity<AuthResetPassword.Response> authResetPassword(
        @RequestParam(value = "code") String code) {

        userService.authResetPassword(code);

        return ResponseEntity.ok(
            new AuthResetPassword.Response(
                SUCCESS_VERIFY_RESET_PASSWORD.getMessage()));
    }

    @PutMapping("/reset-password")
    @Operation(summary = "사용자 새로운 비밀번호 재설정", description = "사용자 새로운 비밀번호로 재설정한다.")
    public ResponseEntity<ResetNewPassword.Response> ResetNewPassword(
        @Validated @RequestBody ResetNewPassword.Request request) {

        userService.updateNewPassword(request.getCode(), request.getPassword());

        return ResponseEntity.ok(
            new ResetNewPassword.Response(
                SUCCESS_RESET_PASSWORD.getMessage()));
    }

    @PostMapping("password")
    @Operation(summary = "사용자 마이페이지 새로운 비밀번호로 변경 전 현재 비밀번호 체크", description = "사용자의 비밀번호를 새로운 비밀번호로 변경하기 전 현재 비밀번호를 체크한다.")
    public ResponseEntity<CheckPassword.Response> checkPassword(
        @Validated @RequestBody CheckPassword.Request request
        , @Parameter(hidden = true) @AuthenticationPrincipal User user) {

        userService.checkPassword(user.getEmail(), request.getPassword());

        return ResponseEntity.ok(
            new CheckPassword.Response(
                SUCCESS_CHECK_PASSWORD.getMessage()));
    }

    @PutMapping("password")
    @Operation(summary = "사용자 마이페이지 새로운 비밀번호로 변경", description = "사용자의 비밀번호를 새로운 비밀번호로 변경한다.")
    public ResponseEntity<ChangePassword.Response> changeNewPassword(
        @Validated @RequestBody ChangePassword.Request request
        , @Parameter(hidden = true) @AuthenticationPrincipal User user) {

        userService.updateUserPassword(user.getEmail(), request.getPassword());

        return ResponseEntity.ok(
            new ChangePassword.Response(
                SUCCESS_CHANGE_PASSWORD.getMessage()));
    }

}
