package za.co.joshuabakerg.taxcalculatorbackend.core.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import za.co.joshuabakerg.taxcalculatorbackend.core.TaxCalculationService;
import za.co.joshuabakerg.taxcalculatorbackend.domain.model.Frequency;
import za.co.joshuabakerg.taxcalculatorbackend.domain.model.TaxCalculation;
import za.co.joshuabakerg.taxcalculatorbackend.domain.model.TaxCalculationRequest;
import za.co.joshuabakerg.taxcalculatorbackend.domain.model.TaxCalculationResponse;

/**
 * @author Joshua Baker on 2021/05/10
 */
@SpringBootTest
@Slf4j
class TaxCalculationServiceTest {

    @Autowired
    private TaxCalculationService taxCalculationService;

    private static final ObjectWriter OM = new ObjectMapper().writerWithDefaultPrettyPrinter();

    @Test
    public void calculate_tax_for_every_income_at_every_bracket() throws Exception {
        for (int i = 0; i < 1_800_000; i++) {
            final TaxCalculationRequest request = new TaxCalculationRequest(0, 25, Frequency.YEARLY, BigDecimal.valueOf(i), 1);
            try {
                final TaxCalculationResponse response = taxCalculationService.calculate(request);
            } catch (Exception e) {
                log.error("Failed at income [{}]", i);
                throw e;
            }
        }
    }

    @Test
    public void calculate_tax_for_2021() throws Exception {
        runTestHolders(Arrays.asList(
                new ExpectedTaxHolder(new TaxCalculationRequest(0, 25, Frequency.MONTHLY, BigDecimal.valueOf(20000), 1), "2580.83"),
                new ExpectedTaxHolder(new TaxCalculationRequest(0, 66, Frequency.MONTHLY, BigDecimal.valueOf(30000), 1), "4657.58"),
                new ExpectedTaxHolder(new TaxCalculationRequest(0, 78, Frequency.MONTHLY, BigDecimal.valueOf(80000), 1), "23373.17")
        ));
    }

    @Test
    public void calculate_tax_for_2021_with_income_same_as_tax_bracket_start() throws Exception {
        runTestHolders(Arrays.asList(
                new ExpectedTaxHolder(new TaxCalculationRequest(0, 25, Frequency.YEARLY, BigDecimal.valueOf(205_901), 1), "1842.02"),
                new ExpectedTaxHolder(new TaxCalculationRequest(0, 25, Frequency.YEARLY, BigDecimal.valueOf(321_601), 1), "4348.86"),
                new ExpectedTaxHolder(new TaxCalculationRequest(0, 25, Frequency.YEARLY, BigDecimal.valueOf(445_101), 1), "7539.28"),
                new ExpectedTaxHolder(new TaxCalculationRequest(0, 25, Frequency.YEARLY, BigDecimal.valueOf(584_201), 1), "11712.28"),
                new ExpectedTaxHolder(new TaxCalculationRequest(0, 25, Frequency.YEARLY, BigDecimal.valueOf(744_801), 1), "16931.78"),
                new ExpectedTaxHolder(new TaxCalculationRequest(0, 25, Frequency.YEARLY, BigDecimal.valueOf(1_577_301), 1), "45375.54")
        ));
    }

    @Test
    public void calculate_tax_for_2021_with_age_20_and_all_tax_brackets() throws Exception {
        runTestHolders(Arrays.asList(
                new ExpectedTaxHolder(new TaxCalculationRequest(0, 20, Frequency.MONTHLY, BigDecimal.valueOf(5000), 1), "0.00"),
                new ExpectedTaxHolder(new TaxCalculationRequest(0, 20, Frequency.MONTHLY, BigDecimal.valueOf(8300), 1), "247.50"),
                new ExpectedTaxHolder(new TaxCalculationRequest(0, 20, Frequency.MONTHLY, BigDecimal.valueOf(21000), 1), "2840.83"),
                new ExpectedTaxHolder(new TaxCalculationRequest(0, 20, Frequency.MONTHLY, BigDecimal.valueOf(32500), 1), "6115.83"),
                new ExpectedTaxHolder(new TaxCalculationRequest(0, 20, Frequency.MONTHLY, BigDecimal.valueOf(42500), 1), "9486.25"),
                new ExpectedTaxHolder(new TaxCalculationRequest(0, 20, Frequency.MONTHLY, BigDecimal.valueOf(55900), 1), "14526.75"),
                new ExpectedTaxHolder(new TaxCalculationRequest(0, 20, Frequency.MONTHLY, BigDecimal.valueOf(100000), 1), "32484.42"),
                new ExpectedTaxHolder(new TaxCalculationRequest(0, 20, Frequency.MONTHLY, BigDecimal.valueOf(300000), 1), "121226.75")
        ));
    }

