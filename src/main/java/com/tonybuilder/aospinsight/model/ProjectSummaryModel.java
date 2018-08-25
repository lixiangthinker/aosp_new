package com.tonybuilder.aospinsight.model;

import java.sql.Timestamp;

public class ProjectSummaryModel {
    private Integer projectSummaryId;
    private Integer projectSummaryOrigId;
    private Integer projectSummaryAdded;
    private Integer projectSummaryDeleted;
    private Integer projectSummaryTotal;
    private Timestamp projectSummarySince;
    private Timestamp projectSummaryUntil;

    public Integer getProjectSummaryId() {
        return projectSummaryId;
    }

    public void setProjectSummaryId(Integer projectSummaryId) {
        this.projectSummaryId = projectSummaryId;
    }

    public Integer getProjectSummaryOrigId() {
        return projectSummaryOrigId;
    }

    public void setProjectSummaryOrigId(Integer projectSummaryOrigId) {
        this.projectSummaryOrigId = projectSummaryOrigId;
    }

    public Integer getProjectSummaryAdded() {
        return projectSummaryAdded;
    }

    public void setProjectSummaryAdded(Integer projectSummaryAdded) {
        this.projectSummaryAdded = projectSummaryAdded;
    }

    public Integer getProjectSummaryDeleted() {
        return projectSummaryDeleted;
    }

    public void setProjectSummaryDeleted(Integer projectSummaryDeleted) {
        this.projectSummaryDeleted = projectSummaryDeleted;
    }

    public Integer getProjectSummaryTotal() {
        return projectSummaryTotal;
    }

    public void setProjectSummaryTotal(Integer projectSummaryTotal) {
        this.projectSummaryTotal = projectSummaryTotal;
    }

    public Timestamp getProjectSummarySince() {
        return projectSummarySince;
    }

    public void setProjectSummarySince(Timestamp projectSummarySince) {
        this.projectSummarySince = projectSummarySince;
    }

    public Timestamp getProjectSummaryUntil() {
        return projectSummaryUntil;
    }

    public void setProjectSummaryUntil(Timestamp projectSummaryUntil) {
        this.projectSummaryUntil = projectSummaryUntil;
    }
}
