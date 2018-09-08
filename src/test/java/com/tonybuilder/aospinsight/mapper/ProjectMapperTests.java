package com.tonybuilder.aospinsight.mapper;

import com.tonybuilder.aospinsight.model.ProjectModel;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProjectMapperTests {
    private static final String TEST_TABLE = "tbl_project";
    @Autowired
    ProjectMapper mapper;

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

    private ProjectModel getProjectModel(String projectName, String projectPath, double line) {
        ProjectModel projectModel = new ProjectModel();
        projectModel.setProjectIsDiscarded((byte) 0x00);
        projectModel.setProjectIsExternalSrc((byte) 0x00);
        projectModel.setProjectModuleType(0);
        projectModel.setProjectName(projectName);
        projectModel.setProjectPath(projectPath);
        projectModel.setProjectLastSubmitData("2018-08-01");
        projectModel.setProjectTotalLines(line);
        return projectModel;
    }
    @Test
    public void testAddProject() {
        Assert.assertNotNull(mapper);
        ProjectModel projectModel = getProjectModel("platform/frameworks/av",
                "frameworks/av", (double) 3000);
        int result = mapper.addProject(projectModel);
        System.out.println("result = " + result);
    }

    @Test
    public void testAddProjectList() {
        ProjectModel projectModel1 = getProjectModel("platform/frameworks/proj1",
                "frameworks/proj1", (double) 7000);
        ProjectModel projectModel2 = getProjectModel("platform/frameworks/proj2",
                "frameworks/proj2", (double) 8000);
        ProjectModel projectModel3 = getProjectModel("platform/frameworks/proj3",
                "frameworks/proj3", (double) 9000);
        List<ProjectModel> list = new ArrayList<>();
        list.add(projectModel1);
        list.add(projectModel2);
        list.add(projectModel3);

        int result = mapper.addProjectList(list);
        System.out.println("result = " + result);
    }

    @Test
    public void testGetProjectByPath() {
        ProjectModel result = mapper.getProjectByPath("frameworks/base");
        System.out.println(result.getProjectName());
    }

    @Test
    public void testGetProjectList() {
        List<ProjectModel> result = mapper.getProjectList();
        for(ProjectModel p: result) {
            System.out.println(p.getProjectName());
        }
    }

    @Test
    public void testGetProjectIdByPath() {
        Integer id = mapper.getProjectIdByPath("frameworks/base");
        System.out.println("id = " + id);
        id = mapper.getProjectIdByPath("path/not/exist");
        Assert.assertNull(id);
    }
}
