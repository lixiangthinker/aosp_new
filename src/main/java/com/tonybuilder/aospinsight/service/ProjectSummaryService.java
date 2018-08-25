package com.tonybuilder.aospinsight.service;

import com.tonybuilder.aospinsight.mapper.ProjectSummaryMapper;
import com.tonybuilder.aospinsight.model.ProjectModel;
import com.tonybuilder.aospinsight.model.ProjectSummaryModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectSummaryService {
    private ProjectSummaryMapper projectSummaryMapper;

    @Autowired
    public ProjectSummaryService(ProjectSummaryMapper mapper) {
        this.projectSummaryMapper = mapper;
    }

    public List<ProjectSummaryModel> getProjectSummaryByName(ProjectModel projectModel) {
        return projectSummaryMapper.getProjectSummaryByName(projectModel);
    }

    public List<ProjectSummaryModel> getProjectSummary() {
        return projectSummaryMapper.getProjectSummary();
    }
}
