package utility.database;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

@Component
public class CustomSqlParameterSourceProvider
    implements SqlParameterSourceProvider {

  private static final Logger logger = LoggerFactory
      .getLogger(CustomSqlParameterSourceProvider.class);

  private Map<String, Object> params;

  public void updateParams(Map<String, Object> params) {
    if (!params.isEmpty()) {
      params.forEach((k, v) -> {
        String updatedVal = null;
        if (v instanceof String) {
          String expr = v.toString();
          StringBuilder exprBuilder = new StringBuilder(expr);
          exprBuilder.insert(0, "'").append("'");
          updatedVal = exprBuilder.toString();
          params.replace(k, updatedVal);
        }
        else if(v instanceof java.util.Date) {
          java.util.Date newV = (java.util.Date)v;
          String sqlDate = new java.sql.Date(newV.getTime()).toString();
          StringBuilder exprBuilder = new StringBuilder(sqlDate);
          exprBuilder.insert(0, "'").append("'");
          updatedVal = exprBuilder.toString();
          params.replace(k, updatedVal);
        }
        
        logger.debug("sql parameters: " + k + " : " + v);
        logger.debug("sql updated parameters: " + k + " : " + updatedVal);

      });
      this.params = params;
    }
  }

  public Map<String, Object> getParams() {
    return params;
  }

  @Override
  public SqlParameterSource getSqlParameterSource() {
    final MapSqlParameterSource paramSource = new MapSqlParameterSource();
    if (null != params) {
      paramSource.addValues(params);
    }
    return paramSource;
  }

  public CustomSqlParameterSourceProvider() {

  }

}
