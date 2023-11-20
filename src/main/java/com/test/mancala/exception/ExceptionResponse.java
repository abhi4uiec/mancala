package com.test.mancala.exception;

import org.springframework.http.HttpStatus;

public record ExceptionResponse(String message, HttpStatus status) { }
