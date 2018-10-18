package com.tonybuilder.aospinsight.api;

import com.github.pagehelper.PageInfo;
import com.tonybuilder.aospinsight.model.CommitModel;
import com.tonybuilder.aospinsight.service.CommitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
                                @PathVariable String month,
                                HttpServletRequest request) {
        String pageIndex = request.getParameter("pageIndex");
        String pageSize = request.getParameter("pageSize");
        logger.info("pageIndex = " + pageIndex + " pageSize = " + pageSize);
        if (pageIndex == null) {
            logger.info("projectId = " + projectId + " month = " + month);
            List<CommitModel> result = commitService.getCommitsByMonth(projectId, month);
            if (result == null) {
                return Api.resourceNotFound("could not find data for projectName " + projectId + " month = " + month);
            }
            return Api.ok(result);
        } else {
            if (pageSize == null) {
                pageSize = "10";
            }
            int iPageIndex = Integer.parseInt(pageIndex);
            int iPageSize = Integer.parseInt(pageSize);
            logger.info("projectId = " + projectId + " month = " + month +
                    "pageIndex = " + pageIndex + " pageSize" + pageSize);
            PageInfo<CommitModel> result = commitService.getPagedCommitsByMonth(projectId, month, iPageIndex, iPageSize);
            if (result == null) {
                return Api.resourceNotFound("could not get data for projectId = " + projectId + " month = " + month +
                        "pageIndex = " + pageIndex + " pageSize" + pageSize);
            }
            return Api.ok(result.getList(), new PagingInfo(result));
        }
    }
}
