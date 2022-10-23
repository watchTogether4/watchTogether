package com.watchtogether.server.exception;

import com.watchtogether.server.exception.type.AuthErrorCode;
import com.watchtogether.server.exception.type.UserErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// 에러 발생시 던져줄 model class
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthExceptionResponse {

    private int status;
    private AuthErrorCode errorCode;
    private String message;




}
