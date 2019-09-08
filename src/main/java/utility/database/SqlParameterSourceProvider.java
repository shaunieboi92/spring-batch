package utility.database;

import java.util.Map;

import org.springframework.jdbc.core.namedparam.SqlParameterSource;

public interface SqlParameterSourceProvider {

  public void updateParams(Map<String, Object> params);

  public SqlParameterSource getSqlParameterSource();
}
