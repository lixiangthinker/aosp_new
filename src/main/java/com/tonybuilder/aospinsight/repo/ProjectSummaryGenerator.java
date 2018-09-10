package com.tonybuilder.aospinsight.repo;

import com.tonybuilder.aospinsight.mapper.CommitMapper;
import com.tonybuilder.aospinsight.mapper.ProjectMapper;
import com.tonybuilder.aospinsight.mapper.ProjectSummaryMapper;
import com.tonybuilder.aospinsight.model.CommitModel;
import com.tonybuilder.aospinsight.model.ProjectModel;
import com.tonybuilder.aospinsight.model.ProjectSummaryModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

@Component
public class ProjectSummaryGenerator {
    private static final Logger logger = LoggerFactory.getLogger(ProjectSummaryGenerator.class);
    private CommitMapper commitMapper;
    private ProjectSummaryMapper projectSummaryMapper;
    private ProjectMapper projectMapper;

    @Autowired
    public ProjectSummaryGenerator(CommitMapper commitMapper, ProjectSummaryMapper projectSummaryMapper,
                                   ProjectMapper projectMapper) {

        this.commitMapper = commitMapper;
        this.projectMapper = projectMapper;
        this.projectSummaryMapper = projectSummaryMapper;
    }

    private static final String TABLE_NAME = "tbl_project_summary";
    private void checkProjectSummaryTable(){
        if (1!=projectSummaryMapper.existTable(TABLE_NAME)) {
            projectSummaryMapper.createNewTable(TABLE_NAME);
        }
    }

    // get commit of each month between since and until, calculate changed lines, insert to summary table.
    public boolean genProjectSummaryForSingleProject(@NotNull String strProject, YearMonth sinceMonth, YearMonth untilMonth) {
        checkProjectSummaryTable();
        ProjectModel project = projectMapper.getProjectByName(strProject);
        if (project == null) {
            logger.info("could not get project " + strProject);
            return false;
        }
        int projectId = project.getProjectId();
        ProjectSummaryModel projectSummary = new ProjectSummaryModel();
        projectSummary.setProjectSummaryOrigId(projectId);

        List<ProjectSummaryModel> projectSummaryModels = new ArrayList<>();

        String tableName = project.getProjectTableName();
        for (YearMonth month = sinceMonth; month.isBefore(untilMonth.plusMonths(1)); month = month.plusMonths(1)) {
            List<CommitModel> commitList = commitMapper.getCommitsSinceUntil(DateTimeUtils.getDateFromYearMonth(month),
                    DateTimeUtils.getDateFromYearMonth(month.plusMonths(1)),
                    tableName);

            if (commitList == null) {
                logger.info("could not find commits in month: " + month);
                continue;
            }

            NumStatInfo numStatInfo = new NumStatInfo();
            for (CommitModel c : commitList) {
                numStatInfo.addInserted(c.getCommitAddedLines());
                numStatInfo.addDeleted(c.getCommitDeletedLines());
            }
            logger.info("genProjectSummaryForSingleProject month = " + month
                    + " commits = " + commitList.size() + " inserted = " + numStatInfo.getInserted()
                    + " deleted = " + numStatInfo.getDeleted());

            Timestamp[] tsSince = DateTimeUtils.getSinceAndUntilTsByMonth(month);
            projectSummary.setProjectSummarySince(tsSince[0]);
            projectSummary.setProjectSummaryUntil(tsSince[1]);
            projectSummary.setProjectSummaryAdded(numStatInfo.getInserted());
            projectSummary.setProjectSummaryDeleted(numStatInfo.getDeleted());
            projectSummary.setProjectSummaryTotal(numStatInfo.getChangedLines());

            projectSummaryModels.add(projectSummary);
            projectSummary = new ProjectSummaryModel();
            projectSummary.setProjectSummaryOrigId(projectId);
        }

        projectSummaryMapper.addProjectSummaryList(projectSummaryModels);
        return true;
    }
}
