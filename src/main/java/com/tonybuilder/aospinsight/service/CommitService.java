package com.tonybuilder.aospinsight.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tonybuilder.aospinsight.mapper.CommitMapper;
import com.tonybuilder.aospinsight.mapper.ProjectMapper;
import com.tonybuilder.aospinsight.model.CommitModel;
import com.tonybuilder.aospinsight.model.ProjectModel;
import com.tonybuilder.aospinsight.repo.DateTimeUtils;
import com.tonybuilder.aospinsight.utils.GlobalSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.sql.Timestamp;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class CommitService {
    private static final Logger logger = LoggerFactory.getLogger(CommitService.class);

    private CommitMapper commitMapper;
    private ProjectMapper projectMapper;

    @Autowired
    public CommitService(CommitMapper commitMapper, ProjectMapper projectMapper) {
        this.commitMapper = commitMapper;
        this.projectMapper = projectMapper;
    }

    public List<CommitModel> getCommitsByMonth(int projectId, String strMonth) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM");
        YearMonth yearMonth = YearMonth.parse(strMonth, dtf);

        ProjectModel project = projectMapper.getProjectById(projectId);
        if (project == null) {
            throw new InvalidParameterException("invalid project id");
        }
        return getCommitsByMonth(project.getProjectName(),yearMonth);
    }

    private List<CommitModel> getCommitsByMonth(String projectName, YearMonth month) {
        logger.info("projectName = " + projectName + " month = " + month);
        Timestamp[] time = DateTimeUtils.getSinceAndUntilTsByMonth(month);
        String tableName = GlobalSettings.getCommitTableName(projectName);
        return commitMapper.getCommitsSinceUntil(time[0],time[1],tableName);
    }

    public PageInfo<CommitModel> getPagedCommitsByMonth(int projectId, String strMonth, int pageIndex, int pageSize) {
        logger.info("page index = " + pageIndex + " pageSize = " + pageSize);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM");
        YearMonth yearMonth = YearMonth.parse(strMonth, dtf);

        ProjectModel project = projectMapper.getProjectById(projectId);
        if (project == null) {
            throw new InvalidParameterException("invalid project id");
        }
        PageHelper.startPage(pageIndex, pageSize);
        List<CommitModel> list = getCommitsByMonth(project.getProjectName(), yearMonth);
        PageInfo<CommitModel> pageInfo = new PageInfo<>(list);
        return  pageInfo;
    }
}
