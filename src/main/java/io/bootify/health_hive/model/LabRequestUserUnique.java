//package io.bootify.health_hive.model;
//
//import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
//import static java.lang.annotation.ElementType.FIELD;
//import static java.lang.annotation.ElementType.METHOD;
//
//import io.bootify.health_hive.service.LabRequestService;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.validation.Constraint;
//import jakarta.validation.ConstraintValidator;
//import jakarta.validation.ConstraintValidatorContext;
//import jakarta.validation.Payload;
//import java.lang.annotation.Documented;
//import java.lang.annotation.Retention;
//import java.lang.annotation.RetentionPolicy;
//import java.lang.annotation.Target;
//import java.util.Map;
//import org.springframework.web.servlet.HandlerMapping;
//
//
///**
// * Validate that the id value isn't taken yet.
// */
//@Target({ FIELD, METHOD, ANNOTATION_TYPE })
//@Retention(RetentionPolicy.RUNTIME)
//@Documented
//@Constraint(
//        validatedBy = LabRequestUserUnique.LabRequestUserUniqueValidator.class
//)
//public @interface LabRequestUserUnique {
//
//    String message() default "{Exists.labRequest.user}";
//
//    Class<?>[] groups() default {};
//
//    Class<? extends Payload>[] payload() default {};
//
//    class LabRequestUserUniqueValidator implements ConstraintValidator<LabRequestUserUnique, Long> {
//
//        private final LabRequestService labRequestService;
//        private final HttpServletRequest request;
//
//        public LabRequestUserUniqueValidator(final LabRequestService labRequestService,
//                                             final HttpServletRequest request) {
//            this.labRequestService = labRequestService;
//            this.request = request;
//        }
//
//        @Override
//        public boolean isValid(final Long value, final ConstraintValidatorContext cvContext) {
//            if (value == null) {
//                // no value present
//                return true;
//            }
//            @SuppressWarnings("unchecked") final Map<String, String> pathVariables =
//                    ((Map<String, String>)request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE));
//            final String currentId = pathVariables.get("id");
//            if (currentId != null && value.equals(labRequestService.get(Long.parseLong(currentId)).getUser())) {
//                // value hasn't changed
//                return true;
//            }
//            return !labRequestService.userExists(value);
//        }
//
//    }
//
//}