package com.cards.exception;

public class BusinessException extends RuntimeException {

    public BusinessException() {}
    public BusinessException(String message, Exception exception) {
        super(message, exception);
    }
}
