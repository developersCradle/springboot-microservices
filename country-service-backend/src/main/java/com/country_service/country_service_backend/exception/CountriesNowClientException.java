package com.country_service.country_service_backend.exception;

public class CountriesNowClientException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private String message;
    private Integer statusCode;
    
    public CountriesNowClientException(String message, Integer statusCode) {
        super(message);
        this.message = message;
        this.statusCode = statusCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }
}
