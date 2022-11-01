package com.watchtogether.server.users.service;

import com.watchtogether.server.users.domain.dto.TokenDto;
import javax.servlet.http.HttpServletRequest;

public interface JwtService {

    TokenDto updateToken(String accessToken, String refreshToken);

    void signOutUser(HttpServletRequest request);
}
