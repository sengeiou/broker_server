package com.xyauto.interact.broker.server.model.vo;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

/**
 * @author liaow
 */
public class Broker {

    /**
     * 经纪人id
     */
    private long brokerId;

    /**
     * 汽车大全用户id
     */
    private long uid;

    /**
     * 经纪人名称
     */
    private String name = StringUtils.EMPTY;

    /**
     * 经纪人身份，1销售顾问，2销售经理
     */
    private int type;

    /**
     * 所属经销商id
     */
    private long dealerId;

    /**
     * 邮箱，可用于登录
     */
    private String email = StringUtils.EMPTY;

    /**
     * 手机，可用于登录
     */
    private String mobile = StringUtils.EMPTY;

    /**
     * 头像
     */
    private String avatar = "http://img1.qcdqcdn.com/group1/M00/1E/3A/o4YBAFrEnluAaI26AAAmX0O2kJ4372.png";

    /**
     * 工作开始时间
     */
    private Date workTime;

    /**
     * 性别，0未知，1男，2女
     */
    private short gender;

    /**
     * 邮编
     */
    private String zipcode = StringUtils.EMPTY;

    /**
     * 简介
     */
    private String intro = StringUtils.EMPTY;

    /**
     * 微店地址
     */
    private String website = StringUtils.EMPTY;

    /**
     * 服务人数
     */
    private int serviceCount;

    /**
     * 点赞人数
     */
    private int agreeCount;

    /**
     * 参与问答的回答数
     */
    private int qaAnswerCount;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 排序值
     */
    private String sort = StringUtils.EMPTY;

    /**
     * 经销商
     */
    private Dealer dealer;

    /**
     * 12级别
     */
    private int level;

    /**
     * 经纪人积分
     */
    private int integral;

    /**
     * 任务完成比例
     */
    private int missionPercent;

    /**
     * 周业绩-新增客户
     */
    private int weekNewCustomerCount;

    /**
     * 周业绩-新增线索
     */
    private int weekNewClueCount;

    /**
     * 周业绩-头条浏览量
     */
    private int weekMaterielViewCount;

    /**
     * 周业绩-微店浏览量
     */
    private int weekWebsiteViewCount;

    /**
     * 获取经纪人米线任务是否有效
     */
    private int missionEnable;
    
    /**
     * 业务范围
     */
    private String bussinessType = StringUtils.EMPTY;

    /**
     * 微店二维码
     */
    private String qrCode = StringUtils.EMPTY;
    
    /**
     * 用户token
     */
    private String token = StringUtils.EMPTY;
    
    /**
     * 分享链接
     */
    private String share = StringUtils.EMPTY;
    
    private short isDelete;
    private Date deleteTime;

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    private String tags;

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
     * @return the uid
     */
    public long getUid() {
        return uid;
    }

