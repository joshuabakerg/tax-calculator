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

    private TaxYearRepository taxYearRepository;

    public TaxCalculationServiceImpl(final TaxYearRepository taxYearRepository) {
        this.taxYearRepository = taxYearRepository;
    }

    @Override
    public TaxCalculationResponse calculate(TaxCalculationRequest request) {
        final BigDecimal yearlyIncome = getIncome(request, Frequency.YEARLY);
        final TaxYear taxYear = taxYearRepository.findById(request.getTaxYearId())
                .orElseThrow();
        final TaxBracket taxBracket = TaxUtils.getTaxBracket(taxYear, yearlyIncome)
                .orElseThrow();
        final BigDecimal taxRebate = TaxUtils.getTaxRebates(taxYear, request.getAge());

        final BigDecimal taxBracketStart = BigDecimal.ZERO.max(taxBracket.getFrom().subtract(BigDecimal.ONE));
        final BigDecimal baseTax = yearlyIncome.subtract(taxBracketStart) // Get income difference from the tax bracket start
                .multiply(BigDecimal.valueOf(taxBracket.getPercentage())) // Calc the percentage tax
                .add(taxBracket.getInitialTax()) // Add the inital tax and percentage calculated teax
                .subtract(taxRebate) // Less the tax rebate
                .max(BigDecimal.ZERO); // If less that zero use zero
        final BigDecimal monthlyTax = baseTax.divide(BigDecimal.valueOf(12), RoundingMode.HALF_UP); // Calculate monthly tax

        final TaxCalculationResponse response = new TaxCalculationResponse();
        final ArrayList<TaxCalculation> taxCalculations = new ArrayList<>();
        final BigDecimal monthlyIncome = getIncome(request, Frequency.MONTHLY);
        taxCalculations.add(TaxCalculation.builder()
                .frequency(Frequency.MONTHLY)
                .grossPay(monthlyIncome)
                .paye(monthlyTax)
                .taxCredits(BigDecimal.ZERO)
                .payeAfterCredits(monthlyTax) //TODO fix this
                .netPay(monthlyIncome.subtract(monthlyTax)) //TODO this needs to be monthly tax less tax credits
                .build());
        taxCalculations.add(TaxCalculation.builder()
                .frequency(Frequency.YEARLY)
                .grossPay(yearlyIncome)
                .paye(baseTax)
                .taxCredits(BigDecimal.ZERO)
                .payeAfterCredits(baseTax) //TODO fix this
                .netPay(yearlyIncome.subtract(baseTax)) //TODO this needs to be monthly tax less tax credits
                .build());
        response.setTaxCalculations(taxCalculations);
        return response;
    }

    public BigDecimal getIncome(final TaxCalculationRequest request, final Frequency frequency) {
        if (frequency == Frequency.MONTHLY) {
            if (request.getFrequency() == Frequency.MONTHLY) {
                return request.getIncome();
            } else if (request.getFrequency() == Frequency.YEARLY) {
                return request.getIncome().divide(BigDecimal.valueOf(12), RoundingMode.HALF_UP);
            }
        } else if (frequency == Frequency.YEARLY) {
            if (request.getFrequency() == Frequency.MONTHLY) {
                return request.getIncome().multiply(BigDecimal.valueOf(12));
            } else if (request.getFrequency() == Frequency.YEARLY) {
                return request.getIncome();
            }
        }
        throw new RuntimeException("Failed to format income");
    }
}
