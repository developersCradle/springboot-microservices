package com.country_service.country_service_backend.controller;


import static com.github.tomakehurst.wiremock.client.WireMock.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // Integration test should be running from some other ports, than the normal one for sake of occupying wrong ports when running.
@ActiveProfiles("test")	// Get different profiles from .yml file, its better to use other than default, since it can conflict with the ports if there is db configured.
@AutoConfigureWebTestClient
@AutoConfigureWireMock(port = 8080)
public class CountriesBackendControllerIntgTest {

	@Autowired
	private  WebTestClient webTestClient; // Web client for Unit testing.
	
	@Value("${restClient.countriesNowUrl}") // Not for myself, this wont work with Static keyword due manages dependency injections.
	private String COUNTRIES_NOW_URL; // For external end point.
	
	
	private String COUNTRIES_URL = "/countries/v1/"; // For Controller end point.
	
    @Test
    void getInformationAboutCountryIntgrTest() {

    	//given
        String countryName = "Nordea";

        stubFor(get(urlEqualTo(COUNTRIES_NOW_URL + countryName)) // When GET call is made to this address.
            .willReturn(aResponse() // We will return following.
                .withHeader("Content-Type", "application/json")
                .withBodyFile("country.json"))); // WireMock JSON to return, this needs to be in __files folder! 

        // Mock other calls
        
        //when
        webTestClient
            .get()
            .uri(COUNTRIES_URL + "{countryName}", countryName)
            .exchange()
            .expectStatus().isOk()
            .expectBody(String.class);

    }
    
    @Test
    void getAllCountriesIntgrTest() {

    	 
		String url = COUNTRIES_NOW_URL.concat("iso");
		
		System.out.println(url);
		System.out.println(COUNTRIES_URL);
		
		  stubFor(get(urlEqualTo(url)) // When GET call is made to this address.
		            .willReturn(aResponse() // We will return following.
		                .withHeader("Content-Type", "*/*") //With following content type
		                .withBodyFile("all-countries.json"))); // WireMock JSON to return, this needs to be in __files folder! 
		
		
		  webTestClient.get()
	                .uri(url)
	                .exchange()
	                .expectStatus().isOk()
	                .expectBody(String.class)
	                .consumeWith(response -> {
	                    System.out.println("Response body: " + response.getResponseBody());
	                });

	}
    
//    	//given
//        String countryName = "Nordea";
//
//        stubFor(get(urlEqualTo(COUNTRIES_NOW_URL + countryName)) // When GET call is made to this address.
//            .willReturn(aResponse() // We will return following.
//                .withHeader("Content-Type", "application/json")
//                .withBodyFile("country.json"))); // WireMock JSON to return, this needs to be in __files folder! 
//
//        // Mock other calls
//        
//        //when
//        webTestClient
//            .get()
//            .uri(COUNTRIES_URL +"{countryName}", countryName)
//            .exchange()
//            .expectStatus().isOk()
//            .expectBody(Country.class);

    }