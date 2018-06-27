package com.xyauto.interact.broker.server.service.es.broker;

import io.searchbox.annotations.JestId;
import java.util.List;

public class BrokerEsEntity {
    
    /**
     * 经纪人id
     */
    @JestId
    private long brokerId;
    
    /**
     * 经纪人名称
     */
    private String name;
    
    /**
     * 经销商id
     */
    private long dealerId;
    
    /**
     * 经销商名称
     */
    private String dealerName;
    
    /**
     * 经销商类型
     */
    private int dealerType;
    
    /**
     * 经销商排期分类
     */
    private int dealerStatus;
    
    /**
     * 城市id
     */
    private int cityId;
    
    /**
     * 省份id
     */
    private int provinceId;
    
    /**
     * 关联品牌
     */
    private List<Integer> brandIds;
    
    /**
     * 关联子品牌
     */
    private List<Integer> subBrandIds;
    
    /**
     * 关联车型
     */
    private List<Integer> seriesIds;
    
    /**
     * 关联车款
     */
    private List<Integer> carIds;
    
    /**
     * 维度
     */
    private double latitude;
    
    /**
     * 精度
     */
    private double longitude;
    
    /**
     * 经纪人12等级
     */
    private int grade;
    
    /**
     * 创建时间
     */
    private long createTime;
    
    /**
     * 所在区id
     */
    private int districtId;
    
    /**
     * 手机号
     */
    private String mobile;
    

    /**
     * @return the brokerId
     */
    public long getBrokerId() {
        return brokerId;
    }

    /**
     * @param brokerId the brokerId to set
     */
    public void setBrokerId(long brokerId) {
        this.brokerId = brokerId;
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

    /**
     * @return the dealerId
     */
    public long getDealerId() {
        return dealerId;
    }

    /**
     * @param dealerId the dealerId to set
     */
    public void setDealerId(long dealerId) {
        this.dealerId = dealerId;
    }

    /**
     * @return the cityId
     */
    public int getCityId() {
        return cityId;
    }

    /**
     * @param cityId the cityId to set
     */
    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    /**
     * @return the provinceId
     */
    public int getProvinceId() {
        return provinceId;
    }

    /**
     * @param provinceId the provinceId to set
     */
    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    /**
     * @return the brandIds
     */
    public List<Integer> getBrandIds() {
        return brandIds;
    }

    /**
     * @param brandIds the brandIds to set
     */
    public void setBrandIds(List<Integer> brandIds) {
        this.brandIds = brandIds;
    }

    /**
     * @return the seriesIds
     */
    public List<Integer> getSeriesIds() {
        return seriesIds;
    }

    /**
     * @param seriesIds the seriesIds to set
     */
    public void setSeriesIds(List<Integer> seriesIds) {
        this.seriesIds = seriesIds;
    }

    /**
     * @return the carIds
     */
    public List<Integer> getCarIds() {
        return carIds;
    }

    /**
     * @param carIds the carIds to set
     */
    public void setCarIds(List<Integer> carIds) {
        this.carIds = carIds;
    }

    /**
     * @return the latitude
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * @param latitude the latitude to set
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    /**
     * @return the longitude
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * @param longitude the longitude to set
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    /**
     * @return the subBrandIds
     */
    public List<Integer> getSubBrandIds() {
        return subBrandIds;
    }

    /**
     * @param subBrandIds the subBrandIds to set
     */
    public void setSubBrandIds(List<Integer> subBrandIds) {
        this.subBrandIds = subBrandIds;
    }

    /**
     * @return the grade
     */
    public int getGrade() {
        return grade;
    }

    /**
     * @param grade the level to set
     */
    public void setGrade(int grade) {
        this.grade = grade;
    }

    /**
     * @return the createTime
     */
    public long getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime the createTime to set
     */
    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    /**
     * @return the districtId
     */
    public int getDistrictId() {
        return districtId;
    }

    /**
     * @param districtId the districtId to set
     */
    public void setDistrictId(int districtId) {
        this.districtId = districtId;
    }

    /**
     * @return the dealerName
     */
    public String getDealerName() {
        return dealerName;
    }

    /**
     * @param dealerName the dealerName to set
     */
    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }

    /**
     * @return the dealerType
     */
    public int getDealerType() {
        return dealerType;
    }

    /**
     * @param dealerType the dealerType to set
     */
    public void setDealerType(int dealerType) {
        this.dealerType = dealerType;
    }

    /**
     * @return the dealerStatus
     */
    public int getDealerStatus() {
        return dealerStatus;
    }

    /**
     * @param dealerStatus the dealerStatus to set
     */
    public void setDealerStatus(int dealerStatus) {
        this.dealerStatus = dealerStatus;
    }

    /**
     * @return the mobile
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * @param mobile the mobile to set
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    
    
}
