package common.service;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;

public interface BaseProcessorFactorySvc<I,O> {
  public abstract ItemProcessor<I,O> getBaseProcessor();
}
