package com.xyauto.interact.broker.server.model.po;

import com.xyauto.interact.broker.server.enums.BrokerSortTypeEnum;

public class BrokerSearchParameters implements IRequestParameters {
    
    private long dealerId;
    private String dealerName = "";
    private int dealerType;
    private int dealerStatus;
    private long brokerId;
    private String brokerName = "";
    private int brandId;
    private int provinceId;
    private int cityId;
    private int seriesId;
    private int carId;
    private int districtId;
    private double latitude;
    private double longitude;
    private String mobile = "";
    private String max = "";
    private long offset;
    private int limit;
    private BrokerSortTypeEnum sort;

    /**
     * @return the brandId
     */
    public int getBrandId() {
        return brandId;
    }

    /**
     * @param brandId
     */
    public void setBrandId(int brandId) {
        this.brandId = brandId;
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
     * @return the seriesId
     */
    public int getSeriesId() {
        return seriesId;
    }

    /**
     * @param seriesId the seriesId to set
     */
    public void setSeriesId(int seriesId) {
        this.seriesId = seriesId;
    }

    /**
     * @return the carId
     */
    public int getCarId() {
        return carId;
    }

    /**
     * @param carId the carId to set
     */
    public void setCarId(int carId) {
        this.carId = carId;
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
     * @return the max
     */
    public String getMax() {
        return max;
    }

    /**
     * @param max the max to set
     */
    public void setMax(String max) {
        this.max = max;
    }

    /**
     * @return the offset
     */
    public long getOffset() {
        return offset;
    }

    /**
     * @param offset the offset to set
     */
    public void setOffset(long offset) {
        this.offset = offset;
    }

    /**
     * @return the limit
     */
    public int getLimit() {
        return limit;
    }

    /**
     * @param limit the limit to set
     */
    public void setLimit(int limit) {
        this.limit = limit;
    }

    /**
     * @return the sort
     */
    public BrokerSortTypeEnum getSort() {
        return sort;
    }

    /**
     * @param sort the sort to set
     */
    public void setSort(BrokerSortTypeEnum sort) {
        this.sort = sort;
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
     * @return the brokerName
     */
    public String getBrokerName() {
        return brokerName;
    }

    /**
     * @param brokerName the brokerName to set
     */
    public void setBrokerName(String brokerName) {
        this.brokerName = brokerName;
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
