package common.service.impl;

import java.util.List;

import common.exception.BaseException;
import service.payload.BatchResponse;

public interface IBatchResult {

  public List<BaseException> getErrorList();

  public boolean hasError();

  public BatchResponse getResult() throws BaseException;
}
