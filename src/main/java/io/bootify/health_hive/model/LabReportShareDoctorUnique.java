package io.bootify.health_hive.model;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

import io.bootify.health_hive.service.LabReportShareService;
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
 * Validate that the userEmail value isn't taken yet.
 */
@Target({ FIELD, METHOD, ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = LabReportShareDoctorUnique.LabReportShareDoctorUniqueValidator.class
)
public @interface LabReportShareDoctorUnique {

    String message() default "{Exists.labReportShare.Doctor}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class LabReportShareDoctorUniqueValidator implements ConstraintValidator<LabReportShareDoctorUnique, String> {

        private final LabReportShareService labReportShareService;
        private final HttpServletRequest request;

        public LabReportShareDoctorUniqueValidator(
                final LabReportShareService labReportShareService,
                final HttpServletRequest request) {
            this.labReportShareService = labReportShareService;
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
            final String currentId = pathVariables.get("id");
            if (currentId != null && value.equalsIgnoreCase(labReportShareService.get(Long.parseLong(currentId)).getDoctor())) {
                // value hasn't changed
                return true;
            }
            return !labReportShareService.doctorExists(value);
        }

    }

}
