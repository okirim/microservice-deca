package com.zema.commons.exceptions;

import org.springframework.http.HttpStatus;

public class AppException extends RuntimeException{
    private final HttpStatus status;

    public AppException(String message, HttpStatus httpStatus) {
        super(message);
        this.status = httpStatus;
    }

    public HttpStatus getStatus() {
        return status;
    }
}