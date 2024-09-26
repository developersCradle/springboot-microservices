import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-country-dialog-component',
  templateUrl: './country-dialog-component.component.html',
  styleUrls: ['./country-dialog-component.component.css']
})
export class CountryDialogComponent implements OnInit {

  constructor( public dialogRef: MatDialogRef<CountryDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any ) {} //Country data comes here
    
    ngOnInit(): void {
    }

    onClose(): void {
    this.dialogRef.close(); 
  }
}
