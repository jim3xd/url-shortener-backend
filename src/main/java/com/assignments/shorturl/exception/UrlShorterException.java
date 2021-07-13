package com.assignments.shorturl.exception;


import org.springframework.http.HttpStatus;

public class UrlShorterException extends Exception {
    private HttpStatus status;

    public UrlShorterException(String message, HttpStatus statusCode) {
        super(message);
        this.status = statusCode;
    }

    public HttpStatus getStatus() {
        return status;
    }

}
