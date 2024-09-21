package com.country_service.country_service_backend.controller;


import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.country_service.country_service_backend.domain.Countries;
import com.country_service.country_service_backend.domain.Country;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // Integration test should be running from some other ports, than the normal one for sake of occupying wrong ports when running.
@ActiveProfiles("test")	// Get different profiles from .yml file, its better to use other than default, since it can conflict with the ports if there is db configured.
@AutoConfigureWebTestClient
@AutoConfigureWireMock(port = 0) //(port = 0) is random port. Also @AutoConfigureWireMock automatically starts a WireMock server on a random port
public class CountriesBackendControllerIntgTest {

    @Autowired
    WebTestClient webTestClient; // Web client for Unit testing.
    
	@Value("${restClient.countriesNowUrl}") // Not for myself, this wont work with Static keyword due manages dependency injections.
	private String COUNTRIES_NOW_URL; // For external end point.
	
	private String COUNTRIES_BASE_URL = "/countries/v1/"; // For Controller base end point.
	
	
	
	/*
	 * Test getInformationAboutCountry with real API behind for OK.
	 * Note. This should done less often since there real API behind,  
	 * run these kind of tests when version release is happening for example.
	 */
	
    @Test
    void getInformationAboutCountry_ReturnsSuccess_AgainstRealAPI() {
    	  
    	//given
    	String countryName = "Finland";
    	
        //When
        webTestClient
            .get()
            .uri(COUNTRIES_BASE_URL + "{nameOfCountry}", countryName)
            .exchange()
            .expectStatus().isOk()
            .expectBody(Country.class)
            .consumeWith(response -> {
            //Then
            	Country country = response.getResponseBody();
                assertNotNull(country);
                assertEquals("Finland", country.getName());
                assertEquals("FI", country.getCountryCode());
                assertEquals("Helsinki", country.getCapital());
                assertEquals(5515525, country.getPopulation());
                assertEquals("https://upload.wikimedia.org/wikipedia/commons/b/bc/Flag_of_Finland.svg", country.getFlagFileUrl());
    	    });
        
        
        
    }
	

    /*
	 * Test getAllCountries with real API behind for OK.
	 * Note. This should done less often since there real API behind,  
	 * run these kind of tests when version release is happening for example.
	 */
	
    @Test
    void getAllCountries_ReturnsSuccess_AgainstRealAPI() {
    	
      	//Given
        
        //When
        webTestClient
            .get()
            .uri(COUNTRIES_BASE_URL)
            .exchange()
            .expectStatus().isOk()
            .expectBody(Countries.class)
            .consumeWith(response -> {
            //Then
            	Countries countries = response.getResponseBody();
            	assert Objects.requireNonNull(countries).getCountries().size() == 220; // Number of list of countries/territories  comes this API at the time.
            	assertEquals("Finland", countries.getCountries().get(66).getName());  // Index of Finland in data set.
    	    });

    }
    	 
	
	
	
	
	
	
	
	
	/*
	 * Experiment, cannot get WireMock working to test disconnections or other network errors.
	 * TODO Heikki(WireMock) Get this thing working!!!
	
    @Test
    void getInformationAboutCountryIntgrTest() {
        
    	//given
        String countryName = "Nordea";

        stubFor(get(urlEqualTo("/api/v0.1/countries/iso")) // When GET call is made to this address.
            .willReturn(aResponse() // We will return following.
                .withHeader("Content-Type", "application/json")
                .withBody("""
                        [
                            { "id": 1, "userId": 1, "title": "my todo" },
                        ]
                        """)));
        // Mock other calls
        
        //when
        webTestClient
            .get()
            .uri("/api/v0.1/countries/iso")
            .exchange()
            .expectBody(String.class)
            .consumeWith( result -> {
            	System.out.println(result);
            });
        

    }
    
    @Test
    void getAllCountriesIntgrTest() {
    	 
        
		String url = COUNTRIES_NOW_URL.concat("iso");
		
		System.out.println(url);
		System.out.println(COUNTRIES_URL);

		// Stubbing WireMock
		stubFor(get(urlEqualTo("/api/v0.1/countries/iso")).willReturn(aResponse()
				.withHeader("Content-Type", "text/plain").withBody("Nordea vastaus!")));
		// We're asserting if WireMock responded properly
		
		//URL is anything after port!
//		stubFor(get(urlEqualTo("/api/v0.1/countries/iso"))  // Stubbing /api/v0.1/countries/iso
//			    .willReturn(aResponse()
//			        .withHeader("Content-Type", "application/json")
//			        .withBody("NORDEA VASTAUS")
//			));
//          
          
//		  stubFor(get(urlEqualTo(url)) // When GET call is made to this address.
//		            .willReturn(aResponse() // We will return following.
//		                .withHeader("Content-Type", "") //With following content type
//		                .withBody("""
//		                        [
//		                            { "id": 1, "userId": 1, "title": "my todo" },
//		                        ]
//		                        """)));

//		                .withBodyFile("all-countries.json"))); // WireMock JSON to return, this needs to be in __files folder! 
		

		webTestClient.get()
	    .uri(COUNTRIES_URL)  // Ensure this matches the stubbed URL
	    .exchange()
	    .expectStatus().isOk()
	    .expectBody(String.class)
	    .consumeWith(response -> {
	        // Log the status and body of the response
	        System.out.println("Response status: " + response.getStatus());
	        System.out.println("Response body: " + response.getResponseBody());
	    });

	}


	*/
	

    }