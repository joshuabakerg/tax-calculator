package za.co.joshuabakerg.taxcalculatorbackend.core.impl;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import za.co.joshuabakerg.taxcalculatorbackend.core.TaxCalculationService;
import za.co.joshuabakerg.taxcalculatorbackend.domain.entities.TaxBracket;
import za.co.joshuabakerg.taxcalculatorbackend.domain.entities.TaxYear;
import za.co.joshuabakerg.taxcalculatorbackend.domain.model.Frequency;
import za.co.joshuabakerg.taxcalculatorbackend.domain.model.TaxCalculation;
import za.co.joshuabakerg.taxcalculatorbackend.domain.model.TaxCalculationRequest;
import za.co.joshuabakerg.taxcalculatorbackend.domain.model.TaxCalculationResponse;
import za.co.joshuabakerg.taxcalculatorbackend.domain.repositories.TaxYearRepository;
import za.co.joshuabakerg.taxcalculatorbackend.utils.TaxUtils;

/**
 * @author Joshua Baker on 2021/05/10
 */
@Service
public class TaxCalculationServiceImpl implements TaxCalculationService {

    private static final BigDecimal MONTHS_IN_YEAR = BigDecimal.valueOf(12);

    private TaxYearRepository taxYearRepository;

    public TaxCalculationServiceImpl(final TaxYearRepository taxYearRepository) {
        this.taxYearRepository = taxYearRepository;
    }

    @Override
    public TaxCalculationResponse calculate(TaxCalculationRequest request) {
        final BigDecimal yearlyIncome = getIncome(request, Frequency.YEARLY);
        final BigDecimal monthlyIncome = getIncome(request, Frequency.MONTHLY);

        //Retrieve the required tax year
        final TaxYear taxYear = taxYearRepository.findById(request.getTaxYearId())
                .orElseThrow();


        //Calculate the users medical aid credits
        final BigDecimal medicalAidCredits = TaxUtils.getMedicalAidCredits(taxYear.getMedAidCredits(), request.getMedicalAidMembers());

        //Retrieve the tax threshold and return no tax if user is below threshold
        final BigDecimal taxThreshold = TaxUtils.getTaxThreshold(taxYear.getTaxThresholds(), request.getAge());
        if (yearlyIncome.compareTo(taxThreshold) <= 0) {
            return buildNoTaxResponse(request, medicalAidCredits);
        }

        //Calculate the users
        final BigDecimal taxRebate = TaxUtils.getTaxRebates(taxYear, request.getAge());

        //Retrieve the users income tax bracket
        final TaxBracket taxBracket = TaxUtils.getTaxBracket(taxYear, yearlyIncome)
                .orElseThrow();

        //Start calculating PAYE
        final BigDecimal taxBracketStart = BigDecimal.ZERO.max(taxBracket.getFrom().subtract(BigDecimal.ONE));
        final BigDecimal yearlyTax = yearlyIncome.subtract(taxBracketStart) // Get income difference from the tax bracket start
                .multiply(BigDecimal.valueOf(taxBracket.getPercentage())) // Calc the percentage tax
                .add(taxBracket.getInitialTax()) // Add the inital tax and percentage calculated teax
                .subtract(taxRebate) // Less the tax rebate
                .max(BigDecimal.ZERO); // If less that zero use zero
        final BigDecimal monthlyTax = yearlyTax.divide(MONTHS_IN_YEAR, RoundingMode.HALF_UP); // Calculate monthly tax

        final TaxCalculationResponse response = new TaxCalculationResponse();
        final ArrayList<TaxCalculation> taxCalculations = new ArrayList<>();
        taxCalculations.add(buildMonthlyCalculation(monthlyIncome, medicalAidCredits, monthlyTax));
        taxCalculations.add(buildYearlyCalculation(yearlyIncome, medicalAidCredits, yearlyTax));
        response.setTaxCalculations(taxCalculations);
        return response;
}

    private TaxCalculation buildMonthlyCalculation(final BigDecimal monthlyIncome, BigDecimal medicalAidCredits, BigDecimal monthlyTax) {
        final BigDecimal monthlyTaxAfterCredits = monthlyTax.subtract(medicalAidCredits).max(BigDecimal.ZERO);
        return TaxCalculation.builder()
                .frequency(Frequency.MONTHLY)
                .grossPay(monthlyIncome)
                .paye(monthlyTax)
                .taxCredits(medicalAidCredits)
                .payeAfterCredits(monthlyTaxAfterCredits)
                .netPay(monthlyIncome.subtract(monthlyTaxAfterCredits))
                .build();
    }

    private TaxCalculation buildYearlyCalculation(BigDecimal yearlyIncome, BigDecimal medicalAidCredits, BigDecimal baseTax) {
        final BigDecimal yearlyTaxCredits = medicalAidCredits.multiply(MONTHS_IN_YEAR);
        final BigDecimal yearlyTaxAfterCredits = baseTax.subtract(yearlyTaxCredits).max(BigDecimal.ZERO);
        return TaxCalculation.builder()
                .frequency(Frequency.YEARLY)
                .grossPay(yearlyIncome)
                .paye(baseTax)
                .taxCredits(yearlyTaxCredits)
                .payeAfterCredits(yearlyTaxAfterCredits)
                .netPay(yearlyIncome.subtract(yearlyTaxAfterCredits))
                .build();
    }

    private TaxCalculationResponse buildNoTaxResponse(final TaxCalculationRequest request, final BigDecimal credits) {
        final TaxCalculationResponse response = new TaxCalculationResponse();
        final ArrayList<TaxCalculation> taxCalculations = new ArrayList<>();
        final BigDecimal monthlyIncome = getIncome(request, Frequency.MONTHLY);
        final BigDecimal yearlyIncome = getIncome(request, Frequency.YEARLY);
        taxCalculations.add(TaxCalculation.builder()
                .frequency(Frequency.MONTHLY)
                .grossPay(monthlyIncome)
                .paye(BigDecimal.ZERO)
                .taxCredits(credits)
                .payeAfterCredits(BigDecimal.ZERO)
                .netPay(monthlyIncome)
                .build());
        taxCalculations.add(TaxCalculation.builder()
                .frequency(Frequency.YEARLY)
                .grossPay(yearlyIncome)
                .paye(BigDecimal.ZERO)
                .taxCredits(credits.multiply(MONTHS_IN_YEAR))
                .payeAfterCredits(BigDecimal.ZERO)
                .netPay(yearlyIncome)
                .build());
        response.setTaxCalculations(taxCalculations);
        return response;
    }

    public BigDecimal getIncome(final TaxCalculationRequest request, final Frequency frequency) {
        if (frequency == Frequency.MONTHLY) {
            if (request.getFrequency() == Frequency.MONTHLY) {
                return request.getIncome();
            } else if (request.getFrequency() == Frequency.YEARLY) {
                return request.getIncome().divide(MONTHS_IN_YEAR, RoundingMode.HALF_UP);
            }
        } else if (frequency == Frequency.YEARLY) {
            if (request.getFrequency() == Frequency.MONTHLY) {
                return request.getIncome().multiply(MONTHS_IN_YEAR);
            } else if (request.getFrequency() == Frequency.YEARLY) {
                return request.getIncome();
            }
        }
        throw new RuntimeException("Failed to format income");
    }
}
