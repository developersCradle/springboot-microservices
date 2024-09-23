package com.country_service.country_service_backend.exception;

public class CountriesNowServerException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private String message;

    public CountriesNowServerException(String message) {
        super(message);
        this.message = message;
    }
}
