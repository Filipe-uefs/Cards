package com.cards.exception;


public class InvalidInputException extends RuntimeException {

    public InvalidInputException(String messageError) {
        super(messageError);
    }
}
