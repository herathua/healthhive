package io.bootify.health_hive.model;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

import io.bootify.health_hive.service.LabService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;
import org.springframework.web.servlet.HandlerMapping;


/**
 * Validate that the labRegID value isn't taken yet.
 */
@Target({ FIELD, METHOD, ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = LabLabRegIDUnique.LabLabRegIDUniqueValidator.class
)
public @interface LabLabRegIDUnique {

    String message() default "{Exists.lab.labRegID}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class LabLabRegIDUniqueValidator implements ConstraintValidator<LabLabRegIDUnique, String> {

        private final LabService labService;
        private final HttpServletRequest request;

        public LabLabRegIDUniqueValidator(final LabService labService,
                                          final HttpServletRequest request) {
            this.labService = labService;
            this.request = request;
        }

        @Override
        public boolean isValid(final String value, final ConstraintValidatorContext cvContext) {
            if (value == null) {
                // no value present
                return true;
            }
            @SuppressWarnings("unchecked") final Map<String, String> pathVariables =
                    ((Map<String, String>)request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE));
            final String currentId = pathVariables.get("email");
            if (currentId != null && value.equalsIgnoreCase(labService.get(currentId).getLabRegID())) {
                // value hasn't changed
                return true;
            }
            return !labService.labRegIDExists(value);
        }

    }

}
