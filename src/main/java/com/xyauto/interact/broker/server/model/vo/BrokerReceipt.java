package com.xyauto.interact.broker.server.model.vo;

import java.util.Date;
import org.apache.commons.lang3.StringUtils;

public class BrokerReceipt {
    /**
     * 经纪人上传购车发票
     */
    private long brokerReceiptId;

    /**
     * 经纪人id
     */
    private long brokerId;

    /**
     * 所属经纪人客户id
     */
    private long brokerCustomerId;

    /**
     * 经纪人客户购车意愿id
     */
    private long brokerCustomerCarsWillId;

    /**
     * 发票图片，逗号分隔
     */
    private String images = StringUtils.EMPTY;

    /**
     * 状态，0等待审核，1已经审核，-1驳回，-2 未上传发票
     */
    private short status;

    public String getStatusDesc() {
        
        switch (this.status) {
            case 0:
                this.statusDesc = "发票已上传，等待审核";break;
            case 1:
                this.statusDesc = "审核通过，已获得100积分";break;
            case -1:
                this.statusDesc = this.getReason();break;
        }    
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    /**
     * 状态，0等待审核，1已经审核，-1驳回，-2 未上传发票
     */
    private String statusDesc = StringUtils.EMPTY;
    
    /**
     * 详细描述
     */
    private String statusFullDesc = StringUtils.EMPTY;

    /**
     * 审核描述
     */
    private String reason = StringUtils.EMPTY;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 审核时间
     */
    private Date verifyTime;

    public long getBrokerReceiptId() {
        return brokerReceiptId;
    }

    public void setBrokerReceiptId(long brokerReceiptId) {
        this.brokerReceiptId = brokerReceiptId;
    }

    public long getBrokerId() {
        return brokerId;
    }

    public void setBrokerId(long brokerId) {
        this.brokerId = brokerId;
    }

    public long getBrokerCustomerId() {
        return brokerCustomerId;
    }

    public void setBrokerCustomerId(long brokerCustomerId) {
        this.brokerCustomerId = brokerCustomerId;
    }

    public long getBrokerCustomerCarsWillId() {
        return brokerCustomerCarsWillId;
    }

    public void setBrokerCustomerCarsWillId(long brokerCustomerCarsWillId) {
        this.brokerCustomerCarsWillId = brokerCustomerCarsWillId;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getVerifyTime() {
        return verifyTime;
    }

    public void setVerifyTime(Date verifyTime) {
        this.verifyTime = verifyTime;
    }
    
    public BrokerCustomer getBrokerCustomer() {
        return brokerCustomer;
    }

    public void setBrokerCustomer(BrokerCustomer brokerCustomer) {
        this.brokerCustomer = brokerCustomer;
    }

    public BrokerCustomerCarsWill getBrokerCustomerCarsWill() {
        return brokerCustomerCarsWill;
    }

    public void setBrokerCustomerCarsWill(BrokerCustomerCarsWill brokerCustomerCarsWill) {
        this.brokerCustomerCarsWill = brokerCustomerCarsWill;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Series getSeries() {
        return series;
    }

    public void setSeries(Series series) {
        this.series = series;
    }

    /**
     * 客户信息
     */
    BrokerCustomer brokerCustomer;

    /**
     * 客户意向车型
     */
    BrokerCustomerCarsWill brokerCustomerCarsWill;

    /**
     * 车型相关信息
     */
    Car car;

    /**
     * 车款相关信息
     */
    Series series;

    /**
     * 经销商信息
     */
    Dealer dealer;

    /**
     * @return the statusFullDesc
     */
    public String getStatusFullDesc() {
        return statusFullDesc;
    }

    /**
     * @param statusFullDesc the statusFullDesc to set
     */
    public void setStatusFullDesc(String statusFullDesc) {
        this.statusFullDesc = statusFullDesc;
    }

}
