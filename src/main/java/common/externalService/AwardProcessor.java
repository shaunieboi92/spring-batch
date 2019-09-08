package common.externalService;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import common.constants.AppConstants;
import common.model.Award;
import common.model.Email;

public class AwardProcessor implements ItemProcessor<Award, Award> {

  private static final Logger logger = LoggerFactory
      .getLogger(AwardProcessor.class);


  @Override
  public Award process(Award item) throws Exception {
    final Date latestApptDate = item.getLatestApptDate();
    logger.debug("latestApptDate = " + latestApptDate);
    item.setAcknowledged(AppConstants.TRUE);
    logger.debug("acknowledged is now : " + item.getAcknowledged());
    return item;
  }
}
