package com.xyauto.interact.broker.server.controller;

import com.google.common.collect.Maps;
import com.xyauto.interact.broker.server.enums.ResultCode;
import com.xyauto.interact.broker.server.exceptions.ResultException;
import com.xyauto.interact.broker.server.service.BrokerLogService;
import com.xyauto.interact.broker.server.service.BrokerService;
import com.xyauto.interact.broker.server.util.BrokerLogUtil;
import com.xyauto.interact.broker.server.util.Result;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/log")
public class BrokerLogController extends BaseController {

    @Autowired
    BrokerLogUtil log;

    @Autowired
    BrokerLogService logService;

    @Autowired
    BrokerService brokerService;

    @RequestMapping(value = "/test")
    public Result test(
            @RequestParam(value = "type", required = true) String type,
            @RequestParam(value = "broker_id", required = true) long brokerId,
            @RequestParam(value = "dealer_id", required = true) long dealerId,
            @RequestParam(value = "target_id", required = false, defaultValue = "0") long targetId,
            @RequestParam(value = "message", required = false, defaultValue = "") String message
    ) throws ResultException {
        log.log(type, brokerId, targetId, dealerId, message);
        return result.format(ResultCode.Success);
    }

    /**
     * 获取日志总入口
     *
     * @param type
     * @param brokerId
     * @param dealerId
     * @param brokerIds
     * @param dealerIds
     * @param targetId
     * @param date
     * @param begin
     * @param end
     * @param names
     * @return
     * @throws com.xyauto.interact.broker.server.exceptions.ResultException
     */
    @RequestMapping(value = "/analytics")
    public Result getLog(
            @RequestParam(value = "type", required = true) int type,
            @RequestParam(value = "broker_id", required = false, defaultValue = "0") long brokerId,
            @RequestParam(value = "dealer_id", required = false, defaultValue = "0") long dealerId,
            @RequestParam(value = "broker_ids", required = false, defaultValue = "") List<Long> brokerIds,
            @RequestParam(value = "dealer_ids", required = false, defaultValue = "") List<Long> dealerIds,
            @RequestParam(value = "target_id", required = false, defaultValue = "0") long targetId,
            @RequestParam(value = "date", required = false, defaultValue = "") String date,
            @RequestParam(value = "begin", required = false, defaultValue = "") String begin,
            @RequestParam(value = "end", required = false, defaultValue = "") String end,
            @RequestParam(value = "names", required = false, defaultValue = "") List<String> names
    ) throws ResultException {
        Map<String, Object> params = Maps.newConcurrentMap();
        if (brokerId != 0) {
            params.put("brokerId", brokerId);
        }
        if (dealerIds.isEmpty() == false) {
            params.put("dealerIds", dealerIds);
        }
        if (brokerIds.isEmpty() == false) {
            params.put("brokerIds", brokerIds);
        }
        if (dealerId != 0) {
            params.put("dealerId", dealerId);
        }
        if (targetId != 0) {
            params.put("targetId", targetId);
        }
        if (names.isEmpty() == false) {
            params.put("names", names);
        }
        if (date.isEmpty() == false) {
            params.put("date", date);
        }
        if (begin.isEmpty() == false) {
            params.put("begin", begin);
        }
        if (end.isEmpty() == false) {
            params.put("end", end);
        }
        Object logs = logService.getLogs(type, params);
        return result.format(ResultCode.Success, logs);
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
     * @throws com.xyauto.interact.broker.server.exceptions.ResultException
     */
    @RequestMapping(value = "/xyh/customer/logs")
    public Result xyhCustomerLog(
            @RequestParam(value = "broker_id", required = false, defaultValue = "0") long brokerId,
            @RequestParam(value = "dealer_id", required = false, defaultValue = "0") long dealerId,
            @RequestParam(value = "begin", required = false, defaultValue = "") String begin,
            @RequestParam(value = "end", required = false, defaultValue = "") String end,
            @RequestParam(value = "lastbegin", required = false, defaultValue = "") String lastBegin,
            @RequestParam(value = "lastend", required = false, defaultValue = "") String lastEnd
    ) throws ResultException {
        Object logs = logService.getXyhCustomerLogs(brokerId, dealerId, begin, end, lastBegin, lastEnd);
        return result.format(ResultCode.Success, logs);
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
     * @throws ResultException 
     */
    @RequestMapping(value = "/xyh/clue/logs")
    public Result xyhClueLog(
            @RequestParam(value = "broker_id", required = false, defaultValue = "0") long brokerId,
            @RequestParam(value = "dealer_id", required = false, defaultValue = "0") long dealerId,
            @RequestParam(value = "begin", required = false, defaultValue = "") String begin,
            @RequestParam(value = "end", required = false, defaultValue = "") String end,
            @RequestParam(value = "lastbegin", required = false, defaultValue = "") String lastBegin,
            @RequestParam(value = "lastend", required = false, defaultValue = "") String lastEnd
    ) throws ResultException {
        Object logs = logService.getXyhClueLogs(brokerId, dealerId, begin, end, lastBegin, lastEnd);
        return result.format(ResultCode.Success, logs);
    }

    /**
     * 慧商机慧销宝中数据周期统计
     * 
     * @param brokerId
     * @param dealerId
     * @param begin
     * @param end
     * @param lastBegin
     * @param lastEnd
     * @return 
     */
    @RequestMapping(value = "/hsj/statistic")
    public Result hsjStatistic(
            @RequestParam(value = "broker_id", required = false, defaultValue = "0") long brokerId,
            @RequestParam(value = "dealer_id", required = false, defaultValue = "0") long dealerId,
            @RequestParam(value = "begin", required = false, defaultValue = "") String begin,
            @RequestParam(value = "end", required = false, defaultValue = "") String end,
            @RequestParam(value = "lastbegin", required = false, defaultValue = "") String lastBegin,
            @RequestParam(value = "lastend", required = false, defaultValue = "") String lastEnd
    ) {
        try {
            Object logs = logService.getHsjStatistic(brokerId, dealerId, begin, end, lastBegin, lastEnd);
            return result.format(ResultCode.Success, logs);
        } catch (ResultException ex) {
            this.error(ex.getMessage());
            return null;
        }
    }
}
