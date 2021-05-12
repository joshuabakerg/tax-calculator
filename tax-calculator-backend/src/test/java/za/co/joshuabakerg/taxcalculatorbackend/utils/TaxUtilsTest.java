package za.co.joshuabakerg.taxcalculatorbackend.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import za.co.joshuabakerg.taxcalculatorbackend.domain.entities.MedicalAidCredits;

/**
 * @author Joshua Baker on 2021/05/12
 */
class TaxUtilsTest {

    private static final List<MedicalAidCredits> DEFAULT_CREDITS = Arrays.asList(
            MedicalAidCredits.builder()
                    .credits(BigDecimal.valueOf(319))
                    .members(1)
                    .build(),
            MedicalAidCredits.builder()
                    .credits(BigDecimal.valueOf(319))
                    .members(1)
                    .build(),
            MedicalAidCredits.builder()
                    .credits(BigDecimal.valueOf(215))
                    .members(-1)
                    .build()
    );

    @Test
    public void getMedicalAidCredits_Should_Return_Correct_Result_For_One_Member() {
        final BigDecimal total = TaxUtils.getMedicalAidCredits(DEFAULT_CREDITS, 1);
        Assertions.assertEquals(total, BigDecimal.valueOf(319));
    }

    @Test
    public void getMedicalAidCredits_Should_Return_Correct_Result_For_Two_Members() {
        final BigDecimal total = TaxUtils.getMedicalAidCredits(DEFAULT_CREDITS, 2);
        Assertions.assertEquals(total, BigDecimal.valueOf(638));
    }

    @Test
    public void getMedicalAidCredits_Should_Return_Correct_Result_For_Three_Members() {
        final BigDecimal total = TaxUtils.getMedicalAidCredits(DEFAULT_CREDITS, 3);
        Assertions.assertEquals(total, BigDecimal.valueOf(853));
    }

    @Test
    public void getMedicalAidCredits_Should_Return_Correct_Result_For_Four_Members() {
        final BigDecimal total = TaxUtils.getMedicalAidCredits(DEFAULT_CREDITS, 4);
        Assertions.assertEquals(total, BigDecimal.valueOf(1068));
    }

    @Test
    public void getMedicalAidCredits_Should_Return_Correct_Result_For_Five_Members() {
        final BigDecimal total = TaxUtils.getMedicalAidCredits(DEFAULT_CREDITS, 5);
        Assertions.assertEquals(total, BigDecimal.valueOf(1283));
    }

    @Test
    public void getMedicalAidCredits_Should_Return_Correct_Result_For_Six_Members() {
        final BigDecimal total = TaxUtils.getMedicalAidCredits(DEFAULT_CREDITS, 6);
        Assertions.assertEquals(total, BigDecimal.valueOf(1498));
    }
}