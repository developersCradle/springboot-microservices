package com.country_service.country_service_backend.client;

import lombok.AllArgsConstructor;
import lombok.Data;

/*
 * Represents body parameter class in:
 * https://countriesnow.space/api/v0.1/countries/population 
 */

@Data
@AllArgsConstructor
public class ParamClass {
	String country;
}
