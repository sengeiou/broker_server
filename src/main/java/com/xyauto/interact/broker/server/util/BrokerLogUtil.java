package com.xyauto.interact.broker.server.util;

import com.alibaba.fastjson.JSONObject;
import com.xyauto.interact.broker.server.model.po.BrokerLogEntity;
import com.xyauto.interact.broker.server.service.BrokerLogService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BrokerLogUtil {

    @Autowired
    BrokerLogService log;

    public void log(String type, long brokerId, long dealerId, long targetId, String message, Object extra) {
        BrokerLogEntity entity = new BrokerLogEntity(type, brokerId, dealerId, targetId, message, extra);
        log.log(entity);
    }

    public void log(String type, long brokerId, long dealerId,  long targetId, String message) {
        this.log(type, brokerId, dealerId, targetId, message, new JSONObject());
    }

    public void log(String type, long brokerId, long dealerId, long targetId) {
        this.log(type, brokerId, dealerId, targetId, StringUtils.EMPTY);
    }
    
    public void log(String type, long brokerId, long dealerId, String message) {
        this.log(type, brokerId, dealerId, 0, message);
    }
    
    

}