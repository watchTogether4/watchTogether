package com.watchtogether.server.components.jwt;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    // token 헤더에 넣을, 토큰의 유형 지정
    public static final String TOKEN_HEADER = "Authorization";

    // 인증 타입
    public static final String TOKEN_PREFIX = "Bearer ";

    private final TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {

        String token = resolveTokenFromRequest(request);

        // token 유효성 검증
        if (StringUtils.hasText(token) && tokenProvider.validToken(token, request)) {
            Authentication auth = tokenProvider.getAuthentication(token, request);
            SecurityContextHolder.getContext().setAuthentication(auth);

            log.info(String.format("[%s] -> %s", tokenProvider.getUserId(token, request),
                request.getRequestURI()));
        }

        filterChain.doFilter(request, response);

    }

    private String resolveTokenFromRequest(HttpServletRequest request) {

        // token 구조 유형 유효성 검사
        String token = request.getHeader(TOKEN_HEADER);

        if (!ObjectUtils.isEmpty(token) && token.startsWith(TOKEN_PREFIX)) {
            log.info("token's value : " + token.substring(TOKEN_PREFIX.length()));
            return token.substring(TOKEN_PREFIX.length());
        }
        return null;
    }
}
