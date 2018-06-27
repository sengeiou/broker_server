package com.xyauto.interact.broker.server.enums;

public enum CustomerLevelEnum {

    H((short) 1, "H"),
    A((short) 2, "A"),
    B((short) 3, "B"),
    C((short) 4, "C"),
    D((short) 5, "D");


    private short value;
    private String name;

    CustomerLevelEnum(short value, String name) {
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

    public static String getCustomerLevelName(Short code){
        for(CustomerLevelEnum Enum : CustomerLevelEnum.values()){
            if(code == Enum.getValue()){
                return Enum.getName();
            }
        }
        return "";
    }
}