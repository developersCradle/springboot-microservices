package com.country_service.country_service_backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;

/*
 * Added Global CORS for now since dealing with REST API calls. This needs to be re-thinked, if deployed other than development environment!
 * https://www.baeldung.com/spring-webflux-cors 
 */

@Configuration
@EnableWebFlux
public class CorsGlobalConfiguration implements WebFluxConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {
        corsRegistry.addMapping("/**");
    }
}
