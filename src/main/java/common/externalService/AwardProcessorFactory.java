package common.externalService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import common.model.Award;
import common.service.impl.BaseProcessorFactorySvcImpl;

public class AwardProcessorFactory
    extends BaseProcessorFactorySvcImpl<Award, Award> {

  private static final Logger logger = LoggerFactory
      .getLogger(AwardProcessorFactory.class);

  @Override
  public ItemProcessor<Award, Award> getBaseProcessor() {
    logger.debug("retrieveing new processor");
    return new AwardProcessor();
  }

}
