package com.country_service.country_service_backend.service;

import com.country_service.country_service_backend.domain.Countries;

import reactor.core.publisher.Mono;

public interface CountriesService {
	
	public Mono<Countries> getCountries();

}
