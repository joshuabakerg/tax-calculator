package za.co.joshuabakerg.taxcalculatorbackend.domain.entities;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * @author Joshua Baker on 2021/05/10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaxBracket {

    private BigDecimal from;

    private BigDecimal to;

    @NonNull
    private BigDecimal initialTax;

    @NonNull
    private Float percentage;


}
