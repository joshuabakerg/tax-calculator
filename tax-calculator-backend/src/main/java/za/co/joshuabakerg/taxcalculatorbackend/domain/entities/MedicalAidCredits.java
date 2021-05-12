package za.co.joshuabakerg.taxcalculatorbackend.domain.entities;

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
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicalAidCredits {

    @NonNull
    private BigDecimal credits;

    private int members;

}
