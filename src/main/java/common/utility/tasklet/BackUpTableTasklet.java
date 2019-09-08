package common.utility.tasklet;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.jdbc.core.JdbcTemplate;

import common.config.DataSourceConfig;
import utility.database.ConnectionUtil;

public class BackUpTableTasklet implements Tasklet {

  private Connection con;

  private DataSourceConfig dataSourceConfig;

  private final String tableName;

  private final String tempTableName;

  private JdbcTemplate template;

  private static final Logger logger = LoggerFactory
      .getLogger(BackUpTableTasklet.class);

  public BackUpTableTasklet(DataSourceConfig dataSourceConfig,
      String tableName) {
    this.tableName = tableName;
    this.dataSourceConfig = dataSourceConfig;
    this.tempTableName = "TMP_" + tableName;
  }

  public String getTempTableName() {
    return tempTableName;
  }

  @Override
  public RepeatStatus execute(StepContribution contribution,
      ChunkContext chunkContext) throws Exception {
    con = ConnectionUtil.initConnection(dataSourceConfig);
    DatabaseMetaData dbm = con.getMetaData();
    template = new JdbcTemplate(dataSourceConfig.generateBatchDataSource());
    logger.debug("password is: " + dataSourceConfig.getPassWord());
    final String copyTableKeysIndex = "CREATE TABLE " + tempTableName + " LIKE "
        + tableName;
    final String copyTableData = "INSERT INTO " + tempTableName
        + " SELECT * FROM " + tableName;

    ResultSet rs = dbm.getTables(null, null, tempTableName, null);
    if (rs.next()) {
      logger.debug(tempTableName + " exists");
    }
    else {
      template.execute(copyTableKeysIndex);
      template.execute(copyTableData);
      logger.debug("Processing: " + copyTableKeysIndex);
      logger.debug("Processing: " + copyTableData);
    }
    return RepeatStatus.FINISHED;
  }

}
