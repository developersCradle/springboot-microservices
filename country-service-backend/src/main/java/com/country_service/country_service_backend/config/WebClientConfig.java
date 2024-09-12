package com.country_service.country_service_backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

	/*
	 * We are configuring WebClient.
	 * Assuming that it will handle only JSON data.
	 */
    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        return builder.defaultHeader(
        		"Accept", MediaType.APPLICATION_JSON_VALUE,
                "Content-Type", MediaType.APPLICATION_JSON_VALUE)
        		.build();
    }
}
