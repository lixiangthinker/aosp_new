package com.tonybuilder.aospinsight.service;

import com.tonybuilder.aospinsight.model.ProjectModel;
import com.tonybuilder.aospinsight.model.ProjectSummaryModel;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.YearMonth;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProjectSummaryServiceTests {

    @Autowired
    private ProjectSummaryService projectSummaryService;

//    aosp.source.root=/home/lixiang/source/aosp/
//    aosp.source.repo_path=.repo/manifest.xml
//    aosp.source.cache_path=.insightCache/
    @Value("${aosp.source.root}")
    private String AOSP_SOURCE_ROOT;
    @Value("${aosp.source.repo_path}")
    private String AOSP_REPO_PATH;
    @Value("${aosp.source.cache_path}")
    private String AOSP_CACHE_PATH;

    @Test
    public void testDefaultValue() {
        Assert.assertEquals("/home/lixiang/source/aosp/", AOSP_SOURCE_ROOT);
    }

    @Test
    //@Rollback
    public void testServiceNotNull() {
        Assert.assertNotNull(projectSummaryService);
    }

    @Test
    public void testGetProjectSummary() {
//        List<ProjectSummaryModel> result = projectSummaryService.getProjectSummary();
//        System.out.println("result.size() = " + result.size());
//        Assert.assertNotEquals(-1, result.size());
    }

    @Test
    public void testGetProjectSummaryByDate() {
        Calendar calendarSince = Calendar.getInstance();
        Calendar calendarUntil = Calendar.getInstance();

        calendarSince.set(Calendar.YEAR, 2018);
        calendarSince.set(Calendar.MONTH, Calendar.MARCH);
        calendarSince.set(Calendar.DATE, 5);

        Date since = calendarSince.getTime();

        calendarUntil.set(Calendar.YEAR, 2018);
        calendarUntil.set(Calendar.MONTH, Calendar.MAY);
        calendarUntil.set(Calendar.DATE, 5);
        Date until = calendarUntil.getTime();
        System.out.println("since: " + since);
        System.out.println("until: " + until);

        List<ProjectSummaryModel> result = projectSummaryService.getProjectSummaryByDate(since, until);

        Assert.assertNotNull(result);
        for (ProjectSummaryModel p : result) {
            System.out.println("since = " + p.getProjectSummarySince() + " until = " + p.getProjectSummaryUntil());
        }
    }

    @Test
    public void testGenProjectSummary() {
        YearMonth since = YearMonth.of(2017, 1);
        YearMonth until = YearMonth.now();
        boolean result = projectSummaryService.genProjectSummary("platform/frameworks/base", since, until);
        Assert.assertEquals(true, result);
    }

    @Test
    public void testGenProjectSummaryForAll() {
        YearMonth since = YearMonth.of(2017, 1);
        YearMonth until = YearMonth.now();

        boolean result = projectSummaryService.genProjectSummary(since, until);
        Assert.assertEquals(true, result);
    }
}
