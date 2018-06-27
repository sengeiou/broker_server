package com.xyauto.interact.broker.server.model.vo;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import org.apache.commons.lang.StringUtils;

/**
 * @author
 */
@Component
public class BrokerCustomer {

    /**
     * 经纪人客户id
     */
    private long brokerCustomerId;

    /**
     * 经纪人线索id(客户建卡来自于线索)
     */
    private long brokerClueId;

    /**
     * 经纪人id
     */
    private long brokerId;

    /**
     * 所属经销商id
     */
    private long dealerId;

    /**
     * 客户姓名
     */
    private String userName = StringUtils.EMPTY;

    /**
     * 性别，0未知，1男，2女
     */
    private short gender;

    /**
     * 客户电话
     */
    private String mobile = StringUtils.EMPTY;

    /**
     * 客户地址
     */
    private String address = StringUtils.EMPTY;

    /**
     * 省份id
     */
    private int provinceId;

    /**
     * 城市id
     */
    private int cityId;

    /**
     * 意向阶段，0未确认，1待跟进，2将到店，4要试驾，5成交，-1无效，-2战败
     */
    private short step;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 排序值
     */
    private String sort;

    /**
     * 首次联系时间
     */
    private Date firstContractTime;

    /**
     * 客户最后分配到的线索id
     */
    private long brokerClueIdLastest;

    /**
     * 最后联系时间
     */
    private Date lastContactTime;

    /**
     * 下次联系时间
     */
    private Date nextContactTime;

    /**
     * 意向等级，1H，2A, 3B,4C,5D
     */
    private short level;

    /**
     * 备注
     */
    private String remark = StringUtils.EMPTY;

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
     * 购车标签
     */
    private String tags = StringUtils.EMPTY;
    
    /**
     * 购车预算
     */
    private String budget = StringUtils.EMPTY;

    /**
     * 意向车型
     */
    private BrokerCustomerCarsWill carsWill;

    /**
     * 客户保有车辆
     */
    private List<BrokerCustomerCars> customerCars;

    /**
     * 线索信息
     */
    private BrokerClue clue;

    /**
     * 线索信息列表
     */
    private List<BrokerClue> clueInfoList;

    /**
     * 经纪人信息
     */
    private Broker brokerInfo;

    /**
     * 所属城市信息
     */
    private City city;

    /**
     * 所属省份id
     */
    private Province province;

    /**
     * 跟进状态描述
     */
    private String stepDesc;

    /**
     * 意向等级描述
     */
    private String levelDesc;

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
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the gender
     */
    public short getGender() {
        return gender;
    }