    @Test
    public void calculate_tax_for_2021_with_age_67_and_all_tax_brackets() throws Exception {
        runTestHolders(Arrays.asList(
                new ExpectedTaxHolder(new TaxCalculationRequest(0, 67, Frequency.MONTHLY, BigDecimal.valueOf(5000), 1), "0.00"),
                new ExpectedTaxHolder(new TaxCalculationRequest(0, 67, Frequency.MONTHLY, BigDecimal.valueOf(12000), 1), "230.25"),
                new ExpectedTaxHolder(new TaxCalculationRequest(0, 67, Frequency.MONTHLY, BigDecimal.valueOf(21000), 1), "2157.58"),
                new ExpectedTaxHolder(new TaxCalculationRequest(0, 67, Frequency.MONTHLY, BigDecimal.valueOf(32500), 1), "5432.58"),
                new ExpectedTaxHolder(new TaxCalculationRequest(0, 67, Frequency.MONTHLY, BigDecimal.valueOf(42500), 1), "8803.00"),
                new ExpectedTaxHolder(new TaxCalculationRequest(0, 67, Frequency.MONTHLY, BigDecimal.valueOf(55900), 1), "13843.50"),
                new ExpectedTaxHolder(new TaxCalculationRequest(0, 67, Frequency.MONTHLY, BigDecimal.valueOf(100000), 1), "31801.17"),
                new ExpectedTaxHolder(new TaxCalculationRequest(0, 67, Frequency.MONTHLY, BigDecimal.valueOf(300000), 1), "120543.50")
        ));
    }

    @Test
    public void calculate_tax_for_2021_with_age_80_and_all_tax_brackets() throws Exception {
        runTestHolders(Arrays.asList(
                new ExpectedTaxHolder(new TaxCalculationRequest(0, 80, Frequency.MONTHLY, BigDecimal.valueOf(5000), 1), "0.00"),
                new ExpectedTaxHolder(new TaxCalculationRequest(0, 80, Frequency.MONTHLY, BigDecimal.valueOf(15000), 1), "542.25"),
                new ExpectedTaxHolder(new TaxCalculationRequest(0, 80, Frequency.MONTHLY, BigDecimal.valueOf(21000), 1), "1929.58"),
                new ExpectedTaxHolder(new TaxCalculationRequest(0, 80, Frequency.MONTHLY, BigDecimal.valueOf(32500), 1), "5204.58"),
                new ExpectedTaxHolder(new TaxCalculationRequest(0, 80, Frequency.MONTHLY, BigDecimal.valueOf(42500), 1), "8575.00"),
                new ExpectedTaxHolder(new TaxCalculationRequest(0, 80, Frequency.MONTHLY, BigDecimal.valueOf(55900), 1), "13615.50"),
                new ExpectedTaxHolder(new TaxCalculationRequest(0, 80, Frequency.MONTHLY, BigDecimal.valueOf(100000), 1), "31573.17"),
                new ExpectedTaxHolder(new TaxCalculationRequest(0, 80, Frequency.MONTHLY, BigDecimal.valueOf(300000), 1), "120315.50")
        ));
    }

    private void runTestHolders(final Collection<ExpectedTaxHolder> expectedTaxHolders) throws Exception {
        for (ExpectedTaxHolder expectedTaxHolder : expectedTaxHolders) {
            final TaxCalculationRequest req = expectedTaxHolder.getReq();
            System.out.println("REQ: " + OM.writeValueAsString(req));
            final TaxCalculationResponse response = taxCalculationService.calculate(req);
            System.out.println("RES: " + OM.writeValueAsString(response));
            final TaxCalculation taxCalc = response.getTaxCalculations().stream()
                    .filter(taxCalculation -> taxCalculation.getFrequency() == Frequency.MONTHLY)
                    .findAny().orElseThrow();
            Assertions.assertEquals(taxCalc.getPaye().setScale(2, RoundingMode.HALF_UP).toString(), expectedTaxHolder.getExpectedTax());
        }
    }

    @Data
    @AllArgsConstructor
    public static class ExpectedTaxHolder {

        private TaxCalculationRequest req;

        private String expectedTax;

    }

}