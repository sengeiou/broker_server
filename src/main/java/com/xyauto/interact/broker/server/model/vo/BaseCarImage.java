package com.xyauto.interact.broker.server.model.vo;

import org.apache.commons.lang.StringUtils;

public class BaseCarImage {
    /**
     *  车年款
     */
    private  int caryear;
    /**
     * 车型图片存储地址。更改图片大小
     */
    private  String  dynamicImgUrl = StringUtils.EMPTY;
    /**
     * 车型图片
     */
    private  String imgUrl = StringUtils.EMPTY;
    /**
     * 车型id
     */
    private  int serialId;
    /**
     * 车型名称
     */
    private  String serialName = StringUtils.EMPTY;

    public int getCaryear() {
        return caryear;
    }

    public void setCaryear(int caryear) {
        this.caryear = caryear;
    }

    public String getDynamicImgUrl() {
        return dynamicImgUrl;
    }

    public void setDynamicImgUrl(String dynamicImgUrl) {
        this.dynamicImgUrl = dynamicImgUrl;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getSerialId() {
        return serialId;
    }

    public void setSerialId(int serialId) {
        this.serialId = serialId;
    }

    public String getSerialName() {
        return serialName;
    }

    public void setSerialName(String serialName) {
        this.serialName = serialName;
    }
}
