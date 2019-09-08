package common.service.impl;

import java.util.List;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import common.config.DataSourceConfig;
import common.exception.BaseException;
import common.model.Email;
import service.payload.BatchResponse;

/**
 * The Interface IBatchResult.
 * 
 * @author shaun.lee
 *
 */
public interface IBatchService<I, O> {

  public ItemReader<I> getBaseReader(DataSourceConfig dataSourceConfig)
      throws UnexpectedInputException, ParseException, Exception;

  public ItemWriter<O> getBaseWriter(DataSourceConfig dataSourceConfig);
}
