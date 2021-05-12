/* tslint:disable */
/* eslint-disable */
import { IncomeFrequency } from './income-frequency';
export interface TaxCalculation {
  frequency: IncomeFrequency;

  /**
   * The gross pay for the user
   */
  grossPay: number;

  /**
   * The users take home pay
   */
  netPay: number;

  /**
   * The PAYE tax before credits
   */
  paye: number;

  /**
   * The PAYE tax after credits
   */
  payeAfterCredits: number;

  /**
   * The users tax credits
   */
  taxCredits: number;
}
