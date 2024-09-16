package com.country_service.country_service_backend.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.country_service.country_service_backend.dto.countries.iso.CountriesIsoResponseDto;
import com.country_service.country_service_backend.dto.countries.iso.CountryIsoResponseDto;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
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

    // Constructor injection preferred!
    public CountriesNowRestClientImpl(WebClient webClient) {
        this.webClient = webClient;
    }
    
    /*
     * Retrieves countries with ISO codes with GET.
     * https://countriesnow.space/api/v0.1/countries/iso
     * 
     * When we’re expecting multiple results from our computation, database, or external service call, then we should use Flux.
     * https://www.baeldung.com/java-reactor-flux-vs-mono#mono-vs-flux
     */
    
	@Override
	public Flux<CountryIsoResponseDto> getCountriesWithIsoByGet() {
		
		String url = countriesNowUrl.concat("iso");
		
		 return webClient.get()
	                .uri(url)
	                .retrieve()
	                .bodyToMono(CountriesIsoResponseDto.class) // Map the entire response to a Mono.
	                .flatMapMany(countriesIsoDto -> Flux.fromIterable(countriesIsoDto.getData())) // We make this Flux here, since there is many elements and helps to process this later.
	                .log();
	}

	
	
	
	/*
	 * Get single country and population data with POST.
	 * https://countriesnow.space/api/v0.1/countries/population
	 * 
	 * @deprecated use {@link #getCountyWithPopulationByGet(String countryName)} instead.
	 * This end point does not work! Check https://github.com/developersCradle/springboot-microservices#problem1
	 * for more!
	 *   
	 */

	@Deprecated
	@Override
	public Mono<String> getCountyWithPopulationByPost(String countryName) {
		
		String url = countriesNowUrl.concat("population");

		//url = "https://test.requestcatcher.com/"; // Testing POST working correctly.
		
		return webClient.post()
        .uri(url)
        .bodyValue(new ParamClass("Finland"))
        .retrieve()
        .bodyToMono(String.class)
        .doOnSuccess(result -> System.out.println("Response from getCountyWithPopulationByPost: " + result));
		
		//TODO(Heikki, API usability) Make redirects from here POST to GET request method, if other does not work -> try another one?
  
	}

	/*
	 *  Get single country and population data with GET.
	 *  https://countriesnow.space/api/v0.1/countries/population/q?country=Finland
	 */
	@Override
	public Mono<String> getCountyWithPopulationByGet(String countryName) {
		return null;
	}

	/*
	 * Get single country and flag URL with POST.
	 */
	@Override
	public Mono<?> getCountyWithWithFlagUrlByPost(String countryIso2) {
		return null;
	}



	
	

}
