package com.country_service.country_service_backend.service;

import com.country_service.country_service_backend.domain.Countries;
import com.country_service.country_service_backend.domain.Country;

import reactor.core.publisher.Mono;

public interface CountriesService {
	
	public Mono<?> getBetaStuff();
	
	public Mono<Countries> getCountries();
	public Mono<Country> getInformationAboutCountry(String countryName);
}
