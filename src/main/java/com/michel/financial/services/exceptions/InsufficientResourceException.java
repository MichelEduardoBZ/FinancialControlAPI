package com.michel.financial.services.exceptions;

public class InsufficientResourceException extends RuntimeException{

    public InsufficientResourceException(String msg) {
        super(msg);
    }
}
