package com.xyauto.interact.broker.server.enums;

/**
 * 经纪人类型枚举
 *
 * @author liucx
 * @date 2018/1/29
 */
public enum BrokerTypeEnum {
    Employee((short) 1, "销售顾问"),
    Manager((short) 2, "销售经理"),
    MarketPersonne((short) 3, "市场专员"),
    MarketManager((short) 4, "市场经理"),
    StoreManager((short) 5, "店总");


    private short value;
    private String name;

    BrokerTypeEnum(short value, String name) {
        this.value = value;
        this.name = name;
    }

    /**
     * @return the value
     */
    public short getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(short value) {
        this.value = value;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
}
