package com.watchtogether.server.exception;


import com.watchtogether.server.exception.type.TransactionErrorCode;
import lombok.Getter;

@Getter
public class TransactionException extends RuntimeException {

    private final TransactionErrorCode transactionErrorCode;
    private final int errorStatus;

    public TransactionException(TransactionErrorCode transactionErrorCode) {
        super(transactionErrorCode.getDetail());
        this.errorStatus = transactionErrorCode.getErrorStatus();
        this.transactionErrorCode = transactionErrorCode;
    }
}
