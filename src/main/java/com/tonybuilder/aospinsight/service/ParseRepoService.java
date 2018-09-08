package com.tonybuilder.aospinsight.service;

import com.tonybuilder.aospinsight.mapper.CommitMapper;
import com.tonybuilder.aospinsight.mapper.ProjectMapper;
import com.tonybuilder.aospinsight.model.CommitModel;
import com.tonybuilder.aospinsight.model.ProjectModel;
import com.tonybuilder.aospinsight.repo.JGitParser;
import com.tonybuilder.aospinsight.repo.RepoParser;
import com.tonybuilder.aospinsight.utils.GlobalSettings;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ParseRepoService {
    private ProjectMapper projectMapper;
    private RepoParser repoParser;
    private JGitParser jGitParser;
    private CommitMapper commitMapper;
    //private CommitsParser commitsParser;

    @Value("${aosp.source.repo_path}")
    private String REPO_PATH;

    @Autowired
    public ParseRepoService(ProjectMapper projectMapper, CommitMapper commitMapper, RepoParser repoParser, JGitParser jGitParser) {
        this.projectMapper = projectMapper;
        this.repoParser = repoParser;
        this.jGitParser = jGitParser;
        this.commitMapper = commitMapper;
    }

    private boolean checkTableCommit(String projectName) {
        if (projectName == null) {
            return false;
        }
        String commitTableName = GlobalSettings.getCommitTableName(projectName);
        if (commitTableName == null) {
            return false;
        }

        if(commitMapper.existTable(commitTableName) != 1) {
            commitMapper.createNewTable(commitTableName);
        }
        return true;
    }

    private boolean checkTableProject() {
        if(projectMapper.existTable("tbl_project") != 1) {
            projectMapper.createNewTable("tbl_project");
        }
        return true;
    }

    public boolean parseProject() {
        checkTableProject();
        File root = repoParser.getSourceDir();
        if (root == null || !root.isDirectory()) {
            return false;
        }

        File manifest = new File(root, REPO_PATH);

        if (!manifest.exists() || !manifest.isFile()) {
            System.out.println("could not find manifest file");
            return false;
        }

        // parse manifest, get project path and name
        List<ProjectModel> parseResult = repoParser.parserXml(manifest);
        for (ProjectModel p : parseResult) {
            System.out.println("["+p.getProjectName()+", "+p.getProjectPath()+"]");
        }
        projectMapper.addProjectList(parseResult);
        System.out.println(parseResult.size() + " projects added to database");
        return true;
    }

    public boolean parseCommit(String projectName, Date since) {
        checkTableCommit(projectName);
        //String path = projectMapper.getProjectByName(projectName).getProjectPath();
        List<CommitModel> list = new ArrayList<>();
        try {
            list = jGitParser.parseProject(projectName, since);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (GitAPIException e) {
            e.printStackTrace();
        }
        //commitsParser.getGitLogForSingleRepo(path, "2018-07-01", true);
        System.out.println("list.size() " + list.size());

        int listSize = list.size();
        for (int begin = 0; begin < listSize; begin += 100) {
            int end = (begin+100)<listSize?begin+100:listSize;
            List<CommitModel> subList = list.subList(begin, end);
            commitMapper.addCommitList(subList, GlobalSettings.getCommitTableName(projectName));
        }
//        list.subList()
//        if (list.size() != 0) {
//            commitMapper.addCommitList(list, GlobalSettings.getCommitTableName(projectName));
//        }
        return true;
    }

    public boolean parseAllCommit() {
        //TODO
        //commitsParser.getGitLogForAllRepo("2018-07-01");
        return false;
    }
}
