package za.co.joshuabakerg.taxcalculatorbackend.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.Collection;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import za.co.joshuabakerg.taxcalculatorbackend.domain.entities.TaxYear;

/**
 * @author Joshua Baker on 2021/05/10
 */
@Component
@ConfigurationProperties("app")
@Validated
@Getter
@Setter
@NoArgsConstructor
public class ApplicationProperties {

    private Collection<TaxYear> taxYears;

}
