package com.country_service.country_service_backend.domain;

import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/*
 * Response dummy from REST API call. This needs to be same as what is returned form REST API, otherwise serialization and de-serialization can have some issues. 
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Country {

		public boolean error;
		public String msg;
		public List<CountryData> data;

}