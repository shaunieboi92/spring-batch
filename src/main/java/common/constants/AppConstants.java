package common.constants;

public class AppConstants {

  public static final String ITEM_READER_NAME = "batch-item-reader";

  public static final String JOB_PARAM_FILE_NAME = "batch-loader.fileName";

  // Overall failure/success

  public static final String STATUS_ERROR_CODE = "00";

  public static final String STATUS_ERROR_DESC = "Batch Job Failed";

  public static final String STATUS_SUCCESS_CODE = "01";

  public static final String STATUS_SUCCESS_PARTIAL_COMPLETION_CODE = "02";

  public static final String STATUS_SUCCESS_PARTIAL_COMPLETION_DESC = "Batch Job Partial Completed";

  public static final String STATUS_SUCCESS_RETRY_COMPLETION_CODE = "03";

  public static final String STATUS_ERROR_RETRY_FAILED_CODE = "04";

  public static final String STATUS_SUCCESS_DESC = "Batch Job Completed";

  public static final String STATUS_SUCCESS_RETRY_COMPLETION_DESC = "Batch Job Retry Completed";

  public static final String STATUS_ERROR_RETRY_FAILED_DESC = "Batch Job Retry Completed";

  // RunTime err Codes
  public static final String RUNTIME_EXCEPTION_CODE = "000";

  public static final String RUNTIME_EXCEPTION_DESC = "Run-Time Exception";

  // BatchJobStepErr Codes

  public static final String READER_FAILURE_CODE = "001";

  public static final String PROCESSOR_FAILURE_CODE = "002";

  public static final String WRITER_FAILURE_CODE = "003";

  public static final String VALIDATION_FAILURE_CODE = "004";

  public static final String READER_FAILURE_DESC = "Fail - Item Reader Failed";

  public static final String PROCESSOR_FAILURE_DESC = "Fail - Item Processor Failed";

  public static final String WRITER_FAILURE_DESC = "Fail - Item Writer Failed";

  public static final String VALIDATION_FAILURE_DESC = "Fail - Input Validation Failed";

  public static final String EX_MESSAGE = ", message:";

  // DELIMITERS

  public static final String DELIMITER_DASH = "-";
  public static final String DELIMITER_COMMA = ",";
  public static final String DELIMITER_SLASH = "/";
  public static final String DELIMITER_BACKSLASH = "\\";
  
  //MCNS
  public static final String NOTIFICATION_STATUS_SENT = "sent"; 
  
  public static final short TRUE = 1;
  public static final short FALSE = 0;
  
  public static final String START_FROM_DATE_FORMAT = "yyyy-MM-dd'T'HH:MM:ssZ";


  private AppConstants() {
  }
}
