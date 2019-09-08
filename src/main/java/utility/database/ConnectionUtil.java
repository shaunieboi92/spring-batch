package utility.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import common.config.DataSourceConfig;
import common.exception.BaseException;

public class ConnectionUtil {

  private static final String CONNECTION_UTIL = "ConnectionUtil";
  private static final String INIT_CONNECTION = "initConnection";

  public static Connection initConnection(DataSourceConfig dataSourceConfig)
      throws BaseException {
    try {
      return DriverManager.getConnection(dataSourceConfig.getUrl(),
          dataSourceConfig.getUserName(), dataSourceConfig.getPassWord());
    }
    catch (SQLException e) {
      throw new BaseException(e, CONNECTION_UTIL, INIT_CONNECTION);
    }

  }
}
