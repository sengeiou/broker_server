package com.xyauto.interact.broker.server.controller;

import com.mcp.validate.annotation.Check;
import com.xyauto.interact.broker.server.enums.ResultCode;
import com.xyauto.interact.broker.server.model.vo.*;
import com.xyauto.interact.broker.server.service.BrokerIntegralLogService;
import com.xyauto.interact.broker.server.service.BrokerIntegralService;
import com.xyauto.interact.broker.server.service.BrokerService;
import com.xyauto.interact.broker.server.util.DateStyle;
import com.xyauto.interact.broker.server.util.DateUtil;
import com.xyauto.interact.broker.server.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * 经纪人积分明细
 */
@RestController
@RequestMapping("/broker/integral")
public class BrokerIntegralLogController extends BaseController {

    @Autowired
    BrokerIntegralLogService brokerIntegralLogService;

    @Autowired
    BrokerIntegralService brokerIntegralService;

    @Autowired
    BrokerService brokerService;

    /**
     * 获取积分余额
     *
     * @param broker_id
     * @return
     */
    @RequestMapping(value = "/brokerintegral", method = RequestMethod.GET)
    public Result getbrokerIntegral(@Check(value = "broker_id") long broker_id) {
        BrokerIntegral model = brokerIntegralService.getInfoByBrokerId(broker_id);
        return result.format(ResultCode.Success, model);
    }


    /**
     * 增减积分
     * @param broker_id
     * @param integral   正负数
     * @param desc
     * @return
     */
    @RequestMapping(value = "/addintegral", method = RequestMethod.GET)
    public Result addIntegral(
            @Check(value = "broker_id") long broker_id,
            @Check("integral") long integral,
            @Check(value = "desc") String desc) {
        if (brokerIntegralService.addIntegral(broker_id, integral, desc)) {
            return result.format(ResultCode.Success);
        }
        return result.format(ResultCode.UnKnownError);
    }

    /**
     * 获取积分明细列表
     *
     * @param broker_id
     * @param max
     * @param limit
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result getIntegralList(
            @Check(value = "broker_id") long broker_id,
            @Check(value = "max") String max,
            @Check(value = "limit") int limit
    ) {
        FlowPagedList pageList = new FlowPagedList();
        Date begin = new Date();
        begin = DateUtil.getDateStart(begin, DateStyle.FIELD_TYPE.DAY, -90);
        int total = brokerIntegralLogService.getListCountByBrokerId(broker_id,
                "0", (int) begin.getTime());
        int pagedCount = 0;
        if (total > 0) {
            pagedCount = brokerIntegralLogService.getListCountByBrokerId(broker_id,
                    max, (int) begin.getTime());
            if (pagedCount > 0) {
                List<BrokerIntegralLog> model = brokerIntegralLogService.getListByBrokerId(broker_id, limit,
                        max, (int) begin.getTime());
                pageList.setList(model);
                pageList.setNext_max(String.valueOf(model.get(model.size() - 1).getCreateTime().getTime()/1000));
            }
        }
        pageList.setCount(total);
        pageList.setLimit(limit);
        pageList.setHas_more(pagedCount > limit ? 1 : 0);
        return result.format(ResultCode.Success, pageList);
    }

    /**
     * 获取积分明细列表
     *
     * @param broker_id
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping(value = "/plist", method = RequestMethod.GET)
    public Result getIntegralList(
            @Check(value = "broker_id") long broker_id,
            @Check(value = "page") int page,
            @Check(value = "limit") int limit
    ) {
        PagedList<BrokerIntegralLog> pageList = new PagedList<>();
        Date begin = new Date();
        begin = DateUtil.getDateStart(begin, DateStyle.FIELD_TYPE.DAY, -90);
        int total = brokerIntegralLogService.getPageListCountByBrokerId(broker_id, (int) begin.getTime());
        if (total > 0) {
            List<BrokerIntegralLog> model = brokerIntegralLogService.getPageListByBrokerId(broker_id, limit, page, (int) begin.getTime());
            if (model.size() > 0) {
                pageList.setList(model);
            }
        }
        pageList.setCount(total);
        pageList.setLimit(limit);
        pageList.setPage(page);
        return result.format(ResultCode.Success, pageList);
    }

}
