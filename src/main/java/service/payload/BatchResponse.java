package service.payload;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

import common.utility.ErrorInfoListType;
import common.utility.StatusInfoType;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BatchResponse implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -1L;
  
  protected StatusInfoType statusInfo;
  protected ErrorInfoListType errorInfoList;

  public StatusInfoType getStatusInfo() {
    return statusInfo;
  }

  public void setStatusInfo(StatusInfoType statusInfo) {
    this.statusInfo = statusInfo;
  }

  public ErrorInfoListType getErrorInfoList() {
    return errorInfoList;
  }

  public void setErrorInfoList(ErrorInfoListType errorInfoList) {
    this.errorInfoList = errorInfoList;
  }

}
