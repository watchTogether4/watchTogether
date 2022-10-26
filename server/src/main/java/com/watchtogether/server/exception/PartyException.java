package com.watchtogether.server.exception;

import com.watchtogether.server.exception.type.PartyErrorCode;
import lombok.Getter;

@Getter
public class PartyException extends RuntimeException{

    private final PartyErrorCode partyErrorCode;
    private final int errorStatus;

    public  PartyException(PartyErrorCode partyErrorCode){
        super(partyErrorCode.getDetail());
        this.errorStatus = partyErrorCode.getErrorStatus();
        this.partyErrorCode = partyErrorCode;
    }

}
