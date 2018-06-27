package com.xyauto.interact.broker.server.enums;

/**
 * 经纪人处理线索类型
 */
public enum BrokerClueHandleTypeEnum {
    /**
     * 其他
     */
    Other(0),
    /**
     * 电话联系
     */
    PhoneContact(1),
    /**
     * 短信联系
     */
    SmsContact(2),
    /**
     * 转化为客户
     */
    ConvertToCustomer(3),
    /**
     * 导出
     */
    Expert(4),
    /**
     * 查看线索详情
     */
    ClueDetail(5);

    private int value;

    BrokerClueHandleTypeEnum(int value) {
        this.value = value;
    }

    public static BrokerClueHandleTypeEnum valueOf(int value) {
        for (BrokerClueHandleTypeEnum item : BrokerClueHandleTypeEnum.values()) {
            if (item.value == value) {
                return item;
            }
        }
        return null;
    }

    /**
     * @return the value
     */
    public int getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(int value) {
        this.value = value;
    }

}
