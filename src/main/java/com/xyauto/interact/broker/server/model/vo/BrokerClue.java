package com.xyauto.interact.broker.server.model.vo;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang.StringUtils;

/**
 * @author
 */
public class BrokerClue {

    /**
     * @return the originCategory
     */
    public int getOriginCategory() {
        return this.category;
    }

    /**
     * @param originCategory the originCategory to set
     */
    public void setOriginCategory(int originCategory) {
        this.originCategory = originCategory;
    }

    /**
     * 经纪人线索id
     */
    private long brokerClueId;

    /**
     * 线索id
     */
    private long clueId;

    /**
     * 经纪人id
     */
    private long brokerId;

    /**
     * 线索类型，1话单线索，2网单线索，3店铺线索
     */
    private short type;

    /**
     * 客户名称
     */
    private String username = StringUtils.EMPTY;

    /**
     * 客户电话
     */
    private String mobile = StringUtils.EMPTY;

    /**
     * 品牌id
     */
    private int brandId;

    /**
     * 子品牌id
     */
    private int subBrandId;

    /**
     * 车系id
     */
    private int seriesId;

    /**
     * 车款id
     */
    private int carId;

    /**
     * 线索来源ip
     */
    private String ip = StringUtils.EMPTY;

    /**
     * 线索来源用户cookie_id标识
     */
    private String cookieId = StringUtils.EMPTY;

    /**
     * 线索来源用户设备id
     */
    private String deviceId = StringUtils.EMPTY;

    /**
     * 线索备注
     */
    private String mem = StringUtils.EMPTY;

    /**
     * 线索类型，1新车，2二手车
     */
    private int category;
    
    /**
     * 原始线索类型
     */
    private int originCategory;

    /**
     * 分配类型，1店铺分配，2个人分配
     */
    private int distributeType;

    /**
     * 渠道Id
     */
    private int channelId;

    /**
     * 购车预算
     */
    private String budget = StringUtils.EMPTY;

    /**
     * 创建时间
     */
    private Date createTime;

    /*
        是否删除  0未删除 1 删除（线索流失）
     */
    private Short isDeleted;

    /*
    删除时间（线索流失时间）
     */
    private Date deleteTime;

    /**
     * 城市id
     */
    private int cityId;

    /**
     * 省份id
     */
    private int provinceId;

    /**
     * 经销商id
     */
    private long dealerId;

    /**
     * 排序值
     */
    private String sort = StringUtils.EMPTY;

    /**
     * 品牌
     */
    private Brand brand;

    /**
     * 子品牌
     */
    private SubBrand subBrand;

    /**
     * 车系
     */
    private Series series;

    /**
     * 车款
     */
    private Car car;

    /**
     * 经纪人
     */
    private Broker broker;

    /**
     * 省份
     */
    private Province province;

    /**
     * 城市
     */
    private City city;
    
    /**
     * 手机定位省份
     */
    private Province mobileProvince;
    
    /**
     * 手机定位城市
     */
    private City mobileCity;

    /**
     * 分配客户id
     */
    private long brokerCustomerId;

    /**
     * 分配客户
     */
    private BrokerCustomer customer;

    /**
     * 是否已处理
     */
    private short isHandled;

    /**
     * 线索来源
     */
    private int source;

    private long poolClueId;

    private long clueRefDealerId;

    /**
     * 置换内容
     */
    private String exchange = StringUtils.EMPTY;

    /**
     * 话单信息
     */
    private String callInfo = StringUtils.EMPTY;

    /**
     * 原始客户id，来自于线索分析
     */
    private long customerId;

    /**
     * 综合购车指数
     */
    private double carPurchasingIndex;
    /**
     * 到店意向度
     */
    private double satisfaction;
    /**
     * 线索新鲜度
     */
    private double freshness;
    /**
     * 购车紧迫性
     */
    private double urgency;

    /**
     * 价格敏感度
     */
    private double sensitive;

    /**
     * 车型确定性
     */
    private double confirm;

    /**
     * 关注车型
     */
    private List<JSONObject> followSeries;

    /**
     * 分配时间
     */
    private Date distributeTime;

    /**
     * 是否已分配
     */
    private int isDistributed;

    /**
     * 手机城市id
     */
    private int mobileCityId;

    /**
     * 手机省份id
     */
    private int mobileProvinceId;

    /**
     * 定位省份id
     */
    private int locationProvinceId;

    /**
     * 城市省份id
     */
    private int locationCityId;

    /**
     * 购车标签
     */
    private String tags = StringUtils.EMPTY;

    /**
     * @return the brokerClueId
     */
    public long getBrokerClueId() {
        return brokerClueId;
    }

    /**
     * @param brokerClueId the brokerClueId to set
     */
    public void setBrokerClueId(long brokerClueId) {
        this.brokerClueId = brokerClueId;
    }

    /**
     * @return the clueId
     */
    public long getClueId() {
        return clueId;
    }

