package com.xyauto.interact.broker.server.model.vo;

import org.springframework.stereotype.Component;

import java.util.Date;
import org.apache.commons.lang.StringUtils;

@Component
public class BrokerIntegralLog {
    public Long getBrokerIntegralLogId() {
        return brokerIntegralLogId;
    }

    public void setBrokerIntegralLogId(long brokerIntegralLogId) {
        this.brokerIntegralLogId = brokerIntegralLogId;
    }

    public long getBrokerId() {
        return brokerId;
    }

    public void setBrokerId(long brokerId) {
        this.brokerId = brokerId;
    }

    public long getIntegral() {
        return integral;
    }

    public void setIntegral(long integral) {
        this.integral = integral;
    }

    public Short getType() {
        return type;
    }

    public void setType(short type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public long getDealerId() {
        return dealerId;
    }

    public void setDealerId(long dealerId) {
        this.dealerId = dealerId;
    }

    /**
     * 经纪人积分流水表
     */
    private long brokerIntegralLogId;

    /**
     * 经纪人id
     */
    private long brokerId;

    /**
     * 积分
     */
    private long integral;



    /**
     * 变更类型，1收入，2支出
     */
    private short type;

    /**
     * 变更说明
     */
    private String desc = StringUtils.EMPTY;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 经纪人id
     */
    private long dealerId;
}
