import { Component, OnInit } from '@angular/core';
import { CountryService } from '../services/country/country.service';
import { Country } from '../types/Country';
import { ActivatedRoute } from '@angular/router';
import { Countries } from '../types/Countries';
import { map } from 'rxjs';

@Component({
  selector: 'app-body',
  templateUrl: './body.component.html',
  styleUrls: ['./body.component.css']
})
export class BodyComponent implements OnInit {

  countries: Country[] = [];

  searchCountry : string = ''; //Filtering by search

  constructor(private cs: CountryService, private route: ActivatedRoute) { }



  ngOnInit(): void {


    this.route.params.subscribe(params => {

      console.log(params);
      this.searchCountry = params['searchCountry']; //Filtering by search

      if (this.searchCountry && this.searchCountry.trim() !== '') {

        const searchCountryLower = params['searchCountry'].toLocaleLowerCase();

        this.cs.getAllCountries().pipe(
          map(countries => countries.filter(country =>
            country.name.toLocaleLowerCase().includes(searchCountryLower.toLocaleLowerCase())
          ))
        ).subscribe(
          (filteredCountries) => {
            this.countries = filteredCountries;
            console.log('Filtered countries:', this.countries);
          },
          (error) => {
            console.error('Error fetching countries:', error);
          }
        );

      } else {
        //call from home page
        this.cs.getAllCountries().subscribe(allCountries => {
          this.countries = allCountries;
        });
      }
    });
  }
}
