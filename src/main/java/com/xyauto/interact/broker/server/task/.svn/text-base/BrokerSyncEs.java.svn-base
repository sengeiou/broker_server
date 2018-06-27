package com.xyauto.interact.broker.server.task;

import com.xyauto.interact.broker.server.model.vo.Broker;
import com.xyauto.interact.broker.server.service.BrokerService;
import com.xyauto.interact.broker.server.service.es.broker.BrokerEsService;
import com.xyauto.interact.broker.server.util.ILogger;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

@Configuration
@EnableScheduling
@Component
public class BrokerSyncEs implements ILogger {

    private final static int Limit = 2000;

    @Autowired
    BrokerEsService brokerEsService;

    @Autowired
    BrokerService brokerService;

    protected Logger logger = LoggerFactory.getLogger(BrokerCustomerSyncEs.class);

    //@Scheduled(cron = "0 20 21 * * ?")
    public void process() throws IOException, InterruptedException {
        brokerEsService.createIndex();
        int count = brokerService.getAllCount();
        int pageCount = 0;
        if (count % Limit == 0) {
            pageCount = (count / Limit);
        } else {
            pageCount = (count / Limit) + 1;
        }
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(200);
        for (int i = 1; i <= pageCount; i++) {
            final int page = i;
            fixedThreadPool.execute(() -> {
                try {
                    // 查询客户
                    List<Broker> list = brokerService.getBatchBrokerList(page, Limit);
                    brokerEsService.add(list);
                } catch (IOException e) {
                    this.error("更新经纪人异常:" + e.getMessage());
                }
            });
            Thread.sleep(200);
            this.info("更新经纪人信息，共有："+pageCount+"页，已更新到第："+page+"页");
        }
        fixedThreadPool.shutdown();
        while (true) {
            if (fixedThreadPool.isTerminated()) {
                this.info("批量添加经纪人结束！");
                break;
            }
        }
    }

}
