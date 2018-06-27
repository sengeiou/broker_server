package com.xyauto.interact.broker.server.model.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.google.common.collect.Lists;
import java.util.Date;
import java.util.List;

public class Dealer {

    /**
     * 经销商id
     */
    private long dealerId;
    
    /**
     * 经销商短名称
     */
    private String shortName;
    
    /**
     * 经销商名称
     */
    private String name;
    
    /**
     * 经销商类型
     */
    private short type;
    
    /**
     * 经销商地址
     */
    private String address;
    
    /**
     * 经销商经度
     */
    private double longitude;
    
    /**
     * 经销商纬度
     */
    private double latitude;
    
    /**
     * 主机厂id
     */
    private int manufacturerId;
    
    /**
     * 集团id
     */
    private int corporationId;
    
    /**
     * 省份id
     */
    private int provinceId;
    
    /**
     * 城市id
     */
    private int cityId;
    
    /**
     * 区id
     */
    private int districtId;
    
    /**
     * 主营品牌集合
     */
    private String brandIds;
    
    /**
     * 状态
     */
    private short status;
    
    /**
     * 创建时间
     */
    private Date createTime;

    
    /**
     * 主营品牌
     */
    private List<Brand> brands = Lists.newArrayList();
    
    /**
     * 在售车型
     */
    private List<Series> series = Lists.newArrayList();
    
    /**
     * 经销商所在城市
     */
    private City city = null;
    
    /**
     * 经销商所在省份
     */
    private Province province = null;
    
    /**
     * 经销商所在区
     */
    private City district = null;

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
     * @return the shortName
     */
    public String getShortName() {
        return shortName;
    }

    /**
     * @param shortName the shortName to set
     */
    public void setShortName(String shortName) {
        this.shortName = shortName;
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
     * @return the type
     */
    public short getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(short type) {
        this.type = type;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
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
     * @return the manufacturerId
     */
    public int getManufacturerId() {
        return manufacturerId;
    }

    /**
     * @param manufacturerId the manufacturerId to set
     */
    public void setManufacturerId(int manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    /**
     * @return the corporationId
     */
    public int getCorporationId() {
        return corporationId;
    }

    /**
     * @param corporationId the corporationId to set
     */
    public void setCorporationId(int corporationId) {
        this.corporationId = corporationId;
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
     * @return the status
     */
    public short getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(short status) {
        this.status = status;
    }

    /**
     * @return the createTime
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime the createTime to set
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return the brands
     */
    public List<Brand> getBrands() {
        return brands;
    }

    /**
     * @param brands the brands to set
     */
    public void setBrands(List<Brand> brands) {
        this.brands = brands;
    }

    /**
     * @return the series
     */
    public List<Series> getSeries() {
        return series;
    }

    /**
     * @param series the series to set
     */
    public void setSeries(List<Series> series) {
        this.series = series;
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
     * @return the brandIds
     */
    public String getBrandIds() {
        return brandIds;
    }

    /**
     * @param brandIds the brandIds to set
     */
    public void setBrandIds(String brandIds) {
        this.brandIds = brandIds;
    }

    /**
     * @return the city
     */
    public City getCity() {
        return city;
    }

    /**
     * @param city the city to set
     */
    public void setCity(City city) {
        this.city = city;
    }

    /**
     * @return the province
     */
    public Province getProvince() {
        return province;
    }

    /**
     * @param province the province to set
     */
    public void setProvince(Province province) {
        this.province = province;
    }

    /**
     * @return the district
     */
    public City getDistrict() {
        return district;
    }

    /**
     * @param district the district to set
     */
    public void setDistrict(City district) {
        this.district = district;
    }

}
