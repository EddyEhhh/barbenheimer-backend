package com.barbenheimer.movieservice.exception;

public class InvalidAuthenticationException extends RuntimeException {
    public InvalidAuthenticationException(String s) {
        super(s);
    }
}
