package com.country_service.country_service_backend.dto.countries.iso;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * Models answer for countries and ISO codes retrieved.
 * URL: http://countriesnow.space/api/v0.1/countries/iso
 * 
 * Response dummy from REST API call. This needs to be same as what is returned form REST API, otherwise serialization and de-serialization can have some issues. 
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CountriesIsoResponseDto {
	private boolean error;
	private String msg;
	private List<CountryIsoResponseDto> data;
}