package common.utility;

import java.io.Serializable;

public class StatusInfoType implements Serializable {
  private final static long serialVersionUID = -1L;
  protected String statusCode;
  protected String statusDescription;

  /**
   * Gets value of status code property
   * @return
   */
  public String getStatusCode() {
    return statusCode;
  }

  /**
   * Sets value of status code property
   * @param statusCode
   */
  public void setStatusCode(String statusCode) {
    this.statusCode = statusCode;
  }

  /**
   * Gets value of status description property
   * @return
   */
  public String getStatusDescription() {
    return statusDescription;
  }

  /**
   * Sets value of status description property
   * @param statusDescription
   */
  public void setStatusDescription(String statusDescription) {
    this.statusDescription = statusDescription;
  }
  
 
}
