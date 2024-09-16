package com.country_service.country_service_backend.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.country_service.country_service_backend.dto.countries.flag.images.CountryFlagImageInfoResponseDto;
import com.country_service.country_service_backend.dto.countries.flag.images.CountryFlagImageResponseDto;
import com.country_service.country_service_backend.dto.countries.iso.CountriesIsoResponseDto;
import com.country_service.country_service_backend.dto.countries.iso.CountryIsoResponseDto;
import com.country_service.country_service_backend.dto.countries.population.CountriesPopulationResponseDto;
import com.country_service.country_service_backend.dto.countries.population.CountryPopulationSingleCountResponseDto;

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
	 */

	@Deprecated
	@Override
	public Mono<String> getCountyWithPopulationByPost(String countryName) {
		
		String url = countriesNowUrl.concat("population");

		// url = "https://test.requestcatcher.com/"; // Testing POST working correctly.
		
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
	public Flux<CountryPopulationSingleCountResponseDto> getCountyWithPopulationByGet(String countryName) {
		
		String url = countriesNowUrl.concat("population/q?country={countryName}");
		
		 return webClient.get()
	                .uri(url, countryName)
	                .retrieve()
	                .bodyToMono(CountriesPopulationResponseDto.class) // Map the entire response to a Mono.
		 			.flatMapMany(response -> Flux.fromIterable(response.getData().getPopulationCounts())); // Get only population counts.
		 			};
		 			

	/*
	 * Get single country and flag URL with POST.
	 * https://countriesnow.space/api/v0.1/countries/flag/images
	 * 
	 * @deprecated use {@link #getCountyWithWithFlagUrlByGet(String countryName)} instead.
	 * This end point does not work! Check https://github.com/developersCradle/springboot-microservices#problem2
	 * for more!
	 */
		 			
	@Deprecated	 			
	@Override
	public Mono<?> getCountyWithWithFlagUrlByPost(String countryIso2) {
		
		String url = countriesNowUrl.concat("/flag/images");
		
		// url = "https://test.requestcatcher.com/"; // Testing POST working correctly.
		
		return webClient.post()
        .uri(url)
        .bodyValue(new ParamClass("NG"))
        .retrieve()
        .bodyToMono(String.class)
        .doOnSuccess(result -> System.out.println("Response from getCountyWithWithFlagUrlByPost: " + result));
		
		//TODO(Heikki, API usability) Make redirects from here POST to GET request method, if other does not work -> try another one?
		
	}

	/* 
	 * Get single country with flag image GET.
	 * https://countriesnow.space/api/v0.1/countries/flag/images/q?country=Finland
	 */

	@Override
	public Mono<CountryFlagImageInfoResponseDto> getCountyWithWithFlagUrlByGet(String countryName) {
		
		String url = countriesNowUrl.concat("flag/images/q?country={countryName}");
		
		return webClient.get()
	                .uri(url, countryName)
	                .retrieve()
	                .bodyToMono(CountryFlagImageResponseDto.class)
	                .flatMap(response -> Mono.just(response.getData()));
		 }
}
