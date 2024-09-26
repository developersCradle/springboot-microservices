import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Action } from 'rxjs/internal/scheduler/Action';


@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {
  
  @Input() searchCountry : string = ''; //Input form parent. Route won't send params to multiple component, so we catch it here.
  
  constructor(private route : ActivatedRoute, private router : Router) { }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      if (params['searchCountry']) {
        this.searchCountry = params['searchCountry'];
      }
    })
  }

  search() : void {
    if(this.searchCountry)
    {
      this.router.navigateByUrl('/search/' + this.searchCountry);
    } else
    {
      this.router.navigateByUrl('/search/');
    }

  }

}
