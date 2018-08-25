package com.tonybuilder.aospinsight;

import com.tonybuilder.aospinsight.model.ProjectSummaryModel;
import com.tonybuilder.aospinsight.service.ProjectSummaryService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AospInsightSsmApplicationTests {

    @Autowired
    private ProjectSummaryService projectSummaryService;

    @Test
    @Rollback
    public void contextLoads() {
        Assert.assertNotNull(projectSummaryService);
        List<ProjectSummaryModel> result = projectSummaryService.getProjectSummary();
        System.out.println("result.size() = " + result.size());
    }

}
