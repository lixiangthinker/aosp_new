package com.tonybuilder.aospinsight.mapper;

import com.tonybuilder.aospinsight.model.ProjectSummaryModel;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProjectSummaryMapperTests {
    private static final String TEST_TABLE = "tbl_project_summary";
    @Autowired
    ProjectSummaryMapper mapper;

    @Test
    public void testDaoNotNull() {
        Assert.assertNotNull(mapper);
    }

    //    int createNewTable(@Param("tableName") String tableName);
    //    int dropTable(@Param("tableName") String tableName);
    //    int existTable(String tableName);
    @Test
    public void testCreateTable() {
        Assert.assertNotNull(mapper);
        int result = mapper.createNewTable(TEST_TABLE);
        System.out.println("result = " + result);
        Assert.assertEquals(1, (int) mapper.existTable(TEST_TABLE));
        result = mapper.dropTable(TEST_TABLE);
        Assert.assertEquals(0, result);
        Assert.assertEquals(0, (int) mapper.existTable(TEST_TABLE));
    }

    @Test
    public void testGetProjectSummaryByProjectId() {
        int projectId = 390;
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD");
        Date sinceDate = null;
        try {
            sinceDate = sdf.parse("2017-01-01");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date untilDate = new Date();


        List<ProjectSummaryModel> result = mapper.getProjectSummaryByProjectId(projectId, sinceDate, untilDate);
        Assert.assertNotNull(result);
        for (ProjectSummaryModel project: result) {
            System.out.println("project " + project.getProjectSummaryOrigId() + " since " + project.getProjectSummarySince());
        }
    }
}
