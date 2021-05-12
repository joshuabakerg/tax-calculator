import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {TaxCalculatorComponent} from './tax-calculator/tax-calculator.component';
import {TaxService} from "./api/tax-service/services/tax.service";
import {HttpClientModule} from "@angular/common/http";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";

@NgModule({
    declarations: [
        AppComponent,
        TaxCalculatorComponent
    ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        NgbModule,
        HttpClientModule,
        FormsModule,
        ReactiveFormsModule
    ],
    providers: [TaxService],
    bootstrap: [AppComponent]
})
export class AppModule {
}
