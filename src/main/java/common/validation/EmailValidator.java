package common.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import common.customconstraint.EmailConstraint;

/**
 * EmailValidator
 * @author shaun.lee
 *
 */
public class EmailValidator
    implements ConstraintValidator<EmailConstraint, String> {
  @Override
  public void initialize(EmailConstraint email) {
  }

  /**
   * Email field >0 && Email field <100
   */
  public boolean isValid(String emailField,
      ConstraintValidatorContext context) {
    String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
    return emailField.matches(regex) && (emailField.length() > 0)
        && (emailField.length() < 100);

  }

}
