package common.validation;

import org.springframework.batch.item.validator.ValidationException;
import org.springframework.batch.item.validator.Validator;

@FunctionalInterface
public interface BaseValidatorSvc<T extends Object> extends Validator<T> {
  
  public void validate(T data) throws ValidationException;

}
