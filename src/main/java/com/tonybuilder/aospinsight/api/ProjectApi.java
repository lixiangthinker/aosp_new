package com.tonybuilder.aospinsight.api;

import com.tonybuilder.aospinsight.model.ProjectModel;
import com.tonybuilder.aospinsight.service.ProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/project")
public class ProjectApi {
    private static final Logger logger = LoggerFactory.getLogger(ProjectApi.class);
    private ProjectService projectService;

    @Autowired
    public ProjectApi(ProjectService service) {
        this.projectService = service;
    }

    @GetMapping("")
    public Api getProjects() {
        logger.info("getProjects");
        List<ProjectModel> result;
        result = projectService.getProjects();

        if (result == null) {
            return Api.resourceNotFound("could not find data for project lists");
        }
        return Api.ok(result);
    }
}
