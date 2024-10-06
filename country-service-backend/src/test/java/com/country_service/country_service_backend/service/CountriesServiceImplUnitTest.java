package com.country_service.country_service_backend.service;

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
import com.country_service.country_service_backend.dto.countries.iso.CountryIsoResponseDto;

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

    	List<CountryIsoResponseDto> countryIsoResponseDto = Arrays.asList(new CountryIsoResponseDto("Nordea Maa", "NORDEA_ISO_2", "NORDEA_ISO_3"), new CountryIsoResponseDto("Nordea Oulu tiimi", "NORDEA_ISO_2", "NORDEA_ISO_3"), new CountryIsoResponseDto("Nordea Oulu Tiime third", "NORDEA_ISO_3", "NORDEA_ISO_3"));
    	Flux<CountryIsoResponseDto> fluxOfCountryIsoResponse = Flux.fromIterable(countryIsoResponseDto);
    	
    	when(countriesNowRestClient.getCountriesWithIsoByGet())
            .thenReturn(fluxOfCountryIsoResponse);

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
    }

    
    /*
     * Test this one when error handling is working.
     */
//	@Override
//	public Mono<Country> testGetInformationAboutCountry(String countryName) {
//	
//	}



    
}