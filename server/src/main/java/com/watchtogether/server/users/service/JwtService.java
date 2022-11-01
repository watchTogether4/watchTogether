package com.watchtogether.server.users.service;

import com.watchtogether.server.users.domain.dto.TokenDto;

public interface JwtService {

    TokenDto updateToken(String accessToken, String refreshToken);
}
