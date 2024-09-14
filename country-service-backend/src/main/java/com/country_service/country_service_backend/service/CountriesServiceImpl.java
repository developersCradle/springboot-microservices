package com.country_service.country_service_backend.service;


import org.springframework.stereotype.Service;

import com.country_service.country_service_backend.client.CountriesNowRestClientImpl;
import com.country_service.country_service_backend.domain.Countries;
import com.country_service.country_service_backend.domain.Country;

import reactor.core.publisher.Mono;

/*
 *	This class is responsible to merging and managing data from external datasources. In this case external API.
 *	This could be done with reactive operations or with Dto mapper, but reactive operations were chosen.
 */
@Service
public class CountriesServiceImpl implements CountriesService {

	private CountriesNowRestClientImpl countriesNowRestClient;

	// Constructor injection preferred!
	public CountriesServiceImpl(CountriesNowRestClientImpl countriesNowRestClient) {
		this.countriesNowRestClient = countriesNowRestClient;
	}

	@Override
	public Mono<Countries> getCountries() {
		
		/*
		*  Mapping Flux elements into Mono of list, since it was in specifications.
		*/
		return countriesNowRestClient.getCountriesWithIso()
				.map(CountryIsoDto -> new Country(CountryIsoDto.getName(), CountryIsoDto.getIso2())).collectList()
				.map(Countries::new); // Iso2 will be used, since it was the same format as in specifications.
	}

}
