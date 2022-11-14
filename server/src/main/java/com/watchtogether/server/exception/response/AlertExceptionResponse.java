package com.watchtogether.server.exception.response;

import com.watchtogether.server.exception.type.AlertErrorCode;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AlertExceptionResponse {

    private int status;
    private AlertErrorCode errorCode;
    private String message;

}
