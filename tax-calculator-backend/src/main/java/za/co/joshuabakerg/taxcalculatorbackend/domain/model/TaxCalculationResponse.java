package za.co.joshuabakerg.taxcalculatorbackend.domain.model;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * @author Joshua Baker on 2021/05/10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaxCalculationResponse {

    public Collection<TaxCalculation> taxCalculations;


}
