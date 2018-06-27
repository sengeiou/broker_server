package com.xyauto.interact.broker.server.model.vo;

import java.util.Date;
import org.springframework.stereotype.Component;

@Component
public class BrokerTemplate implements  Cloneable {

    /**
     * 模板id
     */
    private long brokerTemplateId;

    /**
     * 经纪人id
     */
    private long brokerId;

    /**
     * 模板类型，1短信，2微聊 3 微店标题 4 微店导语
     */
    private Short type;

    /**
     * 模板内容
     */
    private String content;

    /**
     * 是否默认，0否，1是
     */
    private Short isDefault;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否删除，0否，1是
     */
    private short isDeleted;

    /**
     * 删除时间
     */
    private Date deleteTime;

    /**
     * 模板名称
     */
    private String name;
    /**
     * 是否锁定，0否，1是
     */
    private short isLocked;
    
    /**
     * 排序值
     */
    private String sort ;

    /**
     * @return the brokerTemplateId
     */
    public long getBrokerTemplateId() {
        return brokerTemplateId;
    }

    /**
     * @param brokerTemplateId the brokerTemplateId to set
     */
    public void setBrokerTemplateId(long brokerTemplateId) {
        this.brokerTemplateId = brokerTemplateId;
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
    public Short getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(Short type) {
        this.type = type;
    }

    /**
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return the isDefault
     */
    public Short getIsDefault() {
        return isDefault;
    }

    /**
     * @param isDefault the isDefault to set
     */
    public void setIsDefault(Short isDefault) {
        this.isDefault = isDefault;
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
     * @return the updateTime
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime the updateTime to set
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * @return the isDeleted
     */
    public short getIsDeleted() {
        return isDeleted;
    }

    /**
     * @param isDeleted the isDeleted to set
     */
    public void setIsDeleted(short isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * @return the deleteTime
     */
    public Date getDeleteTime() {
        return deleteTime;
    }

    /**
     * @param deleteTime the deleteTime to set
     */
    public void setDeleteTime(Date deleteTime) {
        this.deleteTime = deleteTime;
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
     * @return the isLocked
     */
    public short getIsLocked() {
        return isLocked;
    }

    /**
     * @param isLocked the isLocked to set
     */
    public void setIsLocked(short isLocked) {
        this.isLocked = isLocked;
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


    @Override
    public BrokerTemplate clone() {
        try {
            return (BrokerTemplate)super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }



}