    /**
     * @param clueId the clueId to set
     */
    public void setClueId(long clueId) {
        this.clueId = clueId;
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
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
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

    /**
     * @return the brandId
     */
    public int getBrandId() {
        return brandId;
    }

    /**
     * @param brandId the brandId to set
     */
    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    /**
     * @return the subBrandId
     */
    public int getSubBrandId() {
        return subBrandId;
    }

    /**
     * @param subBrandId the subBrandId to set
     */
    public void setSubBrandId(int subBrandId) {
        this.subBrandId = subBrandId;
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
     * @return the ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * @param ip the ip to set
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * @return the cookieId
     */
    public String getCookieId() {
        return cookieId;
    }

    /**
     * @param cookieId the cookieId to set
     */
    public void setCookieId(String cookieId) {
        this.cookieId = cookieId;
    }

    /**
     * @return the deviceId
     */
    public String getDeviceId() {
        return deviceId;
    }

    /**
     * @param deviceId the deviceId to set
     */
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    /**
     * @return the mem
     */
    public String getMem() {
        return mem;
    }

    /**
     * @param mem the mem to set
     */
    public void setMem(String mem) {
        this.mem = mem;
    }

    /**
     * @return the category
     */
    public int getCategory() {
        if (Lists.newArrayList(0,1,2,6).contains(category)) {
            return 1;
        }
        if (Lists.newArrayList(3,4,5).contains(category)) {
            return 2;
        }
        return 1;
    }

    /**
     * @param category the category to set
     */
    public void setCategory(int category) {
        this.category = category;
    }

    /**
     * @return the distributeType
     */
    public int isDistributeType() {
        return distributeType;
    }

    /**
     * @param distributeType the distributeType to set
     */
    public void setDistributeType(int distributeType) {
        this.distributeType = distributeType;
    }

    /**
     * @return the channelId
     */
    public int getChannelId() {
        return channelId;
    }

    /**
     * @param channelId the channelId to set
     */
    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    /**
     * @return the budget
     */
    public String getBudget() {
        return budget;
    }

    /**
     * @param budget the budget to set
     */
    public void setBudget(String budget) {
        this.budget = budget;
    }

    /**
     * @return the createTime
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @return the cityId
     */
    public int getCityId() {
        if (this.cityId > 0) {
            return this.cityId;
        }
        return this.mobileCityId;
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
        if (this.provinceId > 0) {
            return this.provinceId;
        }
        return this.mobileProvinceId;
    }

    /**
     * @param provinceId the provinceId to set
     */
    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
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
     * @return the sort
     */
    public String getSort() {
        return sort;
    }

    /**
     * @param sort the sort to set
     */
    public void setSort(String sort) {
        this.sort = sort;
    }

    /**
     * @return the brand
     */
    public Brand getBrand() {
        return brand;
    }

    /**
     * @param brand the brand to set
     */
    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    /**
     * @return the subbrand
     */
    public SubBrand getSubBrand() {
        return subBrand;
    }

    /**
     * @param subbrand the subbrand to set
     */
    public void setSubBrand(SubBrand subbrand) {
        this.subBrand = subbrand;
    }

    /**
     * @return the series
     */
    public Series getSeries() {
        return series;
    }

    /**
     * @param series the series to set
     */
    public void setSeries(Series series) {
        this.series = series;
    }

    /**
     * @return the car
     */
    public Car getCar() {
        return car;
    }

    /**
     * @param car the car to set
     */
    public void setCar(Car car) {
        this.car = car;
    }

    /**
     * @return the broker
     */
    public Broker getBroker() {
        return broker;
    }

    /**
     * @param broker the broker to set
     */
    public void setBroker(Broker broker) {
        this.broker = broker;
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

    public double getCarPurchasingIndex() {
        return carPurchasingIndex;
    }

    public void setCarPurchasingIndex(double carPurchasingIndex) {
        this.carPurchasingIndex = carPurchasingIndex;
    }

    public double getSatisfaction() {
        return satisfaction;
    }

    public void setSatisfaction(double satisfaction) {
        this.satisfaction = satisfaction;
    }

    public double getFreshness() {
        return freshness;
    }

    public void setFreshness(double freshness) {
        this.freshness = freshness;
    }

    public double getUrgency() {
        return urgency;
    }

    public void setUrgency(double urgency) {
        this.urgency = urgency;
    }

    /**
     * @return the brokerCustomerId
     */
    public long getBrokerCustomerId() {
        return brokerCustomerId;
    }

    /**
     * @param brokerCustomerId the brokerCustomerId to set
     */
    public void setBrokerCustomerId(long brokerCustomerId) {
        this.brokerCustomerId = brokerCustomerId;
    }

    /**
     * @return the isHandled
     */
    public short getIsHandled() {
        return isHandled;
    }

    /**
     * @param isHandled the isHandled to set
     */
    public void setIsHandled(short isHandled) {
        this.isHandled = isHandled;
    }

    /**
     * @return the source
     */
    public int getSource() {
        return source;
    }

    /**
     * @param source the source to set
     */
    public void setSource(int source) {
        this.source = source;
    }

    /**
     * @param createTime the createTime to set
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return the customer
     */
    public BrokerCustomer getCustomer() {
        return customer;
    }

    /**
     * @param customer the customer to set
     */
    public void setCustomer(BrokerCustomer customer) {
        this.customer = customer;
    }

    public Short getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Short isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Date getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(Date deleteTime) {
        this.deleteTime = deleteTime;
    }

    /**
     * @return the exchange
     */
    public JSONObject getExchange() {
        return JSONObject.parseObject(exchange);
    }

    /**
     * @param exchange the exchange to set
     */
    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    /**
     * @return the callInfo
     */
    public BrokerClueByCall getCallInfo() {
        if (callInfo.replaceAll("\\{\\}", "").trim().isEmpty()) {
            return null;
        }
        return JSONObject.parseObject(callInfo, BrokerClueByCall.class);
    }

    /**
     * @param callInfo the callInfo to set
     */
    public void setCallInfo(String callInfo) {
        this.callInfo = callInfo;
    }

    /**
     * @return the sensitive
     */
    public double getSensitive() {
        return sensitive;
    }

    /**
     * @param sensitive the sensitive to set
     */
    public void setSensitive(double sensitive) {
        this.sensitive = sensitive;
    }

    /**
     * @return the confirm
     */
    public double getConfirm() {
        return confirm;
    }

    /**
     * @param confirm the confirm to set
     */
    public void setConfirm(double confirm) {
        this.confirm = confirm;
    }

    /**
     * @return the customerId
     */
    public long getCustomerId() {
        return customerId;
    }

    /**
     * @param customerId the customerId to set
     */
    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    /**
     * @return the distributeTime
     */
    public Date getDistributeTime() {
        return distributeTime;
    }

    /**
     * @param distributeTime the distributeTime to set
     */
    public void setDistributeTime(Date distributeTime) {
        this.distributeTime = distributeTime;
    }

    /**
     * @return the isDistributed
     */
    public int getIsDistributed() {
        return isDistributed;
    }

    /**
     * @param isDistributed the isDistributed to set
     */
    public void setIsDistributed(int isDistributed) {
        this.isDistributed = isDistributed;
    }

    /**
     * @return the mobileCityId
     */
    public int getMobileCityId() {
        return mobileCityId;
    }

    /**
     * @param mobileCityId the mobileCityId to set
     */
    public void setMobileCityId(int mobileCityId) {
        this.mobileCityId = mobileCityId;
    }

    /**
     * @return the mobileProvinceId
     */
    public int getMobileProvinceId() {
        return mobileProvinceId;
    }

    /**
     * @param mobileProvinceId the mobileProvinceId to set
     */
    public void setMobileProvinceId(int mobileProvinceId) {
        this.mobileProvinceId = mobileProvinceId;
    }

    /**
     * @return the locationProvinceId
     */
    public int getLocationProvinceId() {
        return locationProvinceId;
    }

    /**
     * @param locationProvinceId the locationProvinceId to set
     */
    public void setLocationProvinceId(int locationProvinceId) {
        this.locationProvinceId = locationProvinceId;
    }

    /**
     * @return the locationCityId
     */
    public int getLocationCityId() {
        return locationCityId;
    }

    /**
     * @param locationCityId the locationCityId to set
     */
    public void setLocationCityId(int locationCityId) {
        this.locationCityId = locationCityId;
    }

    /**
     * @return the followSeries
     */
    public List<JSONObject> getFollowSeries() {
        return followSeries;
    }

    /**
     * @param followSeries the followSeries to set
     */
    public void setFollowSeries(List<JSONObject> followSeries) {
        this.followSeries = followSeries;
    }

    /**
     * @return the tags
     */
    public String getTags() {
        return tags;
    }

    /**
     * @param tags the tags to set
     */
    public void setTags(String tags) {
        this.tags = tags;
    }

    /**
     * @return the poolClueId
     */
    public long getPoolClueId() {
        return poolClueId;
    }

    /**
     * @param poolClueId the poolClueId to set
     */
    public void setPoolClueId(long poolClueId) {
        this.poolClueId = poolClueId;
    }

    /**
     * @return the clueRefDealerId
     */
    public long getClueRefDealerId() {
        return clueRefDealerId;
    }

    /**
     * @param clueRefDealerId the clueRefDealerId to set
     */
    public void setClueRefDealerId(long clueRefDealerId) {
        this.clueRefDealerId = clueRefDealerId;
    }

    /**
     * @return the mobileProvince
     */
    public Province getMobileProvince() {
        return mobileProvince;
    }

    /**
     * @param mobileProvince the mobileProvince to set
     */
    public void setMobileProvince(Province mobileProvince) {
        this.mobileProvince = mobileProvince;
    }

    /**
     * @return the mobileCity
     */
    public City getMobileCity() {
        return mobileCity;
    }

    /**
     * @param mobileCity the mobileCity to set
     */
    public void setMobileCity(City mobileCity) {
        this.mobileCity = mobileCity;
    }
}
