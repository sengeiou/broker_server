package com.xyauto.interact.broker.server.service;

import com.xyauto.interact.broker.server.dao.proxy.BrokerIntegralLogDaoProxy;
import com.xyauto.interact.broker.server.model.vo.BrokerIntegralLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrokerIntegralLogService {

    @Autowired
    BrokerIntegralLogDaoProxy brokerIntegralLogDaoProxy;


    /**
     * 获取经纪人积分信息列表
     * @param brokerId
     * @return
     */
    public List<BrokerIntegralLog> getPageListByBrokerId(long brokerId,int limit, int page, int begin ){
        return brokerIntegralLogDaoProxy.getPageListByBrokerId(brokerId,limit,page, begin);
    }

    /**
     * 获取经纪人积分信息列表
     * @param brokerId
     * @param limit
     * @param max
     * @return
     */
    public List<BrokerIntegralLog> getListByBrokerId( long brokerId, int limit, String max, int begin){
        return brokerIntegralLogDaoProxy.getListByBrokerId(brokerId, limit,  max, begin);
    }
    
    /**
     * 获取经纪人积分信息列表--总数
     * @param brokerId
     * @param max
     * @return
     */
    public int getListCountByBrokerId( long brokerId,  String max, int begin){
        return brokerIntegralLogDaoProxy.getListCountByBrokerId(brokerId,  max, begin);
    }
    
    /**
     * 获取经纪人积分信息列表--总数
     * @param brokerId
     * @return
     */
    public int getPageListCountByBrokerId(long brokerId, int begin ){
        return brokerIntegralLogDaoProxy.getPageListCountByBrokerId(brokerId, begin);
    }


    /**
     * 创建积分日志
     * @param brokerIntegralLog
     * @return
     */
    public int create(BrokerIntegralLog brokerIntegralLog){
        return brokerIntegralLogDaoProxy.create(brokerIntegralLog);
    }
}
