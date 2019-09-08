package common.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.UnexpectedJobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import common.exception.BaseException;
import common.model.Notification;
import utility.database.ConnectionUtil;

@Service
@PropertySource("classpath:application.properties")
public class UniqueIdentifierGenerator {
  private static final Logger logger = LogManager
      .getLogger(UniqueIdentifierGenerator.class);

  private static final String NOTIFICATION_TBL_NAME = "MCNS.TBL_NOTIFICATION";

  @Value("${database.mcns.url}")
  private String url;

  @Value("${database.mcns.driverClass}")
  private String driverClassName;

  @Value("${database.mcns.user}")
  private String userName;

  @Value("${database.mcns.pass}")
  private String passWord;

  private Connection con;

  private Statement stmt;

  public String generate() throws BaseException {
    return generateGroupUUID();
  }

  private String generateGroupUUID() throws BaseException {
    String strUuid;
    do {
      strUuid = generateRandomUUID();
      if (strUuid == null || strUuid.isEmpty()) {
        logger.warn(
            "Generated groupUUID is null / empty, continue to re-generate groupUUID");
        continue;
      }
      else {
        strUuid = strUuid.toLowerCase();
      }
    } while (hasNotificationByUUID(strUuid));
    logger.debug("UUID is " + strUuid);
    return strUuid.toLowerCase();
  }

  private String generateRandomUUID() throws BaseException {
    return Optional.ofNullable(UUID.randomUUID().toString()).orElseThrow(
        () -> new BaseException(new NullPointerException(), null, null));
  }

  private boolean hasNotificationByUUID(String uuid) throws BaseException {
    ResultSet rs;
    try {
      con = DriverManager.getConnection(url, userName, passWord);
      rs = stmt.executeQuery("SELECT * from " + NOTIFICATION_TBL_NAME
          + " where \"groupUUID\" = \'" + uuid + "\'");
      return rs.next();

    }
    catch (SQLException e) {
      throw new BaseException(e, "UniqueIdentifierGenerator",
          "hasNotificationByUUID");
    }
  }

}
