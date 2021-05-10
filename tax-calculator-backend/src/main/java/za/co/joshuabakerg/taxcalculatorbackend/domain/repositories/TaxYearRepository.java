package za.co.joshuabakerg.taxcalculatorbackend.domain.repositories;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

import za.co.joshuabakerg.taxcalculatorbackend.domain.entities.TaxYear;

/**
 * @author Joshua Baker on 2021/05/10
 */
public interface TaxYearRepository {

    Optional<TaxYear> findById(final Integer id);

    Optional<TaxYear> findByDateDate(LocalDate date);

    Collection<TaxYear> findAll();

}
