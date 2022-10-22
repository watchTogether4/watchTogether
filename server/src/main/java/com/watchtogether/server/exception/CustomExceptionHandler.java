package com.watchtogether.server.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler({UserException.class})
    public ResponseEntity<ExceptionResponse> UserException(final UserException e) {
        log.error("UserException is occurred. ", e);

        return ResponseEntity.badRequest()
            .body(new ExceptionResponse(e.getUserErrorCode(), e.getMessage()));
    }

}
