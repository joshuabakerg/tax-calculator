package za.co.joshuabakerg.taxcalculatorbackend.domain.core.impl;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import za.co.joshuabakerg.taxcalculatorbackend.domain.core.TaxCalculationService;
import za.co.joshuabakerg.taxcalculatorbackend.domain.entities.TaxBracket;
import za.co.joshuabakerg.taxcalculatorbackend.domain.entities.TaxYear;
import za.co.joshuabakerg.taxcalculatorbackend.domain.model.Frequency;
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
        response.setAnnuallyPAYE(baseTax);
        response.setMonthlyPAYE(monthlyTax);
        response.setIncomeAfterTax(getIncome(request, Frequency.MONTHLY).subtract(monthlyTax));
        return response;
    }

    public BigDecimal getIncome(final TaxCalculationRequest request, final Frequency frequency) {
        if (frequency == Frequency.MONTHLY) {
            if (request.getIncomeFrequency() == Frequency.MONTHLY) {
                return request.getIncome();
            } else if (request.getIncomeFrequency() == Frequency.YEARLY) {
                return request.getIncome().divide(BigDecimal.valueOf(12), RoundingMode.HALF_UP);
            }
        } else if (frequency == Frequency.YEARLY) {
            if (request.getIncomeFrequency() == Frequency.MONTHLY) {
                return request.getIncome().multiply(BigDecimal.valueOf(12));
            } else if (request.getIncomeFrequency() == Frequency.YEARLY) {
                return request.getIncome();
            }
        }
        throw new RuntimeException("Failed to format income");
    }
}
