import { Injectable } from '@angular/core';
import { Countries } from 'src/app/types/Countries';
import { Country } from 'src/app/types/Country';
import { HttpClient } from '@angular/common/http';
import { map, Observable } from 'rxjs';
import { environment } from 'src/environments/environment';


@Injectable({
  providedIn: 'root'
})
export class CountryService {

  private apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) {
  }

  //TODO(Heikki) Ensure exactly what’s being returned and if it’s in then the expected form
   getAllCountries(): Observable<Country[]> {
    return this.http.get<{ countries: Country[] }>(this.apiUrl).pipe(
    map(response => response.countries)); //For now, unwrap the result into Country[]
  }


}
