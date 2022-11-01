package com.watchtogether.server.exception;


import com.watchtogether.server.exception.type.TokenErrorCode;
import lombok.Getter;

@Getter
public class TokenException extends RuntimeException {

    private final TokenErrorCode refreshTokenErrorCode;
    private final int errorStatus;

    public TokenException(TokenErrorCode refreshTokenErrorCode) {
        super(refreshTokenErrorCode.getDetail());
        this.errorStatus = refreshTokenErrorCode.getErrorStatus();
        this.refreshTokenErrorCode = refreshTokenErrorCode;
    }
}
