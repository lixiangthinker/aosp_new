package com.tonybuilder.aospinsight.repo;

import com.tonybuilder.aospinsight.mapper.CommitMapper;
import com.tonybuilder.aospinsight.mapper.ProjectMapper;
import com.tonybuilder.aospinsight.mapper.ProjectSummaryMapper;
import com.tonybuilder.aospinsight.model.CommitModel;
import com.tonybuilder.aospinsight.model.ProjectModel;
import com.tonybuilder.aospinsight.model.ProjectSummaryModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

@Component
public class ProjectSummaryGenerator {
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

//    public static void main(String[] args) {
//        ProjectSummaryUtil util = new ProjectSummaryUtil();
//        ProjectEntity project = util.projectEntityDao.getProjectByPath("frameworks/base");
//        if (project == null) {
//            System.out.println("could not get project.");
//            return;
//        }
//
//        YearMonth sinceDate = YearMonth.of(2017, 1);
//        YearMonth untilDate = YearMonth.now();
//        util.genProjectSummaryForSingleProject(project, sinceDate, untilDate);
//        System.exit(0);
//    }

    private boolean genProjectSummaryForSingleProject(ProjectModel project, YearMonth sinceDate, YearMonth untilDate) {
        // get commit of each month between since and until, calculate changed lines, insert to summary table.
        boolean result = false;
        int projectId = project.getProjectId();
        ProjectSummaryModel projectSummary = new ProjectSummaryModel();
        projectSummary.setProjectSummaryOrigId(projectId);

        List<ProjectSummaryModel> projectSummaryModels = new ArrayList<>();

        String tableName = project.getProjectTableName();
        for (YearMonth month = sinceDate; month.isBefore(untilDate.plusMonths(1)); month = month.plusMonths(1)) {
            List<CommitModel> commitList = commitMapper.getCommitsSince(DateTimeUtils.getDateFromYearMonth(month), tableName);

            if (commitList == null) {
                System.out.println("could not find commits in month: " + month);
                continue;
            }

            NumStatInfo numStatInfo = new NumStatInfo();

            for (CommitModel c : commitList) {
                numStatInfo.addInserted(c.getCommitAddedLines());
                numStatInfo.addDeleted(c.getCommitDeletedLines());
            }
            System.out.println("genProjectSummaryForSingleProject month = " + month
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
        return result;
    }
}