    /**
     * @param uid the uid to set
     */
    public void setUid(long uid) {
        this.uid = uid;
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
    public int getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(int type) {
        this.type = type;
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
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
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
     * @return the avatar
     */
    public String getAvatar() {
        return avatar;
    }

    /**
     * @param avatar the avatar to set
     */
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    /**
     * @return the workTime
     */
    public Date getWorkTime() {
        return workTime;
    }

    /**
     * @param workTime the workTime to set
     */
    public void setWorkTime(Date workTime) {
        this.workTime = workTime;
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
     * @return the zipcode
     */
    public String getZipcode() {
        return zipcode;
    }

    /**
     * @param zipcode the zipcode to set
     */
    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    /**
     * @return the intro
     */
    public String getIntro() {
        return intro;
    }

    /**
     * @param intro the intro to set
     */
    public void setIntro(String intro) {
        this.intro = intro;
    }

    /**
     * @return the website
     */
    public String getWebsite() {
        return website;
    }

    /**
     * @param website the website to set
     */
    public void setWebsite(String website) {
        this.website = website;
    }

    /**
     * @return the serviceCount
     */
    public int getServiceCount() {
        return serviceCount;
    }

    /**
     * @param serviceCount the serviceCount to set
     */
    public void setServiceCount(int serviceCount) {
        this.serviceCount = serviceCount;
    }

    /**
     * @return the agreeCount
     */
    public int getAgreeCount() {
        return agreeCount;
    }

    /**
     * @param agreeCount the agreeCount to set
     */
    public void setAgreeCount(int agreeCount) {
        this.agreeCount = agreeCount;
    }

    /**
     * @return the qaAnswerCount
     */
    public int getQaAnswerCount() {
        return qaAnswerCount;
    }

    /**
     * @param qaAnswerCount the qaAnswerCount to set
     */
    public void setQaAnswerCount(int qaAnswerCount) {
        this.qaAnswerCount = qaAnswerCount;
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
     * @return the dealer
     */
    public Dealer getDealer() {
        return dealer;
    }

    /**
     * @param dealer the dealer to set
     */
    public void setDealer(Dealer dealer) {
        this.dealer = dealer;
    }

    /**
     * @return the level
     */
    public int getLevel() {
        return level;
    }

    /**
     * @param level the level to set
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * @return the integral
     */
    public int getIntegral() {
        return integral;
    }

    /**
     * @param integral the integral to set
     */
    public void setIntegral(int integral) {
        this.integral = integral;
    }

    /**
     * @return the missionPercent
     */
    public int getMissionPercent() {
        return missionPercent;
    }

    /**
     * @param missionPercent the missionPercent to set
     */
    public void setMissionPercent(int missionPercent) {
        this.missionPercent = missionPercent;
    }

    /**
     * @return the weekNewCustomerCount
     */
    public int getWeekNewCustomerCount() {
        return weekNewCustomerCount;
    }

    /**
     * @param weekNewCustomerCount the weekNewCustomerCount to set
     */
    public void setWeekNewCustomerCount(int weekNewCustomerCount) {
        this.weekNewCustomerCount = weekNewCustomerCount;
    }

    /**
     * @return the weekNewClueCount
     */
    public int getWeekNewClueCount() {
        return weekNewClueCount;
    }

    /**
     * @param weekNewClueCount the weekNewClueCount to set
     */
    public void setWeekNewClueCount(int weekNewClueCount) {
        this.weekNewClueCount = weekNewClueCount;
    }

    /**
     * @return the weekMaterielViewCount
     */
    public int getWeekMaterielViewCount() {
        return weekMaterielViewCount;
    }

    /**
     * @param weekMaterielViewCount the weekMaterielViewCount to set
     */
    public void setWeekMaterielViewCount(int weekMaterielViewCount) {
        this.weekMaterielViewCount = weekMaterielViewCount;
    }

    /**
     * @return the weekWebsiteViewCount
     */
    public int getWeekWebsiteViewCount() {
        return weekWebsiteViewCount;
    }

    /**
     * @param weekWebsiteViewCount the weekWebsiteViewCount to set
     */
    public void setWeekWebsiteViewCount(int weekWebsiteViewCount) {
        this.weekWebsiteViewCount = weekWebsiteViewCount;
    }

    /**
     * @return the missionEnable
     */
    public int getMissionEnable() {
        return missionEnable;
    }

    /**
     * @param missionEnable the missionEnable to set
     */
    public void setMissionEnable(int missionEnable) {
        this.missionEnable = missionEnable;
    }

    public String getBussinessType() {
        return bussinessType;
    }
    public void setBussinessType(String bussinessType) {
        this.bussinessType = bussinessType;
    }
    /**
     * @return the qrCode
     */
    public String getQrCode() {
        return qrCode;
    }

    /**
     * @param qrCode the qrCode to set
     */
    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    /**
     * @return the token
     */
    public String getToken() {
        return token;
    }

    /**
     * @param token the token to set
     */
    public void setToken(String token) {
        this.token = token;
    }

	public short getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(short isDelete) {
		this.isDelete = isDelete;
	}

	public Date getDeleteTime() {
		return deleteTime;
	}

	public void setDeleteTime(Date deleteTime) {
		this.deleteTime = deleteTime;
	}

    /**
     * @return the share
     */
    public String getShare() {
        return share;
    }

    /**
     * @param share the share to set
     */
    public void setShare(String share) {
        this.share = share;
    }

}
