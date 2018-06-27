package com.xyauto.interact.broker.server.service;

import com.xyauto.interact.broker.server.dao.proxy.*;
import com.xyauto.interact.broker.server.model.vo.Broker;
import com.xyauto.interact.broker.server.model.vo.BrokerIntegral;
import com.xyauto.interact.broker.server.model.vo.BrokerIntegralLog;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BrokerIntegralService {

    @Autowired
    BrokerIntegralDaoProxy dao;

    @Autowired
    BrokerDaoProxy brokerDaoProxy;

    @Autowired
    BrokerIntegralLogService brokerIntegralLogService;

    public int getBalance(long brokerId) {
       return dao.getBalance(brokerId);
    }

    public BrokerIntegral getInfoByBrokerId(long brokerId){
        BrokerIntegral brokerIntegral = dao.getInfoByBrokerId(brokerId);
        if(brokerIntegral == null){
            brokerIntegral = this.initBrokerIntegral(brokerId);
        }
        return  brokerIntegral;
    }

    /**
     * 初始化新建
     * @param brokerId
     */
    public BrokerIntegral initBrokerIntegral(long brokerId){
        BrokerIntegral model = new BrokerIntegral();
        model.setBrokerId(brokerId);
        model.setBalance(Long.valueOf("0"));
        dao.initBrokerIntegral(model);
        return  model;
    }


    @Transactional
    public boolean addIntegral(long brokerId,long integral,String desc){

        BrokerIntegral model = new BrokerIntegral();
        BrokerIntegralLog integralLog = new BrokerIntegralLog();
        Broker broker = brokerDaoProxy.get(brokerId);
        model = this.getInfoByBrokerId(brokerId);

        //大于0加积分
        if(integral>0){
            model.setBalance(model.getBalance() + integral);
            integralLog.setType(Short.valueOf("1"));
        }else {
            if(model.getBalance() >= Math.abs(integral))
            {
                model.setBalance(model.getBalance() + integral);
                integralLog.setType(Short.valueOf("2"));
            }else {
                return false;
            }
        }
        integralLog.setBrokerId(brokerId);
        integralLog.setDesc(desc);
        integralLog.setDealerId(broker.getDealerId());
        integralLog.setIntegral(Math.abs(integral));
        int suc =  this.updateByParam(model);
        if(suc > 0) {
            brokerIntegralLogService.create(integralLog);
            return true;
        }
        return  false;


    }

    public int updateByParam(BrokerIntegral brokerIntegral){
        return dao.updateByParam(brokerIntegral);
    }


}