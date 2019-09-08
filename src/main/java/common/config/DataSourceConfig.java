package common.config;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@PropertySource("classpath:application.properties")
public class DataSourceConfig {

//  @Value("${spring.datasource.url}")
//  private String url;
//
//  @Value("${spring.datasource.driver-class-name}")
//  private String driverClassName;
//
//  @Value("${spring.datasource.username}")
//  private String userName;
//
//  @Value("${spring.datasource.password}")
//  private String passWord;
  
  private String url = "XXX";
  private String driverClassName = "com.mysql.cj.jdbc.Driver";
  private String userName = "XXX";
  private String passWord = "XXXX";
  
  private static final Logger logger = LoggerFactory
      .getLogger(DataSourceConfig.class);

  public DataSourceConfig() {

  }

//  @Bean(destroyMethod = "close")
  public DataSource generateBatchDataSource() {
    logger.debug("url is " + url);
    return DataSourceBuilder.create().url(url).driverClassName(driverClassName)
        .username(userName).password(passWord).build();

  }

  @Bean
  public NamedParameterJdbcTemplate jdbcTemplate(DataSource dataSource) {
    return new NamedParameterJdbcTemplate(dataSource);
  }

  public String getUrl() {
    return url;
  }

  public String getDriverClassName() {
    return driverClassName;
  }

  public String getUserName() {
    return userName;
  }

  public String getPassWord() {
    return passWord;
  }

}
