package com.xyauto.interact.broker.server.service;

import com.xyauto.interact.broker.server.dao.proxy.BrokerNoticeSettingDaoProxy;
import com.xyauto.interact.broker.server.model.vo.BrokerNoticeSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BrokerNoticeSettingService {
    @Autowired
    BrokerNoticeSettingDaoProxy brokerNoticeSettingDaoProxy;


    public BrokerNoticeSetting getSettingByBrokerId(long brokerId){
        return brokerNoticeSettingDaoProxy.getSettingByBrokerId(brokerId);
    }

    public Integer updateByParam(BrokerNoticeSetting brokerNoticeSetting){
        return brokerNoticeSettingDaoProxy.updateByParam(brokerNoticeSetting);
    }


}
