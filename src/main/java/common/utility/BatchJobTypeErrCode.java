package common.utility;

import common.constants.AppConstants;

public enum BatchJobTypeErrCode {
  PROCESSOR_FAIURE(AppConstants.PROCESSOR_FAILURE_CODE,
      AppConstants.PROCESSOR_FAILURE_DESC), READER_FAILURE(
          AppConstants.READER_FAILURE_CODE,
          AppConstants.READER_FAILURE_DESC), WRITER_FAILURE(
              AppConstants.WRITER_FAILURE_CODE,
              AppConstants.WRITER_FAILURE_DESC), VALIDATION_FAILURE(
                  AppConstants.VALIDATION_FAILURE_CODE,
                  AppConstants.VALIDATION_FAILURE_DESC), RUNTIME_FAILURE(
                      AppConstants.RUNTIME_EXCEPTION_CODE,
                      AppConstants.RUNTIME_EXCEPTION_DESC);

  private String jobStepErrCode;
  private String jobStepErrDescription;

  BatchJobTypeErrCode(String jobStepErrCode, String jobStepErrDescription) {
    this.jobStepErrCode = jobStepErrCode;
    this.jobStepErrDescription = jobStepErrDescription;
  }

  public String getJobStepErrCode() {
    return jobStepErrCode;
  }

  public void setJobStepErrCode(String jobStepErrCode) {
    this.jobStepErrCode = jobStepErrCode;
  }

  public String getJobStepErrDescription() {
    return jobStepErrDescription;
  }

  public void setJobStepErrDescription(String jobStepErrDescription) {
    this.jobStepErrDescription = jobStepErrDescription;
  }

}
