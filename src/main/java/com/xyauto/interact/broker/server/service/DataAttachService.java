package com.xyauto.interact.broker.server.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xyauto.interact.broker.server.enums.ResultCode;
import com.xyauto.interact.broker.server.exceptions.ResultException;
import com.xyauto.interact.broker.server.model.vo.Series;
import com.xyauto.interact.broker.server.service.cloud.ApiServiceFactory;
import com.xyauto.interact.broker.server.service.es.clue.BrokerClueEsService;
import com.xyauto.interact.broker.server.util.ILogger;
import com.xyauto.interact.broker.server.util.Result;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataAttachService implements ILogger {

    @Autowired
    private ApiServiceFactory apiService;

    @Autowired
    private SeriesService seriesService;

    @Autowired
    private BrokerClueEsService brokerClueEsService;

    /**
     * 获取六项购车指标
     *
     * @param customerId
     * @param dealerId
     * @return
     */
    //@Cacheable(value = "data_attach", key = "'data:attach:points:'.concat(#customerId.toString()).concat(':').concat(#dealerId.toString())")
    public Map<String, Double> getDataAttachPoints(long customerId, long dealerId) {
        Map<String, Double> data = Maps.newConcurrentMap();
        //获取数据指数
        try {
            Result ret = apiService.dataCenterService().queryScores(customerId, dealerId);
            if (ret==null || ret.getCode() != ResultCode.Success.getCode()) {
                return data;
            }
            List<JSONObject> list = JSONArray.parseArray(JSON.toJSONString(ret.getData()), JSONObject.class);
            double weightScore = 0;
            for (JSONObject item : list) {
                if (item.containsKey("targetId") == false) {
                    continue;
                }
                if (item.getIntValue("targetId") == 1 && item.containsKey("detailScore")) {
                    data.put("satisfaction", item.getDoubleValue("detailScore"));
                }
                if (item.getIntValue("targetId") == 2 && item.containsKey("detailScore")) {
                    data.put("confirm", item.getDoubleValue("detailScore"));
                }
                if (item.getIntValue("targetId") == 3 && item.containsKey("detailScore")) {
                    data.put("urgency", item.getDoubleValue("detailScore"));
                }
                if (item.getIntValue("targetId") == 4 && item.containsKey("detailScore")) {
                    data.put("freshness", item.getDoubleValue("detailScore"));
                }
                if (item.getIntValue("targetId") == 5 && item.containsKey("detailScore")) {
                    data.put("sensitive", item.getDoubleValue("detailScore"));
                }
                if (item.containsKey("detailScore")) {
                    weightScore = weightScore + item.getDouble("weightScore");
                    BigDecimal bd = new BigDecimal(weightScore);
                    bd = bd.setScale(1, 4);
                    weightScore = bd.doubleValue();
                }
            }
            data.put("carPurchasingIndex", weightScore);
        } catch (ResultException ex) {
            this.error(ex.getMessage());
        }
        return data;
    }

    /**
     * 获取关注车型列表
     *
     * @param customerId
     * @return
     */
    //@Cacheable(value = "data_attach", key = "'data:attach:follow:'.concat(#customerId.toString())")
    public List<JSONObject> getDataAttachFollowCars(long customerId) {
        List<JSONObject> seriesList = Lists.newArrayList();
        try {
            Result ret = apiService.dataCenterService().querySerialProportion(customerId);
            if (ret==null || ret.getCode() != ResultCode.Success.getCode()) {
                return seriesList;
            }
            List<JSONObject> list = JSONArray.parseArray(JSON.toJSONString(ret.getData()), JSONObject.class);
            List<Integer> flgList = Lists.newArrayList();
            for (JSONObject item : list) {
                int seriesId = item.getIntValue("serialId");
                if (flgList.contains(seriesId) == false) {
                    Series series = seriesService.getSeries(seriesId);
                    JSONObject json = new JSONObject();
                    json.put("name", series.getName());
                    json.put("series_id", seriesId);
                    json.put("image", series.getImage());
                    json.put("create_time", item.getLong("createTime"));
                    json.put("percent", item.getFloat("proportion"));
                    seriesList.add(json);
                    flgList.add(seriesId);
                }
            }
        } catch (ResultException ex) {
            this.error(ex.getMessage());
        }
        return seriesList;
    }

    /**
     * 获取购车标签
     *
     * @param customerId
     * @param mobile
     * @return
     */
    //@Cacheable(value = "data_attach", key = "'data:attach:tags:'.concat(#customerId.toString())")
    public String getDataAttachTags(long customerId, String mobile) {
        List<String> list = Lists.newArrayList();
        try {
            //近3个月线索分配到的经销商数
            long followCount = brokerClueEsService.getFollowDealerCount(mobile);
            list.add(String.format("关注%s家经销商", String.valueOf(followCount)));
        } catch (IOException e) {
            this.error(e.getMessage());
        }
        try {
            Result ret = apiService.clueService().getCoutCallFeedBackInfo(customerId);
            if (ret == null || ret.getCode() != ResultCode.Success.getCode()) {
                return StringUtils.join(list, ',');
            }
            if (ret.getData() instanceof String) {
                return StringUtils.join(list, ',');
            }
            JSONArray arr = JSON.parseArray(JSON.toJSONString(ret.getData()));
            for (Object obj : arr) {
                JSONObject json = (JSONObject) obj;
                if (json.containsKey("targetmsg")) {
                    list.add(json.getString("targetmsg"));
                }
                if (json.containsKey("loansmsg")) {
                    list.add(json.getString("loansmsg"));
                }
                if (json.containsKey("vehicleHavesmsg")) {
                    list.add(json.getString("vehicleHavesmsg"));
                }
                if (json.containsKey("toshopmsg")) {
                    list.add(json.getString("toshopmsg"));
                }
            }
        } catch (ResultException ex) {
            this.error(ex.getMessage());
        }
        return StringUtils.join(list, ',');
    }

    /**
     * 获取购车预算
     *
     * @param customerId
     * @return
     */
    public String getPurchaseBudget(long customerId) {
        String budget = StringUtils.EMPTY;
        try {
            Result ret = apiService.dataCenterService().queryPurchaseBudget(customerId);
            if (ret.getCode() == ResultCode.Success.getCode() && ret.getData() != null) {
                JSONObject json = JSON.parseObject(JSON.toJSONString(ret.getData()), JSONObject.class);
                List<String> data = Lists.newArrayList();
                data.add(json.getString("min_budget"));
                data.add(json.getString("max_budget"));
                budget = StringUtils.join(data, "~");
            }
        } catch (ResultException ex) {
        }
        return budget;
    }
}
