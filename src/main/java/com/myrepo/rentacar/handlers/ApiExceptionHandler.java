package com.myrepo.rentacar.handlers;

import com.myrepo.rentacar.exceptions.ExceptionStatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;

@ControllerAdvice
public class ApiExceptionHandler {

    public Logger logger = LoggerFactory.getLogger(ApiExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleExceptions(Exception e) {
        HashMap<String, String> response = new HashMap<>();

        HttpStatus statusCode = getHttpStatus(e);
        if (statusCode.is5xxServerError()) {
            logException(e, e.getMessage());
        }
        response.put("message", e.getMessage());

        return new ResponseEntity<>(response, statusCode);
    }

    private HttpStatus getHttpStatus(Exception e) {
        if (!e.getClass().isAnnotationPresent(ExceptionStatusCode.class)) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return e.getClass().getAnnotation(ExceptionStatusCode.class).value();
    }

    private void logException(Exception e, String message) {
        logger.error(message, e);
    }
}
