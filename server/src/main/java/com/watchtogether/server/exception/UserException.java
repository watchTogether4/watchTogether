package com.watchtogether.server.exception;


import com.watchtogether.server.exception.type.UserErrorCode;
import lombok.Getter;

@Getter
public class UserException extends RuntimeException {

    private final UserErrorCode userErrorCode;
    private final int errorStatus;

    public UserException(UserErrorCode userErrorCode) {
        super(userErrorCode.getDetail());
        this.errorStatus = userErrorCode.getErrorStatus();
        this.userErrorCode = userErrorCode;
    }
}
