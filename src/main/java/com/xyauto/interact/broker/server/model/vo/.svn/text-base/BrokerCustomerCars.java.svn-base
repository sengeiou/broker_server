package com.xyauto.interact.broker.server.model.vo;

import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.StringUtils;

/**
 * @author 
 */
@Component
public class BrokerCustomerCars  {
    /**
     * 经纪人客户持有车辆id
     */
    @Id
    private long brokerCustomerCarsId;

    /**
     * 经纪人客户id
     */
    private long brokerCustomerId;

    /**
     * 经纪人客户意向车辆id(客户成功购车后由意向车辆表转入)
     */
    private long brokerCustomerCarsWillId;

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
     * 车牌号
     */
    private String plateNumber = StringUtils.EMPTY;

    /**
     * vin码
     */
    private String vin = StringUtils.EMPTY;

    /**
     * 发动机识别号
     */
    private String ven = StringUtils.EMPTY;

    /**
     * 车辆注册时间
     */
    private Date registerTime;

    /**
     * 下次车辆保养时间
     */
    private Date nextMaintenanceTime;

    /**
     * 下次车辆保养里程(KM)
     */
    private int nextMaintenanceKm;

    /**
     * 保险公司
     */
    private String insurer = StringUtils.EMPTY;

    /**
     * 交强险到期时间
     */
    private Date tciExpireTime;

    /**
     * 商业险到期时间
     */
    private Date ciExpireTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     *车款耦合实体
     */
    private  Car carInfo;
    /**
     * 车型耦合实体
     */
    private  Series seriesInfo;

    /**
     * @return the brokerCustomerCarsId
     */
    public long getBrokerCustomerCarsId() {
        return brokerCustomerCarsId;
    }

    /**
     * @param brokerCustomerCarsId the brokerCustomerCarsId to set
     */
    public void setBrokerCustomerCarsId(long brokerCustomerCarsId) {
        this.brokerCustomerCarsId = brokerCustomerCarsId;
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
     * @return the brokerCustomerCarsWillId
     */
    public long getBrokerCustomerCarsWillId() {
        return brokerCustomerCarsWillId;
    }

    /**
     * @param brokerCustomerCarsWillId the brokerCustomerCarsWillId to set
     */
    public void setBrokerCustomerCarsWillId(long brokerCustomerCarsWillId) {
        this.brokerCustomerCarsWillId = brokerCustomerCarsWillId;
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
     * @return the plateNumber
     */
    public String getPlateNumber() {
        return plateNumber;
    }

    /**
     * @param plateNumber the plateNumber to set
     */
    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    /**
     * @return the vin
     */
    public String getVin() {
        return vin;
    }

    /**
     * @param vin the vin to set
     */
    public void setVin(String vin) {
        this.vin = vin;
    }

    /**
     * @return the ven
     */
    public String getVen() {
        return ven;
    }

    /**
     * @param ven the ven to set
     */
    public void setVen(String ven) {
        this.ven = ven;
    }

    /**
     * @return the registerTime
     */
    public Date getRegisterTime() {
        return registerTime;
    }

    /**
     * @param registerTime the registerTime to set
     */
    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    /**
     * @return the nextMaintenanceTime
     */
    public Date getNextMaintenanceTime() {
        return nextMaintenanceTime;
    }

    /**
     * @param nextMaintenanceTime the nextMaintenanceTime to set
     */
    public void setNextMaintenanceTime(Date nextMaintenanceTime) {
        this.nextMaintenanceTime = nextMaintenanceTime;
    }

    /**
     * @return the nextMaintenanceKm
     */
    public int getNextMaintenanceKm() {
        return nextMaintenanceKm;
    }

    /**
     * @param nextMaintenanceKm the nextMaintenanceKm to set
     */
    public void setNextMaintenanceKm(int nextMaintenanceKm) {
        this.nextMaintenanceKm = nextMaintenanceKm;
    }

    /**
     * @return the insurer
     */
    public String getInsurer() {
        return insurer;
    }

    /**
     * @param insurer the insurer to set
     */
    public void setInsurer(String insurer) {
        this.insurer = insurer;
    }

    /**
     * @return the tciExpireTime
     */
    public Date getTciExpireTime() {
        return tciExpireTime;
    }

    /**
     * @param tciExpireTime the tciExpireTime to set
     */
    public void setTciExpireTime(Date tciExpireTime) {
        this.tciExpireTime = tciExpireTime;
    }

    /**
     * @return the ciExpireTime
     */
    public Date getCiExpireTime() {
        return ciExpireTime;
    }

    /**
     * @param ciExpireTime the ciExpireTime to set
     */
    public void setCiExpireTime(Date ciExpireTime) {
        this.ciExpireTime = ciExpireTime;
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
     * @return the carInfo
     */
    public Car getCarInfo() {
        return carInfo;
    }

    /**
     * @param carInfo the carInfo to set
     */
    public void setCarInfo(Car carInfo) {
        this.carInfo = carInfo;
    }

    /**
     * @return the seriesInfo
     */
    public Series getSeriesInfo() {
        return seriesInfo;
    }

    /**
     * @param seriesInfo the seriesInfo to set
     */
    public void setSeriesInfo(Series seriesInfo) {
        this.seriesInfo = seriesInfo;
    }

}