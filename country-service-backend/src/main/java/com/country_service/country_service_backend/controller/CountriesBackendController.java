package com.country_service.country_service_backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.country_service.country_service_backend.service.CountriesServiceImpl;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/countries/v1")
public class CountriesBackendController {

	private CountriesServiceImpl countriesService;

    public CountriesBackendController(CountriesServiceImpl countriesService) {
		this.countriesService = countriesService;
	}


	@GetMapping("/")
    public Mono<?> getAllCountries( ) {
        return countriesService.getCountries();
    }
    

    @GetMapping("/{nameOfCountry}")
    public Flux<String> getInformationAboutCountry(@PathVariable String nameOfCountry) {
    	return Flux.fromIterable(List.of("Finland", "FI","5491817", "flag url"))
        		.log();
        
    }

    // TODO(Heikki, StreamAPI) Try to make using SSE a  streaming end-point. Experiment.

}
