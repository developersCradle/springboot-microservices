package com.country_service.country_service_backend.client;


import com.country_service.country_service_backend.dto.countries.iso.CountryIsoResponseDto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CountriesNowRestClient {
	
	/* Flux since REST endpoint returns multiple values */
	Flux<CountryIsoResponseDto> getCountriesWithIsoByGet();

	@Deprecated
	Mono<String> getCountyWithPopulationByPost(String countryName);
	Mono<String> getCountyWithPopulationByGet(String countryName);
	
	Mono<?> getCountyWithWithFlagUrlByPost(String countryIso2);
	

}
