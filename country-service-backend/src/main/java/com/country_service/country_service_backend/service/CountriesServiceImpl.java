package com.country_service.country_service_backend.service;


import org.springframework.stereotype.Service;

import com.country_service.country_service_backend.client.CountriesNowRestClientImpl;
import com.country_service.country_service_backend.domain.Countries;
import com.country_service.country_service_backend.domain.Country;
import com.country_service.country_service_backend.dto.countries.capital.CountryCapitalInfoResponseDto;
import com.country_service.country_service_backend.dto.countries.flag.images.CountryFlagImageInfoResponseDto;
import com.country_service.country_service_backend.dto.countries.population.CountryPopulationSingleCountResponseDto;

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
				.map(CountryIsoResponseDto -> new Country(CountryIsoResponseDto.getName(), CountryIsoResponseDto.getIso2())).collectList() // Iso2 will be used, since it was the same format as in specifications.
				.map(Countries::new); // Keeps code concise and avoids unnecessary intermediate steps.
	}
	
	/*
	* Returns InformationAboutCountry.
	*/
	
	@Override
	public Mono<Country> getInformationAboutCountry(String countryName) {
		 Mono<CountryPopulationSingleCountResponseDto> populationMono = countriesNowRestClient.getCountyWithPopulationByGet(countryName).last(); // for latest population from API.
		 Mono<CountryFlagImageInfoResponseDto> flagMono = countriesNowRestClient.getCountryWithFlagUrlByGet(countryName); // for flag URL from API.
		 Mono<CountryCapitalInfoResponseDto> capitalMono = countriesNowRestClient.getCountyWithCapitalByGet(countryName); // for getting capital from API.
		    
		 return Mono.zip(populationMono, flagMono, capitalMono)
		            .map(tuple -> {
		                CountryPopulationSingleCountResponseDto populationDto = tuple.getT1();
		                CountryFlagImageInfoResponseDto flagDto = tuple.getT2();
		                CountryCapitalInfoResponseDto capitalDto = tuple.getT3();
		                
		                
		                return new Country(
		                		flagDto.getName(), // Name of country.
		                		flagDto.getIso2(), // Country_code, Iso2 will be used, since it was the same format as in specifications. Specification ISO_3166-1_alpha-2.
		                		capitalDto.getCapital(), // Capital.         
		                		populationDto.getValue(), // Population, latest which comes from API.
		                		flagDto.getFlag() // URL to flag.              
		                );
		            });
	}


	@Override
	public Mono<?> getBetaStuff() {
		
		return countriesNowRestClient.getCountyWithFlagUrlByPost("Finland");
	}

}

