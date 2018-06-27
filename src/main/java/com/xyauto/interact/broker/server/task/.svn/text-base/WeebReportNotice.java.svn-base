package com.xyauto.interact.broker.server.task;

import com.google.common.collect.Lists;
import com.xyauto.interact.broker.server.enums.PushMessage;
import com.xyauto.interact.broker.server.model.vo.Broker;
import com.xyauto.interact.broker.server.service.BrokerService;
import com.xyauto.interact.broker.server.service.cloud.ApiServiceFactory;
import com.xyauto.interact.broker.server.util.ILogger;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Configuration
@EnableScheduling
@Component
public class WeebReportNotice implements ILogger {

    @Autowired
    BrokerService brokerService;

    @Autowired
    ApiServiceFactory apiService;

    @Scheduled(cron = "0 0 8 ? * MON")
    public void process() throws IOException, InterruptedException {
        //获取经纪人总数
        int count = brokerService.getAllCount();
        int limit = 1000;
        int page = count / limit + (count % limit > 0 ? 1 : 0);
        for (int i = 1; i <= page; i++) {
            Thread.sleep(1000);
            process(i, limit);
        }
        this.info("周总结推送");
    }

    @Async("executor")
    private void process(int page, int limit) {
        this.info("周总结推送第"+page+"批次");
        List<Long> brokerIds = brokerService.getPagedBrokerIds(page, limit);
        List<String> aliases = Lists.newArrayList();
        for (long brokerId : brokerIds) {
            Broker broker = brokerService.get(brokerId);
            if (broker!=null && broker.getToken().isEmpty()==false) {
                aliases.add(broker.getToken());
            }
        }
        if (aliases.isEmpty() == false) {
            apiService.pushService().push(aliases, PushMessage.WeekReport.getValue(), new HashMap<String, String>() {
                {
                    put("url", PushMessage.WeekReport.getLink());
                }
            });
        }
    }
}
