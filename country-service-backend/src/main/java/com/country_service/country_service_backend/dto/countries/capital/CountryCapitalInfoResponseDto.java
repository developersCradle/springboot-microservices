package com.country_service.country_service_backend.dto.countries.capital;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * Models answer a specific country and its capital.
 * URL: https://countriesnow.space/api/v0.1/countries/capital/q?country=nigeria
 * 
 * Response dummy from REST API call. This needs to be same as what is returned form REST API, otherwise serialization and de-serialization can have some issues. 
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CountryCapitalInfoResponseDto {
	private String name;
	private String capital;
	private String iso2;
	private String iso3;
}