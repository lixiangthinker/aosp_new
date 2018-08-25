package com.tonybuilder.aospinsight.model;

import java.sql.Timestamp;

public class CommitModel {
    private Integer commitId;
    private Integer commitInProject;
    private String commitAuthor;
    private String commitAuthorMail;
    private Timestamp commitAlterDate;
    private String commitHashId;
    private Timestamp commitSubmitDate;
    private Integer commitAddedLines;
    private Integer commitDeletedLines;
    private String commitBranch;
    private Integer commitChangedLines;
    private String commitLog;

    public Integer getCommitId() {
        return commitId;
    }

    public void setCommitId(Integer commitId) {
        this.commitId = commitId;
    }

    public Integer getCommitInProject() {
        return commitInProject;
    }

    public void setCommitInProject(Integer commitInProject) {
        this.commitInProject = commitInProject;
    }

    public String getCommitAuthor() {
        return commitAuthor;
    }

    public void setCommitAuthor(String commitAuthor) {
        this.commitAuthor = commitAuthor;
    }

    public String getCommitAuthorMail() {
        return commitAuthorMail;
    }

    public void setCommitAuthorMail(String commitAuthorMail) {
        this.commitAuthorMail = commitAuthorMail;
    }

    public Timestamp getCommitAlterDate() {
        return commitAlterDate;
    }

    public void setCommitAlterDate(Timestamp commitAlterDate) {
        this.commitAlterDate = commitAlterDate;
    }

    public String getCommitHashId() {
        return commitHashId;
    }

    public void setCommitHashId(String commitHashId) {
        this.commitHashId = commitHashId;
    }

    public Timestamp getCommitSubmitDate() {
        return commitSubmitDate;
    }

    public void setCommitSubmitDate(Timestamp commitSubmitDate) {
        this.commitSubmitDate = commitSubmitDate;
    }

    public Integer getCommitAddedLines() {
        return commitAddedLines;
    }

    public void setCommitAddedLines(Integer commitAddedLines) {
        this.commitAddedLines = commitAddedLines;
    }

    public Integer getCommitDeletedLines() {
        return commitDeletedLines;
    }

    public void setCommitDeletedLines(Integer commitDeletedLines) {
        this.commitDeletedLines = commitDeletedLines;
    }

    public String getCommitBranch() {
        return commitBranch;
    }

    public void setCommitBranch(String commitBranch) {
        this.commitBranch = commitBranch;
    }

    public Integer getCommitChangedLines() {
        return commitChangedLines;
    }

    public void setCommitChangedLines(Integer commitChangedLines) {
        this.commitChangedLines = commitChangedLines;
    }

    public String getCommitLog() {
        return commitLog;
    }

    public void setCommitLog(String commitLog) {
        this.commitLog = commitLog;
    }
}
