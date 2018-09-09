package com.tonybuilder.aospinsight.repo;

public class NumStatInfo{
    private int changedFiles;
    private int inserted;
    private int deleted;
    private int changedLines;

    public NumStatInfo() {
        changedFiles = 0;
        inserted = 0;
        deleted = 0;
        changedLines = 0;
    }

    public int getChangedFiles() {
        return changedFiles;
    }

    public void setChangedFiles(int changedFiles) {
        this.changedFiles = changedFiles;
    }

    public int getInserted() {
        return inserted;
    }

    public void setInserted(int inserted) {
        this.inserted = inserted;
        this.changedLines += inserted;
    }

    public void addInserted(int inserted) {
        this.inserted += inserted;
        this.changedLines += inserted;
    }

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
        this.changedLines += deleted;
    }

    public void addDeleted(int deleted) {
        this.deleted += deleted;
        this.changedLines += deleted;
    }

    public int getChangedLines() {
        return changedLines;
    }

    public void setChangedLines(int changedLines) {
        this.changedLines = changedLines;
    }
}
