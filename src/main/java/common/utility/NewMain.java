package common.utility;

import java.util.HashMap;

import java.util.Map;

import org.springframework.boot.CommandLineRunner;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import common.config.BatchJobConfiguration;
import common.config.DataSourceConfig;
import common.model.Email;
import common.service.impl.EmailBatchResult;

@SpringBootApplication
@EnableScheduling
public class NewMain implements CommandLineRunner {
  private ApplicationContext context;

  private static final String QUERY_FIND_EMAIL = "SELECT * FROM email where address = :address ORDER BY id ASC";

  private static final String QUERY_INSERT = "INSERT INTO email(NAME, ADDRESS) VALUES (:name, :address)";

  public NewMain(ApplicationContext context) {
    this.context = context;
  }

  @Bean
  public DataSourceConfig getDataSourceConfig() {
    return new DataSourceConfig();
  }

  public static void main(String... args) throws Exception {
    ApplicationContext context = new AnnotationConfigApplicationContext(
        NewMain.class, BatchJobConfiguration.class, DataSourceConfig.class);
    NewMain main = new NewMain(context);
    main.run(args);
  }

  @SuppressWarnings("unchecked")
  @Override
  public void run(String... args) throws Exception {
    BatchJobConfiguration<Email, Email> batchSvc = context
        .getBean(BatchJobConfiguration.class);
    Map<String, Object> paramsReader = new HashMap<>();
    paramsReader.put("address", "amk");
    EmailBatchResult result = new EmailBatchResult(QUERY_FIND_EMAIL,
        paramsReader, QUERY_INSERT);
    batchSvc.importBatchData(result.getBaseReader(getDataSourceConfig()),
        result.getProcessorFactory(),
        result.getBaseWriter(getDataSourceConfig()));
  }

  /**
   * Initialize all required beans with AnnotationConfigApplicationContext
   * 
   * @param args
   * @throws Exception
   */

}
