package common.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Validator;

import common.model.Email;

public class EmailProcessor implements ItemProcessor<Email, Email> {

  private static final Logger logger = LoggerFactory
      .getLogger(EmailProcessor.class);

  Validator validator;

//  public void setValidator(Validator validator) {
//    this.validator = validator;
//  }

  @Override
  public Email process(final Email item) throws Exception {
    final String name = item.getName().toUpperCase();
    logger.debug("new name = " + name);
    final String address = item.getAddress().toUpperCase();
    logger.debug("new addres = " + address);

    Email newItem = new Email();
    newItem.setAddress(address);
    newItem.setName(name);
//    final Email transformedEmail = new Email(firstName, lastName);

//    log.info("Converting (" + item + ") into (" + transformedEmail + ")");
    
    return newItem;
  }

//  private BindingResult BindAndValidate(Email email) {
//    DataBinder binder = new DataBinder(email);
//    binder.setValidator(validator);
//
//    binder.validate();
//
//    return binder.getBindingResult();
//
//  }

}
