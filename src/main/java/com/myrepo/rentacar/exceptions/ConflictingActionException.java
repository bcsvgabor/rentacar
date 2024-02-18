package com.myrepo.rentacar.exceptions;

import org.springframework.http.HttpStatus;

@ExceptionStatusCode(HttpStatus.CONFLICT)
public class ConflictingActionException extends RuntimeException{
    public ConflictingActionException(String message) {
        super(message);
    }

    public ConflictingActionException(String message, Throwable cause) {
        super(message, cause);
    }
}
