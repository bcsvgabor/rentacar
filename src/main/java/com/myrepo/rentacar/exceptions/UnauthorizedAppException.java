package com.myrepo.rentacar.exceptions;

import org.springframework.http.HttpStatus;

@ExceptionStatusCode(HttpStatus.FORBIDDEN)
public class UnauthorizedAppException extends RuntimeException {
    public UnauthorizedAppException(String message) {
        super(message);
    }

    public UnauthorizedAppException(String message, Throwable cause) {
        super(message, cause);
    }
}
