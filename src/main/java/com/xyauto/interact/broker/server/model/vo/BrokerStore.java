package com.xyauto.interact.broker.server.model.vo;

import com.xyauto.interact.broker.server.util.ImageHelper;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class BrokerStore {
    /**
     * 主键
     */
    private Long storeId;

    /**
     * 标题
     */
    private String title;

    /**
     * 导语
     */
    private String introduction;

    /**
     * 二维码图片地址
     */
    private String qrCode;

    /**
     * 经纪人ID
     */
    private Long brokerId;

    /**
     * 微店链接
     */
    private String storeUrl;


    /**
     * 模板id
     */
    private Long templateId;
    
    /**
     * 经纪人
     */
    private Broker broker;
    /**
     * 成交车辆
     */
    private Integer dealCount;
    
    /**
     * 服务率
     */
    private int serviceRate;
    /**
     * 经销商ID
     */
    private Long dealerId;

    /**
     * 经销商信息
     */
    private Dealer dealer;


    private BrokerStoreTemplate brokerStoreTemplate;

    public List<Brand> getBrands() {
        return brands;
    }

    public void setBrands(List<Brand> brands) {
        this.brands = brands;
    }

    private List<Brand> brands;

    public Integer getDealCount() {
        return dealCount;
    }

    public void setDealCount(Integer dealCount) {
        this.dealCount = dealCount;
    }

    public Long getDealerId() {
        return dealerId;
    }

    public void setDealerId(Long dealerId) {
        this.dealerId = dealerId;
    }

    public String getSaleSeries() {
        return saleSeries;
    }

    public void setSaleSeries(String saleSeries) {
        this.saleSeries = saleSeries;
    }

    private String saleSeries;

    public Broker getBroker() {
        return broker;
    }

    public void setBroker(Broker broker) {
        this.broker = broker;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public Long getBrokerId() {
        return brokerId;
    }

    public void setBrokerId(Long brokerId) {
        this.brokerId = brokerId;
    }

    public String getStoreUrl() {
        return storeUrl;
    }

    public void setStoreUrl(String storeUrl) {
        this.storeUrl = storeUrl;
    }

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    /**
     * @return the serviceRate
     */
    public int getServiceRate() {
        return serviceRate;
    }

    /**
     * @param serviceRate the serviceRate to set
     */
    public void setServiceRate(int serviceRate) {
        this.serviceRate = serviceRate;
    }

    public Dealer getDealer() {
        return dealer;
    }

    public void setDealer(Dealer dealer) {
        this.dealer = dealer;
    }


    public BrokerStoreTemplate getBrokerStoreTemplate() {
        return brokerStoreTemplate;
    }

    public void setBrokerStoreTemplate(BrokerStoreTemplate brokerStoreTemplate) {
        this.brokerStoreTemplate = brokerStoreTemplate;
    }
}

