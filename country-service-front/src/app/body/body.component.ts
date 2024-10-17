import { Component, OnInit } from '@angular/core';
import { CountryService } from '../services/country/country.service';
import { Country } from '../types/Country';
import { ActivatedRoute } from '@angular/router';
import { MatDialog } from '@angular/material/dialog'; // Import MatDialog service
import { map } from 'rxjs';
import { CountryDialogComponent } from '../country-dialog-component/country-dialog.component';
import { ErrorDialogComponent } from '../error-dialog/error-dialog.component';

@Component({
  selector: 'app-body',
  templateUrl: './body.component.html',
  styleUrls: ['./body.component.css']
})
export class BodyComponent implements OnInit {

  countries: Country[] = [];

  searchCountry : string = ''; //Filtering by search

  constructor(private cs: CountryService, private route: ActivatedRoute, private dialog: MatDialog  ) { }

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
        //call to get all countries
        this.cs.getAllCountries().subscribe(allCountries => {
          this.countries = allCountries;
        });
      }
    });
  }

  openDialog(country: Country): void {
    var countryName = country.name;
    this.cs.getInformationAboutCountry(countryName).subscribe( //Click happened
      (data) => {
        this.dialog.open(CountryDialogComponent, {
          data: data 
        });
      },
      (error) => {
        console.error('Error fetching country data', error);


        this.dialog.open(ErrorDialogComponent, {
          data: error || 'No information about country'
        });
        
      }
    );
  }
}
