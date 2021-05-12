package za.co.joshuabakerg.taxcalculatorbackend.domain.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Joshua Baker on 2021/05/12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaxCalculation {

    private Frequency frequency;

    private BigDecimal grossPay;

    private BigDecimal paye;

    private BigDecimal taxCredits;

    private BigDecimal payeAfterCredits;

    private BigDecimal netPay;

}
