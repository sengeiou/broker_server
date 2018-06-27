package com.xyauto.interact.broker.server.task;

import com.xyauto.interact.broker.server.enums.PushMessage;
import com.xyauto.interact.broker.server.service.BrokerCustomerService;
import com.xyauto.interact.broker.server.service.cloud.ApiServiceFactory;
import com.xyauto.interact.broker.server.util.ILogger;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Configuration
@EnableScheduling
@Component
public class CustomerNotice implements ILogger {

    @Autowired
    private BrokerCustomerService brokerCustomerService;

    @Autowired
    private ApiServiceFactory apiService;

    @Scheduled(cron = "0 0/1 * * * ?")
    public void process() throws IOException, InterruptedException {
        List<Map<String, Object>> data = brokerCustomerService.getAwaitContactList();
        for (Map<String, Object> item : data) {
            process(item);
        }
        this.info("客户跟进时间提醒");
    }

    @Async("executor")
    private void process(Map<String, Object> data) throws IOException {
        String alias = String.valueOf(data.get("token"));
        String alert = String.format(PushMessage.CustomerNotice.getValue(), String.valueOf(data.get("username")));
        apiService.pushService().push(alias, alert, new HashMap<String, String>() {
            {
                put("url", PushMessage.CustomerNotice.getLink());
            }
        });
    }
}
