import { Injectable } from '@angular/core';
import { Country } from 'src/app/types/Country';
import { HttpClient } from '@angular/common/http';
import { map, Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { CountryInformation } from 'src/app/types/CountryInformation';


@Injectable({
  providedIn: 'root'
})
export class CountryService {

  private getAllCountriesUrl = environment.getCountriesBaseUrl;
  private getInformationAboutCountryUrl = environment.getCountriesBaseUrl;

  constructor(private http: HttpClient) {
  }


  //TODO(Heikki) Ensure exactly what’s being returned and if it’s in then the expected form
  getInformationAboutCountry(countryCode: string): Observable<CountryInformation> {
    const url = this.getInformationAboutCountryUrl + `${countryCode}`;
    return this.http.get<CountryInformation>(url);
  }

  //TODO(Heikki) Ensure exactly what’s being returned and if it’s in then the expected form
   getAllCountries(): Observable<Country[]> {
    return this.http.get<{ countries: Country[] }>(this.getAllCountriesUrl).pipe(
    map(response => response.countries)); //For now, unwrap the result into Country[]
  }


}
