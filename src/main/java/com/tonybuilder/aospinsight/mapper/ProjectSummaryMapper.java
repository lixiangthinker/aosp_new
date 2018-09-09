package com.tonybuilder.aospinsight.mapper;

import com.tonybuilder.aospinsight.model.ProjectSummaryModel;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface ProjectSummaryMapper {
    Integer createNewTable(@Param("tableName") String tableName);
    Integer dropTable(@Param("tableName") String tableName);
    Integer existTable(String tableName);
    List<ProjectSummaryModel> getProjectSummaryByDate(@Param("since") Date since, @Param("until") Date until);
    List<ProjectSummaryModel> getProjectSummary();
    Integer addProjectSummaryList(@Param("projectSummaryList") List<ProjectSummaryModel> projectSummaryList);
}
