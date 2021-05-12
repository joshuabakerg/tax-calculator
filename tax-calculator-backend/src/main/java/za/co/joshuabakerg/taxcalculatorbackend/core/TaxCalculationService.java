package za.co.joshuabakerg.taxcalculatorbackend.core;

import za.co.joshuabakerg.taxcalculatorbackend.domain.model.TaxCalculationRequest;
import za.co.joshuabakerg.taxcalculatorbackend.domain.model.TaxCalculationResponse;

/**
 * Service responsible for determining tax related operations.
 *
 * @author Joshua Baker on 2021/05/10
 */
public interface TaxCalculationService {

    /**
     * Calculates the income tax for the give request
     *
     * @param request Object containing the information required to calculate tax
     * @return Response that details the users tax information
     */
    TaxCalculationResponse calculate(TaxCalculationRequest request);

}
