package za.co.joshuabakerg.taxcalculatorbackend.domain.model;

import java.math.BigDecimal;

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

    @NonNull
    private BigDecimal taxMonthly;

    @NonNull
    private BigDecimal taxAnnually;

    @NonNull
    private BigDecimal taxCredits;

    @NonNull
    private BigDecimal taxAfterCredits;

    @NonNull
    private BigDecimal incomeAfterTax;


}
