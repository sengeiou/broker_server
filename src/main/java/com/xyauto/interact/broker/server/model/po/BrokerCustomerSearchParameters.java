package com.xyauto.interact.broker.server.model.po;

import com.google.common.collect.Lists;
import java.util.List;

public class BrokerCustomerSearchParameters implements IRequestParameters {

    /**
     * 经销商id
     */
    private List<Long> dealerIds = Lists.newArrayList();
    /**
     * 经纪人id
     */
    private List<Long> brokerIds = Lists.newArrayList();
    /**
     * 意向等级（HABCD）
     */
    private List<Integer> level = Lists.newArrayList();
    /**
     * 意向阶段
     */
    private List<Integer> step = Lists.newArrayList();
    /**
     * 车型id
     */
    private int seriesId;
    /**
     * 车款id
     */
    private int carId;
    /**
     * 开始时间
     */
    private long begin;
    /**
     * 结束时间
     */
    private long end;
    /**
     * 省份id
     */
    private int provinceId;
    /**
     * 城市id
     */
    private int cityId;
    /**
     * 客户手机号
     */
    private String mobile;
    /**
     * 客户名称
     */
    private String username;
    
    /**
     * 类型（1新车，2二手车）
     */
    private List<Integer> categories = Lists.newArrayList();

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
     * @param level the level to set
     */
    public void setLevel(List<Integer> level) {
        this.level = level;
    }

    /**
     * @param step the step to set
     */
    public void setStep(List<Integer> step) {
        this.step = step;
    }

    /**
     * @return the level
     */
    public List<Integer> getLevel() {
        return level;
    }

    /**
     * @return the step
     */
    public List<Integer> getStep() {
        return step;
    }

    /**
     * @return the categories
     */
    public List<Integer> getCategories() {
        return categories;
    }

    /**
     * @param categories the categories to set
     */
    public void setCategories(List<Integer> categories) {
        this.categories = categories;
    }

}
