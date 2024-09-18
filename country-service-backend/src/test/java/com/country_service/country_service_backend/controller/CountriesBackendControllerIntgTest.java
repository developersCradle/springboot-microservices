package com.country_service.country_service_backend.controller;

import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // Integration test should be running from some other ports, than the normal one for sake of occupying wrong ports when running.
@ActiveProfiles("test")	// Get different profiles from .yml file
@AutoConfigureWebTestClient
public class CountriesBackendControllerIntgTest {
	// TODO (Heikki, IntgTest) Start put here integration test.
}
