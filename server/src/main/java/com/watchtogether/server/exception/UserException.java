package com.watchtogether.server.exception;


import com.watchtogether.server.exception.type.UserErrorCode;
import lombok.Getter;

@Getter
public class UserException extends RuntimeException {

    private final UserErrorCode userErrorCode;

    public UserException(UserErrorCode userErrorCode) {
        super(userErrorCode.getDetail());
        this.userErrorCode = userErrorCode;
    }
}
