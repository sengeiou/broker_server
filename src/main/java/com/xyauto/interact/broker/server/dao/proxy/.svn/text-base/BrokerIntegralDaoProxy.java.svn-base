package com.xyauto.interact.broker.server.dao.proxy;

import com.xyauto.interact.broker.server.dao.IBrokerIntegralDao;
import com.xyauto.interact.broker.server.model.vo.BrokerIntegral;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BrokerIntegralDaoProxy {

    @Autowired
    IBrokerIntegralDao dao;

    public int getBalance(long brokerId) {
        return dao.getBalance(brokerId);
    }

    public BrokerIntegral getInfoByBrokerId(long brokerId){
        return dao.getInfoByBrokerId(brokerId);
    }



    public int updateByParam(BrokerIntegral brokerIntegral){
        return dao.updateByParam(brokerIntegral);
    }

    public int initBrokerIntegral( BrokerIntegral brokerIntegral){
        return dao.initBrokerIntegral(brokerIntegral);
    }

}