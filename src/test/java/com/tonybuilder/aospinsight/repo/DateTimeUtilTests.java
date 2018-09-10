package com.tonybuilder.aospinsight.repo;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DateTimeUtilTests {

    @Test
    public void getDateFromYearMonth() {
        YearMonth yearMonth = YearMonth.of(2017, 1);
        Date result = DateTimeUtils.getDateFromYearMonth(yearMonth);

        String strResult = result.toString();
        System.out.println("result = " + result);
        Assert.assertEquals("Sun Jan 01 08:00:00 CST 2017", strResult);
    }

    @Test
    public void testSimpleDateFormat() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date sinceDate = sdf.parse("2017-01-01");
        Date untilDate = sdf.parse("2018-09-01");

        System.out.println("sinceDate" + sinceDate);
        System.out.println("untilDate" + untilDate);
    }
}
