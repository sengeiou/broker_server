package com.xyauto.interact.broker.server.enums;

public enum BrokerReceiptStatusEnum {
    //状态，0等待审核，1已经审核，-1驳回，-2 未上传发票
    Wait("Wait",(short)0),
    Finsh("Finsh",(short)1),
    Reject("Reject",(short)-1);



    private BrokerReceiptStatusEnum(String name, Short value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Short getValue() {
        return value;
    }

    public void setValue(Short value) {
        this.value = value;
    }

    private String name;
    private Short value;
}
