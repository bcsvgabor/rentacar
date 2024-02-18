package com.myrepo.rentacar.exceptions;

import org.springframework.http.HttpStatus;

@ExceptionStatusCode(HttpStatus.UNAUTHORIZED)
public class UnathorizedUserException extends RuntimeException {

    public UnathorizedUserException(String message) {
        super(message);
    }

    public UnathorizedUserException(String message, Throwable cause) {
        super(message, cause);
    }
}

