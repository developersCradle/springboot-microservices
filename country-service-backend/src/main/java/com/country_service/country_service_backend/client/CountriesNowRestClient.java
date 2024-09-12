package com.country_service.country_service_backend.client;

import reactor.core.publisher.Mono;

public interface CountriesNowRestClient {
	
    Mono<?> getCountries();

    void getCountryInfoByName(String name);
}
