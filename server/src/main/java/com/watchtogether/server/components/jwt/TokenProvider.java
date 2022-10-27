package com.watchtogether.server.components.jwt;

import static com.watchtogether.server.exception.type.AuthErrorCode.EXPIRED_TOKEN;
import static com.watchtogether.server.exception.type.AuthErrorCode.INVALID_TOKEN;

import com.watchtogether.server.users.service.impl.UserServiceImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenProvider {

    private static final long TOKEN_EXPIRE_TIME = 1000 * 60 * 1;   // 1 hour
    private static final String KEY_ROLES = "roles";
    private final UserServiceImpl userServiceImpl;

    @Value("{spring.jwt.secret}")
    private String secretKey;

    /**
     * 토근 생성(발급)
     *
     * @param userId 사용자 이메일
     * @param roles  사용자 역할
     * @return Jwt 값
     */
    public String generateToken(String userId, String roles) {

        // 사용의 권한정보 저장
        Claims claims = Jwts.claims().setSubject(userId);
        claims.put(KEY_ROLES, roles);

        // 토큰 생성된 시간
        Date now = new Date();

        // 토큰 만료 시간
        Date expiredDate = new Date(now.getTime() + TOKEN_EXPIRE_TIME);

        // 토큰에 주입
        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now) // 토큰 생성된 시간
            .setExpiration(expiredDate) // 토큰 만료 시간
            .signWith(SignatureAlgorithm.HS512, secretKey) // 사용할 암호화 알고리즘, 비밀키
            .compact();
    }

    public Authentication getAuthentication(String jwt, HttpServletRequest request) {
        UserDetails userDetails = this.userServiceImpl.loadUserByUsername(getUserId(jwt, request));

        return new UsernamePasswordAuthenticationToken(userDetails, "",
            userDetails.getAuthorities());
    }


    public String getUserId(String token, HttpServletRequest request) {
        return parseClaims(token, request).getSubject();
    }

    /**
     * 토큰 유효성 검사
     *
     * @param token   토큰 값
     * @param request HttpServletRequest
     * @return true or false
     */
    public boolean validToken(String token, HttpServletRequest request) {
        // 토큰이 빈 값인 경우
        if (!StringUtils.hasText(token)) {
            return false;
        }

        // 토큰의 만료 여부 확인
        // 토큰값의 일치 여부 확인
        Claims claims = parseClaims(token, request);
        return !claims.getExpiration().before(new Date());
    }


    private Claims parseClaims(String token, HttpServletRequest request) {

        Claims claims = null;

        try {
            claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();

        } catch (ExpiredJwtException e) {
            log.warn("ExpiredJwtException : " + e);
            request.setAttribute("exception", EXPIRED_TOKEN.getErrorCode());
            return e.getClaims();

        } catch (JwtException e) {
            log.warn("JwtException : " + e);
            request.setAttribute("exception", INVALID_TOKEN.getErrorCode());
        }
        return claims;
    }
}
