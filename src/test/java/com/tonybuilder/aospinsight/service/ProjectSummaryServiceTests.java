package com.tonybuilder.aospinsight.service;

import com.tonybuilder.aospinsight.model.ProjectSummaryModel;
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
public class ProjectSummaryServiceTests {

    @Autowired
    private ProjectSummaryService projectSummaryService;

    @Test
    @Rollback
    public void testServiceNotNull() {
        Assert.assertNotNull(projectSummaryService);
    }

    @Test
    public void testGetProjectSummary() {
        List<ProjectSummaryModel> result = projectSummaryService.getProjectSummary();
        System.out.println("result.size() = " + result.size());
        Assert.assertNotEquals(-1, result.size());
    }
}
