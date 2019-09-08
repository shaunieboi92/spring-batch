package common.config;

import java.io.File;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.BatchConfigurer;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.ResourceAwareItemReaderItemStream;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.support.DatabaseType;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.WritableResource;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.scheduling.annotation.Scheduled;

import com.google.common.base.Preconditions;

import common.exception.BaseException;
import common.service.BaseProcessorFactorySvc;
import common.service.impl.BatchServiceImpl;
import common.utility.JobCompletionNotificationListener;
import common.utility.tasklet.BackUpTableTasklet;
import common.utility.tasklet.TruncateTblTasklet;

//(exclude={DataSourceAutoConfiguration.class})

//@EnableAutoConfiguration
@Configuration
@EnableBatchProcessing
public class BatchJobConfiguration<I, O> extends DefaultBatchConfigurer
    implements BatchService<I, O> {
  private static final Logger logger = LoggerFactory
      .getLogger(BatchJobConfiguration.class);

  @Autowired
  public JobBuilderFactory jobBuilderFactory;

  @Autowired
  public StepBuilderFactory stepBuilderFactory;

  private final List<BaseException> errorList = new ArrayList<>();

  @Autowired
  JobLauncher jobLauncher;

  private ApplicationContext context;

  @Autowired
  private DataSourceConfig dataSourceConfig;

  @Autowired
  private Environment env;

  // @Autowired
  // JobCompletionNotificationListener listener;
  //
  // @Bean
  // JobCompletionNotificationListener getListener() {
  // return new JobCompletionNotificationListener();
  // }

  @Override
  public void setDataSource(DataSource dataSource) {
    dataSourceConfig = getDataSourceConfig();
    super.setDataSource(dataSourceConfig.generateBatchDataSource());
  }

  // @Bean
  public DataSourceConfig getDataSourceConfig() {
    return new DataSourceConfig();
  }

  // @Bean(name = "jobInit")
  public Job jobInit(ItemReader<I> itemReader,
      BaseProcessorFactorySvc<I, O> processorFactory, ItemWriter<O> writer)
      throws Exception {

    return jobBuilderFactory.get("jobInit").incrementer(new RunIdIncrementer())
        .start(update1(itemReader, processorFactory, writer)).build();
  }
  /////////
  // @Bean
  // public Step updateAward() {
  // return stepBuilderFactory.get("updateAward").<Award, Award> chunk(10)
  // .reader(reader()).processor(awardProcessor).writer(writer()).build();
  // }

  // @Bean
  public Step update1(ItemReader<I> itemReader,
      BaseProcessorFactorySvc<I, O> processorFactory, ItemWriter<O> writer)
      throws UnexpectedInputException, ParseException, Exception {
    System.out.println("In the update1u noe");
    return stepBuilderFactory.get("update1").<I, O> chunk(10).reader(itemReader)
        .processor(processorFactory.getBaseProcessor()).writer(writer).build();
  }

  public Job JobBackUpAndTruncate(String tableName) throws BaseException {
    return jobBuilderFactory.get("backUpAndTruncate")
        .incrementer(new RunIdIncrementer()).start(backUp(tableName))
        .next(truncate(tableName)).build();
  }

  // public Job JobBackUpAndTruncate(String tableName) throws BaseException {
  // return jobBuilderFactory.get("backUpAndTruncate")
  // .incrementer(new RunIdIncrementer()).listener(listener)
  // .start(backUp(tableName)).next(truncate(tableName)).build();
  // }

  public Step backUp(String tableName) {
    return stepBuilderFactory.get("backUp")
        .tasklet(new BackUpTableTasklet(getDataSourceConfig(), tableName))
        .build();
  }

  public Step truncate(String tableName) throws BaseException {
    return stepBuilderFactory.get("backUp")
        .tasklet(new TruncateTblTasklet(getDataSourceConfig(), tableName))
        .build();
  }
  ////
  // @SuppressWarnings("unchecked")
  // @Bean
  // public ItemReader<T> genericReader() {
  // return this.readerFactory2.getBaseReader();
  // }

  ///////
  // @Bean
  // public RepositoryItemReader<Award> reader() {
  // RepositoryItemReader<Award> reader = new RepositoryItemReader<>();
  // reader.setRepository(awardRepository);
  // reader.setMethodName("findAll");
  // Map<String, Sort.Direction> sorts = new HashMap<>();
  // sorts.put("id", Direction.ASC);
  // reader.setSort(sorts);
  // return reader;
  // }
  ///////
  // @Bean
  // RepositoryItemWriter<Award> writer() {
  // RepositoryItemWriter<Award> writer = new RepositoryItemWriter<>();
  // writer.setRepository(awardRepository);
  // writer.setMethodName("saveAndFlush");
  // logger.debug("Writer created: " + writer.toString());
  // return writer;
  //
  // }

  // setter for reader, processor and writer
  // @Bean
  // public void initData(BaseReaderFactorySvc<T> readerFactory) {
  // this.readerFactory2 = readerFactory;
  // }

  // @Bean
  // EmailReaderFactory getReaderFac() {
  // return emailReaderFactory;
  // }

  @Scheduled(cron = "0 */1 * * * ?")
  public JobParameters initJobParam() {
    System.out.println("initiating job param");
    return new JobParametersBuilder()
        .addString("JobID", String.valueOf(System.currentTimeMillis()))
        .toJobParameters();
  }

  // @Bean
  // public DataSource dataSource() {
  // final DriverManagerDataSource dataSource = new DriverManagerDataSource();
  // dataSource.setDriverClassName(
  // Preconditions.checkNotNull(env.getProperty("jdbc.driverClassName")));
  // dataSource.setUrl(Preconditions.checkNotNull(env.getProperty("jdbc.url")));
  // dataSource
  // .setUsername(Preconditions.checkNotNull(env.getProperty("jdbc.user")));
  // dataSource
  // .setPassword(Preconditions.checkNotNull(env.getProperty("jdbc.pass")));
  //
  // return dataSource;
  // }

  @Override
  public BatchServiceImpl<I, O> importBatchData(ItemReader<I> itemReader,
      BaseProcessorFactorySvc<I, O> processorFactory, ItemWriter<O> writer)
      throws Exception {
    // EmailBatchResult result = new EmailBatchResult();


    try {
      JobExecution execution;
      Job job = jobInit(itemReader, processorFactory, writer);
      execution = jobLauncher.run(job, initJobParam());
      // errorList.addAll(execution.getAllFailureExceptions());
      String exitStatus = execution.getExitStatus().getExitCode();
//      result.setErrorList(errorList);
      logger.debug("Batch Job Status: " + execution.getStatus());

    }
    catch (JobExecutionAlreadyRunningException | JobRestartException
        | JobInstanceAlreadyCompleteException
        | JobParametersInvalidException e) {
      logger.error(ExceptionUtils.getStackTrace(e));
    }
    return null;
  }

  @Override
  public List<BaseException> setErrorList() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void executeTruncate(String tableName) {
    try {
      JobExecution execution;
      Job job = JobBackUpAndTruncate(tableName);
      execution = jobLauncher.run(job, initJobParam());
      String exitStatus = execution.getExitStatus().getExitCode();
      // result.set

      logger.debug("Batch Job Status: " + execution.getStatus());
    }
    catch (Exception e) {
      e.printStackTrace();
    }

  }

  /**
   * BaseWriter writerBuilder
   * 
   * @return
   */

  /**
   * BaseReader readerBuilder
   * 
   * @return
   */

  // @Bean
  // public RepositoryItemReader<Award> jpaReaderBuilder()
  // throws UnexpectedInputException, ParseException, Exception {
  // Map<String, Sort.Direction> sorts = new HashMap<>();
  // // readerFactory.getBaseReader(Award.class)
  // // .repositoryItemReader(awardRepository, "findAll").read();
  //
  // // RepositoryItemReader<Award> reader = new RepositoryItemReader<>();
  // // reader.setRepository(awardRepository);
  // // reader.setMethodName("findAll");
  // // reader.setPageSize(1);
  // // sorts.put("id", Direction.ASC);
  // // reader.setSort(sorts);
  // // return reader;
  // return readerFactory.getBaseReader(Award.class)
  // .repositoryItemReader(awardRepository, "findAll");
  //
  // }

  // @Bean
  // public ItemReader<Book> newReader() {
  // RepositoryItemReader<Book> reader = new RepositoryItemReader<>();
  // reader.setRepository(bookRepository);
  // reader.setMethodName("findAll");
  // Map<String, Sort.Direction> sorts = new HashMap<>();
  // sorts.put("id", Direction.DESC);
  // reader.setSort(sorts);
  // reader.setPageSize(100);
  // return reader;
  // }

  /**
   * MultiSourceReader
   * 
   * @param inputResources
   * @return
   */
  // @Bean
  // public MultiResourceItemReader<Award> multiResourceItemReader(
  // Resource[] inputResources) {
  //
  // MultiResourceItemReader<Award> resourceItemReader = new
  // MultiResourceItemReader<>();
  // resourceItemReader.setResources(inputResources);
  // resourceItemReader.setDelegate(readerBuilder());
  // return resourceItemReader;
  // }

}
