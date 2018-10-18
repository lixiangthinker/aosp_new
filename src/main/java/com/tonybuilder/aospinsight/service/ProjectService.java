package com.tonybuilder.aospinsight.service;

import com.tonybuilder.aospinsight.mapper.ProjectMapper;
import com.tonybuilder.aospinsight.model.ProjectModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {
    private static final Logger logger = LoggerFactory.getLogger(ProjectService.class);
    private ProjectMapper projectMapper;

    @Autowired
    public ProjectService(ProjectMapper projectMapper) {
        this.projectMapper = projectMapper;
    }

    public List<ProjectModel> getProjects() {
        return projectMapper.getProjectList();
    }
}
