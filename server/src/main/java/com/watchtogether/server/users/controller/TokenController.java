package com.watchtogether.server.users.controller;

import static com.watchtogether.server.users.domain.type.UserSuccess.SUCCESS_REFRESH_TOKEN;
import static com.watchtogether.server.users.domain.type.UserSuccess.SUCCESS_SIGN_OUT;

import com.watchtogether.server.users.domain.dto.TokenDto;
import com.watchtogether.server.users.domain.model.user.SignOutUser;
import com.watchtogether.server.users.domain.model.token.Token;
import com.watchtogether.server.users.service.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "토큰(TokenController)", description = "토큰 API")
@RequestMapping("/api/v1")
public class TokenController {

    private final JwtService jwtService;

    @PostMapping("/refresh-token")
    @Operation(summary = "토큰 값 갱신", description = "만료된 토큰 값 갱신을 위한 요청.")
    public ResponseEntity<Token.Response> refreshToken(
        @Validated @RequestBody Token.Request token) {

        TokenDto tokenDto = jwtService.updateToken(token.getAccessToken(), token.getRefreshToken());

        return ResponseEntity.ok(
            new Token.Response(
                tokenDto.getAccessToken(),
                tokenDto.getRefreshToken(),
                SUCCESS_REFRESH_TOKEN.getMessage()));
    }

    @PostMapping("/users/sign-out")
    @Operation(summary = "사용자 로그아웃", description = "사용자 로그아웃 처리 및 redis에 access token 블랙리스트 처리")
    public ResponseEntity<SignOutUser.Response> signOut(HttpServletRequest request) {

        jwtService.signOutUser(request);

        return ResponseEntity.ok(new SignOutUser.Response(SUCCESS_SIGN_OUT.getMessage()));

    }
}
