package com.xyauto.interact.broker.server.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.primitives.Longs;
import com.mysql.jdbc.StringUtils;
import com.xyauto.interact.broker.server.dao.proxy.DealerClueDistributeSettingDaoProxy;
import com.xyauto.interact.broker.server.model.vo.Broker;
import com.xyauto.interact.broker.server.model.vo.DealerClueDistributeSetting;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DealerClueDistributeSettingService {

    @Autowired
    DealerClueDistributeSettingDaoProxy dao;

    @Autowired
    BrokerService brokerService;

    /**
     * 获取经销商自动分配规则设置
     *
     * @param dealerId
     * @return
     */
    public DealerClueDistributeSetting getDistribution(long dealerId) {
        DealerClueDistributeSetting info = dao.getDistribution(dealerId);
        boolean isInit = false;
        //获取经纪人
        if (info.getBrokerDistribute() == -1) {
            //未设置默认处理为全部打开
            info.setDealerDistribute(1);
            info.setBrokerDistribute(1);
            info.setBrokerIds("");
            isInit = true;
        }
        List<String> brokerIdsSetting = StringUtils.split(info.getBrokerIds(), ",", true);
        List<Object> brokerList = Lists.newArrayList();

        List<Long> brokerIds = brokerService.getDealerBrokerIds(dealerId);
        for (long brokerId : brokerIds) {
            Broker broker = brokerService.getTiny(brokerId);
            if (broker != null) {
                JSONObject obj = JSONObject.parseObject(JSON.toJSONString(broker));
                if (isInit == true) {
                    obj.put("distribute", 1);
                } else {
                    if (brokerIdsSetting.contains(String.valueOf(brokerId))) {
                        obj.put("distribute", 1);
                    } else {
                        obj.put("distribute", 0);
                    }
                }
                brokerList.add(obj);
            }
        }
        info.setBrokers(brokerList);
        return info;
    }

    /**
     * 修改经销商自动分配规则设置
     *
     * @param dealerId
     * @param dealerDistribute
     * @param brokerDistribute
     * @param brokerIds
     */
    public void updateDistribution(long dealerId, int dealerDistribute, int brokerDistribute, List<Long> brokerIds) {
        dao.updateDistribution(dealerId, dealerDistribute, brokerDistribute, Longs.join(",", Longs.toArray(brokerIds)));
    }

}
