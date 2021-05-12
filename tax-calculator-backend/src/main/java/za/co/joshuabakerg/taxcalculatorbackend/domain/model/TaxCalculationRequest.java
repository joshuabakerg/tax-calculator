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
public class TaxCalculationRequest {

    @NonNull
    private Integer taxYearId;

    @NonNull
    private Integer age;

    @NonNull
    private Frequency frequency;

    @NonNull
    private BigDecimal income;

    @NonNull
    private Integer medicalAidMembers;

}
