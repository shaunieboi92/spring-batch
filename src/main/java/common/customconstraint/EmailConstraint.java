package common.customconstraint;

import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import common.validation.EmailValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;

@Documented
@Constraint(validatedBy = EmailValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface EmailConstraint {
  String message() default "Invalid Email";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

}
