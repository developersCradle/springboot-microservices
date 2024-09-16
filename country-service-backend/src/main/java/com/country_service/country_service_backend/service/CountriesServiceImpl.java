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

	/*
	* Returns Countries.
	*/
	
	@Override
	public Mono<Countries> getCountries() {
		
		/*
		*  Mapping Flux elements into Mono of list, since it was in specifications.
		*/
		return countriesNowRestClient.getCountriesWithIsoByGet()
				.map(CountryIsoResponseDto -> new Country(CountryIsoResponseDto.getName(), CountryIsoResponseDto.getIso2())).collectList()
				.map(Countries::new); // Iso2 will be used, since it was the same format as in specifications.
	}

	/*
	* Returns InformationAboutCountry.
	*/
	
	@Override
	public Mono<Country> getInformationAboutCountry(String countryName) {
		
		
		// Merge Country here
		
		// return countriesNowRestClient.getCountyWithPopulationByGet("Finland").last() for latest population from API.
		// countriesNowRestClient.getCountyWithWithFlagUrlByGet("Finland") for flag url from API.
		// countriesNowRestClient.getCountyWithCapitalByGet("Finland"); for getting capital
		
		return Mono.just(new Country("Country name","Country code","Capital", "1000 people", "some url"));
	}

	@Override
	public Mono<?> getBetaStuff() {
		
		return countriesNowRestClient.getCountyWithCapitalByGet("Finland");
	}

}

