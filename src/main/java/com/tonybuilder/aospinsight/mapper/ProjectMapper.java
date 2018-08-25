package com.tonybuilder.aospinsight.mapper;

import com.tonybuilder.aospinsight.model.ProjectModel;

import java.util.List;

public interface ProjectMapper {
    ProjectModel getProjectByPath(String path);
    List<ProjectModel> getProjectList();
    int getProjectIdByPath(String path);
    boolean addProjectList(List<ProjectModel> projectList);
    boolean updateProjectLoc(List<ProjectModel> projectList);
}
