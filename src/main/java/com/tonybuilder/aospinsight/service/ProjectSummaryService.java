package com.tonybuilder.aospinsight.service;

import com.tonybuilder.aospinsight.mapper.ProjectMapper;
import com.tonybuilder.aospinsight.mapper.ProjectSummaryMapper;
import com.tonybuilder.aospinsight.model.ProjectModel;
import com.tonybuilder.aospinsight.model.ProjectSummaryModel;
import com.tonybuilder.aospinsight.repo.ProjectSummaryGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.Date;
import java.util.List;

@Service
public class ProjectSummaryService {
    private ProjectMapper projectMapper;
    private ProjectSummaryMapper projectSummaryMapper;
    private ProjectSummaryGenerator projectSummaryGenerator;

    @Autowired
    public ProjectSummaryService(ProjectSummaryMapper mapper,
                                 ProjectSummaryGenerator projectSummaryGenerator,
                                 ProjectMapper projectMapper) {
        this.projectSummaryMapper = mapper;
        this.projectSummaryGenerator = projectSummaryGenerator;
        this.projectMapper = projectMapper;
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

    public boolean genProjectSummary(YearMonth since, YearMonth until) {
        List<ProjectModel> projectList = projectMapper.getProjectList();
        if (projectList == null) {
            System.out.println("could not get project list");
            return false;
        }
        boolean result = false;

        for (ProjectModel project : projectList) {
            String projectName = project.getProjectName();
            result = projectSummaryGenerator.genProjectSummaryForSingleProject(projectName, since, until);
            if (!result) {
                System.out.println("could not generate project summary for " + projectName);
                return false;
            } else {
                System.out.println("project summary gerated, projectName = " + projectName
                        + " since = " + since + " until = " + until);
            }
        }
        return true;
    }
}
