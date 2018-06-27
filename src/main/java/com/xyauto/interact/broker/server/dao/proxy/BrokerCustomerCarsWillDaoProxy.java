package com.xyauto.interact.broker.server.dao.proxy;

import com.xyauto.interact.broker.server.dao.IBrokerCustomerCarsWillDao;
import com.xyauto.interact.broker.server.model.po.BrokerCustomerUpdateParameters;
import com.xyauto.interact.broker.server.model.vo.BrokerCustomerCarsWill;
import com.xyauto.interact.broker.server.model.vo.BrokerDealRecord;
import com.xyauto.interact.broker.server.model.vo.BrokerDealVo;
import java.util.AbstractMap;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BrokerCustomerCarsWillDaoProxy {

    @Autowired
    IBrokerCustomerCarsWillDao dao;

    /**
     * 根据线索id获取客户意向车型
     *
     * @param clueId
     * @return
     */
    public BrokerCustomerCarsWill getCarsWillByClueId(long clueId) {
        return dao.getCarsWillByClueId(clueId);
    }

    /**
     * 根据主键获取意向车型
     * @param brokerCustomerCarsWillId
     * @return
     */
    public BrokerCustomerCarsWill getCarsWillByPrimaryKey(long brokerCustomerCarsWillId){
        return dao.getCarsWillByPrimaryKey(brokerCustomerCarsWillId);
    }

    /**
     * 购车完成设置为保有车辆
     * @param brokerCustomerCarsWillId
     * @return
     */
     public int BuyCarFinsh(long brokerCustomerCarsWillId){
         return dao.BuyCarFinsh(brokerCustomerCarsWillId);
     }
    /**
     * 根据客户id 查看意向车型
     *
     * @param brokerCustomerId
     * @return
     */
    public BrokerCustomerCarsWill getCarsWillByCustomerId(long brokerCustomerId) {
        return dao.getCarsWillByCustomerId(brokerCustomerId);
    }

    /**
     * 根据主键获取客户意向车型
     *
     * @param carsWillId
     * @return
     */
    public BrokerCustomerCarsWill getCustomerCarsByID(long carsWillId, long brokerCustomerId) {
        return dao.getCustomerCarsByID(carsWillId, brokerCustomerId);
    }

    /**
     * 根据提供实体修改 意向车型（修改sql语句需要根据需求补充）
     *
     * @param record
     * @return
     */
    public Integer updateParamByCustomerId(BrokerCustomerCarsWill record) {
        return dao.updateParamByCustomerId(record);
    }

    /**
     * 获取客户意向车型成交记录
     *
     * @param brokerIds
     * @return
     */
    public List<BrokerDealRecord> getDealRecords(List<Long> brokerIds) {
        return dao.getDealRecords(brokerIds);
    }

    /**
     * 创建客户意向车型
     *
     * @param model
     * @return
     */
    public int create(BrokerCustomerCarsWill model) {
        return dao.create(model);
    }

    /**
     * 获取经纪人时间段售卖数量
     *
     * @param brokerId
     * @param dealTime
     * @return
     */
    public int getBrokerDealCount(long brokerId, String dealTime) {
        return dao.getBrokerDealCount(brokerId, dealTime);
    }

    /**
     * 查询成交记录
     *
     * @param dealerId
     * @param brokerId
     * @param brandId
     * @param seriesId
     * @param carId
     * @param startTime
     * @param endTime
     * @param status
     * @param page
     * @param limit
     * @return
     */
    public List<BrokerDealVo> getDealRecords(Long dealerId, Long brokerId, Integer brandId,
            Integer seriesId, Integer carId, Date startTime, Date endTime,
            Integer status, Integer page, Integer limit) {
        return dao.getDealRecordList(dealerId, brokerId, brandId, seriesId, carId,
                startTime, endTime, status, page == null ? null : (page - 1) * limit, limit);
    }

    public int getDealRecordsCount(Long dealerId, Long brokerId, Integer brandId,
            Integer seriesId, Integer carId, Date startTime, Date endTime,
            Integer status) {
        return dao.getDealRecordsCount(dealerId, brokerId, brandId, seriesId, carId,
                startTime, endTime, status);
    }

    /**
     * 更新客户意向车型
     *
     * @param params
     * @return
     */
    public int update(BrokerCustomerUpdateParameters params) {
        return dao.update(params);
    }

    /**
     * 获取意向车型列表
     * @param keys
     * @return 
     */
    public List<BrokerCustomerCarsWill> getMaps(List<AbstractMap.SimpleEntry<Long, Long>> keys) {
        return dao.getMaps(keys);
    }
}
