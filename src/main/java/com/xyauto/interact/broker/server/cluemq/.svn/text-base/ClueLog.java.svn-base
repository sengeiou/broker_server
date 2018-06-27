package com.xyauto.interact.broker.server.cluemq;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.xyauto.interact.broker.server.util.ILogger;
import com.xyauto.interact.broker.server.util.SnowflakeIdWorker;

@Component
public class ClueLog implements ILogger {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Resource(name = "redisTemplate")
    ValueOperations<String, Object> redis;
    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

    public void log(ClueMessageEntity message) {
        long id = snowflakeIdWorker.nextId();
        message.setIncrementId(id);
        String parameters = JSON.toJSONString(message.getContainterParameters());
        message.setParameters(parameters);
        String content = JSON.toJSONString(message);
        try {
            kafkaTemplate.send("business_opportunity_operation_topic", "opportunity_xingyuan_distribution", content);
        } catch (Exception e) {
            this.error("发送kafka失败");
        }
    }
}
