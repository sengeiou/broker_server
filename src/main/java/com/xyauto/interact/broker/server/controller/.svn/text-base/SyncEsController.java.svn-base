package com.xyauto.interact.broker.server.controller;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.collect.Lists;
import com.xyauto.interact.broker.server.model.vo.Broker;
import com.xyauto.interact.broker.server.model.vo.BrokerClue;
import com.xyauto.interact.broker.server.model.vo.BrokerCustomer;
import com.xyauto.interact.broker.server.service.BrokerClueService;
import com.xyauto.interact.broker.server.service.BrokerCustomerService;
import com.xyauto.interact.broker.server.service.BrokerService;
import com.xyauto.interact.broker.server.service.es.broker.BrokerEsService;
import com.xyauto.interact.broker.server.service.es.clue.BrokerClueEsService;
import com.xyauto.interact.broker.server.service.es.customer.BrokerCustomerEsService;
import com.xyauto.interact.broker.server.util.ILogger;

@Controller
public class SyncEsController extends BaseController implements ILogger {

    private final static int Limit = 2000;

    @Autowired
    BrokerClueEsService brokerClueEsService;

    @Autowired
    BrokerClueService brokerClueService;

    @Autowired
    BrokerCustomerEsService brokerCustomerEsService;

    @Autowired
    BrokerCustomerService brokerCustomerService;

    @Autowired
    BrokerEsService brokerEsService;

    @Autowired
    BrokerService brokerService;
    
    static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(200);

    @RequestMapping("sync/broker")
    public void syncBroker() throws IOException, InterruptedException {
        brokerEsService.createIndex();
        int count = brokerService.getAllCount();
        int pageCount = 0;
        if (count % Limit == 0) {
            pageCount = (count / Limit);
        } else {
            pageCount = (count / Limit) + 1;
        }
        
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
            this.info("更新经纪人信息，共有：" + pageCount + "页，已更新到第：" + page + "页");
        }
    }

    @RequestMapping("sync/brokerClue")
    public void syncBrokerClue() throws IOException, InterruptedException {
        brokerClueEsService.createIndex();
        int count = brokerClueService.getAllCount();
        int pageCount = 0;
        if (count % Limit == 0) {
            pageCount = (count / Limit);
        } else {
            pageCount = (count / Limit) + 1;
        }
        for (int i = 1; i <= pageCount; i++) {
            final int page = i;
            fixedThreadPool.execute(() -> {
                // 查询问题
                List<BrokerClue> list = brokerClueService.getBatchBrokerList(page, Limit);
                try {
                    brokerClueEsService.add(list);
                } catch (IOException e) {
                    this.warn("更新经纪人线索异常:" + e.getMessage());
                }
            });
            Thread.sleep(200);
            this.info("更新经纪人线索信息，共有：" + pageCount + "页，已更新到第：" + page + "页");
        }
    }

    @RequestMapping("sync/brokerCustmer")
    public void syncBrokerCustmer() throws IOException, InterruptedException {
        brokerCustomerEsService.createIndex();
        int count = brokerCustomerService.getAllCount();
        int pageCount = 0;
        if (count % Limit == 0) {
            pageCount = (count / Limit);
        } else {
            pageCount = (count / Limit) + 1;
        }
        for (int i = 1; i <= pageCount; i++) {
            final int page = i;
            fixedThreadPool.execute(() -> {
                try {
                    // 查询客户
                    List<Long> ids = brokerCustomerService.getBatchBrokerList(page, Limit);
                    List<BrokerCustomer> list = Lists.newArrayList();
                    for (long id : ids) {
                        BrokerCustomer customer = brokerCustomerService.get(id);
                        if (customer != null) {
                            list.add(customer);
                        }
                    }
                    brokerCustomerEsService.add(list);
                } catch (IOException e) {
                    this.error("更新经纪人客户异常:" + e.getMessage());
                }
            });
            Thread.sleep(200);
            this.info("更新经纪人客户信息，共有：" + pageCount + "页，已更新到第：" + page + "页");
        }
    }
}
