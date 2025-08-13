package io.mgueye.sandbox;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoLocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class DateAndTimePg {


  public static void main(String[] args) {
    // https://jeffkayser.com/projects/date-format-string-composer/index.html
    // https://www.baeldung.com/java-string-to-instant

    //Instant dateTimeMillisecondUtcFormat = DateTimeFormatter.ofPattern("%Y-%m-%d %H:%M%S.%L %z");
    //DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("%Y-%m-%d");
    //LocalDateTime dateTimeMsUtc = LocalDateTime.parse("2024-10-30 00:00:00.000 +0100", dateTimeMillisecondUtcFormat);
    //LocalDateTime date = LocalDateTime.parse("2024-10-30", dateFormat);
    Date today = new Date(2024, Calendar.NOVEMBER, 20);
    ChronoLocalDateTime d = LocalDateTime.parse("2024-11-20T00:00:00");
    System.out.println(today);
    System.out.println(d.atZone(ZoneId.of( "Europe/Paris" )).toInstant());
  }
}
