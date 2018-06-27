package com.xyauto.interact.broker.server.model.po;

import com.google.common.collect.Lists;
import java.util.List;
import org.apache.commons.lang.StringUtils;

public class BrokerClueSearchParameters implements IRequestParameters {

    /**
     * 线索ID
     */
    private long brokerClueId;
    /**
     * 原始线索id
     */
    private long clueId;
    /**
     * 关联经销商ids
     */
    private List<Long> brokerIds = Lists.newArrayList();
    /**
     * 关联经纪人ids
     */
    private List<Long> dealerIds = Lists.newArrayList();
    /**
     * 经销商名称
     */
    private String dealerName = StringUtils.EMPTY;
    /**
     * 品牌id
     */
    private int brandId;
    /**
     * 城市id
     */
    private int cityId;
    /**
     * 省份id
     */
    private int provinceId;
    /**
     * 车型id
     */
    private int seriesId;
    /**
     * 车款id
     */
    private int carId;
    /**
     * 是否已处理，0未处理，1已处理 -1 全部
     */
    private int isHandled = -1 ;
    /**
     * 开始时间
     */
    private long begin;
    /**
     * 结束时间
     */
    private long end;
    /**
     * 客户名称
     */
    private String username = StringUtils.EMPTY;
    /**
     * 是否删除，0未删除，1已删除
     */
    private int isdelete ;
    /**
     * 删除时间
     */
    private int deletedtime;
    /**
     * 原始客户id
     */
    private long customerId;
    /**
     * 客户手机号
     */
    private String mobile = StringUtils.EMPTY;
    /**
     * 批量手机号查询
     */
    private List<String> mobileList =Lists.newArrayList();
    /**
     * 线索分类，1话单，2网单
     */
    private short type;
    /**
     * 线索分类，0新车(新车)，1试驾(新车)，2置换(新车)，3二手车，4预约看车(二手车)，5砍价(二手车)，6新车 
     */
    private List<Integer> categories = Lists.newArrayList();
    /**
     * 是否建卡 -1 全部，0否，1是
     */
    private short isCustomer = -1;
    /**
     * 渠道来源,1 店铺线索，2 微店线索， 3问答线索，4头条线索 ， 5公共线索
     */
    private List<Integer> sources = Lists.newArrayList();
    
    /**
     * 分配开始时间
     */
    private long distributeBegin;
    
    /**
     * 分配结束时间
     */
    private long distirbuteEnd;
    
    /**
     * 是否分配
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
     * 定位城市id
     */
    private int locationCityId;
    
    /**
     * 定位省份id
     */
    private int locationProvinceId;
    
    
    private String max = "0";
    private long offset;
    private int limit;
    /*
    是否根据手机号分组取最新一条
     */
    private int isGroupbyMobile;


    /**
     * 主叫号码
     */
    private String callerPhoneNumber = StringUtils.EMPTY;

    /**
     * 被叫号码
     */
    private String calleeNumber = StringUtils.EMPTY;

    /**
     * 被叫真实号码
     */
    private String calleeRealNumber = StringUtils.EMPTY;

    /**
     * 呼叫状态
     * 0 全部 -1 未接通 1 接通(answer)
     */
    private int callstatus = 0;

    /**
     * 呼叫开始时间
     */
    private Long callbegintime;


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
     * @return the brokerIds
     */
    public List<Long> getBrokerIds() {
        return brokerIds;
    }

    /**
     * @param brokerIds the brokerIds to set
     */
    public void setBrokerIds(List<Long> brokerIds) {
        this.brokerIds = brokerIds;
    }

    /**
     * @return the dealerIds
     */
    public List<Long> getDealerIds() {
        return dealerIds;
    }

