package common.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import common.config.DataSourceConfig;
import common.exception.BaseException;
import common.model.BatchResultEntity;
import common.model.Email;
import service.payload.BatchResponse;
import service.payload.BatchResponseTypeBuilder;

public class BatchServiceImpl<I, O> implements IBatchService<I, O> {

  

  @Override
  public ItemReader<I> getBaseReader(DataSourceConfig dataSourceConfig)
      throws UnexpectedInputException, ParseException, Exception {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ItemWriter<O> getBaseWriter(DataSourceConfig dataSourceConfig) {
    // TODO Auto-generated method stub
    return null;
  }

 
  
  
  


}
