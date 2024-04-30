//package io.bootify.health_hive.model;
//
//import io.bootify.health_hive.service.LabService;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.validation.Constraint;
//import jakarta.validation.ConstraintValidator;
//import jakarta.validation.ConstraintValidatorContext;
//import jakarta.validation.Payload;
//import org.springframework.web.servlet.HandlerMapping;
//
//import java.lang.annotation.Documented;
//import java.lang.annotation.Retention;
//import java.lang.annotation.RetentionPolicy;
//import java.lang.annotation.Target;
//import java.util.Map;
//
//import static java.lang.annotation.ElementType.*;
//
//
///**
// * Validate that the email value isn't taken yet.
// */
//@Target({ FIELD, METHOD, ANNOTATION_TYPE })
//@Retention(RetentionPolicy.RUNTIME)
//@Documented
//@Constraint(
//        validatedBy = LabEmailUnique.LabEmailUniqueValidator.class
//)
//public @interface LabEmailUnique {
//
//    String message() default "{Exists.lab.email}";
//
//    Class<?>[] groups() default {};
//
//    Class<? extends Payload>[] payload() default {};
//
//    class LabEmailUniqueValidator implements ConstraintValidator<LabEmailUnique, String> {
//
//        private final LabService labService;
//        private final HttpServletRequest request;
//
//        public LabEmailUniqueValidator(final LabService labService,
//                final HttpServletRequest request) {
//            this.labService = labService;
//            this.request = request;
//        }
//
//        @Override
//        public boolean isValid(final String value, final ConstraintValidatorContext cvContext) {
//            if (value == null) {
//                // no value present
//                return true;
//            }
//            @SuppressWarnings("unchecked") final Map<String, String> pathVariables =
//                    ((Map<String, String>)request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE));
//            final String currentId = pathVariables.get("id");
//            if (currentId != null && value.equalsIgnoreCase(labService.get(Long.parseLong(currentId)).getEmail())) {
//                // value hasn't changed
//                return true;
//            }
//            return !labService.emailExists(value);
//        }
//
//    }
//
//}
