package common.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import common.config.DataSourceConfig;
import common.exception.BaseException;
import common.model.Email;
import common.utility.BaseWriter;

//extends BatchResultImpl
@Component
public class EmailBatchResult extends BatchServiceImpl<Email, Email> {

  private static final Logger logger = LoggerFactory
      .getLogger(EmailBatchResult.class);

  @Autowired
  EmailProcessorFactory processorFactory;

  private String preParamedSql;

  private String InsertParamedSql;

  Map<String, Object> params;

  public EmailProcessorFactory getProcessorFactory() {
    return new EmailProcessorFactory();
  }

  public void setProcessorFactory(EmailProcessorFactory processorFactory) {
    this.processorFactory = processorFactory;
  }

  private final List<BaseException> errorList = new ArrayList<>();

  public EmailBatchResult(String preParamedSql, Map<String, Object> params,
      String InsertParamedSql) {
    this.preParamedSql = preParamedSql;
    this.InsertParamedSql = InsertParamedSql;
    this.params = params;
    logger.debug("initializing " + this.getClass().getCanonicalName());

  }

  public ItemReader<Email> getBaseReader(DataSourceConfig dataSourceConfig)
      throws UnexpectedInputException, ParseException, Exception {
    return new CustomJdbcReader<Email>(preParamedSql, params, dataSourceConfig,
        Email.class);
  }

  public ItemWriter<Email> getBaseWriter(DataSourceConfig dataSourceConfig) {
    return new CustomJdbcWriter<Email>(dataSourceConfig, InsertParamedSql);
  }

  // set reader

}
