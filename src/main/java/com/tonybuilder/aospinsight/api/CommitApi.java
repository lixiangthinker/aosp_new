package com.tonybuilder.aospinsight.api;

import com.tonybuilder.aospinsight.model.CommitModel;
import com.tonybuilder.aospinsight.service.CommitService;
import com.tonybuilder.aospinsight.service.ProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/commit")
public class CommitApi {
    private static final Logger logger = LoggerFactory.getLogger(CommitApi.class);
    private CommitService commitService;

    @Autowired
    public CommitApi(CommitService service) {
        this.commitService = service;
    }

    @GetMapping("/{projectId}/{month}")
    public Api getCommitByMonth(@PathVariable int projectId,
                                 @PathVariable String month) {
        logger.info("projectId = " + projectId + " month = " + month);
        List<CommitModel> result = commitService.getCommitsByMonth(projectId, month);

        if (result == null) {
            return Api.resourceNotFound("could not find data for projectName " + projectId + " month = " + month);
        }
        return Api.ok(result);
    }
}
