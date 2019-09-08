package common.utility.tasklet;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.UnexpectedJobExecutionException;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.dao.DataIntegrityViolationException;

import common.config.DataSourceConfig;
import common.exception.BaseException;
import utility.database.ConnectionUtil;

public class TruncateTblTasklet
    implements Tasklet, InitializingBean, StepExecutionListener {

  private static final String TRUNCATE_TBL_TASKLET = "TruncateTblTasklet";
  private static final String REPEAT_STATUS_EXEC = "RepeatStatusExecute";
  private static final String TRUNCATE = "truncate";
  private static final String AFTER_PROPERTIES_SET = "afterPropertiesSet";

  private static final Logger logger = LoggerFactory
      .getLogger(TruncateTblTasklet.class);

  private DataSourceConfig dataSourceConfig;

  private static final String TRUNCATE_SQL = "TRUNCATE ";

  private Connection con;

  private String tableName;

  private Statement stmt;

  public TruncateTblTasklet(DataSourceConfig dataSourceConfig, String tableName)
      throws BaseException {
    this.tableName = tableName;
    con = ConnectionUtil.initConnection(dataSourceConfig);
    try {
      stmt = con.createStatement();
    }
    catch (SQLException e) {
      throw new BaseException(e, TRUNCATE_TBL_TASKLET, TRUNCATE);
    }
  }

  public void truncate() throws BaseException {
    String sql = TRUNCATE_SQL + tableName;
    try {
      stmt.executeUpdate(sql);
    }
    catch (SQLException e) {
      throw new BaseException(e, TRUNCATE_TBL_TASKLET, TRUNCATE);
    }

  }

  @Override
  public void afterPropertiesSet() throws BaseException {
    ResultSet rs;
    try {
      rs = stmt.executeQuery("SELECT * from " + tableName);
      if (rs.next() == false) {
        System.out.println("TBL " + tableName + " is empty");
      }
      else
        throw new BaseException(
            new UnexpectedJobExecutionException("Error in truncate"),
            TRUNCATE_TBL_TASKLET, AFTER_PROPERTIES_SET);
    }
    catch (SQLException e) {
      throw new BaseException(e, TRUNCATE_TBL_TASKLET, AFTER_PROPERTIES_SET);
    }

  }

  @Override
  public RepeatStatus execute(StepContribution contribution,
      ChunkContext chunkContext) throws Exception {
    try {
      truncate();
    }
    catch (ConstraintViolationException | DataIntegrityViolationException e) {
      throw new BaseException(e, TRUNCATE_TBL_TASKLET, REPEAT_STATUS_EXEC);
    }

    return RepeatStatus.FINISHED;
  }

  /**
   * Backup table and store data
   * 
   * @param stepExecution
   */
  @Override
  public void beforeStep(StepExecution stepExecution) {

    logger.debug("Table truncation initialized.");
  }

  /**
   * Return complete
   */
  @Override
  public ExitStatus afterStep(StepExecution stepExecution) {
    // stepExecution.
    return ExitStatus.COMPLETED;
  }

}
