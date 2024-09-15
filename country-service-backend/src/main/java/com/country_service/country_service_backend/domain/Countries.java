package com.country_service.country_service_backend.domain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * Models Countries domain class. 
*/

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Countries {
	private List<Country> countries;
}