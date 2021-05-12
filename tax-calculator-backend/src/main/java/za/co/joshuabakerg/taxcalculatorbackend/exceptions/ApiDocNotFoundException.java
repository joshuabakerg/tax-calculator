package za.co.joshuabakerg.taxcalculatorbackend.exceptions;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

/**
 * @author Joshua Baker on 2021/05/12
 */
public class ApiDocNotFoundException extends AbstractThrowableProblem {

    public ApiDocNotFoundException(final String version){
        super(
                ServletUriComponentsBuilder.fromCurrentContextPath().path("/errors/not-found").build().toUri(),
                "Not found",
                Status.NOT_FOUND,
                String.format("Api doc version[%s] not found", version)
        );
    }

}
