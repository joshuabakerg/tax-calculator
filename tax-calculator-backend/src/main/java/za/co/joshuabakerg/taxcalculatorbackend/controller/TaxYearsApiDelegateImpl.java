package za.co.joshuabakerg.taxcalculatorbackend.controller;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import za.co.joshuabakerg.taxcalculatorbackend.adapter.Mappers;
import za.co.joshuabakerg.taxcalculatorbackend.controller.model.TaxYear;
import za.co.joshuabakerg.taxcalculatorbackend.core.TaxYearService;

/**
 * Delegate for the tax year controller
 *
 * @author Joshua Baker on 2021/05/12
 */
@Component
public class TaxYearsApiDelegateImpl implements TaxYearsApiDelegate {

    private final TaxYearService taxYearService;

    public TaxYearsApiDelegateImpl(TaxYearService taxYearService) {
        this.taxYearService = taxYearService;
    }

    @Override
    public ResponseEntity<List<TaxYear>> retrieveTaxYears() {
        final List<TaxYear> taxYears = CollectionUtils.emptyIfNull(taxYearService.retrieveAllTaxYears()).stream()
                .map(Mappers.TAX_YEAR::entityToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(taxYears);
    }
}
