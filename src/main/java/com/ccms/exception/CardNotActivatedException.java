package com.ccms.exception;

public class CardNotActivatedException extends RuntimeException {
    public CardNotActivatedException(String message) {
        super(message);
    }
}
