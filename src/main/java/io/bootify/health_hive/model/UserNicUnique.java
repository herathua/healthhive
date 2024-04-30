package io.bootify.health_hive.model;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

import io.bootify.health_hive.service.UserService;
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
 * Validate that the nic value isn't taken yet.
 */
@Target({ FIELD, METHOD, ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = UserNicUnique.UserNicUniqueValidator.class
)
public @interface UserNicUnique {

    String message() default "{Exists.user.nic}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class UserNicUniqueValidator implements ConstraintValidator<UserNicUnique, String> {

        private final UserService userService;
        private final HttpServletRequest request;

        public UserNicUniqueValidator(final UserService userService,
                                      final HttpServletRequest request) {
            this.userService = userService;
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
            final String currentId = pathVariables.get("userEmail");
            if (currentId != null && value.equalsIgnoreCase(userService.get(currentId).getNic())) {
                // value hasn't changed
                return true;
            }
            return !userService.nicExists(value);
        }

    }

}
