package com.tonybuilder.aospinsight.api;

import com.tonybuilder.aospinsight.model.ProjectSummaryModel;
import com.tonybuilder.aospinsight.service.ProjectSummaryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/projectSummary")
public class ProjectSummaryApi {
    private static final Logger logger = LoggerFactory.getLogger(ProjectSummaryApi.class);
    private ProjectSummaryService projectSummaryService;

    @Autowired
    public ProjectSummaryApi(ProjectSummaryService service) {
        this.projectSummaryService = service;
    }

    @GetMapping("/{projectId}")
    public Api getProjectSummary(@PathVariable int projectId) {
        return getProjectSummary(projectId, null, null);
    }

    @GetMapping("/{projectId}/{since}")
    public Api getProjectSummary(@PathVariable int projectId, @PathVariable String since) {
        return getProjectSummary(projectId, since, null);
    }

    @GetMapping("/{projectId}/{since}/{until}")
    public Api getProjectSummary(@PathVariable int projectId,
                                @PathVariable String since,
                                @PathVariable String until) {
        logger.info("projectId = " + projectId + " since = " + since + " until = " + until);
        List<ProjectSummaryModel> result;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date sinceDate;
        Date untilDate = new Date();
        try {
            sinceDate = sdf.parse("2017-01-01");
            if (since != null) {
                sinceDate = sdf.parse(since);
            }
            if (until != null) {
                untilDate = sdf.parse(until);
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return Api.paramNotValid("error when parse date format, try YYYY-MM-DD");
        }

        result = projectSummaryService.getProjectSummary(projectId, sinceDate, untilDate);

        if (result == null) {
            return Api.resourceNotFound("could not find data for project " + projectId);
        }
        return Api.ok(result);
    }
}
