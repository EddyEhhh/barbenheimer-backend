package com.distinction.barbenheimer.exception;

import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.ErrorResponse;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message){
        super(message);

    }


}
