package com.country_service.country_service_backend.globalerrorhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.country_service.country_service_backend.exceptionhandler.CountriesNowClientException;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalErrorHandler {
	
    @ExceptionHandler
    public ResponseEntity<String> handleClientException(CountriesNowClientException exception) { // Handling CountriesNowClientException exception.

        log.error("Exception Caught in handleClientException: {}", exception.getMessage());
        return ResponseEntity.status(exception.getStatusCode()).body(exception.getMessage()); // We can map error messages dynamically.
    }

    @ExceptionHandler
    public ResponseEntity<String> handleRuntimeException(RuntimeException exception) { // Handling RuntimeException exception.

        log.error("Exception Caught in handleClientException: {}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
    }
	
}