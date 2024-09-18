package com.country_service.country_service_backend.exceptionhandler;

import org.springframework.web.bind.annotation.ControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalErrorHandler {
	//TODO Heikki(Expection handling) Catch here all errors which comes from Controller. Example Bean validation error and custom error.
}