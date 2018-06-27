package com.xyauto.interact.broker.server.model.vo;

import java.util.List;
import org.apache.commons.lang.StringUtils;

public class FlowPagedList<T> {

    private int has_more;
    private String next_max = StringUtils.EMPTY;
    private int count;
    private List<T> list;
    private int limit;
    
    /**
     * @return the has_more
     */
    public int getHas_more() {
        return has_more;
    }

    /**
     * @param has_more the has_more to set
     */
    public void setHas_more(int has_more) {
        this.has_more = has_more;
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
     * @return the next_max
     */
    public String getNext_max() {
        return next_max;
    }

    /**
     * @param next_max the next_max to set
     */
    public void setNext_max(String next_max) {
        this.next_max = next_max;
    }
    
}
