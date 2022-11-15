package com.watchtogether.server.exception;


import com.watchtogether.server.exception.type.AlertErrorCode;
import lombok.Getter;

@Getter
public class AlertException extends RuntimeException {

    private final AlertErrorCode alertErrorCode;
    private final int errorStatus;

    public AlertException(AlertErrorCode alertErrorCode) {
        super(alertErrorCode.getDetail());
        this.errorStatus = alertErrorCode.getErrorStatus();
        this.alertErrorCode = alertErrorCode;
    }
}
