import {Component, OnInit} from '@angular/core';
import {TaxService} from "../api/tax-service/services/tax.service";
import {TaxYear} from "../api/tax-service/models/tax-year";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {IncomeFrequency, TaxCalculation, TaxCalculationRequest} from "../api/tax-service/models";

@Component({
    selector: 'app-tax-calculator',
    templateUrl: './tax-calculator.component.html',
    styleUrls: ['./tax-calculator.component.css']
})
export class TaxCalculatorComponent implements OnInit {

    taxYears: TaxYear[] = [];

    taxCalcMonthly: TaxCalculation | undefined;
    taxCalcYearly: TaxCalculation | undefined;

    requestForm: FormGroup;

    showValidationError = false;

    errorMessages: string[] | undefined = undefined;

    constructor(private formBuilder: FormBuilder, private taxService: TaxService) {

        this.requestForm = this.formBuilder.group({
            "age": new FormControl(undefined, Validators.required),
            "frequency": new FormControl("MONTHLY", Validators.required),
            "income": new FormControl(undefined, Validators.required),
            "medicalAidMembers": undefined,
            "taxYearId": new FormControl(undefined, Validators.required),
        });
    }

    ngOnInit(): void {
        this.taxService.retrieveTaxYears().subscribe(taxYears => {
            this.taxYears = taxYears;
            if (taxYears && taxYears.length) {
                this.requestForm.controls['taxYearId'].setValue(taxYears[0].id);
            }
        })
    }

    onSubmitClicked(): void {
        this.showValidationError = false;
        this.errorMessages = undefined;
        if (this.requestForm.valid) {
            let request: TaxCalculationRequest = this.requestForm.value;
            this.taxCalcYearly = undefined;
            this.taxCalcMonthly = undefined;
            this.taxService.calculateTax({body: request}).subscribe(response => {
                this.taxCalcMonthly = response.calculations?.filter(calc => calc.frequency === IncomeFrequency.Monthly)[0]
                this.taxCalcYearly = response.calculations?.filter(calc => calc.frequency === IncomeFrequency.Yearly)[0]
            }, error => {
                let body = error.error;
                if (body.status === 400 && body.type === 'https://zalando.github.io/problem/constraint-violation') {
                    this.errorMessages = [];
                    for (const violation of body.violations) {
                        this.errorMessages.push(`Validation error: ${violation.field} ${violation.message}`)
                    }
                } else {
                    this.errorMessages = ["Encounterd error, please try again later or contact the developer"]
                }
            })
        } else {
            this.showValidationError = true;
        }
    }

}
