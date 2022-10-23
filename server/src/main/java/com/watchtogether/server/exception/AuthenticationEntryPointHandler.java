package com.watchtogether.server.exception;

import static com.watchtogether.server.exception.type.AuthErrorCode.UNAUTHORIZED;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

public class AuthenticationEntryPointHandler implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
        AuthenticationException authException) throws IOException, ServletException {
        ObjectMapper objectMapper = new ObjectMapper();

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json;charset=UTF-8");
        String json = objectMapper.writeValueAsString(AuthExceptionResponse.builder()
            .status(UNAUTHORIZED.getErrorStatus())
            .errorCode(UNAUTHORIZED)
            .message(UNAUTHORIZED.getDetail())
            .build());

        PrintWriter writer = response.getWriter();
        writer.write(json);
        writer.flush();
    }
}
