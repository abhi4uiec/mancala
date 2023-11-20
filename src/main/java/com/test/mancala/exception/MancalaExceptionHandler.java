package com.test.mancala.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Methods annotated with @ExceptionHandler are shared globally across multiple @Controller components
 * to capture exceptions and translate them to HTTP responses
 */
@RestControllerAdvice
public class MancalaExceptionHandler {

    @ExceptionHandler(MancalaException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final ResponseEntity handleMancalaException(final MancalaException ex) {
        ExceptionResponse response = new ExceptionResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        return ResponseEntity.status(response.status()).body(response);
    }

    @ExceptionHandler(MancalaIllegalMoveException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final ResponseEntity handleIllegalMove(final MancalaIllegalMoveException ex) {
        ExceptionResponse response = new ExceptionResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        return ResponseEntity.status(response.status()).body(response);
    }

}
