package com.myrepo.rentacar.exceptions;

import org.springframework.http.HttpStatus;

@ExceptionStatusCode(HttpStatus.BAD_REQUEST)
public class ValidationAppException extends RuntimeException {
    public ValidationAppException(String message) {
        super(message);
    }

    public ValidationAppException(String message, Throwable cause) {
        super(message, cause);
    }
}
