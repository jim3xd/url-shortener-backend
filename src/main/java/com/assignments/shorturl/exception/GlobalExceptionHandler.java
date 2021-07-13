package com.assignments.shorturl.exception;


import com.assignments.shorturl.model.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = UrlShorterException.class)
    public ResponseEntity<ApiResponse> handleException(UrlShorterException e) {
        return ResponseEntity.status(e.getStatus()).body(new ApiResponse(false, e.getLocalizedMessage()));
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ApiResponse> exceptions(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(false, "Something went wrong! Please try again"));
    }
}
