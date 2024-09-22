import { Injectable } from '@angular/core';
import { Countries } from 'src/app/types/Countries';
import { Country } from 'src/app/types/Country';
import { HttpClient } from '@angular/common/http';
import { map, Observable } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class CountryService {

  private apiUrl = 'http://localhost:8080/countries/v1/'; //Find way to dynamically make this, example in docker compose file.

  constructor(private http: HttpClient) {
  }

  //TODO(Heikki) Ensure exactly what’s being returned and if it’s in then the expected form
   getAllCountries(): Observable<Country[]> {
    return this.http.get<{ countries: Country[] }>(this.apiUrl).pipe(
    map(response => response.countries)); //For now, unwrap the result into Country[]
  }


}
