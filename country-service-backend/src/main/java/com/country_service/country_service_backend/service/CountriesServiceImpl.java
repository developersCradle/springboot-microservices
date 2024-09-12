package com.country_service.country_service_backend.service;

import org.springframework.stereotype.Service;

import com.country_service.country_service_backend.client.CountriesNowRestClientImpl;

import reactor.core.publisher.Mono;

/*
 *	This class is responsible to merging and managing data from external datasources. In this case external API.
 */
@Service
public class CountriesServiceImpl implements CountriesService {

    private CountriesNowRestClientImpl countriesNowRestClient;

    public CountriesServiceImpl(CountriesNowRestClientImpl countriesNowRestClient) {
        this.countriesNowRestClient = countriesNowRestClient;
    }
    
	@Override
	public Mono<?> getCountries() {
		return countriesNowRestClient.getCountries();

		
	}

}
