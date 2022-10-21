package com.watchtogether.server.exception;

import com.watchtogether.server.exception.type.PartyErrorCode;
import lombok.Getter;

@Getter
public class PartyException extends RuntimeException{

    private final PartyErrorCode partyErrorCode;

    public  PartyException(PartyErrorCode partyErrorCode){
        super(partyErrorCode.getDetail());
        this.partyErrorCode = partyErrorCode;
    }

}
