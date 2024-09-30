package com.country_service.country_service_backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.country_service.country_service_backend.domain.Countries;
import com.country_service.country_service_backend.domain.Country;
import com.country_service.country_service_backend.service.CountriesServiceImpl;

import jakarta.validation.constraints.Size;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/countries/v1")
@Validated
public class CountriesBackendController {

	private CountriesServiceImpl countriesService;

    // Constructor injection preferred!
    public CountriesBackendController(CountriesServiceImpl countriesService) {
		this.countriesService = countriesService;
	}

    //Testing end point for all these weird API end points.
	@GetMapping("/test")
    public Mono<?> getBetaStuff( ) {
        return countriesService.getBetaStuff();
    }

	@GetMapping("/")
    public Mono<ResponseEntity<Countries>> getAllCountries( ) {
        return countriesService.getCountries()
        		.map(ResponseEntity.ok()::body)
        		.switchIfEmpty(Mono.just(ResponseEntity.notFound().build())) // If empty is returned provide empty Mono.
        		.log(); // If message was OK, put body into inside to the ResponseEntity. With method reference.
    }
	

	// Bean validation, shortest country name in English were 4 character long, like Laos. Longest were United Kingdom of Great Britain and Northern Ireland.
    @GetMapping("/{nameOfCountry}")
    public Mono<ResponseEntity<Country>> getInformationAboutCountry(@PathVariable @Size(
    		  min = 4,
    		  max = 56,
    		  message = "The country name must be between {min} and {max} characters long."
    		) String nameOfCountry) {
    	return countriesService.getInformationAboutCountry(nameOfCountry)
    	        .map(country -> { // If message was OK, put body into inside to the ResponseEntity. Same, but with traditional way.
    	        	return ResponseEntity.ok().body(country);
    			}).switchIfEmpty(Mono.just(ResponseEntity.notFound().build())) // If empty is returned provide empty Mono.
    	        .log();
    }

    // TODO(Heikki, StreamAPI) Try to make using SSE a  streaming end-point. Experiment.

}
