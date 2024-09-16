package com.country_service.country_service_backend.dto.countries.flag.images;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * Models answer for country with flag images URL.
 * URL: https://countriesnow.space/api/v0.1/countries/flag/images/q?country=Finland
 * 
 * Response dummy from REST API call. This needs to be same as what is returned form REST API, otherwise serialization and de-serialization can have some issues. 
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CountryFlagImageResponseDto {
	private boolean error;
	private String msg;
	private CountryFlagImageInfoResponseDto data;
}