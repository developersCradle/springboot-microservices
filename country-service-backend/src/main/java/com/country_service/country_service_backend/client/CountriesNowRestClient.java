package com.country_service.country_service_backend.client;


import com.country_service.country_service_backend.dto.countries.capital.CountryCapitalInfoResponseDto;
import com.country_service.country_service_backend.dto.countries.flag.images.CountryFlagImageInfoResponseDto;
import com.country_service.country_service_backend.dto.countries.iso.CountryIsoResponseDto;
import com.country_service.country_service_backend.dto.countries.population.CountryPopulationSingleCountResponseDto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CountriesNowRestClient {
	
	/* Flux since REST end point returns multiple values, even thought it will be wrapped into the Mono in the end */
	Flux<CountryIsoResponseDto> getCountriesWithIsoByGet();

	@Deprecated
	Mono<String> getCountryWithPopulationByPost(String countryName);
	/* Flux since REST end point returns multiple values, even thought it will be wrapped into the Mono in the end */
	Flux<CountryPopulationSingleCountResponseDto> getCountyWithPopulationByGet(String countryName);
	
	@Deprecated
	Mono<String> getCountyWithFlagUrlByPost(String countryIso2);
	Mono<CountryFlagImageInfoResponseDto> getCountryWithFlagUrlByGet(String countryName);
	
	@Deprecated
	Mono<String> getCountyWithCapitalByPost(String countryName);
	Mono<CountryCapitalInfoResponseDto> getCountyWithCapitalByGet(String countryName);


}
