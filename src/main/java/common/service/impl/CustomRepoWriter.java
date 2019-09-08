package common.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;

import common.config.BatchJobConfiguration;
import common.model.Email;

public class CustomRepoWriter implements ItemWriter<Email> {
  
  private static final Logger logger = LoggerFactory
      .getLogger(BatchJobConfiguration.class);

  public void write(List<? extends Email> items) throws Exception {

    for (Email item : items) {
      Email indicator = new Email();
      indicator.setAddress("NewAdd");
      indicator.setName("newName");
      logger.debug("new Email is " + indicator.toString() );
    }
    
  }
  
  



}
