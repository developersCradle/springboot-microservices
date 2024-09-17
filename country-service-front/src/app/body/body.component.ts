import { Component, OnInit } from '@angular/core';
import { CountryService } from '../services/country/country.service';
import { Country } from '../types/Country';

@Component({
  selector: 'app-body',
  templateUrl: './body.component.html',
  styleUrls: ['./body.component.css']
})
export class BodyComponent implements OnInit {

  countries : Country[] = [];
  constructor(private cs : CountryService) { }

  ngOnInit(): void {
    this.countries = this.cs.getAllCountries();
  }

}
