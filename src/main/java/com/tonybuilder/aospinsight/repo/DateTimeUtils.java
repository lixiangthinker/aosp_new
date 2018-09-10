package com.tonybuilder.aospinsight.repo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.time.*;
import java.util.Date;

public class DateTimeUtils {
    private static final Logger logger = LoggerFactory.getLogger(DateTimeUtils.class);
    public static Timestamp getTimestampByMonth(YearMonth month) {
        LocalDateTime since = LocalDateTime.of(month.getYear(), month.getMonth(), 1, 0,0,0);
        ZonedDateTime zonedSince = ZonedDateTime.of(since, ZoneId.of("Z"));
        Timestamp tsSince = Timestamp.from(zonedSince.toInstant());
        return tsSince;
    }

    public static Timestamp[] getSinceAndUntilTsByMonth(YearMonth month) {
        LocalDateTime since = LocalDateTime.of(month.getYear(), month.getMonth(), 1, 0,0,0);
        ZonedDateTime zonedSince = ZonedDateTime.of(since, ZoneId.of("Z"));
        Timestamp tsSince = Timestamp.from(zonedSince.toInstant());
        Timestamp tsUntil = Timestamp.from(zonedSince.plusMonths(1).toInstant());
        Timestamp[] result = new Timestamp[2];
        logger.info("since = " + tsSince + " until = " + tsUntil);
        result[0] = tsSince;
        result[1] = tsUntil;
        return result;
    }

    public static Date getDateFromYearMonth(YearMonth yearMonth) {
        LocalDateTime localDateTime = LocalDateTime.of(yearMonth.getYear(), yearMonth.getMonth(), 1, 0, 0);
        Date result = Date.from(localDateTime.toInstant(ZoneOffset.UTC));
        return result;
    }
}
