package com.country_service.country_service_backend.exceptionhandler;

public class CountriesNowServerException extends RuntimeException {

    private String message;

    public CountriesNowServerException(String message) {
        super(message);
        this.message = message;
    }
}
