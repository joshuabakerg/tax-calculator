package za.co.joshuabakerg.taxcalculatorbackend.utils;

import org.apache.commons.collections4.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import za.co.joshuabakerg.taxcalculatorbackend.domain.entities.MedicalAidCredits;
import za.co.joshuabakerg.taxcalculatorbackend.domain.entities.TaxBracket;
import za.co.joshuabakerg.taxcalculatorbackend.domain.entities.TaxRebate;
import za.co.joshuabakerg.taxcalculatorbackend.domain.entities.TaxThreshold;
import za.co.joshuabakerg.taxcalculatorbackend.domain.entities.TaxYear;

/**
 * @author Joshua Baker on 2021/05/10
 */
public final class TaxUtils {

    public static Optional<TaxBracket> getTaxBracket(final TaxYear taxYear, final BigDecimal income) {
        return CollectionUtils.emptyIfNull(taxYear.getBrackets()).stream()
                .filter(taxBracket -> taxBracket.getFrom() == null || income.compareTo(taxBracket.getFrom()) >= 0)
                .filter(taxBracket -> taxBracket.getTo() == null || income.compareTo(taxBracket.getTo()) <= 0)
                .findFirst();
    }

    public static BigDecimal getTaxRebates(final TaxYear taxYear, int age) {
        return CollectionUtils.emptyIfNull(taxYear.getTaxRebates()).stream()
                .filter(taxRebate -> age >= taxRebate.getAgeRequired())
                .map(TaxRebate::getAmount)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    public static BigDecimal getTaxThreshold(final List<TaxThreshold> taxThresholds, int age) {
        return CollectionUtils.emptyIfNull(taxThresholds).stream()
                .filter(taxThreshold -> taxThreshold.getMinAge() == null || age >= taxThreshold.getMinAge())
                .filter(taxThreshold -> taxThreshold.getMaxAge() == null || age <= taxThreshold.getMaxAge())
                .map(TaxThreshold::getThreshold)
                .findFirst().orElseThrow();
    }

    public static BigDecimal getMedicalAidCredits(final List<MedicalAidCredits> credits, int medicalAidMembers) {
        int members = medicalAidMembers;
        BigDecimal total = BigDecimal.ZERO;
        for (MedicalAidCredits medAidCredit : CollectionUtils.emptyIfNull(credits)) {
            if (members <= 0) {
                break;
            }
            if (medAidCredit.getMembers() == -1) {
                total = total.add(medAidCredit.getCredits().multiply(BigDecimal.valueOf(members)));
                break;
            }
            final int multiplier = Math.min(members, medAidCredit.getMembers());
            total = total.add(medAidCredit.getCredits().multiply(BigDecimal.valueOf(multiplier)));
            members -= multiplier;
        }
        return total;
    }

    private TaxUtils() {
    }
}
