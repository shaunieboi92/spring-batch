package common.exception;

import common.constants.AppConstants;
import common.utility.BatchJobTypeErrCode;

public class BaseException extends Exception {

  /**
   * 
   */
  private static final long serialVersionUID = 1542912660721409422L;

  private BatchJobTypeErrCode errorType;

  private String errorCode;

  private String errorDesc;

  private String developerMessage;

  private final String logClass;

  private final String logMethod;

  public String getDeveloperMessage() {
    return developerMessage;
  }

  /** The exception bean. */
  private final Exception exceptionBean;

  public BatchJobTypeErrCode getErrorType() {
    return errorType;
  }

  public void setErrorType(BatchJobTypeErrCode errorType) {
    this.errorType = errorType;
  }

  public String getErrorCode() {
    return errorCode;
  }

  public void setErrorCode(String errorCode) {
    this.errorCode = errorCode;
  }

  public String getErrorDesc() {
    return errorDesc;
  }

  public void setErrorDesc(String errorDesc) {
    this.errorDesc = errorDesc;
  }

  public Exception getExceptionBean() {
    return exceptionBean;
  }

  // Failure at processor
  public BaseException(Exception ex, String developerMessage, String classDef,
      String methodDef) {
    super(ex.getClass() + ":" + ex.getMessage());

    // Processor Failure - Business Logic
    this.errorType = BatchJobTypeErrCode.PROCESSOR_FAIURE;
    this.errorCode = errorType.getJobStepErrCode();
    this.errorDesc = errorType.getJobStepErrDescription();
    this.exceptionBean = ex;
    this.developerMessage = developerMessage;
    this.logClass = classDef;
    this.logMethod = methodDef;
  }

  // failure at intermitent steps
  public BaseException(Exception ex, String classDef, String methodDef) {
    super(ex.getClass() + ":" + ex.getMessage());

    this.errorType = BatchJobTypeErrCode.RUNTIME_FAILURE;
    this.errorCode = errorType.getJobStepErrCode();
    this.errorDesc = errorType.getJobStepErrDescription();
    this.exceptionBean = ex;
    this.logClass = classDef;
    this.logMethod = methodDef;
  }

}
