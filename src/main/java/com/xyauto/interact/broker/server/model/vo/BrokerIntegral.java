package com.xyauto.interact.broker.server.model.vo;

public class BrokerIntegral {

    /**
     * 经纪人id
     */
    private long brokerId;

    /**
     * 可用积分
     */
    private long balance;

    /**
     * 冻结积分
     */
    private long freeze;

    /**
     * 已使用积分
     */
    private long used;


    public long getBrokerId() {
        return brokerId;
    }

    public void setBrokerId(long brokerId) {
        this.brokerId = brokerId;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public long getFreeze() {
        return freeze;
    }

    public void setFreeze(long freeze) {
        this.freeze = freeze;
    }

    public long getUsed() {
        return used;
    }

    public void setUsed(long used) {
        this.used = used;
    }


}
