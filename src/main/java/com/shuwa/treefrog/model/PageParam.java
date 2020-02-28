package com.shuwa.treefrog.model;

public class PageParam {
    public  int pageNum = 1;  //当前页面
    public   boolean isFirstPage;
    public   boolean isLastPage;
    public  long pageTotal;//总页数
    public  int firstPage = 1;//第一页
    public int lastPage;//最后一页

    public  int getFirstPage() {
        return firstPage;
    }

    public void setFirstPage(int firstPage) {
        this.firstPage = firstPage;
    }

    public  int getLastPage() {
        return lastPage;
    }

    public void setLastPage(int lastPage) {
        this.lastPage = lastPage;
    }

    public  long getPageTotal() {
        return pageTotal;
    }
    public void setPageTotal(long pageTotal) {
        this.pageTotal = pageTotal;
    }
    public int getPageNum() {
        return pageNum;
    }
    public  void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public  boolean isIsFirstPage() {
        return isFirstPage;
    }

    public  void setIsFirstPage(boolean isFirstPage) {
        this.isFirstPage = isFirstPage;
    }

    public  boolean isIsLastPage() {
        return isLastPage;
    }

    public  void setIsLastPage(boolean isLastPage) {
        this.isLastPage = isLastPage;
    }
}
