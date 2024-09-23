package com.country_service.country_service_backend.globalerrorhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import com.country_service.country_service_backend.exception.CountriesNowClientException;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalErrorHandler {
	
    @ExceptionHandler
    public ResponseEntity<String> handleClientException(CountriesNowClientException exception) { // Handling CountriesNowClientException exception.

        log.error("Exception Caught in handleClientException: {}", exception.getMessage(), exception);
        
        return ResponseEntity.status(exception.getStatusCode()).body(exception.getMessage()); // We can map error messages  here dynamically, like such
    }


    @ExceptionHandler
    public ResponseEntity<String> handleRuntimeException(ResponseStatusException exception) { // Handling ResponseStatusException exception, meaning if navigate address where is no end point mapping.

    	log.error("Runtime Exception Caught: {}", exception.getMessage(), exception);
    	
        return ResponseEntity.status(exception.getStatusCode()).body(exception.getMessage());  // We can map error messages  here dynamically, like such
        
    }

    
    @ExceptionHandler
    public ResponseEntity<String> handleRuntimeException(RuntimeException exception) { // Handling RuntimeException exception, meaning anything else will be mapped here.
    	 
    	log.error("Runtime Exception Caught: {}", exception.getMessage(), exception);
    	
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());  // We can map error messages  here dynamically, like such
        
    }
    
    // Add here more specific exceptions if there will be in future.
	
}