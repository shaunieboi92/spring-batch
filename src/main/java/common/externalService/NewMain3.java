package common.externalService;

import java.util.Date;

//import java.sql.Date;

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
import common.model.Award;
import common.model.Email;
import common.service.impl.BatchServiceImpl;
import common.service.impl.EmailBatchResult;

@SpringBootApplication
@EnableScheduling
public class NewMain3 implements CommandLineRunner {
  private ApplicationContext context;

  private static final String QUERY_FIND_AWARD = "select * from award where DATE(latest_appointment_date) = date_add(:curDate, interval 3 day) and acknowledged = :acknowledged";

  private static final String QUERY_INSERT = "UPDATE award SET ACKNOWLEDGED = :acknowledged where id = :id ";

  public NewMain3(ApplicationContext context) {
    this.context = context;
  }

  @Bean
  public DataSourceConfig getDataSourceConfig() {
    return new DataSourceConfig();
  }

  public static void main(String... args) throws Exception {
    ApplicationContext context = new AnnotationConfigApplicationContext(
        NewMain3.class, BatchJobConfiguration.class, DataSourceConfig.class);
    NewMain3 main = new NewMain3(context);
    main.run(args);
  }

  @SuppressWarnings("unchecked")
  @Override
  public void run(String... args) throws Exception {
    BatchJobConfiguration<Award, Award> batchSvc = context
        .getBean(BatchJobConfiguration.class);
    Map<String, Object> paramsReader = new HashMap<>();
    paramsReader.put("curDate",new Date());
    paramsReader.put("acknowledged", 0);

    AwardBatchService result = new AwardBatchService(QUERY_FIND_AWARD,
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
