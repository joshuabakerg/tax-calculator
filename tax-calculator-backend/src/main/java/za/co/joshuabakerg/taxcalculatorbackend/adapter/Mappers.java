package za.co.joshuabakerg.taxcalculatorbackend.adapter;

import za.co.joshuabakerg.taxcalculatorbackend.domain.model.TaxCalculationResponse;

/**
 * @author Joshua Baker on 2021/05/12
 */
public class Mappers {

    public static final TaxCalculationRequestMapper TAX_CALCULATION_REQUEST = org.mapstruct.factory.Mappers.getMapper(TaxCalculationRequestMapper.class);
    public static final TaxCalculationResponseMapper TAX_CALCULATION_RESPONSE = org.mapstruct.factory.Mappers.getMapper(TaxCalculationResponseMapper.class);
    public static final TaxYearMapper TAX_YEAR = org.mapstruct.factory.Mappers.getMapper(TaxYearMapper.class);

}
