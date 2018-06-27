package com.xyauto.interact.broker.server.dao.proxy;

import com.xyauto.interact.broker.server.dao.IDealerSettingDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class DealerSettingDaoProxy {

    @Autowired
    IDealerSettingDao dealerSettingDao;

    /**
     * 获取经销商意向等级
     * @param dealerId
     * @return 
     */
    public List<Map<String, Object>> getCustomerIntentionLevel(long dealerId) {
        return dealerSettingDao.getCustomerIntentionLevel(dealerId);
    }

    /**
     * 修改经销商意向等级
     * @param dealerId
     * @param type
     * @param days 
     */
    public void setCustomerIntentionLevel(long dealerId, int type, int days) {
        dealerSettingDao.setCustomerIntentionLevel(dealerId, type, days);
    }

    /**
     * 重置经销商意向等级
     * @param dealerId 
     */
    public void resetCustomerIntentionLevel(long dealerId) {
        dealerSettingDao.resetCustomerIntentionLevel(dealerId);
    }

}
