package com.xyauto.interact.broker.server.model.vo;

import java.util.List;

public class PagedList<T> {

    private int page;
    private int count;
    private int page_count;
    private List<T> list;
    private int limit;

    public PagedList(int page, int count, int page_count, List<T> list, int limit) {
        this.page = page;
        this.count = count;
        this.page_count = page_count;
        this.list = list;
        this.limit = limit;
    }
    public PagedList() {

    }
    /**
     * @return the page
     */
    public int getPage() {
        return page;
    }

    /**
     * @param page the page to set
     */
    public void setPage(int page) {
        this.page = page;
    }

    /**
     * @return the count
     */
    public int getCount() {
        return count;
    }

    /**
     * @param count the count to set
     */
    public void setCount(int count) {
        this.count = count;
    }

    /**
     * @return the list
     */
    public List<T> getList() {
        return list;
    }

    /**
     * @param list the list to set
     */
    public void setList(List<T> list) {
        this.list = list;
    }

    /**
     * @return the limit
     */
    public int getLimit() {
        return limit;
    }

    /**
     * @param limit the limit to set
     */
    public void setLimit(int limit) {
        this.limit = limit;
    }

    /**
     * @return the page_count
     */
    public int getPage_count() {
        
        if (this.count==0)
            return 1;
        return this.count/this.limit + (this.count%this.limit>0?1:0);
    }
    
}