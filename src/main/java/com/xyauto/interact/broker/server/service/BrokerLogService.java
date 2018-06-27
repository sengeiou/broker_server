package com.xyauto.interact.broker.server.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.primitives.Longs;
import com.xyauto.interact.broker.server.enums.BrokerLogEnum;
import com.xyauto.interact.broker.server.enums.CustomerLevelEnum;
import com.xyauto.interact.broker.server.enums.CustomerStepEnum;
import com.xyauto.interact.broker.server.enums.LogDesc;
import com.xyauto.interact.broker.server.exceptions.ResultException;
import com.xyauto.interact.broker.server.model.po.BrokerLogEntity;
import com.xyauto.interact.broker.server.model.vo.BrokerCustomer;
import com.xyauto.interact.broker.server.model.vo.Car;
import com.xyauto.interact.broker.server.service.cloud.ApiServiceFactory;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.xyauto.interact.broker.server.util.BrokerLogUtil;
import com.xyauto.interact.broker.server.util.ILogger;
import com.xyauto.interact.broker.server.util.ListUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class BrokerLogService implements ILogger {

    @Autowired
    private KafkaTemplate log;

    @Autowired
    private ApiServiceFactory apiService;

    @Autowired
    @Lazy
    private BrokerClueService brokerClueService;

    @Autowired
    @Lazy
    private BrokerCustomerService brokerCustomerService;

    public void log(BrokerLogEntity entity) {
        log.send("interact_broker_operation_topic", entity.getType(), JSON.toJSONString(entity));
    }

    public void log(long brokerId, long dealerId, long targetId, BrokerLogEnum logType, Object extension, Object... replacement) {
        BrokerLogEntity entity = new BrokerLogEntity();
        entity.setBrokerId(brokerId);
        entity.setDealerId(dealerId);
        entity.setTargetId(targetId);
        entity.setType(logType.getName());

        if (replacement.length == 0) {
            entity.setMessage(logType.getDesc());
        } else {
            entity.setMessage(String.format(logType.getDesc(), replacement));
        }
        entity.setExtra(extension);
        this.log(entity);
    }

    public void log(long brokerId, long dealerId, long targetId, BrokerLogEnum logType, Object extension) {
        this.log(brokerId, dealerId, targetId, logType, extension, new Object[]{});
    }

    public void log(long brokerId, long dealerId, BrokerLogEnum logType, Object extension) {
        this.log(brokerId, dealerId, 0L, logType, extension, new Object[]{});
    }

    public void log(long brokerId, long dealerId, BrokerLogEnum logType) {
        this.log(brokerId, dealerId, 0L, logType, new JSONObject(), new Object[]{});
    }

    public void log(long brokerId, long dealerId, BrokerLogEnum logType, Object... replacement) {
        this.log(brokerId, dealerId, 0L, logType, new JSONObject(), replacement);
    }

    public Object getLogs(int type, Map<String, Object> params) throws ResultException {
        Object data = null;
        switch (type) {
            case 1:
                data = this.getXyhLogs(params);
                break;
            case 2:
                data = this.getCustomerLogs(params);
                break;
            case 3:
                data = this.getDateBrokerLogs(params);
                break;
            case 4:
                data = this.getDurationBrokerLogs(params);
                break;
            case 5:
                data = this.getToDoLogs(params);
                break;
            case 6:
                data = this.getBrokerLogsSum(params);
                break;
            case 7:
                data = this.getBrokerLogsList(params);
                break;
            case 8:
                data = this.getBrokerLogsSum1(params);
                break;
            case 9:
                data = this.getBrokerCustomerLogsSum(params);
                break;
            case 10:
                data = this.getBrokerClueStatisticsLogs(params);
                break;
            case 99:
                data = this.getDurationLogs(params);
                break;
        }
        return data;
    }

    /**
     * 获取日志(日新增客户，日新增线索，日线索处理率，日线索覆盖车型数，日线索覆盖经销商数)
     *
     * @return
     */
    private Object getXyhLogs(Map<String, Object> params) throws ResultException {
        if (params.containsKey("dealerIds") == false) {
            return Maps.newConcurrentMap();
        }
        List<Long> dealerIds = (List<Long>) params.get("dealerIds");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String date = df.format(new Date());
        if (params.containsKey("date")) {
            date = params.get("date").toString();
        }
        List<String> ops = Lists.newArrayList();
        ops.add("broker_clue_add");
        ops.add("broker_clue_handle");
        ops.add("broker_customer_add");
        //今日线索覆盖车型数
        ops.add("broker_clue_add$broker_clue_series_count@extra::jsonb->'series_id'#extra::jsonb%%'series_id'");
        //今日线索覆盖经销商数
        ops.add("broker_clue_add$broker_clue_dealer_count@dealer_id");
        //线索处理率
        ops.add("broker_clue_handel_rate");
        Map<String, Object> data = apiService.brokerLogService().pointSum(date, Longs.join(",", Longs.toArray(dealerIds)), "", StringUtils.join(ops, ','));
        JSONObject json = (JSONObject) JSON.toJSON(data);
        if (json.getInteger("broker_clue_add") != 0) {
            json.put("broker_clue_handle_rate", json.getInteger("broker_clue_handle") / json.getInteger("broker_clue_add"));
        }
        return data;
    }

    /**
     * 获取经纪人客户操作记录
     *
     * @param params
     * @return
     */
    private Object getCustomerLogs(Map<String, Object> params) throws ResultException {
        if (params.containsKey("dealerId") == false) {
            return null;
        }
        if (params.containsKey("targetId") == false) {
            return null;
        }
        long dealerId = Long.valueOf(params.get("dealerId").toString());
        long targetId = Long.valueOf(params.get("targetId").toString());
        long brokerClueId = 0;
        BrokerCustomer brokerCustomer = brokerCustomerService.get(targetId);
        if (brokerCustomer != null && brokerCustomer.getBrokerClueId() > 0) {
            brokerClueId = brokerCustomer.getBrokerClueId();
        }
        List<Long> targets = Lists.newArrayList(targetId, brokerClueId);
        targets = ListUtil.clearZero(targets);
        //todo:缺失经理分配线索  缺失客户转移
        String sql = "select create_time, broker_id, target_id, broker_logs.type, message, extra"
                + " from broker_logs join broker_log_type on broker_logs.type = broker_log_type.id"
                + " where target_id in (" + StringUtils.join(targets, ",") + ") and dealer_id=" + dealerId
                + " and broker_logs.type in (2,3,5,6,7,18,23,25,22,26,35,39,42)"
                + " order by create_time desc";
        return apiService.brokerLogService().exec(sql);
    }

    /**
     * 获取经纪人日统计
     *
     * @param params
     * @return
     */
    private Object getDateBrokerLogs(Map<String, Object> params) throws ResultException {
        String date = params.get("date").toString();
        List<Long> dealerIds = Lists.newArrayList();
        List<Long> brokerIds = Lists.newArrayList();
        if (params.containsKey("dealerIds")) {
            dealerIds = (List<Long>) params.get("dealerIds");
        }
        if (params.containsKey("brokerIds")) {
            brokerIds = (List<Long>) params.get("brokerIds");
        }
        List<String> ops = Lists.newArrayList();
        //日新增客户
        ops.add("broker_customer_add");
        //日到店客户
        ops.add("broker_customer_arrival@target_id");
        //日成交客户
        ops.add("broker_customer_deal@target_id");
        //日战败客户
        ops.add("broker_customer_lose@target_id");
        //日电话跟进客户
        ops.add("broker_customer_contact_phone@target_id");
        //日短信跟进客户
        ops.add("broker_customer_contact_sms@target_id");
        //日跟进客户
        ops.add("broker_customer_contact@target_id");
        //日更改意向阶段客户
        ops.add("broker_customer_step_update@target_id");
        //日微店浏览量
        ops.add("broker_website_view");
        //日头条浏览量
        ops.add("broker_materiel_view");
        //日车型浏览量
        ops.add("broker_car_recommend_car_view");
        //日活动浏览量cheg
        ops.add("broker_activity_view");
        //日店铺线索数
        ops.add("broker_clue_dealer_add");
        //日个人线索数
        ops.add("broker_clue_person_add");
        //总线索数
        ops.add("broker_clue_add");
        //线索处理数
        ops.add("broker_clue_handle");
        //线索覆盖车型数
        ops.add("broker_clue_add$broker_clue_series_count@extra::jsonb->'series_id'");
        //线索覆盖经销商数
        ops.add("broker_clue_add$broker_clue_dealer_count@dealer_id");
        //日总响应客户时间
        ops.add("broker_customer_contact_interval");
        //日总响应客户次数
        ops.add("broker_customer_contact$broker_customer_contact_times");
        Map<String, Object> data = apiService.brokerLogService().pointSum(date, Longs.join(",", Longs.toArray(dealerIds)), Longs.join(",", Longs.toArray(brokerIds)), StringUtils.join(ops, ','));
        return data;
    }

    /**
     * 获取经纪人周期统计
     *
     * @param params
     * @return
     */
    private Object getDurationBrokerLogs(Map<String, Object> params) {
        return null;
    }

    /**
     * 获取行圆慧代办事项统计
     *
     * @param params
     * @return
     */
    private Object getToDoLogs(Map<String, Object> params) {
        long dealerId = 0;
        long brokerId = 0;
        if (params.containsKey("dealerId")) {
            dealerId = Long.valueOf(params.get("dealerId").toString());
        }
        if (params.containsKey("brokerId")) {
            brokerId = Long.valueOf(params.get("brokerId").toString());
        }
        Map<String, Object> data = Maps.newConcurrentMap();
        //待处理线索
        int clueUntreatedCount = brokerClueService.getNotHandleCount(dealerId, brokerId);
        //待建卡线索
        int clueNotToCustomerCount = brokerClueService.getNotToCustomerCount(dealerId, brokerId);
        //待分配线索
        int clueNotDistributeCount = brokerClueService.getNotDistributeCount(dealerId);
        //待跟进客户
        int customerTimeoutCount = brokerCustomerService.getTimeoutCustomerCount(dealerId, brokerId);
        data.put("clue_untreated_count", clueUntreatedCount);
        data.put("clue_uncustomer_count", clueNotToCustomerCount);
        data.put("clue_undistribute_count", clueNotDistributeCount);
        data.put("customer_timeout_count", customerTimeoutCount);
        return data;
    }

    /**
     * 获取行圆慧多店经纪人周期统计和
     *
     * @param params
     * @return
     * @throws ResultException
     */
    private Object getBrokerLogsSum(Map<String, Object> params) throws ResultException {
        if (params.containsKey("begin") == false) {
            return null;
        }
        if (params.containsKey("end") == false) {
            return null;
        }
        List<String> ops = Lists.newArrayList();
        String begin = params.get("begin").toString();
        String end = params.get("end").toString();
        List<Long> dealerIds = Lists.newArrayList();
        List<Long> brokerIds = Lists.newArrayList();
        if (params.containsKey("dealerIds")) {
            dealerIds = (List<Long>) params.get("dealerIds");
        }
        if (params.containsKey("brokerIds")) {
            brokerIds = (List<Long>) params.get("brokerIds");
        }
        //新建客户总数
        ops.add("broker_customer_add");
        //到店客户总数
        ops.add("broker_customer_arrival@target_id");
        //成交客户总数
        ops.add("broker_customer_deal@target_id");
        //新建客户经纪人总数
        ops.add("broker_customer_add$broker_counts@broker_id");
        return apiService.brokerLogService().durationSum(begin, end, Longs.join(",", Longs.toArray(dealerIds)), Longs.join(",", Longs.toArray(brokerIds)), StringUtils.join(ops, ','));

    }

    /**
     * 获取行圆慧多店经纪人周期统计列表
     *
     * @param params
     * @return
     * @throws ResultException
     */
    private Object getBrokerLogsList(Map<String, Object> params) throws ResultException {
        if (params.containsKey("begin") == false) {
            return null;
        }
        if (params.containsKey("end") == false) {
            return null;
        }
        List<String> ops = Lists.newArrayList();
        String begin = params.get("begin").toString();
        String end = params.get("end").toString();
        List<Long> dealerIds = Lists.newArrayList();
        List<Long> brokerIds = Lists.newArrayList();
        if (params.containsKey("dealerIds")) {
            dealerIds = (List<Long>) params.get("dealerIds");
        }
        if (params.containsKey("brokerIds")) {
            brokerIds = (List<Long>) params.get("brokerIds");
        }
        //新建客户数
        ops.add("broker_customer_add");
        //到店客户数
        ops.add("broker_customer_arrival@target_id");
        //成交客户数
        ops.add("broker_customer_deal@target_id");
        //战败客户数
        ops.add("broker_customer_lose@target_id");
        //新增线索数
        ops.add("broker_clue_add");
        //处理线索数
        ops.add("broker_clue_handle");
        //联系客户数
        ops.add("broker_customer_contact@target_id");
        //线索覆盖经销商数
        ops.add("broker_clue_add$broker_clue_dealer_count@dealer_id");
        return apiService.brokerLogService().duration(begin, end, Longs.join(",", Longs.toArray(dealerIds)), Longs.join(",", Longs.toArray(brokerIds)), StringUtils.join(ops, ','));

    }

    /**
     * 获取行圆慧经纪人日统计(客户和线索)
     *
     * @param params
     * @return
     */
    private Object getBrokerLogsSum1(Map<String, Object> params) throws ResultException {
        if (params.containsKey("begin") == false) {
            return null;
        }
        if (params.containsKey("end") == false) {
            return null;
        }
        String begin = params.get("begin").toString();
        String end = params.get("end").toString();
        List<Long> dealerIds = Lists.newArrayList();
        List<Long> brokerIds = Lists.newArrayList();
        if (params.containsKey("dealerIds")) {
            dealerIds = (List<Long>) params.get("dealerIds");
        }
        if (params.containsKey("brokerIds")) {
            brokerIds = (List<Long>) params.get("brokerIds");
        }
        List<String> ops = Lists.newArrayList();
        //日新增客户
        ops.add("broker_customer_add");
        //日到店客户
        ops.add("broker_customer_arrival@target_id");
        //日成交客户
        ops.add("broker_customer_deal@target_id");
        //日战败客户
        ops.add("broker_customer_lose@target_id");
        //日电话跟进客户
        ops.add("broker_customer_contact_phone@target_id");
        //日短信跟进客户
        ops.add("broker_customer_contact_sms@target_id");
        //日跟进客户
        ops.add("broker_customer_contact@target_id");
        //日更改意向阶段客户
        ops.add("broker_customer_step_update@target_id");
        //日微店浏览量
        ops.add("broker_website_view");
        //日头条浏览量
        ops.add("broker_materiel_view");
        //日车型浏览量
        ops.add("broker_car_recommend_car_view");
        //日活动浏览量cheg
        ops.add("broker_activity_view");
        //日店铺线索数
        ops.add("broker_clue_dealer_add");
        //日个人线索数
        ops.add("broker_clue_person_add");
        //总线索数
        ops.add("broker_clue_add");
        //线索处理数
        ops.add("broker_clue_handle");
        //线索覆盖车型数
        ops.add("broker_clue_add$broker_clue_series_count@extra::jsonb->>'series_id'");
        //线索覆盖经销商数
        ops.add("broker_clue_add$broker_clue_dealer_count@dealer_id");
        //日总响应客户时间
        ops.add("broker_customer_contact_interval");
        //日总响应客户次数
        ops.add("broker_customer_contact$broker_customer_contact_times");
        Map<String, Object> data = apiService.brokerLogService().durationSum(begin, end, Longs.join(",", Longs.toArray(dealerIds)), Longs.join(",", Longs.toArray(brokerIds)), StringUtils.join(ops, ','));
        return data;
    }

    /**
     * 行圆慧获取经纪人客户周期统计和(数据中心)
     *
     * @param params
     * @return
     */
    private Object getBrokerCustomerLogsSum(Map<String, Object> params) throws ResultException {
        if (params.containsKey("begin") == false) {
            return null;
        }
        if (params.containsKey("end") == false) {
            return null;
        }
        String begin = params.get("begin").toString();
        String end = params.get("end").toString();
        List<Long> dealerIds = Lists.newArrayList();
        List<Long> brokerIds = Lists.newArrayList();
        if (params.containsKey("dealerIds")) {
            dealerIds = (List<Long>) params.get("dealerIds");
        }
        if (params.containsKey("brokerIds")) {
            brokerIds = (List<Long>) params.get("brokerIds");
        }
        List<String> ops = Lists.newArrayList();
        //新增客户
        ops.add("broker_customer_add");
        //成交客户
        ops.add("broker_customer_deal");
        //待跟进客户
        ops.add("broker_customer_await");
        //到店客户
        ops.add("broker_customer_arrival");
        //战败客户
        ops.add("broker_customer_lose");
        //无效客户
        ops.add("broker_customer_cancel");
        Map<String, Object> data = apiService.brokerLogService().durationSum(begin, end, Longs.join(",", Longs.toArray(dealerIds)), Longs.join(",", Longs.toArray(brokerIds)), StringUtils.join(ops, ','));
        return data;
    }

    /**
     * 行圆慧获取经纪人线索周期综合统计数据(数据中心)
     *
     * @param params
     * @return
     */
    private Object getBrokerClueStatisticsLogs(Map<String, Object> params) throws ResultException {
        if (params.containsKey("begin") == false) {
            return null;
        }
        if (params.containsKey("end") == false) {
            return null;
        }
        String begin = params.get("begin").toString();
        String end = params.get("end").toString();
        List<Long> dealerIds = Lists.newArrayList();
        List<Long> brokerIds = Lists.newArrayList();
        if (params.containsKey("dealerIds")) {
            dealerIds = (List<Long>) params.get("dealerIds");
        }
        if (params.containsKey("brokerIds")) {
            brokerIds = (List<Long>) params.get("brokerIds");
        }
        List<String> ops = Lists.newArrayList();
        //新增线索
        ops.add("broker_clue_add");
        //新增店铺分配订单线索
        ops.add("broker_clue_dealer_add");
        //新增个人分配订单线索
        ops.add("broker_clue_person_add");
        //新增店铺分配话单线索
        ops.add("broker_clue_call_dealer_add");
        //新增个人分配话单线索
        ops.add("broker_clue_call_person_add");
        //线索处理数
        ops.add("broker_clue_handle");
        //新车线索数
        ops.add("broker_clue_add$broker_clue_add_new#extra::jsonb%%'category' and extra::jsonb->>'category'='0'");
        //试驾线索数
        ops.add("broker_clue_add$broker_clue_add_try#extra::jsonb%%'category' and extra::jsonb->>'category'='1'");
        //置换线索数
        ops.add("broker_clue_add$broker_clue_add_exchange#extra::jsonb%%'category' and extra::jsonb->>'category'='2'");
        //微店线索量
        ops.add("broker_clue_add$broker_clue_source_website#extra::jsonb%%'source' and extra::jsonb->>'source'='2'");
        //头条线索量
        ops.add("broker_clue_add$broker_clue_source_meteriel#extra::jsonb%%'source' and extra::jsonb->>'source'='4'");
        //问答线索量
        ops.add("broker_clue_add$broker_clue_source_ask#extra::jsonb%%'source' and extra::jsonb->>'source'='3'");
        //会员页线索量
        ops.add("broker_clue_add$broker_clue_source_shop#extra::jsonb%%'source' and extra::jsonb->>'source'='1'");
        Map<String, Object> data = apiService.brokerLogService().durationSum(begin, end, Longs.join(",", Longs.toArray(dealerIds)), Longs.join(",", Longs.toArray(brokerIds)), StringUtils.join(ops, ','));

        //线索处理时长
        String sql = "select  coalesce(sum(cast(extra::jsonb->>'interval' as int)),0) as broker_clue_handle_interval from broker_logs as log "
                + "join broker_log_type as log_type on log_type.id=log.type "
                + "where log_type.name='broker_clue_handle' and extra::jsonb %% 'interval'"
                + "and create_time::date >= '" + begin + "' "
                + "and create_time::date <= '" + end + "' ";
        if (dealerIds.isEmpty() == false) {
            sql += " and dealer_id in (" + Longs.join(",", Longs.toArray(dealerIds)) + ") ";
        }
        if (brokerIds.isEmpty() == false) {
            sql += " and broker_id in (" + Longs.join(",", Longs.toArray(brokerIds)) + ") ";
        }
        data.putAll(apiService.brokerLogService().exec(sql).get(0));

        //线索量分日期
        ops.clear();
        //新增线索
        ops.add("broker_clue_add");
        //新增店铺分配订单线索
        ops.add("broker_clue_dealer_add");
        //新增个人分配订单线索
        ops.add("broker_clue_person_add");
        //新增店铺分配话单线索
        ops.add("broker_clue_call_dealer_add");
        //新增个人分配话单线索
        ops.add("broker_clue_call_person_add");
        Map<String, Object> clueList = apiService.brokerLogService().duration(begin, end, Longs.join(",", Longs.toArray(dealerIds)), Longs.join(",", Longs.toArray(brokerIds)), StringUtils.join(ops, ','));
        data.put("clueList", clueList);
        return data;
    }

    /**
     * 获取指定项周期数据统计列表
     *
     * @param params
     * @return
     */
    private Object getDurationLogs(Map<String, Object> params) throws ResultException {
        if (params.containsKey("names") == false) {
            return null;
        }
        if (params.containsKey("begin") == false) {
            return null;
        }
        if (params.containsKey("end") == false) {
            return null;
        }
        List<String> names = (List<String>) params.get("names");
        List<String> ops = Lists.newArrayList();
        names.forEach(item->{
            if (item.equals("broker_clue_add")) {
                item = "broker_clue_add@target_id";
            }
            ops.add(item);
        });
        String begin = params.get("begin").toString();
        String end = params.get("end").toString();
        List<Long> dealerIds = Lists.newArrayList();
        List<Long> brokerIds = Lists.newArrayList();
        if (params.containsKey("dealerIds")) {
            dealerIds = (List<Long>) params.get("dealerIds");
        }
        if (params.containsKey("brokerIds")) {
            brokerIds = (List<Long>) params.get("brokerIds");
        }
        return apiService.brokerLogService().duration(begin, end, Longs.join(",", Longs.toArray(dealerIds)), Longs.join(",", Longs.toArray(brokerIds)), StringUtils.join(ops, ','));
    }

    /**
     * 获取行圆慧数据中心客户统计
     *
     * @param brokerId
     * @param dealerId
     * @param begin
     * @param end
     * @param lastBegin
     * @param lastEnd
     * @return
     */
    public Object getXyhCustomerLogs(long brokerId, long dealerId, String begin, String end, String lastBegin, String lastEnd) throws ResultException {

        List<Long> dealerIds = Lists.newArrayList();
        List<Long> brokerIds = Lists.newArrayList();
        if (dealerId > 0) {
            dealerIds.add(dealerId);
        }
        if (brokerId > 0) {
            brokerIds.add(brokerId);
        }
        List<String> ops = Lists.newArrayList();
        //新增客户
        ops.add("broker_customer_add");
        //成交客户
        ops.add("broker_customer_deal");
        //待跟进客户
        ops.add("broker_customer_await");
        //到店客户
        ops.add("broker_customer_arrival");
        //战败客户
        ops.add("broker_customer_lose");
        //无效客户
        ops.add("broker_customer_cancel");
        Map<String, Object> data = apiService.brokerLogService().durationSum(begin, end, Longs.join(",", Longs.toArray(dealerIds)), Longs.join(",", Longs.toArray(brokerIds)), StringUtils.join(ops, ','));
        Map<String, Object> lastData = apiService.brokerLogService().durationSum(lastBegin, lastEnd, Longs.join(",", Longs.toArray(dealerIds)), Longs.join(",", Longs.toArray(brokerIds)), StringUtils.join(ops, ','));
        //新增客户变化
        data.put("broker_customer_add_diff", Integer.valueOf(data.get("broker_customer_add").toString()) - Integer.valueOf(lastData.get("broker_customer_add").toString()));
        //成交客户变化
        data.put("broker_customer_deal_diff", Integer.valueOf(data.get("broker_customer_deal").toString()) - Integer.valueOf(lastData.get("broker_customer_deal").toString()));
        //待跟进客户变化
        data.put("broker_customer_await_diff", Integer.valueOf(data.get("broker_customer_await").toString()) - Integer.valueOf(lastData.get("broker_customer_await").toString()));
        //到店客户变化
        data.put("broker_customer_arrival_diff", Integer.valueOf(data.get("broker_customer_arrival").toString()) - Integer.valueOf(lastData.get("broker_customer_arrival").toString()));
        //战败客户变化
        data.put("broker_customer_lose_diff", Integer.valueOf(data.get("broker_customer_lose").toString()) - Integer.valueOf(lastData.get("broker_customer_lose").toString()));
        //无效客户变化
        data.put("broker_customer_cancel_diff", Integer.valueOf(data.get("broker_customer_cancel").toString()) - Integer.valueOf(lastData.get("broker_customer_cancel").toString()));
        //本周期率值计算
        //成交率
        data.put("broker_customer_deal_rate", 0);
        //待跟进率
        data.put("broker_customer_await_rate", 0);
        //到店率
        data.put("broker_customer_arrival_rate", 0);
        //战败率
        data.put("broker_customer_lose_rate", 0);
        //无效率
        data.put("broker_customer_cancel_rate", 0);
        if (Integer.valueOf(data.get("broker_customer_add").toString()) > 0) {
            data.put("broker_customer_deal_rate", Integer.valueOf(data.get("broker_customer_deal").toString()) / Integer.valueOf(data.get("broker_customer_add").toString()) * 100);
            data.put("broker_customer_await_rate", Integer.valueOf(data.get("broker_customer_await").toString()) / Integer.valueOf(data.get("broker_customer_add").toString()) * 100);
            data.put("broker_customer_arrival_rate", Integer.valueOf(data.get("broker_customer_arrival").toString()) / Integer.valueOf(data.get("broker_customer_add").toString()) * 100);
            data.put("broker_customer_lose_rate", Integer.valueOf(data.get("broker_customer_lose").toString()) / Integer.valueOf(data.get("broker_customer_add").toString()) * 100);
            data.put("broker_customer_cancel_rate", Integer.valueOf(data.get("broker_customer_cancel").toString()) / Integer.valueOf(data.get("broker_customer_add").toString()) * 100);
        }
        //上周期率值计算
        //成交率
        lastData.put("broker_customer_deal_rate", 0);
        //待跟进率
        lastData.put("broker_customer_await_rate", 0);
        //到店率
        lastData.put("broker_customer_arrival_rate", 0);
        //战败率
        lastData.put("broker_customer_lose_rate", 0);
        //无效率
        lastData.put("broker_customer_cancel_rate", 0);
        if (Integer.valueOf(lastData.get("broker_customer_add").toString()) > 0) {
            lastData.put("broker_customer_deal_rate", Integer.valueOf(lastData.get("broker_customer_deal").toString()) / Integer.valueOf(lastData.get("broker_customer_add").toString()) * 100);
            lastData.put("broker_customer_await_rate", Integer.valueOf(lastData.get("broker_customer_await").toString()) / Integer.valueOf(lastData.get("broker_customer_add").toString()) * 100);
            lastData.put("broker_customer_arrival_rate", Integer.valueOf(lastData.get("broker_customer_arrival").toString()) / Integer.valueOf(lastData.get("broker_customer_add").toString()) * 100);
            lastData.put("broker_customer_lose_rate", Integer.valueOf(lastData.get("broker_customer_lose").toString()) / Integer.valueOf(lastData.get("broker_customer_add").toString()) * 100);
            lastData.put("broker_customer_cancel_rate", Integer.valueOf(lastData.get("broker_customer_cancel").toString()) / Integer.valueOf(lastData.get("broker_customer_add").toString()) * 100);
        }
        //周期率值变化
        data.put("broker_customer_deal_rate_diff", Integer.valueOf(data.get("broker_customer_deal_rate").toString()) - Integer.valueOf(lastData.get("broker_customer_deal_rate").toString()));
        data.put("broker_customer_await_rate_diff", Integer.valueOf(data.get("broker_customer_await_rate").toString()) - Integer.valueOf(lastData.get("broker_customer_await_rate").toString()));
        data.put("broker_customer_arrival_rate_diff", Integer.valueOf(data.get("broker_customer_arrival_rate").toString()) - Integer.valueOf(lastData.get("broker_customer_arrival_rate").toString()));
        data.put("broker_customer_lose_rate_diff", Integer.valueOf(data.get("broker_customer_lose_rate").toString()) - Integer.valueOf(lastData.get("broker_customer_lose_rate").toString()));
        data.put("broker_customer_cancel_rate_diff", Integer.valueOf(data.get("broker_customer_cancel_rate").toString()) - Integer.valueOf(lastData.get("broker_customer_cancel_rate").toString()));

        return data;
    }

    /**
     * 获取行圆慧数据中心线索统计
     *
     * @param brokerId
     * @param dealerId
     * @param begin
     * @param end
     * @param lastBegin
     * @param lastEnd
     * @return
     */
    public Object getXyhClueLogs(long brokerId, long dealerId, String begin, String end, String lastBegin, String lastEnd) throws ResultException {
        List<Long> dealerIds = Lists.newArrayList();
        List<Long> brokerIds = Lists.newArrayList();
        if (dealerId > 0) {
            dealerIds.add(dealerId);
        }
        if (brokerId > 0) {
            brokerIds.add(brokerId);
        }
        List<String> ops = Lists.newArrayList();
        //总线索量
        ops.add("broker_clue_add");
        //网单线索总量
        ops.add("broker_clue_add$broker_clue_net_ab%%'type' and extra::jsonb->>'type'='2'");
        //新车线索总量
        ops.add("broker_clue_add$broker_clue_new_add#extra::jsonb%%'category' and extra::jsonb->>'category'='0'");
        //试驾线索总量
        ops.add("broker_clue_add$broker_clue_try_add#extra::jsonb%%'category' and extra::jsonb->>'category'='1'");
        //置换线索总量
        ops.add("broker_clue_add$broker_clue_exchange_add#extra::jsonb%%'category' and extra::jsonb->>'category'='2'");

        //话单线索总量
        ops.add("broker_clue_add$broker_clue_mobile_add#extra::jsonb%%'type' and extra::jsonb->>'type'='1'");
        //店铺话单总量
        ops.add("broker_clue_add$broker_clue_mobile_dealer_add#extra::jsonb%%'type' and extra::jsonb->>'type'='1' and extra::jsonb->>'brokerId'='0'");
        //个人话单总量
        ops.add("broker_clue_add$broker_clue_mobile_person_add#extra::jsonb%%'type' and extra::jsonb->>'type'='1' and extra::jsonb->>'brokerId'!='0'");
        //接听率
        ops.add("broker_clue_add$broker_clue_mobile_success_rate");

        //线索处理总数
        ops.add("broker_clue_deal");
        //线索处理率
        ops.add("broker_clue_deal_rate");
        //线索处理时长
        ops.add("broker_clue_handle_time");

        //微店线索来源
        ops.add("broker_clue_add$broker_clue_website_add#extra::jsonb%%'source' and extra::jsonb->>'source'='2'");
        //头条线索来源
        ops.add("broker_clue_add$broker_clue_material_add#extra::jsonb%%'source' and extra::jsonb->>'source'='4'");
        //汽车大全线索来源
        ops.add("broker_clue_add$broker_clue_qcdq_add#extra::jsonb%%'source' and (extra::jsonb->>'source'='1' or extra::jsonb->>'source'='3')");

        Map<String, Object> data = apiService.brokerLogService().durationSum(begin, end, Longs.join(",", Longs.toArray(dealerIds)), Longs.join(",", Longs.toArray(brokerIds)), StringUtils.join(ops, ','));
        Map<String, Object> lastData = apiService.brokerLogService().durationSum(lastBegin, lastEnd, Longs.join(",", Longs.toArray(dealerIds)), Longs.join(",", Longs.toArray(brokerIds)), StringUtils.join(ops, ','));

        //新增线索变化
        data.put("broker_clue_add_diff", Integer.valueOf(data.get("broker_clue_add").toString()) - Integer.valueOf(lastData.get("broker_clue_add").toString()));
        //新增网单变化
        data.put("broker_clue_net_add_diff", Integer.valueOf(data.get("broker_clue_net_add").toString()) - Integer.valueOf(lastData.get("broker_clue_net_add").toString()));
        //新增新车线索变化
        data.put("broker_clue_new_add_diff", Integer.valueOf(data.get("broker_clue_new_add").toString()) - Integer.valueOf(lastData.get("broker_clue_new_add").toString()));
        //新增试驾线索变化
        data.put("broker_clue_try_add_diff", Integer.valueOf(data.get("broker_clue_try_add").toString()) - Integer.valueOf(lastData.get("broker_clue_try_add").toString()));
        //新增置换线索变化
        data.put("broker_clue_exchange_add_diff", Integer.valueOf(data.get("broker_clue_exchange_add").toString()) - Integer.valueOf(lastData.get("broker_clue_exchange_add").toString()));
        //新增话单线索变化
        data.put("broker_clue_mobile_add_diff", Integer.valueOf(data.get("broker_clue_mobile_add").toString()) - Integer.valueOf(lastData.get("broker_clue_mobile_add").toString()));
        //新增店铺话单变化
        data.put("broker_clue_mobile_dealer_add_diff", Integer.valueOf(data.get("broker_clue_mobile_dealer_add").toString()) - Integer.valueOf(lastData.get("broker_clue_mobile_dealer_add").toString()));
        //新增个人话单变化
        data.put("broker_clue_mobile_person_add_diff", Integer.valueOf(data.get("broker_clue_mobile_person_add").toString()) - Integer.valueOf(lastData.get("broker_clue_mobile_person_add").toString()));
        //新增接听率变化
        data.put("broker_clue_mobile_success_rate_diff", (Integer.valueOf(data.get("broker_clue_mobile_success_rate").toString()) - Integer.valueOf(lastData.get("broker_clue_mobile_success_rate").toString())) * 100);
        //线索处理率
        if (Integer.valueOf(data.get("broker_clue_add").toString()) > 0) {
            data.put("broker_clue_deal_rate", Integer.valueOf(data.get("broker_clue_deal").toString()) / Integer.valueOf(data.get("broker_clue_add").toString()));
        }
        if (Integer.valueOf(lastData.get("broker_clue_add").toString()) > 0) {
            lastData.put("broker_clue_deal_rate", Integer.valueOf(lastData.get("broker_clue_deal").toString()) / Integer.valueOf(lastData.get("broker_clue_add").toString()));
        }
        //新增线索处理比率变化
        data.put("broker_clue_deal_rate_diff", Integer.valueOf(data.get("broker_clue_deal_rate").toString()) - Integer.valueOf(lastData.get("broker_clue_deal_rate").toString()));
        //新增线索处理时长变化
        data.put("broker_clue_handle_time_diff", Integer.valueOf(data.get("broker_clue_handle_time").toString()) - Integer.valueOf(lastData.get("broker_clue_handle_time").toString()));

        //线索量分日期
        List<String> opss = Lists.newArrayList();
        //新增线索
        opss.add("broker_clue_add");
        //网单线索总量
        opss.add("broker_clue_add$broker_clue_net_add#extra::jsonb%%'type' and extra::jsonb->>'type'='2'");
        //话单线索总量
        opss.add("broker_clue_add$broker_clue_mobile_add#extra::jsonb%%'type' and extra::jsonb->>'type'='1'");

        Map<String, Object> clueList = apiService.brokerLogService().duration(begin, end, Longs.join(",", Longs.toArray(dealerIds)), Longs.join(",", Longs.toArray(brokerIds)), StringUtils.join(opss, ','));
        data.put("clueList", clueList);

        return data;
    }

    /**
     * 获取慧商机h5端周期数据统计
     *
     * @param brokerId
     * @param dealerId
     * @param begin
     * @param end
     * @param lastBegin
     * @param lastEnd
     * @return
     */
    public Object getHsjStatistic(long brokerId, long dealerId, String begin, String end, String lastBegin, String lastEnd) throws ResultException {
        List<Long> dealerIds = Lists.newArrayList();
        List<Long> brokerIds = Lists.newArrayList();
        if (dealerId > 0) {
            dealerIds.add(dealerId);
        }
        if (brokerId > 0) {
            brokerIds.add(brokerId);
        }
        List<String> ops = Lists.newArrayList();
        //新增客户
        ops.add("broker_customer_add@extra::jsonb->'mobile'");
        //跟进客户
        ops.add("broker_customer_await@extra::jsonb->'mobile'");
        //到店客户
        ops.add("broker_customer_arrival@extra::jsonb->'mobile'");
        //成交客户
        ops.add("broker_customer_deal@extra::jsonb->'mobile'");
        //战败客户
        ops.add("broker_customer_lose@extra::jsonb->'mobile'");
        //流失客户
        ops.add("broker_clue_miss@extra::jsonb->'mobile'");
        //平均响应时间:todo
        ops.add("broker_customer_contact_interval");
        //新增店铺线索
        ops.add("broker_clue_add");
        //新增个人线索
        ops.add("broker_clue_person_add");
        //微店浏览量
        ops.add("broker_website_view");
        //车型浏览量
        ops.add("broker_car_recommend_view");
        //头条浏览量
        ops.add("broker_materiel_view");
        //活动浏览量
        ops.add("broker_activity_view");

        Map<String, Object> data = apiService.brokerLogService().durationSum(begin, end, Longs.join(",", Longs.toArray(dealerIds)), Longs.join(",", Longs.toArray(brokerIds)), StringUtils.join(ops, ','));
        Map<String, Object> lastData = apiService.brokerLogService().durationSum(lastBegin, lastEnd, Longs.join(",", Longs.toArray(dealerIds)), Longs.join(",", Longs.toArray(brokerIds)), StringUtils.join(ops, ','));
        //率值计算
        data.put("broker_customer_contact_rate", 0);
        data.put("broker_customer_arrival_rate", 0);
        data.put("broker_customer_deal_rate", 0);

        lastData.put("broker_customer_contact_rate", 0);
        lastData.put("broker_customer_arrival_rate", 0);
        lastData.put("broker_customer_deal_rate", 0);

        //新增客户变化
        data.put("broker_customer_add_diff", Integer.valueOf(data.get("broker_customer_add").toString()) - Integer.valueOf(lastData.get("broker_customer_add").toString()));
        //成交客户变化
        data.put("broker_customer_deal_diff", Integer.valueOf(data.get("broker_customer_deal").toString()) - Integer.valueOf(lastData.get("broker_customer_deal").toString()));
        //跟进客户变化
        data.put("broker_customer_contact_diff", Integer.valueOf(data.get("broker_customer_contact").toString()) - Integer.valueOf(lastData.get("broker_customer_contact").toString()));
        //到店客户变化
        data.put("broker_customer_arrival_diff", Integer.valueOf(data.get("broker_customer_arrival").toString()) - Integer.valueOf(lastData.get("broker_customer_arrival").toString()));
        //战败客户变化
        data.put("broker_customer_lose_diff", Integer.valueOf(data.get("broker_customer_lose").toString()) - Integer.valueOf(lastData.get("broker_customer_lose").toString()));
        //流失客户变化
        data.put("broker_customer_miss_diff", Integer.valueOf(data.get("broker_customer_miss").toString()) - Integer.valueOf(lastData.get("broker_customer_miss").toString()));
        //平均响应时间变化
        data.put("broker_customer_contact_interval_diff", Integer.valueOf(data.get("broker_customer_contact_interval").toString()) - Integer.valueOf(lastData.get("broker_customer_contact_interval").toString()));
        //客户跟进率变化
        data.put("broker_customer_contact_rate_diff", Integer.valueOf(data.get("broker_customer_contact_rate").toString()) - Integer.valueOf(lastData.get("broker_customer_contact_rate").toString()));
        //客户到店率变化
        data.put("broker_customer_arrival_rate_diff", Integer.valueOf(data.get("broker_customer_arrival_rate").toString()) - Integer.valueOf(lastData.get("broker_customer_arrival_rate").toString()));
        //客户成交率变化
        data.put("broker_customer_arrival_deal_diff", Integer.valueOf(data.get("broker_customer_deal_rate").toString()) - Integer.valueOf(lastData.get("broker_customer_deal_rate").toString()));
        //店铺线索变化
        data.put("broker_clue_dealer_add_diff", Integer.valueOf(data.get("broker_clue_dealer_add").toString()) - Integer.valueOf(lastData.get("broker_clue_dealer_add").toString()));
        //个人线索变化
        data.put("broker_clue_person_add_diff", Integer.valueOf(data.get("broker_clue_person_add").toString()) - Integer.valueOf(lastData.get("broker_clue_person_add").toString()));
        //微店浏览量变化
        data.put("broker_website_view_diff", Integer.valueOf(data.get("broker_website_view").toString()) - Integer.valueOf(lastData.get("broker_website_view").toString()));
        //车型浏览量变化
        data.put("broker_car_recommend_view_diff", Integer.valueOf(data.get("broker_car_recommend_view").toString()) - Integer.valueOf(lastData.get("broker_car_recommend_view").toString()));
        //头条浏览量
        data.put("broker_materiel_view_diff", Integer.valueOf(data.get("broker_materiel_view").toString()) - Integer.valueOf(lastData.get("broker_materiel_view").toString()));
        //活动浏览量
        data.put("broker_activity_view_diff", Integer.valueOf(data.get("broker_activity_view").toString()) - Integer.valueOf(lastData.get("broker_activity_view").toString()));
        return data;
    }

}
