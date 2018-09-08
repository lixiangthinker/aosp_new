package com.tonybuilder.aospinsight.repo;

import com.tonybuilder.aospinsight.model.CommitModel;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.LogCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.diff.Edit;
import org.eclipse.jgit.diff.RawTextComparator;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.filter.RevFilter;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.util.io.DisabledOutputStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class JGitParser {

    @Value("${aosp.source.root}")
    private String SOURCE_BASE;
    private String projectName;
    public JGitParser()  {
    }

    public void setProjectName(String projectName){
        this.projectName = projectName;
    }

    public List<CommitModel> parseProject() throws IOException, GitAPIException {
        List<CommitModel> result = new ArrayList<>();
        if(projectName == null) {
            return result;
        }
        return parseProject(projectName);
    }

    public String getProjectDirByName(@NotNull String projectName) {
        String projectDir = null;
        String strProjectPath = projectName.substring("platform/".length()).replace("/",File.separator);
        projectDir = SOURCE_BASE+strProjectPath+File.separator+".git"+File.separator;
        return projectDir;
    }

    private CommitModel getCommitFromRevCommit(RevCommit rev) {
        CommitModel c = new CommitModel();
        c.setCommitHashId(rev.getId().getName());
        c.setCommitLog(rev.getFullMessage());

        PersonIdent authorIndent = rev.getAuthorIdent();
        c.setCommitAuthor(authorIndent.getName());
        c.setCommitAuthorMail(authorIndent.getEmailAddress());
        Timestamp timestamp = new Timestamp(authorIndent.getWhen().getTime());
        c.setCommitAlterDate(timestamp);

        PersonIdent commiterIndent = rev.getCommitterIdent();
        c.setCommitSubmitDate(new Timestamp(commiterIndent.getWhen().getTime()));

        c.setCommitBranch("master");
        c.setCommitInProject(-1);
        return c;
    }

    private class NumStatInfo{
        int changedFiles;
        int inserted;
        int deleted;

        NumStatInfo() {
            changedFiles = 0;
            inserted = 0;
            deleted = 0;
        }
    }

    private NumStatInfo getNumStatInfo(@NotNull Repository repository, @NotNull RevCommit rev) throws IOException {
        NumStatInfo numStatInfo = new NumStatInfo();

        ObjectId oldId = rev.getTree().getId();
        ObjectId newId = rev.getParentCount() > 0 ? rev.getParent(0).getTree().getId() : null;

        // init node do not have any parents
        if (oldId != null) {
            System.out.println("Diff id = " + rev.getId().getName() + " date = " + rev.getCommitterIdent().getWhen());
            DiffFormatter df = new DiffFormatter(DisabledOutputStream.INSTANCE);
            df.setRepository(repository);
            df.setDiffComparator(RawTextComparator.DEFAULT);
            df.setDetectRenames(true);
            List<DiffEntry> diffs = df.scan(oldId, newId);

            numStatInfo.changedFiles = diffs.size();
            for (DiffEntry diff : diffs) {
                for (Edit edit : df.toFileHeader(diff).toEditList()) {
                    numStatInfo.deleted += edit.getEndA() - edit.getBeginA();
                    numStatInfo.inserted += edit.getEndB() - edit.getBeginB();
                }
            }
        }
        return numStatInfo;
    }

    public List<CommitModel> parseProject(@NotNull String projectName) throws IOException, GitAPIException{
        return parseProject(projectName, null, null);
    }

    public List<CommitModel> parseProject(@NotNull String projectName, Date since)
            throws IOException, GitAPIException{
        return parseProject(projectName, since, null);
    }

    public List<CommitModel> parseProject(@NotNull String projectName, Date since, Date until)
            throws IOException, GitAPIException{
        List<CommitModel> result = new ArrayList<>();
        CommitModel c;

        String projectDir = getProjectDirByName(projectName);
        File dir = new File(projectDir);
        if (!dir.exists() || !dir.isDirectory()) {
            System.out.println("project dir is not exist: " + projectName);
            return result;
        }

        try (Repository repository = openJGitRepository(getProjectDirByName(projectName))) {
            try (Git git = new Git(repository)) {
                LogCommand cmd = git.log().setRevFilter(RevFilter.NO_MERGES);
                Iterable<RevCommit> logs = cmd.call();

                int count = 0;
                for (RevCommit rev : logs) {
                    Date revDate = rev.getCommitterIdent().getWhen();
                    if (until != null) {
                        if (revDate.after(until)) {
                            continue;
                        }
                    }
                    if (since != null) {
                        if (revDate.before(since)) {
                            break;
                        }
                    }
                    System.out.println("count " + count);
                    c = getCommitFromRevCommit(rev);

                    NumStatInfo numStatInfo = getNumStatInfo(repository, rev);
                    c.setCommitAddedLines(numStatInfo.inserted);
                    c.setCommitDeletedLines(numStatInfo.deleted);
                    c.setCommitChangedLines(numStatInfo.inserted + numStatInfo.deleted);
                    result.add(c);
                    count++;
                }
                System.out.println("Had " + count + " commits overall on current branch");
            }
        }

        return result;
    }

    public Repository openJGitRepository(String projectPath) {
        Repository repo = null;
        FileRepositoryBuilder builder = new FileRepositoryBuilder();
        File dir = new File(projectPath);
        try {
            repo = builder.setGitDir(dir)
                    .readEnvironment() // scan environment GIT_* variables
                    .findGitDir() // scan up the file system tree
                    .build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return repo;
    }

    public static Repository openJGitRepository() throws IOException {
        FileRepositoryBuilder builder = new FileRepositoryBuilder();
        File dir = new File("D:\\source\\aosp\\frameworks\\base\\.git\\");
        return builder.setGitDir(dir)
                .readEnvironment() // scan environment GIT_* variables
                .findGitDir() // scan up the file system tree
                .build();
    }

    public static void main(String[] args) throws IOException, GitAPIException {
        try (Repository repository = JGitParser.openJGitRepository()) {
            try (Git git = new Git(repository)) {
                LogCommand cmd = git.log().setRevFilter(RevFilter.NO_MERGES);
                Iterable<RevCommit> logs = cmd.call();

                int count = 0;
                ObjectId oldId = null;
                ObjectId newId = null;
                for (RevCommit rev : logs) {
                    if (count < 10) {

                        LocalDateTime localDateTime =
                                LocalDateTime.ofInstant(Instant.ofEpochMilli(rev.getCommitTime()), ZoneId.systemDefault());
                        newId = rev.getTree().getId();
                        oldId = rev.getParent(0).getTree().getId();

                        PersonIdent authorIndent = rev.getAuthorIdent();
                        //PersonIdent commiterIndent = rev.getCommitterIdent();
                        System.out.println("author date" + authorIndent.getWhen());
                        System.out.println("author time zone" + authorIndent.getTimeZone());
                        System.out.println("author time zone offset" + authorIndent.getTimeZoneOffset());
                        //c.setCommitAlterDate(authorIndent.getWhen());

                        System.out.println("AuthorIdent: " + rev.getAuthorIdent());
                        System.out.println("CommitterIdent: " + rev.getCommitterIdent());
                        System.out.println("CommitTime: " + rev.getCommitTime());
                        System.out.println("CommitTime changed:" + localDateTime);
                        System.out.println("FullMessage: " + rev.getFullMessage());
                        System.out.println("ShortMessage: " + rev.getShortMessage());
                        System.out.println("Id: " + rev.getId().getName());
                        System.out.println("Tree id " + rev.getTree().getId());
                        System.out.println("oldId = " + oldId);
                        System.out.println("newId = " + newId);
                        //System.out.println();

                        if(oldId != null){
                            System.out.println("Printing diff between tree: " + oldId + " and " + newId);

                            DiffFormatter df = new DiffFormatter(DisabledOutputStream.INSTANCE);
                            df.setRepository(repository);
                            df.setDiffComparator(RawTextComparator.DEFAULT);
                            df.setDetectRenames(true);
                            List<DiffEntry> diffs = df.scan(oldId, newId);
                            int filesChanged = diffs.size();

                            int linesAdded = 0;
                            int linesDeleted = 0;

                            for (DiffEntry diff : diffs) {
                                for (Edit edit : df.toFileHeader(diff).toEditList()) {
                                    linesDeleted += edit.getEndA() - edit.getBeginA();
                                    linesAdded += edit.getEndB() - edit.getBeginB();
                                }
                            }

                            System.out.println("files changed = " + filesChanged + " linesDeleted = " + linesDeleted + " linesAdded = " + linesAdded);
                            System.out.println("===============================================");
                        }
                    }
                    count++;
                }
                System.out.println("Had " + count + " commits overall on current branch");
            }
        }
    }
}
