package com.tonybuilder.aospinsight.api;

import com.tonybuilder.aospinsight.model.ProjectModel;
import com.tonybuilder.aospinsight.service.ProjectSummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/project_summary")
public class ProjectSummaryApi {
    private ProjectSummaryService projectSummaryService;

    @Autowired
    public ProjectSummaryApi(ProjectSummaryService service) {
        this.projectSummaryService = service;
    }

//    @PostMapping("")
//    public Object getProjecSummary(@RequestBody ProjectModel projectModel) {
//        return projectSummaryService.getProjectSummaryByName(projectModel);
//    }

//    @PostMapping("")
    @GetMapping("")
    public Object getProjecSummary() {
        return projectSummaryService.getProjectSummary();
    }
}
