package com.country_service.country_service_backend.client;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import org.springframework.http.HttpStatus;
import com.country_service.country_service_backend.dto.countries.capital.CountryCapitalInfoResponseDto;
import com.country_service.country_service_backend.dto.countries.capital.CountryCapitalResponseDto;
import com.country_service.country_service_backend.dto.countries.flag.images.CountryFlagImageInfoResponseDto;
import com.country_service.country_service_backend.dto.countries.flag.images.CountryFlagImageResponseDto;
import com.country_service.country_service_backend.dto.countries.iso.CountriesIsoResponseDto;
import com.country_service.country_service_backend.dto.countries.iso.CountryIsoResponseDto;
import com.country_service.country_service_backend.dto.countries.population.CountriesPopulationResponseDto;
import com.country_service.country_service_backend.dto.countries.population.CountryPopulationSingleCountResponseDto;
import com.country_service.country_service_backend.exception.CountriesNowClientException;
import com.country_service.country_service_backend.exception.CountriesNowServerException;
import com.country_service.country_service_backend.util.RetryUtil;

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
     * URL: https://countriesnow.space/api/v0.1/countries/iso
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
	                .onStatus(clientResponse -> clientResponse.is4xxClientError(), clientResponse -> {
	                	
	                	log.info("Status code is : {}", clientResponse.statusCode().value()); //Logging error.
	                	
	                	// Error will be wrapped in Mono.
	                	if (clientResponse.statusCode().equals(HttpStatus.NOT_FOUND)) { // If not found, exception wrapped into custom error message.
							return Mono.error(new CountriesNowClientException("There is no Countries with iso code. ", clientResponse.statusCode().value()));
						}
	                
	                 // Get the error message with bodyToMono and flatMap.
	                 return clientResponse.bodyToMono(String.class) // Error code not found.
	                		 .flatMap(responseMessage -> Mono.error( new CountriesNowClientException( 
	                				 responseMessage, clientResponse.statusCode().value()
	                				 )));
	                })
	                .onStatus(clientResponse -> clientResponse.is5xxServerError(), clientResponse -> {
	                	
	                	log.info("Status code is : {}", clientResponse.statusCode().value()); //Logging error.
	                	
	                 // Get the error message with bodyToMono and flatMap.
	                 return clientResponse.bodyToMono(String.class) // Error code not found.
	                		 .flatMap(responseMessage -> Mono.error( new CountriesNowServerException( 
	                			 "Server Exception in CountriesNowRestClient " + responseMessage)));
	                })
	                .bodyToMono(CountriesIsoResponseDto.class) // Map the entire response to a Mono.
	                .flatMapMany(countriesIsoDto -> Flux.fromIterable(countriesIsoDto.getData())) // We make this Flux here, since there is many elements and helps to process this later.
	                .retryWhen(RetryUtil.retrySpec());
	}

	
	

	
	
	/*
	 * Get single country and population data with POST.
	 * URL: https://countriesnow.space/api/v0.1/countries/population
	 * 
	 * @deprecated use {@link #getCountyWithPopulationByGet(String countryName)} instead.
	 * This end point does not work! Check https://github.com/developersCradle/springboot-microservices#problem1
	 * for more!
	 */

	@Deprecated
	@Override
	public Mono<String> getCountryWithPopulationByPost(String countryName) {
		
		String url = countriesNowUrl.concat("population");

//		 url = "https://test.requestcatcher.com/"; // Testing POST working correctly.
		
		Map<String, String> param = new HashMap<>();
		param.put("country", "Finland");
		
		return webClient.post()
				.uri(url)
				.bodyValue(param)
				.retrieve()
				.onStatus(clientResponse -> clientResponse.is4xxClientError(), clientResponse -> {
                	
                	log.info("Status code is : {}", clientResponse.statusCode().value()); //Logging error.
                	
                	// Error will be wrapped in Mono.
                	if (clientResponse.statusCode().equals(HttpStatus.NOT_FOUND)) { // If not found, exception wrapped into custom error message.
						return Mono.error(new CountriesNowClientException("There is no Country with population. ", clientResponse.statusCode().value()));
					}
                
                 // Get the error message with bodyToMono and flatMap.
                 return clientResponse.bodyToMono(String.class) // Error code not found.
                		 .flatMap(responseMessage -> Mono.error( new CountriesNowClientException( 
                				 responseMessage, clientResponse.statusCode().value()
                				 )));
                })
                .onStatus(clientResponse -> clientResponse.is5xxServerError(), clientResponse -> {
                	
                	log.info("Status code is : {}", clientResponse.statusCode().value()); //Logging error.
                	
                 // Get the error message with bodyToMono and flatMap.
                 return clientResponse.bodyToMono(String.class) // Error code not found.
                		 .flatMap(responseMessage -> Mono.error( new CountriesNowServerException( 
                				 "Server Exception in CountriesNowRestClient " + responseMessage)));
                })
				.bodyToMono(String.class)
				.retryWhen(RetryUtil.retrySpec())
				.doOnSuccess(result -> System.out.println("Response from getCountyWithPopulationByPost: " + result));
		
		// TODO(Heikki, API usability) Make redirects from here POST to GET request method, if other does not work -> try another one?
		// Adding to more to this idea. HTTP 101 Switching Protocols: The server is switching to the protocol the client requested?
	}

	/*
	 *  Get single country and population data with GET.
	 *  URL: https://countriesnow.space/api/v0.1/countries/population/q?country=Finland
	 */
	
	@Override
	public Flux<CountryPopulationSingleCountResponseDto> getCountyWithPopulationByGet(String countryName) {
		
		String url = countriesNowUrl.concat("population/q?country={countryName}");
		
		 return webClient.get()
	                .uri(url, countryName)
	                .retrieve()
	                .onStatus(clientResponse -> clientResponse.is4xxClientError(), clientResponse -> {
	                	
	                	log.info("Status code is : {}", clientResponse.statusCode().value()); //Logging error.
	                	
	                	// Error will be wrapped in Mono.
	                	if (clientResponse.statusCode().equals(HttpStatus.NOT_FOUND)) { // If not found, exception wrapped into custom error message.
							return Mono.error(new CountriesNowClientException("There is no Country with population. ", clientResponse.statusCode().value()));
						}
	                
	                 // Get the error message with bodyToMono and flatMap.
	                 return clientResponse.bodyToMono(String.class) // Error code not found.
	                		 .flatMap(responseMessage -> Mono.error( new CountriesNowClientException( 
	                				 responseMessage, clientResponse.statusCode().value()
	                				 )));
	                })
	                .onStatus(clientResponse -> clientResponse.is5xxServerError(), clientResponse -> {
	                	
	                	log.info("Status code is : {}", clientResponse.statusCode().value()); //Logging error.
	                	
	                 // Get the error message with bodyToMono and flatMap.
	                 return clientResponse.bodyToMono(String.class) // Error code not found.
	                		 .flatMap(responseMessage -> Mono.error( new CountriesNowServerException( 
	                				 "Server Exception in CountriesNowRestClient " + responseMessage)));
	                })
	                .bodyToMono(CountriesPopulationResponseDto.class) // Map the entire response to a Mono.
	                .retryWhen(RetryUtil.retrySpec())
		 			.flatMapMany(response -> Flux.fromIterable(response.getData().getPopulationCounts())); // Get only population counts.
		 			};
		 			
		 			

	/*
	 * Get single country and flag URL with POST.
	 * URL: https://countriesnow.space/api/v0.1/countries/flag/images
	 * 
	 * @deprecated use {@link #getCountyWithWithFlagUrlByGet(String countryName)} instead.
	 * This end point does not work! Check https://github.com/developersCradle/springboot-microservices#problem2
	 * for more!
	 */
		 			
	@Deprecated	 			
	@Override
	public Mono<String> getCountyWithFlagUrlByPost(String countryIso2) {
		
		String url = countriesNowUrl.concat("/flag/images");
		
		// url = "https://test.requestcatcher.com/"; // Testing POST working correctly.
		
		Map<String, String> param = new HashMap<>();
		param.put("country", "NG");
		
		return webClient.post()
				.uri(url)
				.bodyValue(param)
				.retrieve()
				.onStatus(clientResponse -> clientResponse.is4xxClientError(), clientResponse -> {
                	
                	log.info("Status code is : {}", clientResponse.statusCode().value()); //Logging error.
                	
                	// Error will be wrapped in Mono.
                	if (clientResponse.statusCode().equals(HttpStatus.NOT_FOUND)) { // If not found, exception wrapped into custom error message.
						return Mono.error(new CountriesNowClientException("There is no Country with flag url. ", clientResponse.statusCode().value()));
					}
                
                 // Get the error message with bodyToMono and flatMap.
                 return clientResponse.bodyToMono(String.class) // Error code not found.
                		 .flatMap(responseMessage -> Mono.error( new CountriesNowClientException( 
                				 responseMessage, clientResponse.statusCode().value()
                				 )));
                })
                .onStatus(clientResponse -> clientResponse.is5xxServerError(), clientResponse -> {
                	
                	log.info("Status code is : {}", clientResponse.statusCode().value()); //Logging error.
                	
                 // Get the error message with bodyToMono and flatMap.
                 return clientResponse.bodyToMono(String.class) // Error code not found.
                		 .flatMap(responseMessage -> Mono.error( new CountriesNowServerException( 
                				 "Server Exception in CountriesNowRestClient " + responseMessage)));
                })
				.bodyToMono(String.class)
				.retryWhen(RetryUtil.retrySpec())
				.doOnSuccess(result -> System.out.println("Response from getCountyWithFlagUrlByPost: " + result));
		
		//TODO(Heikki, API usability) Make redirects from here POST to GET request method, if other does not work -> try another one?
		// Adding to more to this idea. HTTP 101 Switching Protocols: The server is switching to the protocol the client requested?
	}

	/* 
	 * Get single country with flag image GET.
	 * URL: https://countriesnow.space/api/v0.1/countries/flag/images/q?country=Finland
	 */

	@Override
	public Mono<CountryFlagImageInfoResponseDto> getCountryWithFlagUrlByGet(String countryName) {
		
		String url = countriesNowUrl.concat("flag/images/q?country={countryName}");
		
		return webClient.get()
	                .uri(url, countryName)
	                .retrieve()
	                .onStatus(clientResponse -> clientResponse.is4xxClientError(), clientResponse -> {
	                	
	                	log.info("Status code is : {}", clientResponse.statusCode().value()); //Logging error.
	                	
	                	// Error will be wrapped in Mono.
	                	if (clientResponse.statusCode().equals(HttpStatus.NOT_FOUND)) { // If not found, exception wrapped into custom error message.
							return Mono.error(new CountriesNowClientException("There is no Country with flag url. ", clientResponse.statusCode().value()));
						}
	                
	                 // Get the error message with bodyToMono and flatMap.
	                 return clientResponse.bodyToMono(String.class) // Error code not found.
	                		 .flatMap(responseMessage -> Mono.error( new CountriesNowClientException( 
	                				 responseMessage, clientResponse.statusCode().value()
	                				 )));
	                })
	                .onStatus(clientResponse -> clientResponse.is5xxServerError(), clientResponse -> {
	                	
	                	log.info("Status code is : {}", clientResponse.statusCode().value()); //Logging error.
	                	
	                 // Get the error message with bodyToMono and flatMap.
	                 return clientResponse.bodyToMono(String.class) // Error code not found.
	                		 .flatMap(responseMessage -> Mono.error( new CountriesNowServerException( 
	                				 "Server Exception in CountriesNowRestClient " + responseMessage)));
	                })
	                .bodyToMono(CountryFlagImageResponseDto.class)
	                .retryWhen(RetryUtil.retrySpec())
	                .flatMap(response -> Mono.just(response.getData()));
		 }

	/*
	 * Get a specific country and its capital POST.
	 * URL: https://countriesnow.space/api/v0.1/countries/capital
	 * 
	 * @deprecated use {@link #getCountyWithCapitalByGet(String countryName)} instead.
	 * This end point does not work! Check https://github.com/developersCradle/springboot-microservices#problem3
	 * for more!
	 */
	
	@Deprecated
	@Override
	public Mono<String> getCountyWithCapitalByPost(String countryName) {
		
		String url = countriesNowUrl.concat("/capital");

		// url = "https://test.requestcatcher.com/"; // Testing POST working correctly.
		
		Map<String, String> param = new HashMap<>();
		param.put("country", "nigeria");
		
		
		return webClient.post()
				.uri(url)
				.bodyValue(param)
				.retrieve()
				.onStatus(clientResponse -> clientResponse.is4xxClientError(), clientResponse -> {
                	
                	log.info("Status code is : {}", clientResponse.statusCode().value()); //Logging error.
                	
                	// Error will be wrapped in Mono.
                	if (clientResponse.statusCode().equals(HttpStatus.NOT_FOUND)) { // If not found, exception wrapped into custom error message.
						return Mono.error(new CountriesNowClientException("There is no Country with capital. ", clientResponse.statusCode().value()));
					}
                
                 // Get the error message with bodyToMono and flatMap.
                 return clientResponse.bodyToMono(String.class) // Error code not found.
                		 .flatMap(responseMessage -> Mono.error( new CountriesNowClientException( 
                				 responseMessage, clientResponse.statusCode().value()
                				 )));
                })
                .onStatus(clientResponse -> clientResponse.is5xxServerError(), clientResponse -> {
                	
                	log.info("Status code is : {}", clientResponse.statusCode().value()); //Logging error.
                	
                 // Get the error message with bodyToMono and flatMap.
                 return clientResponse.bodyToMono(String.class) // Error code not found.
                		 .flatMap(responseMessage -> Mono.error( new CountriesNowServerException( 
                				 "Server Exception in CountriesNowRestClient " + responseMessage)));
                })
				.bodyToMono(String.class)
				.retryWhen(RetryUtil.retrySpec())
				.doOnSuccess(result -> System.out.println("Response from getCountyWithCapitalPost: " + result));
		
		//TODO(Heikki, API usability) Make redirects from here POST to GET request method, if other does not work -> try another one?
		// Adding to more to this idea. HTTP 101 Switching Protocols: The server is switching to the protocol the client requested?
	}

	/*
	 * Get a specific country and its capital GET.
	 * URL: https://countriesnow.space/api/v0.1/countries/capital/q?country=nigeria
	 */
	
	@Override
	public Mono<CountryCapitalInfoResponseDto> getCountyWithCapitalByGet(String countryName) {

	String url = countriesNowUrl.concat("capital/q?country={countryName}");
	
	 return webClient.get()
               .uri(url, countryName)
               .retrieve()
               .onStatus(clientResponse -> clientResponse.is4xxClientError(), clientResponse -> {
               	
               	log.info("Status code is : {}", clientResponse.statusCode().value()); //Logging error.
               	
               	// Error will be wrapped in Mono.
               	if (clientResponse.statusCode().equals(HttpStatus.NOT_FOUND)) { // If not found, exception wrapped into custom error message.
						return Mono.error(new CountriesNowClientException("There is no Country with capital. ", clientResponse.statusCode().value()));
					}
               
                // Get the error message with bodyToMono and flatMap.
                return clientResponse.bodyToMono(String.class) // Error code not found.
               		 .flatMap(responseMessage -> Mono.error( new CountriesNowClientException( 
               				 responseMessage, clientResponse.statusCode().value()
               				 )));
               })
               .onStatus(clientResponse -> clientResponse.is5xxServerError(), clientResponse -> {
               	
               	log.info("Status code is : {}", clientResponse.statusCode().value()); //Logging error.
               	
                // Get the error message with bodyToMono and flatMap.
                return clientResponse.bodyToMono(String.class) // Error code not found.
               		 .flatMap(responseMessage -> Mono.error( new CountriesNowServerException( 
               				 "Server Exception in CountriesNowRestClient " + responseMessage)));
               })
               .bodyToMono(CountryCapitalResponseDto.class) // Map the entire response to a Mono.
               .retryWhen(RetryUtil.retrySpec())
               .flatMap(response -> Mono.just(response.getData())); // Get only capital information.
	 			
	 };
		
}
