package com.country_service.country_service_backend.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * Models Country domain class.
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY) // Hide empty fields. This is common domain class for answers. We are re using this for now. 
@JsonPropertyOrder({"name", "countryCode"}) // For make ordering like in specification.
public class Country {

	private String name;
	@JsonProperty("country_code") // Staying consistent with the formatting.
	private String countryCode;

	private String capital;
	private String population;
	@JsonProperty("flag_file_url") // Staying consistent with the formatting.
	private String flagFileUrl;

	/*
	 * We want only these two to be shown in certain use case.
	 */
	public Country(String name, String countryCode) {
		this.name = name;
		this.countryCode = countryCode;
	}

}