package com.country_service.country_service_backend.client;


import com.country_service.country_service_backend.dto.countries.flag.images.CountryFlagImageInfoResponseDto;
import com.country_service.country_service_backend.dto.countries.iso.CountryIsoResponseDto;
import com.country_service.country_service_backend.dto.countries.population.CountryPopulationSingleCountResponseDto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CountriesNowRestClient {
	
	/* Flux since REST endpoint returns multiple values, even thought its wrapped in Mono */
	Flux<CountryIsoResponseDto> getCountriesWithIsoByGet();

	@Deprecated
	Mono<String> getCountyWithPopulationByPost(String countryName);
	
	/* Flux since REST endpoint returns multiple values, even thought its wrapped in Mono */
	Flux<CountryPopulationSingleCountResponseDto> getCountyWithPopulationByGet(String countryName);
	
	@Deprecated
	Mono<?> getCountyWithWithFlagUrlByPost(String countryIso2);
	Mono<CountryFlagImageInfoResponseDto> getCountyWithWithFlagUrlByGet(String countryName);
	

}
