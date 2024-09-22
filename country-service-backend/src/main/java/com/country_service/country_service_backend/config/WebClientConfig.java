package com.country_service.country_service_backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.netty.http.client.HttpClient;

@Configuration
public class WebClientConfig {

	/*
	 * We are configuring WebClient.
	 * Assuming that it will handle only JSON data.
	 * Weird bug, with Reactor Netty and countriesnow.space POST end point.
	 * They need for some reason not "Accept", MediaType.APPLICATION_JSON_VALUE, even thought it return JSON.
	 * Fix for this behavior was "Accept", MediaType.ALL_VALUE.
	 */
	
	  @Bean
	   public WebClient webClient(WebClient.Builder builder) {
	        return builder.defaultHeader(
	        		"Accept", MediaType.ALL_VALUE
	        		)
	        		.build();
	    }
	  
	  
	  /*
	   * return builder.defaultHeader(
	        		"Accept", MediaType.ALL_VALUE
	        		).clientConnector(new ReactorClientHttpConnector(HttpClient.newConnection().followRedirect(true)))
	        		.build();
	        		
	  TODO(redirect) if time figure out how to configure redirect.
	   */
	  
}
