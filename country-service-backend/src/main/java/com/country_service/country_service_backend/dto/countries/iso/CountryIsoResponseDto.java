package com.country_service.country_service_backend.dto.countries.iso;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class CountryIsoResponseDto {
	private String name;
	
	/*
	 * @JsonProperty is used to map a Java object property to a JSON property with a
	 * different name, or to override the default mapping.
	 * 
	 * https://techyexito.medium.com/dealing-with-two-different-names-for-same-
	 * object-in-api-response-with-spring-boot-mapper-2a0b5d6c679b 
	 * For now we are going override default mapping of Spring. Jackson is used behind scenes in
	 * Spring. Fixes bug with iso fields not getting initialized.
	 */
	
	@JsonProperty("Iso2")
	private String iso2;
	@JsonProperty("Iso3")
	private String iso3;

}