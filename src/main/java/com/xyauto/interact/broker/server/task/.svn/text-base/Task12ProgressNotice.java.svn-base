package com.xyauto.interact.broker.server.task;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.primitives.Longs;
import com.xyauto.interact.broker.server.enums.PushMessage;
import com.xyauto.interact.broker.server.exceptions.ResultException;
import com.xyauto.interact.broker.server.model.vo.Broker;
import com.xyauto.interact.broker.server.service.BrokerService;
import com.xyauto.interact.broker.server.service.cloud.ApiServiceFactory;
import com.xyauto.interact.broker.server.util.ILogger;
import com.xyauto.interact.broker.server.util.Result;
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
public class Task12ProgressNotice implements ILogger {

    @Autowired
    BrokerService brokerService;

    @Autowired
    ApiServiceFactory apiService;

    @Scheduled(cron = "0 0 12 0/1 * ?")
    public void process() throws IOException, InterruptedException {
        //获取经纪人总数
        int count = brokerService.getAllCount();
        int limit = 1000;
        int page = count / limit + (count % limit > 0 ? 1 : 0);
        for (int i = 1; i <= page; i++) {
            Thread.sleep(1000);
            process(i, limit);
        }
        this.info("任务完成度12点推送");
    }

    @Async("executor")
    private void process(int page, int limit) {
        try {
            List<Long> brokerIds = brokerService.getPagedBrokerIds(page, limit);
            Result ret = apiService.missionService().getBatchTaskProgress(Longs.join(",", Longs.toArray(brokerIds)));
            List<JSONObject> list = (List<JSONObject>) ret.getData();
            List<String> aliases = Lists.newArrayList();
            for (JSONObject json : list) {
                Broker broker = brokerService.get(json.getLong("brokerId"));
                float progress = json.getIntValue("progress") * 100;
                if (progress >= 40 || progress < 80) {
                    if (broker != null && broker.getToken().isEmpty() == false) {
                        aliases.add(broker.getToken());
                    }
                }
            }
            if (aliases.isEmpty() == false) {
                apiService.pushService().push(aliases, PushMessage.Task12ProgressNotice.getValue(), new HashMap<String, String>() {
                    {
                        put("url", PushMessage.Task12ProgressNotice.getLink());
                    }
                });
            }
        } catch (ResultException ex) {
            this.error("获取任务完成度失败:"+ex.getMessage());
        }
    }
}
