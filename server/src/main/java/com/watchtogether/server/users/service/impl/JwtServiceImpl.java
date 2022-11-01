package com.watchtogether.server.users.service.impl;


import static com.watchtogether.server.exception.type.TokenErrorCode.EXPIRED_REFRESH_TOKEN;
import static com.watchtogether.server.exception.type.TokenErrorCode.INVALID_ACCESS_TOKEN;
import static com.watchtogether.server.exception.type.TokenErrorCode.INVALID_REFRESH_TOKEN;
import static com.watchtogether.server.exception.type.TokenErrorCode.IS_EMPTY_ACCESS_TOKEN;
import static com.watchtogether.server.exception.type.TokenErrorCode.IS_EMPTY_REFRESH_TOKEN;
import static com.watchtogether.server.exception.type.TokenErrorCode.WRONG_REFRESH_TOKEN;
import static com.watchtogether.server.exception.type.UserErrorCode.NOT_FOUND_USER;

import com.watchtogether.server.components.jwt.TokenProvider;
import com.watchtogether.server.exception.TokenException;
import com.watchtogether.server.exception.UserException;
import com.watchtogether.server.users.domain.dto.TokenDto;
import com.watchtogether.server.users.domain.entitiy.User;
import com.watchtogether.server.users.domain.repository.UserRepository;
import com.watchtogether.server.users.service.JwtService;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    private final TokenProvider tokenProvider;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public TokenDto updateToken(String accessToken, String refreshToken) {

        // access 토큰 값이 빈 경우
        if (accessToken == null || accessToken.trim().length() == 0) {
            throw new TokenException(IS_EMPTY_ACCESS_TOKEN);
        }

        // refresh 토큰 값이 빈 경우
        if (refreshToken == null || refreshToken.trim().length() == 0) {
            throw new TokenException(IS_EMPTY_REFRESH_TOKEN);
        }

        // 만료된 refresh 토큰 에러, 잘못된 refresh 토큰 값 에러
        if (tokenProvider.validToken(refreshToken)
            .equals(EXPIRED_REFRESH_TOKEN.getErrorCode())) {

            throw new TokenException(EXPIRED_REFRESH_TOKEN);

        } else if (tokenProvider.validToken(refreshToken)
            .equals(INVALID_REFRESH_TOKEN.getErrorCode())) {

            throw new TokenException(INVALID_REFRESH_TOKEN);
        }

        // 잘못된 access 토큰 값 에러
        if (tokenProvider.validAccessToken(accessToken)
            .equals(INVALID_ACCESS_TOKEN.getErrorCode())) {

            throw new TokenException(INVALID_ACCESS_TOKEN);
        }

        // Access 토큰에서 userId 가져오기
        Authentication authentication = tokenProvider.getAuthentication(accessToken);

        // userId로 User 검색
        User user = userRepository.findById(authentication.getName())
            .orElseThrow(() -> new UserException(NOT_FOUND_USER));

        // User 에 저장된 refresh 토큰 값과 일치하지 않음
        if (!user.getRefreshToken().equals(refreshToken)) {
            throw new TokenException(WRONG_REFRESH_TOKEN);
        }

        // access 토큰 재발급, refresh 토큰 재발급, refresh 토큰 값 저장
        String newAccessToken = tokenProvider.generateAccessToken(user.getEmail(), user.getRoles());
        String newRefreshToken = tokenProvider.generateRefreshToken(user.getRoles());
        user.setRefreshToken(newRefreshToken);

        return TokenDto.builder()
            .accessToken(newAccessToken)
            .refreshToken(newRefreshToken)
            .build();
    }
}
