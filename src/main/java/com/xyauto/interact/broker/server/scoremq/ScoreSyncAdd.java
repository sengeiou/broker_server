package com.xyauto.interact.broker.server.scoremq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xyauto.interact.broker.server.model.vo.Broker;
import com.xyauto.interact.broker.server.service.BrokerIntegralService;
import com.xyauto.interact.broker.server.service.BrokerService;
import com.xyauto.interact.broker.server.util.ILogger;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * 积分同步导入处理
 */
@Component
public class ScoreSyncAdd implements ILogger { 
    
    @Autowired
    BrokerIntegralService brokerIntegralService;
    
    @Autowired
    BrokerService brokerService;
    
    @KafkaListener(topics = {"broker_task_point_topic","interact_task_add_point_topic"})
    public void listen(ConsumerRecord<?, ?> record) {
        JSONObject json = JSON.parseObject(record.value().toString(), JSONObject.class);
        if (json.containsKey("broker_id") && json.containsKey("point")) {
            long brokerId = json.getLong("broker_id");
            Broker broker = brokerService.get(brokerId);
            if (broker==null) {
                this.info(brokerId+"新增积分失败，用户不存在");
                return;
            }
            int point = json.getIntValue("point");
            String description = json.getString("description");
            this.info(brokerId+"新增积分"+point);
            brokerIntegralService.addIntegral(brokerId, point, description);
        }
    }
}
