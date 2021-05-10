package za.co.joshuabakerg.taxcalculatorbackend.domain.entities;

import java.math.BigDecimal;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * @author Joshua Baker on 2021/05/10
 */
@Data
@RequiredArgsConstructor
public class TaxBracket {

    private BigDecimal from;

    private BigDecimal to;

    @NonNull
    private final BigDecimal initialTax;

    @NonNull
    private final Float percentage;


}
