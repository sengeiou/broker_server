package com.xyauto.interact.broker.server.model.vo;

import java.util.List;

public class DealerClueDistributeSetting {
    private long dealerId;
    private int dealerDistribute;
    private int brokerDistribute;
    private String brokerIds;
    private List<Object> brokers;

    /**
     * @return the dealerDistribute
     */
    public int getDealerDistribute() {
        return dealerDistribute;
    }

    /**
     * @param dealerDistribute the dealerDistribute to set
     */
    public void setDealerDistribute(int dealerDistribute) {
        this.dealerDistribute = dealerDistribute;
    }

    /**
     * @return the brokerDistribute
     */
    public int getBrokerDistribute() {
        return brokerDistribute;
    }

    /**
     * @param brokerDistribute the brokerDistribute to set
     */
    public void setBrokerDistribute(int brokerDistribute) {
        this.brokerDistribute = brokerDistribute;
    }

    /**
     * @return the brokerIds
     */
    public String getBrokerIds() {
        return brokerIds;
    }

    /**
     * @param brokerIds the brokerIds to set
     */
    public void setBrokerIds(String brokerIds) {
        this.brokerIds = brokerIds;
    }

    /**
     * @return the brokers
     */
    public List<Object> getBrokers() {
        return brokers;
    }

    /**
     * @param brokers the brokers to set
     */
    public void setBrokers(List<Object> brokers) {
        this.brokers = brokers;
    }

    /**
     * @return the dealerId
     */
    public long getDealerId() {
        return dealerId;
    }

    /**
     * @param dealerId the dealerId to set
     */
    public void setDealerId(long dealerId) {
        this.dealerId = dealerId;
    }
}
