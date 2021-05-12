package za.co.joshuabakerg.taxcalculatorbackend.core;

import org.springframework.stereotype.Service;

import java.util.Collection;

import za.co.joshuabakerg.taxcalculatorbackend.domain.entities.TaxYear;

/**
 * Service responsible for tax year operations.
 *
 * @author Joshua Baker on 2021/05/12
 */
public interface TaxYearService {

    Collection<TaxYear> retrieveAllTaxYears();

}
