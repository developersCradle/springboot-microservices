package com.country_service.country_service_backend.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.country_service.country_service_backend.dto.CountriesIsoDto;
import com.country_service.country_service_backend.dto.CountryIsoDto;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

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

    // Constructor injection preferred!
    public CountriesNowRestClientImpl(WebClient webClient) {
        this.webClient = webClient;
    }
    
    /*
     * Retrieves countries with ISO codes.
     * 
     * When we’re expecting multiple results from our computation, database, or external service call, then we should use Flux.
     * https://www.baeldung.com/java-reactor-flux-vs-mono#mono-vs-flux
     */
    
	@Override
	public Flux<CountryIsoDto> getCountriesWithIso() {
		
		String url = countriesNowUrl.concat("iso");
		
		 return webClient.get()
	                .uri(url)
	                .retrieve()
	                .bodyToMono(CountriesIsoDto.class) // Map the entire response to a Mono.
	                .flatMapMany(countriesIsoDto -> Flux.fromIterable(countriesIsoDto.getData())) // We make this FLux here, since there is many elements and helps to process this later.
	                .log();
	}

    
	
	
	@Override
	public void getCountryInfoByName(String name) {
		//TODO
	}

}
