package com.watchtogether.server.exception.handler;

import com.watchtogether.server.exception.PartyException;
import com.watchtogether.server.exception.response.UserExceptionResponse;
import com.watchtogether.server.exception.UserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler({UserException.class})
    public ResponseEntity<UserExceptionResponse> UserException(final UserException e) {
        log.error("UserException is occurred. ", e);

        return ResponseEntity.badRequest()
            .body(new UserExceptionResponse(e.getErrorStatus(),e.getUserErrorCode(), e.getMessage()));
    }
    @ExceptionHandler({PartyException.class})
    public ResponseEntity<UserExceptionResponse> PartyException(final UserException e) {
        log.error("UserException is occurred. ", e);

        return ResponseEntity.badRequest()
                .body(new UserExceptionResponse(e.getErrorStatus(),e.getUserErrorCode(), e.getMessage()));
    }

}