    /**
     * @param dealerIds the dealerIds to set
     */
    public void setDealerIds(List<Long> dealerIds) {
        this.dealerIds = dealerIds;
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
     * @return the isHandled
     */
    public int getIsHandled() {
        return isHandled;
    }

    /**
     * @param isHandled the isHandled to set
     */
    public void setIsHandled(int isHandled) {
        this.isHandled = isHandled;
    }

    /**
     * @return the begin
     */
    public long getBegin() {
        return begin;
    }

    /**
     * @param begin the begin to set
     */
    public void setBegin(long begin) {
        this.begin = begin;
    }

    /**
     * @return the end
     */
    public long getEnd() {
        return end;
    }

    /**
     * @param end the end to set
     */
    public void setEnd(long end) {
        this.end = end;
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
     * @return the isdelete
     */
    public int getIsdelete() {
        return isdelete;
    }

    /**
     * @param isdelete the isdelete to set
     */
    public void setIsdelete(int isdelete) {
        this.isdelete = isdelete;
    }

    /**
     * @return the deletedtime
     */
    public int getDeletedtime() {
        return deletedtime;
    }

    /**
     * @param deletedtime the deletedtime to set
     */
    public void setDeletedtime(int deletedtime) {
        this.deletedtime = deletedtime;
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
     * @return the categories
     */
    public List<Integer> getCategories() {
        List<Integer> list = Lists.newArrayList();
        if (categories.contains(1)) {
            list.addAll(Lists.newArrayList(0,1,2,6));
        }
        if (categories.contains(2)) {
            list.addAll(Lists.newArrayList(3,4,5));
        }
        return categories;
    }

    /**
     * @param categories the categories to set
     */
    public void setCategories(List<Integer> categories) {
        this.categories = categories;
    }

    /**
     * @return the isCustomer
     */
    public short getIsCustomer() {
        return isCustomer;
    }

    /**
     * @param isCustomer the isCustomer to set
     */
    public void setIsCustomer(short isCustomer) {
        this.isCustomer = isCustomer;
    }

    /**
     * @return the sources
     */
    public List<Integer> getSources() {
        return sources;
    }

    /**
     * @param sources the sources to set
     */
    public void setSources(List<Integer> sources) {
        this.sources = sources;
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
     * @return the isGroupbyMobile
     */
    public int getIsGroupbyMobile() {
        return isGroupbyMobile;
    }

    /**
     * @param isGroupbyMobile the isGroupbyMobile to set
     */
    public void setIsGroupbyMobile(int isGroupbyMobile) {
        this.isGroupbyMobile = isGroupbyMobile;
    }


    public String getCallerPhoneNumber() {
        return callerPhoneNumber;
    }

    public void setCallerPhoneNumber(String callerPhoneNumber) {
        this.callerPhoneNumber = callerPhoneNumber;
    }

    public String getCalleeNumber() {
        return calleeNumber;
    }

    public void setCalleeNumber(String calleeNumber) {
        this.calleeNumber = calleeNumber;
    }

    public String getCalleeRealNumber() {
        return calleeRealNumber;
    }

    public void setCalleeRealNumber(String calleeRealNumber) {
        this.calleeRealNumber = calleeRealNumber;
    }

    public int getCallstatus() {
        return callstatus;
    }

    public void setCallstatus(int callstatus) {
        this.callstatus = callstatus;
    }

    public Long getCallbegintime() {
        return callbegintime;
    }

    public void setCallbegintime(Long callbegintime) {
        this.callbegintime = callbegintime;
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
     * @return the distributeBegin
     */
    public long getDistributeBegin() {
        return distributeBegin;
    }

    /**
     * @param distributeBegin the distributeBegin to set
     */
    public void setDistributeBegin(long distributeBegin) {
        this.distributeBegin = distributeBegin;
    }

    /**
     * @return the distirbuteEnd
     */
    public long getDistirbuteEnd() {
        return distirbuteEnd;
    }

    /**
     * @param distirbuteEnd the distirbuteEnd to set
     */
    public void setDistirbuteEnd(long distirbuteEnd) {
        this.distirbuteEnd = distirbuteEnd;
    }

    public long getBrokerClueId() {
        return brokerClueId;
    }

    public void setBrokerClueId(long brokerClueId) {
        this.brokerClueId = brokerClueId;
    }

    public List<String> getMobileList() {
        return mobileList;
    }

    public void setMobileList(List<String> mobileList) {
        this.mobileList = mobileList;
    }
}
