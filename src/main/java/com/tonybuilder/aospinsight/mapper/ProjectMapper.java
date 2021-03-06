package com.tonybuilder.aospinsight.mapper;

import com.tonybuilder.aospinsight.model.ProjectModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@Mapper
public interface ProjectMapper {
    Integer createNewTable(@Param("tableName") String tableName);
    Integer dropTable(@Param("tableName") String tableName);
    Integer existTable(String tableName);

    ProjectModel getProjectByPath(@Param("path") String path);
    ProjectModel getProjectByName(@Param("projectName") String projectName);
    ProjectModel getProjectById(@Param("id") Integer id);
    List<ProjectModel> getProjectList();
    Integer getProjectIdByPath(@Param("path") String path);
    Integer addProjectList(List<ProjectModel> projectList);
    Integer addProject(ProjectModel project);
    Integer updateProjectLoc(List<ProjectModel> projectList);
}
