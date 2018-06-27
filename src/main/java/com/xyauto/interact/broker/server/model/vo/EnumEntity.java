package com.xyauto.interact.broker.server.model.vo;

public class EnumEntity {

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    /**
     * 主键id
     */
    private Integer id;

    /**
     * 枚举类型，1经纪人业务范围，2经纪人类型，3经纪人职业标签
     */
    private Short type;

    /**
     * 父类id
     */
    private Integer parentId;

    /**
     * 类型值
     */
    private String value;


}
