package common.utility;

public enum BatchJobStepStatus {
  FAILED("Failed"), SUCCESS("Success");

  private String stepType;

  private BatchJobStepStatus(String stepType) {
    this.stepType = stepType;
  }

  public String getStepType() {
    return stepType;
  }

  public void setStepType(String stepType) {
    this.stepType = stepType;
  }
}
