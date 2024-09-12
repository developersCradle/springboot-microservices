package com.country_service.country_service_backend.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * Response dummy from REST API call. This needs to be same as what is returned form REST API, otherwise serialization and de-serialization can have some issues. 
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CountryData {

    public String name;
    /*
     * @JsonProperty is used to map a Java object property to a JSON property with a different name, or to override the default mapping.
     * 
     * https://techyexito.medium.com/dealing-with-two-different-names-for-same-object-in-api-response-with-spring-boot-mapper-2a0b5d6c679b
     * For now we are going override default mapping. 
     * Fixes bug with iso fields not getting initialized. 
     */
    @JsonProperty("Iso2")
    public String iso2;
    @JsonProperty("Iso3")
    public String iso3;

}