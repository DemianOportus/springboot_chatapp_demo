package com.dfm.chatapp.exception;

import org.springframework.http.HttpStatus;

public class ServiceException extends RuntimeException {
    private HttpStatus errorType;

    public ServiceException(String message, HttpStatus errorType) {
        super(message);
        this.errorType = errorType;
    }

    public HttpStatus getErrorType() {
        return errorType;
    }
}
