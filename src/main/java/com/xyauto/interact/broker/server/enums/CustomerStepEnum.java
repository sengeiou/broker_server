package com.xyauto.interact.broker.server.enums;



/**
 * 客户意向阶段枚举
 *
 * @author liucx
 * @date 2018/1/29
 */
public enum CustomerStepEnum {
    unConfirm((short) 0, "未确认"),
    stay((short) 1, "待跟进"),
    will((short) 2, "将到店"),
    testDrive((short) 4, "要试驾"),
    deal((short) 5, "成交"),
    invalid((short) -1, "无效"),
    defeat((short) -2, "战败");

    private short value;
    private String name;

    CustomerStepEnum(short value, String name) {
        this.value = value;
        this.name = name;
    }

    public short getValue() {
        return value;
    }

    public void setValue(short value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static String getCustomerStepName(Short code){
        for(CustomerStepEnum Enum : CustomerStepEnum.values()){
            if(code == Enum.getValue()){
                return Enum.getName();
            }
        }
        return "";
    }
}