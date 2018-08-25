package com.tonybuilder.aospinsight.mapper;

import com.tonybuilder.aospinsight.model.ProjectModel;
import com.tonybuilder.aospinsight.model.ProjectSummaryModel;

import java.util.Date;
import java.util.List;

public interface ProjectSummaryMapper {
    List<ProjectSummaryModel> getProjectSummaryByName(ProjectModel projectModel);
    List<ProjectSummaryModel> getProjectSummaryByDate(Date since, Date until);
    List<ProjectSummaryModel> getProjectSummary();
    boolean addProjectSummaryList(List<ProjectSummaryModel> projectSummaryModelList);
}
