package com.watchtogether.server.exception.response;

import com.watchtogether.server.exception.type.PartyErrorCode;
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
public class PartyExceptionResponse {

    private int status;

    // todo UserErrorCode 에서 PartyErrorCode로 수정
    private PartyErrorCode errorCode;
    private String Message;

}
