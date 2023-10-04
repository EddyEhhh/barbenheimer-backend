package com.barbenheimer.ticket.exception;

public class InvalidAuthenticationException extends RuntimeException {
    public InvalidAuthenticationException(String s) {
        super(s);
    }
}
