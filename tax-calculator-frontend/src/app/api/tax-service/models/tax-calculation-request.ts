/* tslint:disable */
/* eslint-disable */
import { IncomeFrequency } from './income-frequency';
export interface TaxCalculationRequest {

  /**
   * The age of the user
   */
  age: number;
  frequency: IncomeFrequency;

  /**
   * The users income
   */
  income: number;

  /**
   * How many medical aid members the user pays for
   */
  medicalAidMembers?: number;

  /**
   * The tax year id
   */
  taxYearId: number;
}
