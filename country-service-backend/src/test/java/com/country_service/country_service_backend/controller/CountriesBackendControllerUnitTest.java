package com.country_service.country_service_backend.controller;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.country_service.country_service_backend.domain.Countries;
import com.country_service.country_service_backend.domain.Country;
import com.country_service.country_service_backend.service.CountriesServiceImpl;

import reactor.core.publisher.Mono;

@WebFluxTest(controllers = CountriesBackendController.class) // What Unit we are testing. 
@AutoConfigureWebTestClient //  configure and set up a WebTestClient for Spring WebFlux applications.
public class CountriesBackendControllerUnitTest {
	
	@Autowired
	private  WebTestClient webServiceClient; // Web client for Unit testing.
	
	@MockBean // @MockBean != @Mock. @MockBean loads into Spring context. Use this if need Spring context!  
	private CountriesServiceImpl countriesService;
	
	static String COUNTRIES_URL = "/countries/v1/";

	
	/*
	 *  Unit test getCountries() end point.
	 */
	  
	@Test
	void getAllCountries_shouldReturn2xx_whenValidInput() {

		// Given - START.
		
		List<Country> listOfCountries = new ArrayList<>();
	    listOfCountries.add(new Country("Afghanistan", "AF", "Kabul", 38928346, "https://flagcdn.com/w320/af.png"));
	    listOfCountries.add(new Country("Albania", "AL", "Tirana", 2877797, "https://flagcdn.com/w320/al.png"));
	    listOfCountries.add(new Country("Algeria", "DZ", "Algiers", 43851044, "https://flagcdn.com/w320/dz.png"));
	    listOfCountries.add(new Country("Andorra", "AD", "Andorra la Vella", 77265, "https://flagcdn.com/w320/ad.png"));
	    listOfCountries.add(new Country("Angola", "AO", "Luanda", 32866272, "https://flagcdn.com/w320/ao.png"));
	    
	    // Given - END.
	    
	    // When - START.
	    
	    when(countriesService.getCountries()).thenReturn(Mono.just((new Countries(listOfCountries))));
		
	    // When - END.
	    
	    // Then - START.
	    
		webServiceClient
                .get()
                .uri(COUNTRIES_URL)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(Countries.class)
                .consumeWith(response -> {
                	Countries responseBody = response.getResponseBody();
                    assertNotNull(responseBody);
                    List<Country> countries = responseBody.getCountries();
                    assertNotNull(countries);
                    assertEquals(5, countries.size()); 
                });
		
		// Then - END.
		
	}
	
	
	/*
	 *  Unit test getInformationAboutCountry() end point.
	 */
	
	@Test
	void getInformationAboutCountry_shouldReturn2xx_whenValidInput() {

		// Given - START.
		
		String nameOfCountry= "United States";
		
		Country usa = new Country("United States", "US", "Washington, D.C.", 331002651,
				"https://upload.wikimedia.org/wikipedia/en/a/a4/Flag_of_the_United_States.svg");
		
		// Given - END.
		
		// When - START.
		
	    when(countriesService.getInformationAboutCountry(anyString())).thenReturn(Mono.just(usa));
	    
	    // When - END.
	    
	    // Then - START.
	    
	    webServiceClient
                .get()
                .uri(COUNTRIES_URL + "/{nameOfCountry}", nameOfCountry)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody() 
                .jsonPath("$.name").isEqualTo("United States") 
	    		.jsonPath("$.country_code").isEqualTo("US")
	    		.jsonPath("$.capital").isEqualTo("Washington, D.C.")
	    		.jsonPath("$.population").isEqualTo(331002651)
	    		.jsonPath("$.flag_file_url").isEqualTo("https://upload.wikimedia.org/wikipedia/en/a/a4/Flag_of_the_United_States.svg");
	    
	    // Then - END.
	}
	
	
	/*
	 *  Unit test getInformationAboutCountry() for Bean validation.
	 */
	
	@Test
	void getInformationAboutCountry_validationError_whenInputTooShort() {

		// Given - START.
		
		String nameOfCountry= "sd";
		
		// Given - END.
		
		// When - START.
		// When - END.
		
		// Then - START.
		
	    webServiceClient
                .get()
                .uri(COUNTRIES_URL + "/{nameOfCountry}", nameOfCountry)
                .exchange()
                .expectStatus()
                .is4xxClientError()
                .expectBody(String.class) 
                .consumeWith(response -> {
	                var responseBody = response.getResponseBody(); // get error body.
			        System.out.println("ResponseBody : " + responseBody);
	                assertNotNull(responseBody);
	                var expectedErrorMessage =  "The country name must be between 4 and 56 characters long."; // For now, we have only one validation.
			        assertEquals(responseBody, expectedErrorMessage); 
	    });
	    
	    // Then - END.
	    
		}
	
	/*
	 *  Unit test getInformationAboutCountry() for Bean validation.
	 */
	
	@Test
	void getInformationAboutCountry_validationError_whenInputTooLong() {

		// Given - START.
		
		String nameOfCountry= "nordeaMoney$$$$€€€".repeat(20); //Definitely too long! Needs to be more than 56 character. 
		
		// Given - END.
		
		// When - START.
		// When - END.
		
		// Then - START.
		
	    webServiceClient
                .get()
                .uri(COUNTRIES_URL + "/{nameOfCountry}", nameOfCountry)
                .exchange()
                .expectStatus()
                .is4xxClientError()
                .expectBody(String.class) 
                .consumeWith(response -> {
	                var responseBody = response.getResponseBody(); // get error body.
			        System.out.println("ResponseBody : " + responseBody);
	                assertNotNull(responseBody);
	                var expectedErrorMessage =  "The country name must be between 4 and 56 characters long."; // For now, we have only one validation.
			        assertEquals(responseBody, expectedErrorMessage); 
	    });
	    
	    // Then - END.
	    
		}
}
