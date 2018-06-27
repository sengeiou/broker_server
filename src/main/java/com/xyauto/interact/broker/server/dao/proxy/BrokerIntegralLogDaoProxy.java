package com.xyauto.interact.broker.server.dao.proxy;

import com.xyauto.interact.broker.server.dao.IBrokerIntegralLogDao;
import com.xyauto.interact.broker.server.model.vo.BrokerIntegralLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BrokerIntegralLogDaoProxy {

    @Autowired
    IBrokerIntegralLogDao brokerIntegralLogDao;
    /**
     * 获取经纪人积分信息列表
     * @param brokerId
     * @return
     */
    public List<BrokerIntegralLog> getPageListByBrokerId(long brokerId,int limit, int page,int begin){
        return brokerIntegralLogDao.getPageListByBrokerId(brokerId, limit,  page,begin);
    }

    public List<BrokerIntegralLog> getListByBrokerId( long brokerId, int limit, String max,int begin){
        return brokerIntegralLogDao.getListByBrokerId(brokerId, limit,  max,begin);
    }
    
    public int getListCountByBrokerId( long brokerId, String max, int begin){
        return brokerIntegralLogDao.getListCountByBrokerId(brokerId,  max, begin);
    }
    
    public int getPageListCountByBrokerId(long brokerId,int begin){
        return brokerIntegralLogDao.getPageListCountByBrokerId(brokerId,begin);
    }

    public int create(BrokerIntegralLog brokerIntegralLog){
        return brokerIntegralLogDao.create(brokerIntegralLog);
    }
}
