package com.watchtogether.server.exception;


import com.watchtogether.server.exception.type.OttErrorCode;
import com.watchtogether.server.exception.type.TransactionErrorCode;
import lombok.Getter;

@Getter
public class OttException extends RuntimeException {

    private final OttErrorCode ottErrorCode;
    private final int errorStatus;

    public OttException(OttErrorCode ottErrorCode) {
        super(ottErrorCode.getDetail());
        this.errorStatus = ottErrorCode.getErrorStatus();
        this.ottErrorCode = ottErrorCode;
    }
}
