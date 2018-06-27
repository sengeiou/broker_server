package com.xyauto.interact.broker.server.model.po;

/**
 * 经销商客户意向等级输入模型
 * @author joe
 */
public class DealerCustomerIntention {
    
    private int type;
    private int days;

    /**
     * @return the type
     */
    public int getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(int type) {
        this.type = type;
    }

    /**
     * @return the days
     */
    public int getDays() {
        return days;
    }

    /**
     * @param days the days to set
     */
    public void setDays(int days) {
        this.days = days;
    }
}
