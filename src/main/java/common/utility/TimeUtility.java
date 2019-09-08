package common.utility;

import java.util.Date;

import org.springframework.stereotype.Component;

import java.util.Calendar;

@Component
public class TimeUtility {
  public Date addDays(Date date, int days) {
    Calendar c = Calendar.getInstance();
    c.setTime(date);
    c.add(Calendar.DATE, days);
    return c.getTime();
  }
}
