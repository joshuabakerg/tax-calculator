package za.co.joshuabakerg.taxcalculatorbackend.adapter;

import org.mapstruct.Mapper;

import za.co.joshuabakerg.taxcalculatorbackend.domain.model.TaxCalculationRequest;

/**
 * @author Joshua Baker on 2021/05/12
 */
@Mapper
public interface TaxCalculationRequestMapper {

    TaxCalculationRequest dtoToDomain(za.co.joshuabakerg.taxcalculatorbackend.controller.model.TaxCalculationRequest dto);

}
