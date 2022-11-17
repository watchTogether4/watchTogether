package com.watchtogether.server.exception.handler;

import static com.watchtogether.server.exception.type.AuthErrorCode.EXPIRED_TOKEN;
import static com.watchtogether.server.exception.type.AuthErrorCode.INVALID_TOKEN;
import static com.watchtogether.server.exception.type.AuthErrorCode.IS_EMPTY_TOKEN;
import static com.watchtogether.server.exception.type.AuthErrorCode.IS_SIGN_OUT_TOKEN;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.watchtogether.server.exception.response.AuthExceptionResponse;
import com.watchtogether.server.exception.type.AuthErrorCode;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

@Slf4j
public class AuthenticationEntryPointHandler implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
        AuthenticationException authException) throws IOException {

        String exception = (String) request.getAttribute("exception");
        AuthErrorCode errorCode;

        // 토큰이 없는 경우
        if (exception == null) {
            errorCode = IS_EMPTY_TOKEN;
            setResponse(response, errorCode);
        }
        // 토큰이 만료된 경우
        else if (exception.equals(EXPIRED_TOKEN.getErrorCode())) {
            errorCode = EXPIRED_TOKEN;
            setResponse(response, errorCode);
        }
        // 토큰 서명 값이 일치하지 않은 경우
        else if (exception.equals(INVALID_TOKEN.getErrorCode())) {
            errorCode = INVALID_TOKEN;
            setResponse(response, errorCode);
        }
        //로그아웃 처리된 토큰 값인 경우
        else if (exception.equals(IS_EMPTY_TOKEN.getErrorCode())) {
            errorCode = IS_SIGN_OUT_TOKEN;
            setResponse(response, errorCode);
        }
    }

    private void setResponse(HttpServletResponse response, AuthErrorCode errorCode)
        throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        String json = objectMapper.writeValueAsString(AuthExceptionResponse.builder()
            .status(errorCode.getErrorStatus())
            .errorCode(errorCode.getErrorCode())
            .message(errorCode.getDetail())
            .build());

        PrintWriter writer = response.getWriter();
        writer.write(json);
        writer.flush();
    }
}
