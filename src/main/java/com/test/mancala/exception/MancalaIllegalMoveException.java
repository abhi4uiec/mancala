package com.test.mancala.exception;

public class MancalaIllegalMoveException extends RuntimeException {

    public MancalaIllegalMoveException(final String message) {
        super(message);
    }
}
