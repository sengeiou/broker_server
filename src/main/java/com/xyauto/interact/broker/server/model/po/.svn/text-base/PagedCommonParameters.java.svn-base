package com.xyauto.interact.broker.server.model.po;

public class PagedCommonParameters<T extends IRequestParameters> {
    
    private T params;
    private String max;
    private long offset;
    private int limit;
    
    public PagedCommonParameters(T params, String max, int limit) {
        this.params = params;
        this.max = max;
        this.limit = limit;
    }
    
    public PagedCommonParameters(T params, long offset, int limit) {
        this.params = params;
        this.offset = offset;
        this.limit = limit;
    }

    /**
     * @return the params
     */
    public T getParams() {
        return params;
    }

    /**
     * @param params the params to set
     */
    public void setParams(T params) {
        this.params = params;
    }

    /**
     * @return the max
     */
    public String getMax() {
        return max;
    }

    /**
     * @param max the max to set
     */
    public void setMax(String max) {
        this.max = max;
    }

    /**
     * @return the offset
     */
    public long getOffset() {
        return offset;
    }

    /**
     * @param offset the offset to set
     */
    public void setOffset(long offset) {
        this.offset = offset;
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

    
 
}
