package com.xyauto.interact.broker.server.dao.proxy;

import com.xyauto.interact.broker.server.dao.IBrokerNoticeSettingDao;
import com.xyauto.interact.broker.server.model.vo.BrokerNoticeSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BrokerNoticeSettingDaoProxy {

    @Autowired
    IBrokerNoticeSettingDao iBrokerNoticeSettingDao;

    public BrokerNoticeSetting getSettingByBrokerId(long brokerId){
        return iBrokerNoticeSettingDao.getSettingByBrokerId(brokerId);
    }

    public Integer updateByParam(BrokerNoticeSetting brokerNoticeSetting){
        return iBrokerNoticeSettingDao.updateByParam(brokerNoticeSetting);
    }
}
