package za.co.joshuabakerg.taxcalculatorbackend.domain.repositories.impl;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

import lombok.AllArgsConstructor;
import za.co.joshuabakerg.taxcalculatorbackend.config.ApplicationProperties;
import za.co.joshuabakerg.taxcalculatorbackend.domain.entities.TaxYear;
import za.co.joshuabakerg.taxcalculatorbackend.domain.repositories.TaxYearRepository;

/**
 * Tax resposotry impl that uses application properties as a source.
 *
 * @author Joshua Baker on 2021/05/10
 */
@Component("taxYearRepository")
@AllArgsConstructor
public class TaxYearRepositoryImpl implements TaxYearRepository {

    private final ApplicationProperties applicationProperties;

    @Override
    public Optional<TaxYear> findById(Integer id) {
        if(id == null){
            return Optional.empty();
        }
        return CollectionUtils.emptyIfNull(applicationProperties.getTaxYears()).stream()
                .filter(taxYear -> id.equals(taxYear.getId()))
                .findFirst();
    }

    @Override
    public Optional<TaxYear> findByDateDate(LocalDate date) {
        if(date == null){
            return Optional.empty();
        }
        return CollectionUtils.emptyIfNull(applicationProperties.getTaxYears()).stream()
                .filter(taxYear -> date.isEqual(taxYear.getStrart()) || date.isAfter(taxYear.getStrart()))
                .filter(taxYear -> date.isEqual(taxYear.getEnd()) || date.isBefore(taxYear.getEnd()))
                .findFirst();
    }

    @Override
    public Collection<TaxYear> findAll() {
        return applicationProperties.getTaxYears();
    }
}
