package com.country_service.country_service_backend.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * Models Country domain class. 
 * 
*/

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Country {
	private String name;
	@JsonProperty("country_code") // Staying consistent with the formatting
	private String countryCode;
}