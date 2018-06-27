package com.xyauto.interact.broker.server.model.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 */
public class BrokerNoticeSetting implements Serializable {
    /**
     *  经纪人id
     */
    private long brokerId;

    /**
     * 允许操作id
     */
    private int promit;

    private Date createTime;

    private int count;


    public long getBrokerId() {
        return brokerId;
    }

    public void setBrokerId(long brokerId) {
        this.brokerId = brokerId;
    }

    public int getPromit() {
        return promit;
    }

    public void setPromit(int promit) {
        this.promit = promit;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}