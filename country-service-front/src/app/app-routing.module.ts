import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BodyComponent } from './body/body.component';

const routes : Routes = [
  { path: '', component:BodyComponent },
  { path: 'search/:searchCountry', component: BodyComponent }, //These are rendered with <router-outlet></router-outlet>. Messed up comment structure!
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes)
  ],
  exports: [RouterModule]
})
export class AppRoutingModule { }
