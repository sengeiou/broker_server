package com.xyauto.interact.broker.server.model.vo;

import java.util.Date;

public class Car {

    /**
     * 车款id
     */
    private int carId;

    /**
     * 品牌id
     */
    private int brandId;


    public int getSubBrandId() {
        return subBrandId;
    }

    public void setSubBrandId(int subBrandId) {
        this.subBrandId = subBrandId;
    }

    /**
     * 子品牌id
     */
    private int subBrandId;

    /**
     * 车系id
     */
    private int seriesId;

    /**
     * 车款名称
     */
    private String name;

    /**
     * 年款
     */
    private int year;

    /**
     * 状态，0无效，1有效
     */
    private boolean status;

    /**
     * 创建时间
     */
    private Date createTime;

    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }


    public Integer getSeriesId() {
        return seriesId;
    }

    public void setSeriesId(Integer seriesId) {
        this.seriesId = seriesId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
}
