package common.utility;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ErrorInfoListType implements Serializable {
  private final static long serialVersionUID = -1L;
  protected List<ErrorInfoListType.ErrorInfo> errorInfos;

  public List<ErrorInfoListType.ErrorInfo> getErrorInfos() {
    if (errorInfos == null) {
      errorInfos = new ArrayList<>();
    }
    return this.errorInfos;
  }

  public static class ErrorInfo implements Serializable {

    private final static long serialVersionUID = -1L;
    protected String errorCode;
    protected String errorDescription;

    /**
     * Gets the value of the errorCode property.
     * 
     * @return
     * 
     * 
     */
    public String getErrorCode() {
      return errorCode;
    }

    /**
     * Sets the value of the errorCode property.
     * 
     * @param value
     * 
     * 
     */
    public void setErrorCode(String value) {
      this.errorCode = value;
    }

    /**
     * Gets the value of the errorDescription property.
     * 
     * @return
     * 
     * 
     */
    public String getErrorDescription() {
      return errorDescription;
    }

    /**
     * Sets the value of the errorDescription property.
     * 
     * @param value
     * 
     * 
     */
    public void setErrorDescription(String value) {
      this.errorDescription = value;
    }

  }

}
