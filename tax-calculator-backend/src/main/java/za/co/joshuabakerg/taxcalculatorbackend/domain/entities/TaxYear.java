package za.co.joshuabakerg.taxcalculatorbackend.domain.entities;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * @author Joshua Baker on 2021/05/10
 */
@Data
@RequiredArgsConstructor
public class TaxYear {

    @NonNull
    private final Integer id;

    @NonNull
    private final String name;

    @NonNull
    private final LocalDate strart;

    @NonNull
    private final LocalDate end;

    private List<TaxBracket> brackets;

}
