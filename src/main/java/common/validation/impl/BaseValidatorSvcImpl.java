package common.validation.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.item.validator.ValidationException;
import org.springframework.stereotype.Component;

import common.exception.BaseException;
import common.validation.BaseValidatorSvc;

@Component
public abstract class BaseValidatorSvcImpl<T extends Object>
    implements BaseValidatorSvc<T> {
  
  List<BaseException> errorList = new ArrayList<>();

  @Override
  public void validate(Object data) throws ValidationException {
    // TODO Auto-generated method stub
  }

}
