package common.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import common.model.Email;

@Component
public class EmailProcessorFactory extends BaseProcessorFactorySvcImpl<Email,Email>{

  private static final Logger logger = LoggerFactory
      .getLogger(EmailProcessorFactory.class);
  @Override
  public ItemProcessor<Email, Email> getBaseProcessor() {
    logger.debug("retrieveing new processor");
    return new EmailProcessor();
  }

}
