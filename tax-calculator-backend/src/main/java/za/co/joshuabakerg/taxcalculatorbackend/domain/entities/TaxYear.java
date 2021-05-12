package za.co.joshuabakerg.taxcalculatorbackend.domain.entities;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

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
public class TaxYear {

    @NonNull
    private Integer id;

    @NonNull
    private String name;

    @NonNull
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate start;

    @NonNull
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate end;

    private List<TaxBracket> brackets;

    private List<TaxRebate> taxRebates;

    private List<TaxThreshold> taxThresholds;

    private List<MedicalAidCredits> medAidCredits;

}
