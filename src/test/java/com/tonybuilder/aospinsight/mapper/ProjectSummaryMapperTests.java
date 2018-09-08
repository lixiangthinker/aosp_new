package com.tonybuilder.aospinsight.mapper;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProjectSummaryMapperTests {
    private static final String TEST_TABLE = "tbl_project_summary_test";
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
        Assert.assertEquals(1, mapper.existTable(TEST_TABLE));
        result = mapper.dropTable(TEST_TABLE);
        Assert.assertEquals(0, result);
        Assert.assertEquals(0, mapper.existTable(TEST_TABLE));
    }
}
