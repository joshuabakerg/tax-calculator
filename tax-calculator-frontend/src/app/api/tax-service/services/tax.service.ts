/* tslint:disable */
/* eslint-disable */
import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { BaseService } from '../base-service';
import { ApiConfiguration } from '../api-configuration';
import { StrictHttpResponse } from '../strict-http-response';
import { RequestBuilder } from '../request-builder';
import { Observable } from 'rxjs';
import { map, filter } from 'rxjs/operators';

import { TaxCalculationRequest } from '../models/tax-calculation-request';
import { TaxCalculationResponse } from '../models/tax-calculation-response';
import { TaxYear } from '../models/tax-year';

@Injectable({
  providedIn: 'root',
})
export class TaxService extends BaseService {
  constructor(
    config: ApiConfiguration,
    http: HttpClient
  ) {
    super(config, http);
  }

  /**
   * Path part for operation calculateTax
   */
  static readonly CalculateTaxPath = '/tax';

  /**
   * Calculates tax.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `calculateTax()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  calculateTax$Response(params?: {
    body?: TaxCalculationRequest
  }): Observable<StrictHttpResponse<TaxCalculationResponse>> {

    const rb = new RequestBuilder(this.rootUrl, TaxService.CalculateTaxPath, 'post');
    if (params) {
      rb.body(params.body, 'application/json');
    }

    return this.http.request(rb.build({
      responseType: 'json',
      accept: 'application/json'
    })).pipe(
      filter((r: any) => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<TaxCalculationResponse>;
      })
    );
  }

  /**
   * Calculates tax.
   *
   *
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `calculateTax$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  calculateTax(params?: {
    body?: TaxCalculationRequest
  }): Observable<TaxCalculationResponse> {

    return this.calculateTax$Response(params).pipe(
      map((r: StrictHttpResponse<TaxCalculationResponse>) => r.body as TaxCalculationResponse)
    );
  }

  /**
   * Path part for operation retrieveTaxYears
   */
  static readonly RetrieveTaxYearsPath = '/taxYears';

  /**
   * Retrieves the applicable tax years.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `retrieveTaxYears()` instead.
   *
   * This method doesn't expect any request body.
   */
  retrieveTaxYears$Response(params?: {
  }): Observable<StrictHttpResponse<Array<TaxYear>>> {

    const rb = new RequestBuilder(this.rootUrl, TaxService.RetrieveTaxYearsPath, 'get');
    if (params) {
    }

    return this.http.request(rb.build({
      responseType: 'json',
      accept: 'application/json'
    })).pipe(
      filter((r: any) => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<Array<TaxYear>>;
      })
    );
  }

  /**
   * Retrieves the applicable tax years.
   *
   *
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `retrieveTaxYears$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  retrieveTaxYears(params?: {
  }): Observable<Array<TaxYear>> {

    return this.retrieveTaxYears$Response(params).pipe(
      map((r: StrictHttpResponse<Array<TaxYear>>) => r.body as Array<TaxYear>)
    );
  }

}
