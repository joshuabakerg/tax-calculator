package za.co.joshuabakerg.taxcalculatorbackend.domain.entities;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * @author Joshua Baker on 2021/05/10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaxThreshold {

    @NonNull
    private String name;

    private Integer minAge;

    private Integer maxAge;

    @NonNull
    private BigDecimal threshold;

}
