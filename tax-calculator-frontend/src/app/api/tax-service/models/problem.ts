/* tslint:disable */
/* eslint-disable */

/**
 * Defines a "problem detail" as a way to carry machine-readable details of
 * errors in a HTTP response to avoid the need to define new error response
 * formats for HTTP APIs
 */
export interface Problem {

  /**
   * A human readable explanation specific to this occurrence of the
   * problem.
   */
  detail?: string;

  /**
   * An absolute URI that identifies the specific occurrence of the problem.
   * It may or may not yield further information if dereferenced.N
   */
  instance?: string;

  /**
   * The HTTP status code generated by the origin server for this occurrence
   * of the problem.
   */
  status?: number;

  /**
   * A short, summary of the problem type. Written in english and readable
   * for engineers (usually not suited for non technical stakeholders and
   * not localized)
   */
  title?: string;

  /**
   * An absolute URI that identifies the problem type.  When dereferenced,
   * it SHOULD provide human-readable documentation for the problem type
   * (e.g., using HTML).
   */
  type?: string;
}
