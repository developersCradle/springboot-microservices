package com.country_service.country_service_backend.controller;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;



@WebFluxTest(controllers = CountriesBackendController.class)
@AutoConfigureWebTestClient
public class CountriesBackendControllerUnitTest {
	// TODO (Heikki, UnitTest) Start put here unit test.
}
