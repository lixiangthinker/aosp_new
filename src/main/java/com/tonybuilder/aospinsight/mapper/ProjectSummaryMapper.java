package com.tonybuilder.aospinsight.mapper;

import com.tonybuilder.aospinsight.model.ProjectSummaryModel;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface ProjectSummaryMapper {
    int createNewTable(@Param("tableName") String tableName);
    int dropTable(@Param("tableName") String tableName);
    int existTable(String tableName);
    List<ProjectSummaryModel> getProjectSummaryByDate(@Param("since") Date since, @Param("until") Date until);
    List<ProjectSummaryModel> getProjectSummary();
    boolean addProjectSummaryList(List<ProjectSummaryModel> projectSummaryModelList);
}
