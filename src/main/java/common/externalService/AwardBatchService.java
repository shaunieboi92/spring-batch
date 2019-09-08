package common.externalService;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import common.config.DataSourceConfig;
import common.model.Award;
import common.service.impl.BatchResultImpl;
import common.service.impl.BatchServiceImpl;
import common.service.impl.CustomJdbcReader;
import common.service.impl.CustomJdbcWriter;

public class AwardBatchService extends BatchServiceImpl<Award, Award> {

  private static final Logger logger = LoggerFactory
      .getLogger(AwardBatchService.class);

  private String preParamedSql;

  private String InsertParamedSql;

  Map<String, Object> params;

  public AwardBatchService(String preParamedSql, Map<String, Object> params,
      String InsertParamedSql) {
    this.preParamedSql = preParamedSql;
    this.InsertParamedSql = InsertParamedSql;
    this.params = params;
    logger.debug("initializing " + this.getClass().getCanonicalName());

  }

  @Override
  public ItemReader<Award> getBaseReader(DataSourceConfig dataSourceConfig)
      throws UnexpectedInputException, ParseException, Exception {
    return new CustomJdbcReader<Award>(preParamedSql, params, dataSourceConfig,
        Award.class);
  }

  @Override
  public ItemWriter<Award> getBaseWriter(DataSourceConfig dataSourceConfig) {
    return new CustomJdbcWriter<Award>(dataSourceConfig, InsertParamedSql);
  }
  
  public AwardProcessorFactory getProcessorFactory() {
    return new AwardProcessorFactory();
  }

}
