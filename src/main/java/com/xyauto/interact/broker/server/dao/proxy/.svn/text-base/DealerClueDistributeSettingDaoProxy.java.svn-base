package com.xyauto.interact.broker.server.dao.proxy;

import com.xyauto.interact.broker.server.dao.IDealerClueDistributeSettingDao;
import com.xyauto.interact.broker.server.model.vo.DealerClueDistributeSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class DealerClueDistributeSettingDaoProxy {

    @Autowired
    IDealerClueDistributeSettingDao dao;

    /**
     * 获取经销商自动分配规则设置
     * @param dealerId
     * @return 
     */
    public DealerClueDistributeSetting getDistribution(long dealerId) {
        return dao.getDistribution(dealerId);
    }

    /**
     * 修改经销商自动分配规则设置
     * @param dealerId
     * @param dealerDistribute
     * @param brokerDistribute
     * @param brokerIds 
     */
    public void updateDistribution(long dealerId, int dealerDistribute, int brokerDistribute, String brokerIds) {
        dao.updateDistribution(dealerId, dealerDistribute, brokerDistribute, brokerIds);
    }

    

}
