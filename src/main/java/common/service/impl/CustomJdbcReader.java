package common.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.core.SqlTypeValue;
import org.springframework.jdbc.core.StatementCreatorUtils;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterUtils;
import org.springframework.jdbc.core.namedparam.ParsedSql;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.util.Assert;

import common.config.DataSourceConfig;
import common.exception.BaseException;
import common.model.Email;
import utility.database.CustomSqlParameterSourceProvider;

public class CustomJdbcReader<I> extends JdbcCursorItemReader<I> {

  private static final Logger logger = LoggerFactory
      .getLogger(CustomJdbcReader.class);

  private static final String CUSTOM_JDBC_READER = "CustomJdbcReader";
  private static final String PARSE = "parse";

  private CustomSqlParameterSourceProvider parameterSourceProvider;

  private static final String DELIMITER_COLON = ":";

  private static String pattern = "\\:[a-zA-Z0-9]+($|\\s|\\;)";

  private String paramedSql;

  private Connection con;

  private final List<String> fields = new ArrayList<>();

  DataSourceConfig dataSourceConfig;

  final Class<I> typeParameterClass;

  public CustomJdbcReader(String sqlStatement, Map<String, Object> params,
      DataSourceConfig dataSourceConfig, Class<I> typeParameterClass) {
    BatchResultImpl result = new BatchResultImpl();//
    this.typeParameterClass = typeParameterClass;
    this.dataSourceConfig = dataSourceConfig;
    parameterSourceProvider = new CustomSqlParameterSourceProvider();
    parameterSourceProvider.updateParams(params);
    init(sqlStatement, result);
  }

  public void init(String sqlStatement, BatchResultImpl result) {
    DataSource source = dataSourceConfig.generateBatchDataSource();
    this.setRowMapper(new BeanPropertyRowMapper<>(typeParameterClass));
    this.setSql(sqlStatement);
    this.setDataSource(source);
  }

  @Override
  public void setSql(String sql) {
    Assert.notNull(parameterSourceProvider,
        "You have to set parameterSourceProvider before the SQL statement");
    Assert.notNull(sql, "sql must not be null");

    try {
      paramedSql = parse(sql, parameterSourceProvider.getParams());
    }
    catch (BaseException e) {
      e.printStackTrace();
    }

    logger.debug("finalized sql statement is :" + paramedSql);
    super.setSql(paramedSql);
  }

  public String parse(String query, Map<String, Object> paramMap)
      throws BaseException {
    List<String> unparsedList = new ArrayList<>();
    String unparsedErrorMsg = "Unparsed parameters: ";
    Pattern p = Pattern.compile(pattern);
    Matcher m = p.matcher(query);
    while (m.find()) {
      fields.add(m.group(0).replace(":", "").replace(";", "").trim());
    }

    fields.forEach(x -> {
      if (!paramMap.containsKey(x)) {
        unparsedList.add(x);
      }

    });

    for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
      StringBuilder sb = new StringBuilder(entry.getKey());
      String colonApd = sb.insert(0, DELIMITER_COLON).toString();
      query = query.replace(colonApd, entry.getValue().toString());
    }

    if (!unparsedList.isEmpty()) {
      StringBuilder sb = new StringBuilder(unparsedErrorMsg);
      unparsedList.forEach(x -> sb.append(x + " | "));
      throw new BaseException(new ParseException(sb.toString(), 0),
          CUSTOM_JDBC_READER, PARSE);
    }

    return query;
  }

}