    /**
     * @param gender the gender to set
     */
    public void setGender(short gender) {
        this.gender = gender;
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
     * @return the provinceId
     */
    public Integer getProvinceId() {
        return provinceId;
    }

    /**
     * @param provinceId the provinceId to set
     */
    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    /**
     * @return the cityId
     */
    public Integer getCityId() {
        return cityId;
    }

    /**
     * @param cityId the cityId to set
     */
    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    /**
     * @return the step
     */
    public short getStep() {
        return step;
    }

    /**
     * @param step the step to set
     */
    public void setStep(short step) {
        this.step = step;
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
     * @return the firstContractTime
     */
    public Date getFirstContractTime() {
        return firstContractTime;
    }

    /**
     * @param firstContractTime the firstContractTime to set
     */
    public void setFirstContractTime(Date firstContractTime) {
        this.firstContractTime = firstContractTime;
    }

    /**
     * @return the brokerClueIdLastest
     */
    public long getBrokerClueIdLastest() {
        return brokerClueIdLastest;
    }

    /**
     * @param brokerClueIdLastest the brokerClueIdLastest to set
     */
    public void setBrokerClueIdLastest(long brokerClueIdLastest) {
        this.brokerClueIdLastest = brokerClueIdLastest;
    }

    /**
     * @return the lastContactTime
     */
    public Date getLastContactTime() {
        return lastContactTime;
    }

    /**
     * @param lastContactTime the lastContactTime to set
     */
    public void setLastContactTime(Date lastContactTime) {
        this.lastContactTime = lastContactTime;
    }

    /**
     * @return the nextContactTime
     */
    public Object getNextContactTime() {
        if (this.nextContactTime == null || this.createTime == null) {
            return 0L;
        }
        if (this.nextContactTime.getTime() < this.createTime.getTime()) {
            return 0L;
        }
        return nextContactTime;
    }

    /**
     * @param nextContactTime the nextContactTime to set
     */
    public void setNextContactTime(Date nextContactTime) {
        this.nextContactTime = nextContactTime;
    }

    /**
     * @return the level
     */
    public short getLevel() {
        return level;
    }

    /**
     * @param level the level to set
     */
    public void setLevel(short level) {
        this.level = level;
    }

    /**
     * @return the remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark the remark to set
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * @return the carPurchasingIndex
     */
    public double getCarPurchasingIndex() {
        return carPurchasingIndex;
    }

    /**
     * @param carPurchasingIndex the carPurchasingIndex to set
     */
    public void setCarPurchasingIndex(double carPurchasingIndex) {
        this.carPurchasingIndex = carPurchasingIndex;
    }

    /**
     * @return the satisfaction
     */
    public double getSatisfaction() {
        return satisfaction;
    }

    /**
     * @param satisfaction the satisfaction to set
     */
    public void setSatisfaction(double satisfaction) {
        this.satisfaction = satisfaction;
    }

    /**
     * @return the freshness
     */
    public double getFreshness() {
        return freshness;
    }

    /**
     * @param freshness the freshness to set
     */
    public void setFreshness(double freshness) {
        this.freshness = freshness;
    }

    /**
     * @return the urgency
     */
    public double getUrgency() {
        return urgency;
    }

    /**
     * @param urgency the urgency to set
     */
    public void setUrgency(double urgency) {
        this.urgency = urgency;
    }

    /**
     * @return the carsWill
     */
    public BrokerCustomerCarsWill getCarsWill() {
        return carsWill;
    }

    /**
     * @param carsWill the carsWill to set
     */
    public void setCarsWill(BrokerCustomerCarsWill carsWill) {
        this.carsWill = carsWill;
    }

    /**
     * @return the customerCars
     */
    public List<BrokerCustomerCars> getCustomerCars() {
        return customerCars;
    }

    /**
     * @param customerCars the customerCars to set
     */
    public void setCustomerCars(List<BrokerCustomerCars> customerCars) {
        this.customerCars = customerCars;
    }

    /**
     * @return the clue
     */
    public BrokerClue getClue() {
        return clue;
    }

    /**
     * @param clue the clue to set
     */
    public void setClue(BrokerClue clue) {
        this.clue = clue;
    }

    /**
     * @return the clueInfoList
     */
    public List<BrokerClue> getClueInfoList() {
        return clueInfoList;
    }

    /**
     * @param clueInfoList the clueInfoList to set
     */
    public void setClueInfoList(List<BrokerClue> clueInfoList) {
        this.clueInfoList = clueInfoList;
    }

    /**
     * @return the brokerInfo
     */
    public Broker getBrokerInfo() {
        return brokerInfo;
    }

    /**
     * @param brokerInfo the brokerInfo to set
     */
    public void setBrokerInfo(Broker brokerInfo) {
        this.brokerInfo = brokerInfo;
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
     * @return the stepDesc
     */
    public String getStepDesc() {
        String desc = "";
        switch (this.step) {
            case 1:
                desc = "待跟进";
                break;
            case 2:
                desc = "到店";
                break;
            case 5:
                desc = "成交";
                break;
            case -1:
                desc = "无效";
                break;
            case -2:
                desc = "战败";
                break;
        }
        return desc;
    }

    /**
     * @param stepDesc the stepDesc to set
     */
    public void setStepDesc(String stepDesc) {
        this.stepDesc = stepDesc;
    }

    /**
     * @return the levelDesc
     */
    public String getLevelDesc() {
        String desc = "";
        switch (this.level) {
            case 1:
                desc = "H";
                break;
            case 2:
                desc = "A";
                break;
            case 3:
                desc = "B";
                break;
            case 4:
                desc = "C";
                break;
            case 5:
                desc = "D";
                break;
        }
        return desc;
    }

    /**
     * @param levelDesc the levelDesc to set
     */
    public void setLevelDesc(String levelDesc) {
        this.levelDesc = levelDesc;
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

}
