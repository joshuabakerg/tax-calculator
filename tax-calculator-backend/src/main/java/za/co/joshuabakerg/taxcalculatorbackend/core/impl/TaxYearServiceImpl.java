package za.co.joshuabakerg.taxcalculatorbackend.core.impl;

import org.springframework.stereotype.Service;

import java.util.Collection;

import za.co.joshuabakerg.taxcalculatorbackend.core.TaxYearService;
import za.co.joshuabakerg.taxcalculatorbackend.domain.entities.TaxYear;
import za.co.joshuabakerg.taxcalculatorbackend.domain.repositories.TaxYearRepository;

/**
 * @author Joshua Baker on 2021/05/12
 */
@Service
public class TaxYearServiceImpl implements TaxYearService {

    private final TaxYearRepository taxYearRepository;

    public TaxYearServiceImpl(TaxYearRepository taxYearRepository) {
        this.taxYearRepository = taxYearRepository;
    }

    @Override
    public Collection<TaxYear> retrieveAllTaxYears() {
        return taxYearRepository.findAll();
    }
}
