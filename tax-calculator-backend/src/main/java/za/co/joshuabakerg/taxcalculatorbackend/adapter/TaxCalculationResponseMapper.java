package za.co.joshuabakerg.taxcalculatorbackend.adapter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import za.co.joshuabakerg.taxcalculatorbackend.domain.model.TaxCalculationResponse;

/**
 * @author Joshua Baker on 2021/05/12
 */
@Mapper
public interface TaxCalculationResponseMapper {

    @Mapping(source = "taxCalculations", target = "calculations")
    za.co.joshuabakerg.taxcalculatorbackend.controller.model.TaxCalculationResponse domainToDto(TaxCalculationResponse domain);

}
