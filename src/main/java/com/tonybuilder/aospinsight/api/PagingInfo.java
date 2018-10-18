package com.tonybuilder.aospinsight.api;

import com.github.pagehelper.PageInfo;

/**
 * Simplified class for PageInfo
 * total: 1709,
 * pageNum: 14,
 * pageSize: 10,
 * size: 10,
 * startRow: 131,
 * endRow: 140,
 * pages: 171,
 * prePage: 13,
 * nextPage: 15
 */
public class PagingInfo {
    private long total;
    private int pageNum;
    private int pageSize;
    private int startRow;
    private int endRow;
    private int pages;
    private int prePage;
    private int nextPage;

    public <T> PagingInfo(PageInfo<T> pageInfo) {
        this.total = pageInfo.getTotal();
        this.pageNum = pageInfo.getPageNum();
        this.pageSize = pageInfo.getPageSize();
        this.startRow = pageInfo.getStartRow();
        this.endRow = pageInfo.getEndRow();
        this.pages = pageInfo.getPages();
        this.prePage = pageInfo.getPrePage();
        this.nextPage = pageInfo.getNextPage();
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getStartRow() {
        return startRow;
    }

    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    public int getEndRow() {
        return endRow;
    }

    public void setEndRow(int endRow) {
        this.endRow = endRow;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getPrePage() {
        return prePage;
    }

    public void setPrePage(int prePage) {
        this.prePage = prePage;
    }

    public int getNextPage() {
        return nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }
}
