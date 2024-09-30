package com.country_service.country_service_backend.globalerrorhandler;

import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import com.country_service.country_service_backend.exception.CountriesNowClientException;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;

/*
 * Logging is called handleClientException for reason that, "Client exceptions refer to errors that are caused by incorrect or invalid requests made by the client". They all comes from invalid user input.
 * Role of this class will be handle Client exceptions, for now. 
 */

@ControllerAdvice
@Slf4j
public class GlobalErrorHandler {
	
    @ExceptionHandler
    public ResponseEntity<String> handleClientException(CountriesNowClientException exception) { // Handling CountriesNowClientException exception.

        log.error("Exception Caught in handleClientException: {}", exception.getMessage(), exception);
        
        return ResponseEntity.status(exception.getStatusCode()).body(exception.getMessage()); // We can map error messages  here dynamically, like such.
    }


    @ExceptionHandler
    public ResponseEntity<String> handleRequestBodyError(ConstraintViolationException exception) { //Handling Bean validation error.

    	log.error("Exception Caught in handleClientException: {}", exception.getMessage(), exception);
    	
    	// We can extract bean validation errors like such.
    	var error = exception.getConstraintViolations().stream()
    			.map(ConstraintViolation::getMessage).sorted().collect(Collectors.joining(",")); // We get all ConstraintViolation which have occurred, sort and put them neatly waiting to processed later.  
    	// This not truly needed, its for future if there would be multiple classes to be validated. For now just single field.
    	
    	log.error("Errors is : {} ", error);
    	
    	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error); // We can map error messages  here dynamically, like such. 
    }

    
    @ExceptionHandler
    public ResponseEntity<String> handleRuntimeException(ResponseStatusException exception) { // Handling ResponseStatusException exception, meaning if navigate address where is no end point mapping.

    	log.error("Exception Caught in handleClientException: {}", exception.getMessage(), exception);
    	
        return ResponseEntity.status(exception.getStatusCode()).body(exception.getMessage());  // We can map error messages  here dynamically, like such.
        
    }

    // Add here more specific exceptions if there will be in future.
    
    @ExceptionHandler
    public ResponseEntity<String> handleRuntimeException(RuntimeException exception) { // Handling RuntimeException exception, meaning anything else will be mapped here.
    	 
    	log.error("Exception Caught in handleClientException: {}", exception.getMessage(), exception);
    	
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());  // We can map error messages  here dynamically, like such.
        
    }
    
	
}