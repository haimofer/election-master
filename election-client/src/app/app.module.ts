import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import {HttpService} from './HttpService';
import {HttpClient, HttpClientModule} from '@angular/common/http';

import { ChartsModule } from 'ng2-charts/ng2-charts';


// @ts-ignore
@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    ChartsModule
  ],
  providers: [
    HttpClient,
    HttpService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
