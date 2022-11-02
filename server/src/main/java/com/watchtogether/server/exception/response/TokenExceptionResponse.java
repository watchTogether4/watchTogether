package com.watchtogether.server.exception.response;

import com.watchtogether.server.exception.type.TokenErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// 에러 발생 시 던져줄 model class
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenExceptionResponse {

    private int status;
    private TokenErrorCode errorCode;
    private String Message;

}
