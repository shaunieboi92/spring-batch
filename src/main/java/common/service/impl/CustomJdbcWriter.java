package common.service.impl;

import javax.sql.DataSource;

import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import common.config.DataSourceConfig;
import common.model.Email;

public class CustomJdbcWriter<O> extends JdbcBatchItemWriter<O> {

  DataSourceConfig dataSourceConfig;

  public NamedParameterJdbcTemplate jdbcTemplate(DataSource dataSource) {
    return new NamedParameterJdbcTemplate(dataSource);
  }

  public CustomJdbcWriter(DataSourceConfig dataSourceConfig, String preParamedSql) {
    this.dataSourceConfig = dataSourceConfig;
    System.out.println("writing...");
    this.setSql(preParamedSql);
    logger.debug("writer sql: " + preParamedSql);
    this.setItemSqlParameterSourceProvider(
        new BeanPropertyItemSqlParameterSourceProvider<O>());
    DataSource source = dataSourceConfig.generateBatchDataSource();
    this.setJdbcTemplate(jdbcTemplate(source));
    this.setDataSource(source);
    this.afterPropertiesSet();

  }

}
