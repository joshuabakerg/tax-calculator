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
@NoArgsConstructor
@AllArgsConstructor
public class TaxRebate {

    private int id;

    @NonNull
    private String name;

    @NonNull
    private Integer ageRequired;

    @NonNull
    private BigDecimal amount;

}
