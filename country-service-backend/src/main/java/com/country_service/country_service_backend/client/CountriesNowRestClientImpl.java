package com.country_service.country_service_backend.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.country_service.country_service_backend.domain.Country;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/*
 *	This class is responsible to interacting with CountriesNow REST API end point.
 *	URL: https://countriesnow.space/
 */
@Component
@Slf4j
public class CountriesNowRestClientImpl implements CountriesNowRestClient {

    private WebClient webClient;

    @Value("${restClient.countriesNowUrl}")
    private String countriesNowUrl;

    // Constructor injection proffered!
    public CountriesNowRestClientImpl(WebClient webClient) {
        this.webClient = webClient;
    }

	@Override
	public Mono<?> getCountries() {
		String url = countriesNowUrl.concat("iso");
		
	        return webClient.get()
	                .uri(url)
	                .retrieve()
	                .bodyToMono(Country.class);
		
	}

	@Override
	public void getCountryInfoByName(String name) {
		//TODO
	}

}
