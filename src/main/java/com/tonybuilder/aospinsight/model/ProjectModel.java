package com.tonybuilder.aospinsight.model;

public class ProjectModel {
    private Integer projectId;
    private String projectName;
    private Double projectTotalLines;
    private String projectLastSubmitData;
    private Byte projectIsExternalSrc;
    private Integer projectModuleType;
    private Byte projectIsDiscarded;
    private String projectPath;

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Double getProjectTotalLines() {
        return projectTotalLines;
    }

    public void setProjectTotalLines(Double projectTotalLines) {
        this.projectTotalLines = projectTotalLines;
    }

    public String getProjectLastSubmitData() {
        return projectLastSubmitData;
    }

    public void setProjectLastSubmitData(String projectLastSubmitData) {
        this.projectLastSubmitData = projectLastSubmitData;
    }

    public Byte getProjectIsExternalSrc() {
        return projectIsExternalSrc;
    }

    public void setProjectIsExternalSrc(Byte projectIsExternalSrc) {
        this.projectIsExternalSrc = projectIsExternalSrc;
    }

    public Integer getProjectModuleType() {
        return projectModuleType;
    }

    public void setProjectModuleType(Integer projectModuleType) {
        this.projectModuleType = projectModuleType;
    }

    public Byte getProjectIsDiscarded() {
        return projectIsDiscarded;
    }

    public void setProjectIsDiscarded(Byte projectIsDiscarded) {
        this.projectIsDiscarded = projectIsDiscarded;
    }

    public String getProjectPath() {
        return projectPath;
    }

    public void setProjectPath(String projectPath) {
        this.projectPath = projectPath;
    }
}
