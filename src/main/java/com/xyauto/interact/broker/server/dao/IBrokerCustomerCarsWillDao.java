package com.xyauto.interact.broker.server.dao;

import com.xyauto.interact.broker.server.model.po.BrokerCustomerUpdateParameters;
import com.xyauto.interact.broker.server.model.vo.BrokerCustomerCarsWill;
import com.xyauto.interact.broker.server.model.vo.BrokerDealRecord;
import com.xyauto.interact.broker.server.model.vo.BrokerDealVo;
import java.util.AbstractMap;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface IBrokerCustomerCarsWillDao {

    BrokerCustomerCarsWill getCarsWillByClueId(
            @Param(value = "clueId") long clueId);

    BrokerCustomerCarsWill getCarsWillByPrimaryKey(
            @Param(value = "brokerCustomerCarsWillId") long brokerCustomerCarsWillId);

    int BuyCarFinsh(@Param(value = "brokerCustomerCarsWillId") long brokerCustomerCarsWillId);

    /**
     * 获取未成交意向车型
     * @param brokerCustomerId
     * @return
     */
    BrokerCustomerCarsWill getCarsWillByCustomerId(
            @Param(value = "brokerCustomerId") long brokerCustomerId);

    /**
     * 获取有效发票
     * @param carsWillId
     * @param brokerCustomerId
     * @return
     */
    BrokerCustomerCarsWill getCustomerCarsByID(
            @Param(value = "cars_will_id") long carsWillId,
            @Param(value = "broker_customer_id") long brokerCustomerId);

    Integer updateParamByCustomerId(
            @Param(value = "record") BrokerCustomerCarsWill record);

    public List<BrokerDealRecord> getDealRecords(
            @Param(value = "broker_ids") List<Long> brokerIds);

    public int create(BrokerCustomerCarsWill record);

    int getBrokerDealCount(@Param(value = "brokerId") long brokerId,
            @Param(value = "dealTime") String dealTime);

    List<BrokerDealVo> getDealRecordList(@Param(value = "dealerId") Long dealerId,
            @Param(value = "brokerId") Long brokerId,
            @Param(value = "brandId") Integer brandId,
            @Param(value = "seriesId") Integer seriesId,
            @Param(value = "carId") Integer carId,
            @Param(value = "startTime") Date startTime,
            @Param(value = "endTime") Date endTime,
            @Param(value = "status") Integer status,
            @Param(value = "page") Integer page,
            @Param(value = "limit") Integer limit);

    int getDealRecordsCount(@Param(value = "dealerId") Long dealerId,
            @Param(value = "brokerId") Long brokerId,
            @Param(value = "brandId") Integer brandId,
            @Param(value = "seriesId") Integer seriesId,
            @Param(value = "carId") Integer carId,
            @Param(value = "startTime") Date startTime,
            @Param(value = "endTime") Date endTime,
            @Param(value = "status") Integer status);

    public int update(BrokerCustomerUpdateParameters params);

    /**
     * 获取意向车型列表
     * @param keys
     * @return 
     */
    public List<BrokerCustomerCarsWill> getMaps(@Param("keys") List<AbstractMap.SimpleEntry<Long, Long>> keys);
}
