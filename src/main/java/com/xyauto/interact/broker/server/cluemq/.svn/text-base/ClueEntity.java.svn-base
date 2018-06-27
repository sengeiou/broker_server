package com.xyauto.interact.broker.server.cluemq;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class ClueEntity {

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
     * 来源id，1000微店，1001会员页
     */
    private long sourceId;
    
    /**
     * 线索类型，1话单，2网单
     */
    private int type;
    /**
     * 经销商省份id
     */
    private int dealerProvinceId;
    /**
     * 顾问id(对应broker_id),有此数据表示直接下单到经纪人的
     */
    private long distributedAdviserId;
    /**
     * 子品牌id
     */
    private int subBrandId;
    /**
     * 公共池线索id
     */
    private long opportunityPoolId;
    /**
     * 手机所在城市id
     */
    private int mobileCityId;
    /**
     * 手机所在省份id
     */
    private int mobileProvinceId;
    
    /**
     * 
     */
    private int subscribePrice;
    
    private int cityId;
    
    /**
     * 设备id
     */
    private String deviceId = "";
    
    private long assignedCarId;
    
    /**
     * 下单时间
     */
    private Object orderTime;
    /**
     * 未知
     */
    private boolean specialType;
    /**
     * 客户电话
     */
    private String customerContact = "";
    /**
     * 是否删除
     */
    private int isDeleted;
    
    private int locakStatus;
    
    private int choiceProvinceId;
    
    /**
     * 车型id
     */
    private int serialId;
    /**
     * 失败原因
     */
    private String handleReason = "";
    /**
     * 客户id
     */
    private long customerId;
    
    private int dealerCreditStatus;
    
    private int assignedCityId;
            
    /**
     * 处理状态，0未处理，1处理成功，2处理失败
     */
    private int handleStatus;
    /**
     * 自增商机id
     */
    private long id;
    /**
     * 经销商城市id
     */
    private int dealerCityid;
    
    private int businessBufferInfoId;
            
    /**
     * 商机类型，线索类型，0新车(新车)，1试驾(新车)，2置换(新车)，3二手车，4预约看车(二手车)，5砍价(二手车)，6新车 
     */
    private int businessOpportunityTypeId;
    /**
     * 未知
     */
    private long productId;
    
    private int choiceCityId;
            
    /**
     * 经销商id
     */
    private long dealerId;
    /**
     * 客户性别，1男，2女
     */
    private int customerGender;
    /**
     * 下单ip地址
     */
    private String ipAddress = "";
    
    private String saleArea;
            
    /**
     * 省份id
     */
    private int provinceId;
    /**
     * 客户姓名
     */
    private String customerName = "";
    /**
     * 车款id
     */
    private int carId;
    /**
     * 下单客户的账号id
     */
    private long customerAccountId;
    /**
     * 品牌id
     */
    private int brandId;
    
    private int assignedDealerId;
            
    /**
     * 经销商排期状态，-1免费，0收费，1使用
     */
    private int isTrail;
    /**
     * 经销商线索
     */
    private long opportunityRelDealerId;
    /**
     * 商机状态，1有效商机，2流失商机，3外呼商机
     */
    private int isOutCall;
    /**
     * 已存储经纪人线索id(broker_server)
     */
    private long brokerClueId;
    
    /**
     * 关联客户id
     */
    private long brokerCustomerId;
    
    /**
     * 置换信息
     */
    private OwendCarInfoDTO ownedCarInfoDTO;
    
    /**
     * 原始推送数据
     */
    private String extension = StringUtils.EMPTY;
    
    /**
     * 话单数据
     */
    private CallClueEntity call;

    private  long brokerId;
    /**
     * @return the sourceId
     */
    public long getSourceId() {
        return sourceId;
    }

    /**
     * @param sourceId the sourceId to set
     */
    public void setSourceId(long sourceId) {
        this.sourceId = sourceId;
    }

    /**
     * @return the dealerProvinceId
     */
    public int getDealerProvinceId() {
        return dealerProvinceId;
    }

    /**
     * @param dealerProvinceId the dealerProvinceId to set
     */
    public void setDealerProvinceId(int dealerProvinceId) {
        this.dealerProvinceId = dealerProvinceId;
    }

    /**
     * @return the distributeAdviserId
     */
    public long getDistributedAdviserId() {
        return distributedAdviserId;
    }

    /**
     * @param distributedAdviserId the distributedAdviserId to set
     */
    public void setDistributedAdviserId(long distributedAdviserId) {
        this.distributedAdviserId = distributedAdviserId;
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
     * @return the opportunityPoolId
     */
    public long getOpportunityPoolId() {
        return opportunityPoolId;
    }

    /**
     * @param opportunityPoolId the opportunityPoolId to set
     */
    public void setOpportunityPoolId(long opportunityPoolId) {
        this.opportunityPoolId = opportunityPoolId;
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
     * @return the orderTime
     */
    public Object getOrderTime() {
        return orderTime;
    }

    /**
     * @param orderTime the orderTime to set
     */
    public void setOrderTime(Object orderTime) {
        this.orderTime = orderTime;
    }

    /**
     * @return the specialType
     */
    public boolean isSpecialType() {
        return specialType;
    }

    /**
     * @param specialType the specialType to set
     */
    public void setSpecialType(boolean specialType) {
        this.specialType = specialType;
    }

    /**
     * @return the customerContact
     */
    public String getCustomerContact() {
        return customerContact;
    }

    /**
     * @param customerContact the customerContact to set
     */
    public void setCustomerContact(String customerContact) {
        this.customerContact = customerContact;
    }

    /**
     * @return the isDeleted
     */
    public int getIsDeleted() {
        return isDeleted;
    }

    /**
     * @param isDeleted the isDeleted to set
     */
    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * @return the serialId
     */
    public int getSerialId() {
        return serialId;
    }

    /**
     * @param serialId the serialId to set
     */
    public void setSerialId(int serialId) {
        this.serialId = serialId;
    }

    /**
     * @return the handleReason
     */
    public String getHandleReason() {
        return handleReason;
    }

    /**
     * @param handleReason the handleReason to set
     */
    public void setHandleReason(String handleReason) {
        this.handleReason = handleReason;
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
     * @return the handleStatus
     */
    public int getHandleStatus() {
        return handleStatus;
    }

    /**
     * @param handleStatus the handleStatus to set
     */
    public void setHandleStatus(int handleStatus) {
        this.handleStatus = handleStatus;
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the dealerCityid
     */
    public int getDealerCityid() {
        return dealerCityid;
    }

    /**
     * @param dealerCityid the dealerCityid to set
     */
    public void setDealerCityid(int dealerCityid) {
        this.dealerCityid = dealerCityid;
    }

    /**
     * @return the businessOpportunityTypeId
     */
    public int getBusinessOpportunityTypeId() {
        return businessOpportunityTypeId;
    }

    /**
     * @param businessOpportunityTypeId the businessOpportunityTypeId to set
     */
    public void setBusinessOpportunityTypeId(int businessOpportunityTypeId) {
        this.businessOpportunityTypeId = businessOpportunityTypeId;
    }

    /**
     * @return the productId
     */
    public long getProductId() {
        return productId;
    }

    /**
     * @param productId the productId to set
     */
    public void setProductId(long productId) {
        this.productId = productId;
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
     * @return the customerGender
     */
    public int getCustomerGender() {
        return customerGender;
    }

    /**
     * @param customerGender the customerGender to set
     */
    public void setCustomerGender(int customerGender) {
        this.customerGender = customerGender;
    }

    /**
     * @return the ipAddress
     */
    public String getIpAddress() {
        return ipAddress;
    }

    /**
     * @param ipAddress the ipAddress to set
     */
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
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
     * @return the customerName
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * @param customerName the customerName to set
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
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
     * @return the customerAccountId
     */
    public long getCustomerAccountId() {
        return customerAccountId;
    }

    /**
     * @param customerAccountId the customerAccountId to set
     */
    public void setCustomerAccountId(long customerAccountId) {
        this.customerAccountId = customerAccountId;
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
     * @return the isTrail
     */
    public int getIsTrail() {
        return isTrail;
    }

    /**
     * @param isTrail the isTrail to set
     */
    public void setIsTrail(int isTrail) {
        this.isTrail = isTrail;
    }

    /**
     * @return the opportunityRelDealerId
     */
    public long getOpportunityRelDealerId() {
        return opportunityRelDealerId;
    }

    /**
     * @param opportunityRelDealerId the opportunityRelDealerId to set
     */
    public void setOpportunityRelDealerId(long opportunityRelDealerId) {
        this.opportunityRelDealerId = opportunityRelDealerId;
    }

    /**
     * @return the isOutCall
     */
    public int getIsOutCall() {
        return isOutCall;
    }

    /**
     * @param isOutCall the isOutCall to set
     */
    public void setIsOutCall(int isOutCall) {
        this.isOutCall = isOutCall;
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
     * @return the subscribePrice
     */
    public int getSubscribePrice() {
        return subscribePrice;
    }

    /**
     * @param subscribePrice the subscribePrice to set
     */
    public void setSubscribePrice(int subscribePrice) {
        this.subscribePrice = subscribePrice;
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
     * @return the assignedCarId
     */
    public long getAssignedCarId() {
        return assignedCarId;
    }

    /**
     * @param assignedCarId the assignedCarId to set
     */
    public void setAssignedCarId(long assignedCarId) {
        this.assignedCarId = assignedCarId;
    }

    /**
     * @return the locakStatus
     */
    public int getLocakStatus() {
        return locakStatus;
    }

    /**
     * @param locakStatus the locakStatus to set
     */
    public void setLocakStatus(int locakStatus) {
        this.locakStatus = locakStatus;
    }

    /**
     * @return the choiceProvinceId
     */
    public int getChoiceProvinceId() {
        return choiceProvinceId;
    }

    /**
     * @param choiceProvinceId the choiceProvinceId to set
     */
    public void setChoiceProvinceId(int choiceProvinceId) {
        this.choiceProvinceId = choiceProvinceId;
    }

    /**
     * @return the dealerCreditStatus
     */
    public int getDealerCreditStatus() {
        return dealerCreditStatus;
    }

    /**
     * @param dealerCreditStatus the dealerCreditStatus to set
     */
    public void setDealerCreditStatus(int dealerCreditStatus) {
        this.dealerCreditStatus = dealerCreditStatus;
    }

    /**
     * @return the assignedCityId
     */
    public int getAssignedCityId() {
        return assignedCityId;
    }

    /**
     * @param assignedCityId the assignedCityId to set
     */
    public void setAssignedCityId(int assignedCityId) {
        this.assignedCityId = assignedCityId;
    }

    /**
     * @return the businessBufferInfoId
     */
    public int getBusinessBufferInfoId() {
        return businessBufferInfoId;
    }

    /**
     * @param businessBufferInfoId the businessBufferInfoId to set
     */
    public void setBusinessBufferInfoId(int businessBufferInfoId) {
        this.businessBufferInfoId = businessBufferInfoId;
    }

    /**
     * @return the choiceCityId
     */
    public int getChoiceCityId() {
        return choiceCityId;
    }

    /**
     * @param choiceCityId the choiceCityId to set
     */
    public void setChoiceCityId(int choiceCityId) {
        this.choiceCityId = choiceCityId;
    }

    /**
     * @return the saleArea
     */
    public String getSaleArea() {
        return saleArea;
    }

    /**
     * @param saleArea the saleArea to set
     */
    public void setSaleArea(String saleArea) {
        this.saleArea = saleArea;
    }

    /**
     * @return the assignedDealerId
     */
    public int getAssignedDealerId() {
        return assignedDealerId;
    }

    /**
     * @param assignedDealerId the assignedDealerId to set
     */
    public void setAssignedDealerId(int assignedDealerId) {
        this.assignedDealerId = assignedDealerId;
    }

    /**
     * @return the extension
     */
    public String getExtension() {
        return extension;
    }

    /**
     * @param extension the extension to set
     */
    public void setExtension(String extension) {
        this.extension = extension;
    }

    /**
     * @return the ownedCarInfoDTO
     */
    public String getOwnedCarInfoDTO() {
        return ownedCarInfoDTO==null?"{}":JSON.toJSONString(ownedCarInfoDTO);
    }

    /**
     * @param ownedCarInfoDTO the ownedCarInfoDTO to set
     */
    public void setOwnedCarInfoDTO(OwendCarInfoDTO ownedCarInfoDTO) {
        this.ownedCarInfoDTO = ownedCarInfoDTO;
    }
    
    
    public class OwendCarInfoDTO {
        
        /**
         * 有效线索主键id
         */
        private long businessOpportunityId;
        /**
         * 首次上牌时间
         */
        private String buyCarDate;
        /**
         * 现有车款id
         */
        private int carId;
        /**
         * 颜色
         */
        private String color;
        /**
         * 创建时间
         */
        private long createTime;
        /**
         * 形式里程
         */
        private String drivingMileage;
        /**
         * 自增id
         */
        private long id;
        /**
         * 下单时间
         */
        private int locationId;
        /**
         * 是否删除
         */
        private int isDeleted;
        /**
         * 备注
         */
        private String remark;
        /**
         * 更新时间
         */
        private long updateTime;

        /**
         * @return the businessOpportunityId
         */
        public long getBusinessOpportunityId() {
            return businessOpportunityId;
        }

        /**
         * @param businessOpportunityId the businessOpportunityId to set
         */
        public void setBusinessOpportunityId(long businessOpportunityId) {
            this.businessOpportunityId = businessOpportunityId;
        }

        /**
         * @return the buyCarDate
         */
        public String getBuyCarDate() {
            return buyCarDate;
        }

        /**
         * @param buyCarDate the buyCarDate to set
         */
        public void setBuyCarDate(String buyCarDate) {
            this.buyCarDate = buyCarDate;
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
         * @return the color
         */
        public String getColor() {
            return color;
        }

        /**
         * @param color the color to set
         */
        public void setColor(String color) {
            this.color = color;
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
         * @return the drivingMileage
         */
        public String getDrivingMileage() {
            return drivingMileage;
        }

        /**
         * @param drivingMileage the drivingMileage to set
         */
        public void setDrivingMileage(String drivingMileage) {
            this.drivingMileage = drivingMileage;
        }

        /**
         * @return the id
         */
        public long getId() {
            return id;
        }

        /**
         * @param id the id to set
         */
        public void setId(long id) {
            this.id = id;
        }

        /**
         * @return the locationId
         */
        public int getLocationId() {
            return locationId;
        }

        /**
         * @param locationId the locationId to set
         */
        public void setLocationId(int locationId) {
            this.locationId = locationId;
        }

        /**
         * @return the isDeleted
         */
        public int getIsDeleted() {
            return isDeleted;
        }

        /**
         * @param isDeleted the isDeleted to set
         */
        public void setIsDeleted(int isDeleted) {
            this.isDeleted = isDeleted;
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
         * @return the updateTime
         */
        public long getUpdateTime() {
            return updateTime;
        }

        /**
         * @param updateTime the updateTime to set
         */
        public void setUpdateTime(long updateTime) {
            this.updateTime = updateTime;
        }
    }

    /**
     * @return the call
     */
    public String getCall() {
        return call==null?"{}":JSON.toJSONString(call);
    }

    /**
     * @param call the call to set
     */
    public void setCall(CallClueEntity call) {
        this.call = call;
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

    public long getBrokerId() {
        return brokerId;
    }

    public void setBrokerId(long brokerId) {
        this.brokerId = brokerId;
    }
}
