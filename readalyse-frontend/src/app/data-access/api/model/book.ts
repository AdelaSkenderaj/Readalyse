/**
 * Readalyse API
 * Readalyse API
 *
 * The version of the OpenAPI document: 0.0.1
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


/**
 * Book model.
 */
export interface Book { 
    id?: number;
    title?: string;
    description?: string;
    downloads?: string;
    agents?: Array<object>;
    bookshelves?: Array<object>;
    languages?: Array<object>;
    resources?: Array<object>;
    subjects?: Array<object>;
}

