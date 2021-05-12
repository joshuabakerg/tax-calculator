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

import { TaxYear } from '../models/tax-year';

@Injectable({
  providedIn: 'root',
})
export class YearsService extends BaseService {
  constructor(
    config: ApiConfiguration,
    http: HttpClient
  ) {
    super(config, http);
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

    const rb = new RequestBuilder(this.rootUrl, YearsService.RetrieveTaxYearsPath, 'get');
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
