package com.tonybuilder.aospinsight.service;

import com.tonybuilder.aospinsight.mapper.ProjectSummaryMapper;
import com.tonybuilder.aospinsight.model.ProjectSummaryModel;
import com.tonybuilder.aospinsight.repo.ProjectSummaryGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.Date;
import java.util.List;

@Service
public class ProjectSummaryService {
    private ProjectSummaryMapper projectSummaryMapper;
    private ProjectSummaryGenerator projectSummaryGenerator;

    @Autowired
    public ProjectSummaryService(ProjectSummaryMapper mapper, ProjectSummaryGenerator projectSummaryGenerator) {
        this.projectSummaryMapper = mapper;
        this.projectSummaryGenerator = projectSummaryGenerator;
    }

    public List<ProjectSummaryModel> getProjectSummaryByDate(Date since, Date until) {
        return projectSummaryMapper.getProjectSummaryByDate(since, until);
    }

    public List<ProjectSummaryModel> getProjectSummary() {
        return projectSummaryMapper.getProjectSummary();
    }

    public boolean genProjectSummary(String projectName, YearMonth since, YearMonth until){
        return projectSummaryGenerator.genProjectSummaryForSingleProject(projectName, since, until);
    }
}
