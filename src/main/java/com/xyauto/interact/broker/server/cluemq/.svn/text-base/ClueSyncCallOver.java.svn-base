package com.xyauto.interact.broker.server.cluemq;

import com.xyauto.interact.broker.server.util.ILogger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 线索外呼完成处理
 * @author joe
 */
@Component
public class ClueSyncCallOver implements ILogger {  
    
    @RabbitListener(queues = "qcdq-out-call-over-queue")
    public void process(String message) {
        this.info("Receiver 延时: " + message);
    }
    
}