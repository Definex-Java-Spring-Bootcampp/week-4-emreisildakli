package com.patika.kredinbizdeservice.exceptions;

public class KredinbizdeException extends RuntimeException {

    private final String message;

    public KredinbizdeException(String message) {
        super(message);
        this.message = message;
    }
}
