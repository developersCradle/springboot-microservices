package com.country_service.country_service_backend.dto.countries.population;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * Models answer for single country and population data.
 * URL: https://countriesnow.space/api/v0.1/countries/population
 * 
 * Response dummy from REST API call. This needs to be same as what is returned form REST API, otherwise serialization and de-serialization can have some issues. 
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CountryPopulationResponseDto {
	private String country;
	private String code; //This probably ISO2?
	private String iso3;
	private List<CountryPopulationSingleCountResponseDto> populationCounts; // Populations by years.

}