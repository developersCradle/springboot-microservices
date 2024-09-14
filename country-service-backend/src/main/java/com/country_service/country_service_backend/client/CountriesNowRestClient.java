package com.country_service.country_service_backend.client;


import com.country_service.country_service_backend.dto.CountryIsoDto;

import reactor.core.publisher.Flux;

public interface CountriesNowRestClient {
	
	/* Flux since REST endpoint returns multiple values */
	Flux<CountryIsoDto> getCountriesWithIso();

    void getCountryInfoByName(String name);
}
