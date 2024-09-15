package com.country_service.country_service_backend.dto.countries.population;


import lombok.AllArgsConstructor;
import lombok.Data;

/*
 * Models answer for single country and population data.
 * URL: https://countriesnow.space/api/v0.1/countries/population
 * 
 * Response dummy from REST API call. This needs to be same as what is returned form REST API, otherwise serialization and de-serialization can have some issues. 
 */

@Data
@AllArgsConstructor
public class CountryPopulationSingleCountResponseDto {
    private int year;
    private int value;
}