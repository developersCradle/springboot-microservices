import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BodyComponent } from './body/body.component';
import { SearchComponent } from './search/search.component';

const routes : Routes = [
  { path: '', component:BodyComponent },
  { path: 'search/:searchCountry', component: BodyComponent },
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes)
  ],
  exports: [RouterModule]
})
export class AppRoutingModule { }
