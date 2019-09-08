package common.utility;

import java.util.List;
import java.util.Optional;

import org.springframework.boot.CommandLineRunner;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import org.springframework.scheduling.annotation.EnableScheduling;

import common.config.BatchJobConfiguration;
import common.config.DataSourceConfig;
import common.model.Email;
import common.repository.EmailRepository;

@SpringBootApplication
@EnableScheduling
public class NewMain2 implements CommandLineRunner {
  private ApplicationContext context;

  @Bean
  public EmailRepository getRepo() {
    return new EmailRepository();
  }

  public NewMain2(ApplicationContext context) {
    this.context = context;
  }

  public static void main(String... args) throws Exception {
    ApplicationContext context = new AnnotationConfigApplicationContext(
        NewMain2.class, BatchJobConfiguration.class, DataSourceConfig.class);
    NewMain2 main = new NewMain2(context);
    main.run(args);
  }

  @SuppressWarnings("unchecked")
  @Override
  public void run(String... args) throws Exception {
    BatchJobConfiguration<Email, Email> batchSvc = context
        .getBean(BatchJobConfiguration.class);
    // EmailBatchResult result = new EmailBatchResult();
    batchSvc.executeTruncate(getRepo().getActualTbl());

  }

}
