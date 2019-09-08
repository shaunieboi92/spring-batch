package common.service.impl;

import java.util.ArrayList;
import java.util.List;

import common.exception.BaseException;
import common.model.BatchResultEntity;
import service.payload.BatchResponse;
import service.payload.BatchResponseTypeBuilder;

public class BatchResultImpl implements IBatchResult{

  /** The error list. */
  private final List<BaseException> errorList = new ArrayList<>();

  /** The bean. */
  private BatchResultEntity bean;

  public BatchResultImpl() {
    bean = new BatchResultEntity();
  }

  @Override
  public List<BaseException> getErrorList() {
    return errorList;
  }
  

  @Override
  public boolean hasError() {
    return !getErrorList().isEmpty();
  }

  /**
   * Gets the bean.
   *
   * @return the bean
   */
  public BatchResultEntity getBean() {
    return bean;
  }

  @Override
  public BatchResponse getResult() throws BaseException {
    return BatchResponseTypeBuilder.getResult(hasError(), errorList);
  }
}
