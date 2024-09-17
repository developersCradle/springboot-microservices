package com.country_service.country_service_backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.country_service.country_service_backend.domain.Countries;
import com.country_service.country_service_backend.domain.Country;
import com.country_service.country_service_backend.service.CountriesServiceImpl;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/countries/v1")
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
    public Mono<Countries> getAllCountries( ) {
        return countriesService.getCountries();
    }
    

    @GetMapping("/{nameOfCountry}")
    public Mono<Country> getInformationAboutCountry(@PathVariable String nameOfCountry) {
    	return countriesService.getInformationAboutCountry(nameOfCountry);
    }

    // TODO(Heikki, StreamAPI) Try to make using SSE a  streaming end-point. Experiment.

}
