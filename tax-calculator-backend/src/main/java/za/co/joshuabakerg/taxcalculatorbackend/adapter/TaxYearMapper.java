package za.co.joshuabakerg.taxcalculatorbackend.adapter;

import org.mapstruct.Mapper;

import za.co.joshuabakerg.taxcalculatorbackend.domain.entities.TaxYear;

/**
 * @author Joshua Baker on 2021/05/12
 */
@Mapper
public interface TaxYearMapper {

    za.co.joshuabakerg.taxcalculatorbackend.controller.model.TaxYear entityToDto(TaxYear domain);

}
