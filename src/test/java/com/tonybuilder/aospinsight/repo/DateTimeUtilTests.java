package com.tonybuilder.aospinsight.repo;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DateTimeUtilTests {
    private static final Logger logger = LoggerFactory.getLogger(DateTimeUtilTests.class);
    @Test
    public void getDateFromYearMonth() {
        YearMonth yearMonth = YearMonth.of(2017, 1);
        Date result = DateTimeUtils.getDateFromYearMonth(yearMonth);

        String strResult = result.toString();
        logger.info("result = " + result);
        Assert.assertEquals("Sun Jan 01 08:00:00 CST 2017", strResult);
    }

    @Test
    public void testSimpleDateFormat() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date sinceDate = sdf.parse("2017-01-01");
        Date untilDate = sdf.parse("2018-09-01");

        logger.info("sinceDate" + sinceDate);
        logger.info("untilDate" + untilDate);
    }
}
