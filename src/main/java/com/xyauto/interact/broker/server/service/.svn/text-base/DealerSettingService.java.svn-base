package com.xyauto.interact.broker.server.service;

import com.xyauto.interact.broker.server.dao.proxy.DealerSettingDaoProxy;
import com.xyauto.interact.broker.server.model.po.DealerCustomerIntention;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DealerSettingService {

    @Autowired
    DealerSettingDaoProxy dealerSettingDaoProxy;

    /**
     * 获取经销商意向等级
     * @param dealerId
     * @return 
     */
    public List<Map<String, Object>> getCustomerIntentionLevel(long dealerId) {
        return dealerSettingDaoProxy.getCustomerIntentionLevel(dealerId);
    }

    /**
     * 修改经销商意向等级
     * @param dealerId
     * @param list 
     */
    public void setCustomerIntentionLevel(long dealerId, List<DealerCustomerIntention> list) {
        list.forEach(item->{
            dealerSettingDaoProxy.setCustomerIntentionLevel(dealerId, item.getType(), item.getDays());
        });
    }

    /**
     * 重置经销商意向等级
     * @param dealerId 
     */
    public void resetCustomerIntentionLevel(long dealerId) {
        dealerSettingDaoProxy.resetCustomerIntentionLevel(dealerId);
    }
}
