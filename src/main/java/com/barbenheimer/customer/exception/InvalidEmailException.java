package com.barbenheimer.customer.exception;

public class InvalidEmailException extends RuntimeException{

    public InvalidEmailException(String message) {
        super(message);
    }
}
