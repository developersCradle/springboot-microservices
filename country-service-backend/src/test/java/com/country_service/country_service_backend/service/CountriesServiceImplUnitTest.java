package com.country_service.country_service_backend.service;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.country_service.country_service_backend.client.CountriesNowRestClientImpl;
import com.country_service.country_service_backend.domain.Countries;
import com.country_service.country_service_backend.dto.countries.capital.CountryCapitalInfoResponseDto;
import com.country_service.country_service_backend.dto.countries.flag.images.CountryFlagImageInfoResponseDto;
import com.country_service.country_service_backend.dto.countries.iso.CountryIsoResponseDto;
import com.country_service.country_service_backend.dto.countries.population.CountryPopulationSingleCountResponseDto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
public class CountriesServiceImplUnitTest {

    @Mock
    private CountriesNowRestClientImpl countriesNowRestClient;

    @InjectMocks
    private CountriesServiceImpl countriesServiceImpl;
    
    	
    @Test
    public void getCountries_shouldReturnFlux_whenValidInput() {

    	// Given - START.
    	List<CountryIsoResponseDto> countryIsoResponseDto = Arrays.asList(new CountryIsoResponseDto("Nordea Maa", "NORDEA_ISO_2", "NORDEA_ISO_3"), new CountryIsoResponseDto("Nordea Oulu tiimi", "NORDEA_ISO_2", "NORDEA_ISO_3"), new CountryIsoResponseDto("Nordea Oulu Tiime third", "NORDEA_ISO_3", "NORDEA_ISO_3"));
    	Flux<CountryIsoResponseDto> fluxOfCountryIsoResponse = Flux.fromIterable(countryIsoResponseDto);
    	// Given - END.
    	
    	// When - START.
    	when(countriesNowRestClient.getCountriesWithIsoByGet())
            .thenReturn(fluxOfCountryIsoResponse);
    	// When - END.

    	
    	// Then - START.
        Mono<Countries> countriesMono = countriesServiceImpl.getCountries().log();

        StepVerifier.create(countriesMono)
        .expectNextMatches(countries -> 
            countries.getCountries().size() == 3 &&  
            countries.getCountries().get(0).getName().equals("Nordea Maa") &&  
            countries.getCountries().get(0).getCountryCode().equals("NORDEA_ISO_2") &&
            countries.getCountries().get(1).getName().equals("Nordea Oulu tiimi") &&
            countries.getCountries().get(1).getCountryCode().equals("NORDEA_ISO_2") &&
            countries.getCountries().get(2).getName().equals("Nordea Oulu Tiime third") &&
            countries.getCountries().get(2).getCountryCode().equals("NORDEA_ISO_3")
        )
        .verifyComplete();
    	// Then - END.
    }

    
    /*
     * Test this one when error handling is working.
     */
    @Test
    public void getInformationAboutCountry_shouldReturn_whenValidInput() {

    	// Given - START.
        
    	CountryPopulationSingleCountResponseDto populationDto = new CountryPopulationSingleCountResponseDto(2024, 6000000);
        CountryFlagImageInfoResponseDto flagDto = new CountryFlagImageInfoResponseDto("Nordea","https://example.com/NordaeFlag.png", "Iso2", "Iso3");
        CountryCapitalInfoResponseDto capitalDto = new CountryCapitalInfoResponseDto("Finland", "Oulu", "Iso2", "Iso3");
        
        // Given - END.
        
        // We want mono likes for the zip function.
        
        // When- START.
        
        when(countriesNowRestClient.getCountyWithPopulationByGet(anyString())).thenReturn(Flux.just(populationDto));
        when(countriesNowRestClient.getCountryWithFlagUrlByGet(anyString())).thenReturn(Mono.just(flagDto));
        when(countriesNowRestClient.getCountyWithCapitalByGet(anyString())).thenReturn(Mono.just(capitalDto));
        
        // When - END.
        


        // Then - START.
        
        StepVerifier.create(countriesServiceImpl.getInformationAboutCountry("Nordea"))
                .expectNextMatches(country -> 
                    "Nordea".equals(country.getName()) &&
                    "Iso2".equals(country.getCountryCode()) &&
                    
                    "Oulu".equals(country.getCapital()) &&
                    country.getPopulation() == 6000000 &&
                    "https://example.com/NordaeFlag.png".equals(country.getFlagFileUrl())
                )
                .verifyComplete();
        
        // Then - END.
	}



    
}