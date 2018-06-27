package com.xyauto.interact.broker.server.task;

import com.xyauto.interact.broker.server.model.vo.BrokerClue;
import com.xyauto.interact.broker.server.service.BrokerClueService;
import com.xyauto.interact.broker.server.service.es.clue.BrokerClueEsService;
import com.xyauto.interact.broker.server.util.ILogger;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class BrokerClueSyncEs implements ILogger {

    protected Logger logger = LoggerFactory.getLogger(BrokerClueSyncEs.class);
    private final static int Limit = 2000;

    @Autowired
    BrokerClueEsService brokerClueEsService;

    @Autowired
    BrokerClueService brokerClueService;

    //@Scheduled(cron = "0 05 21 * * ?")
    public void process() throws IOException, InterruptedException {
        brokerClueEsService.createIndex();
        int count = brokerClueService.getAllCount();
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
                // 查询问题
                List<BrokerClue> list = brokerClueService.getBatchBrokerList(page, Limit);
                try {
                    brokerClueEsService.add(list);
                } catch (IOException e) {
                    logger.warn("更新经纪人线索异常:"+e.getMessage());
                }
            });
            Thread.sleep(200);
            logger.info("更新经纪人线索信息，共有：{}页，已更新到第：{}页", pageCount, page);
        }
        fixedThreadPool.shutdown();
        while (true) {
            if (fixedThreadPool.isTerminated()) {
                this.info("批量添加经纪人线索结束！");
                break;
            }
        }
    }
}
