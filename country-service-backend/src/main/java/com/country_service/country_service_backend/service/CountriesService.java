package com.country_service.country_service_backend.service;

import reactor.core.publisher.Mono;

public interface CountriesService {
	
	public Mono<?> getCountries();

}
