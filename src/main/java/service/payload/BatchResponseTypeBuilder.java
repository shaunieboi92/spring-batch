package service.payload;

import java.util.List;

import org.springframework.util.ObjectUtils;

import common.constants.AppConstants;
import common.exception.BaseException;
import common.utility.ErrorInfoListType;
import common.utility.StatusInfoType;
import common.utility.ErrorInfoListType.ErrorInfo;

public class BatchResponseTypeBuilder {

  private static final String GEN_BATCH_SERVICE_RESPONSE_TYPE = "genBatchServiceResponseType";
  private static final String BATCH_RESPONSE_TYPE_BUILDER = "BatchResponseTypeBuilder";

  private BatchResponseTypeBuilder() {

  }

  public static BatchResponse genBatchServiceResponseType(boolean isFailed,
      List<BaseException> errorList) throws BaseException {
    try {
      BatchResponse resp = new BatchResponse();
      resp.setErrorInfoList(new ErrorInfoListType());

      StatusInfoType status = new StatusInfoType();
      if (isFailed) {
        status.setStatusDescription(AppConstants.STATUS_ERROR_DESC);

        if (ObjectUtils.isEmpty(errorList)) {
          // throw wrapper exception*******for not having any errors in list
          // although status has failed
        }
      }
      else {
        status.setStatusCode(AppConstants.STATUS_SUCCESS_CODE);
        status.setStatusDescription(AppConstants.STATUS_SUCCESS_DESC);
      }

      resp.setStatusInfo(status);
      return resp;
    }
    catch (NumberFormatException e) {
      throw new BaseException(e, BATCH_RESPONSE_TYPE_BUILDER,
          GEN_BATCH_SERVICE_RESPONSE_TYPE);
    }
  }

  public static BatchResponse getResult(boolean hasBatchJobError,
      List<BaseException> errorList) throws BaseException {
    BatchResponse responseTypeBean = BatchResponseTypeBuilder
        .genBatchServiceResponseType(hasBatchJobError, errorList);
    for (BaseException e : errorList) {
      ErrorInfo err = new ErrorInfo();
      err.setErrorCode(e.getErrorCode());
      err.setErrorDescription(e.getDeveloperMessage());
      responseTypeBean.getErrorInfoList().getErrorInfos().add(err);
    }
    return responseTypeBean;
  }
}
