package common.config;

import java.util.List;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.data.jpa.repository.JpaRepository;

import common.exception.BaseException;
import common.model.Email;
import common.service.BaseProcessorFactorySvc;
import common.service.impl.BatchServiceImpl;
import common.service.impl.IBatchService;

public interface BatchService<I, O> {
  public BatchServiceImpl importBatchData(ItemReader<I> itemReader,
      BaseProcessorFactorySvc<I, O> processorFactory, ItemWriter<O> writer)
      throws Exception;

  public List<BaseException> setErrorList();

  public void executeTruncate(String tableName);

}
