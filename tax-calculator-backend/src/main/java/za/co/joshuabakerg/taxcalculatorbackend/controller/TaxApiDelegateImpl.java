package za.co.joshuabakerg.taxcalculatorbackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import za.co.joshuabakerg.taxcalculatorbackend.adapter.Mappers;
import za.co.joshuabakerg.taxcalculatorbackend.controller.model.TaxCalculationRequest;
import za.co.joshuabakerg.taxcalculatorbackend.controller.model.TaxCalculationResponse;
import za.co.joshuabakerg.taxcalculatorbackend.core.TaxCalculationService;

/**
 * @author Joshua Baker on 2021/05/12
 */
@Component
public class TaxApiDelegateImpl implements TaxApiDelegate {

    private final TaxCalculationService service;

    public TaxApiDelegateImpl(TaxCalculationService service) {
        this.service = service;
    }

    @Override
    public ResponseEntity<TaxCalculationResponse> calculateTax(final TaxCalculationRequest taxCalculationRequest) {
        final za.co.joshuabakerg.taxcalculatorbackend.domain.model.TaxCalculationResponse serviceResponse =
                service.calculate(Mappers.TAX_CALCULATION_REQUEST.dtoToDomain(taxCalculationRequest));
        final TaxCalculationResponse response = Mappers.TAX_CALCULATION_RESPONSE.domainToDto(serviceResponse);
        return ResponseEntity.ok(response);
    }
}
