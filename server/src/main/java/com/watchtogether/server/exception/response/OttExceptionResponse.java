package com.watchtogether.server.exception.response;

import com.watchtogether.server.exception.type.OttErrorCode;
import com.watchtogether.server.exception.type.TransactionErrorCode;
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
public class OttExceptionResponse {

    private int status;
    private OttErrorCode errorCode;
    private String message;

}
