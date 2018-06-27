package com.xyauto.interact.broker.server.controller;

import com.alibaba.fastjson.JSONArray;
import com.mcp.validate.annotation.Check;
import com.xyauto.interact.broker.server.enums.ResultCode;
import com.xyauto.interact.broker.server.model.po.DealerCustomerIntention;
import com.xyauto.interact.broker.server.model.vo.DealerClueDistributeSetting;
import com.xyauto.interact.broker.server.service.DealerClueDistributeSettingService;
import com.xyauto.interact.broker.server.service.DealerSettingService;
import com.xyauto.interact.broker.server.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/dealer/setting")
public class DealerSettingController extends  BaseController {
    
    @Autowired
    DealerSettingService dealerSettingService;
    
    @Autowired
    DealerClueDistributeSettingService dealerClueDistributeSettingService;
    
    /**
     * 获取经销商客户意向等级
     * @param dealerId
     * @return 
     */
    @RequestMapping(value = "/intention", method = RequestMethod.GET)
    public Result getCustomerIntentionLevel(
            @Check(value = "dealer_id", required = true) long dealerId
    ) {
        List<Map<String, Object>> data = dealerSettingService.getCustomerIntentionLevel(dealerId);
        return result.format(ResultCode.Success, data);
    }
    
    /**
     * 修改经销商客户意向等级
     * @param dealerId
     * @param data(json格式)
     * @return 
     */
    @RequestMapping(value = "/intention/update")
    public Result updateCustomerIntentionLevel(
            @Check(value = "dealer_id", required = true) long dealerId,
            @Check(value = "data", required = true) String data
    ) {
        
        List<DealerCustomerIntention> list = JSONArray.parseArray(data, DealerCustomerIntention.class);
        if (list.isEmpty()==false) {
            dealerSettingService.setCustomerIntentionLevel(dealerId, list);
        }
        List<Map<String, Object>> ret = dealerSettingService.getCustomerIntentionLevel(dealerId);
        return result.format(ResultCode.Success, ret);
    }
    
    /**
     * 重置经销商客户意向等级
     * @param dealerId
     * @return 
     */
    @RequestMapping(value = "/intention/reset")
    public Result updateCustomerIntentionLevel(
            @Check(value = "dealer_id", required = true) long dealerId
    ) {
        dealerSettingService.resetCustomerIntentionLevel(dealerId);
        List<Map<String, Object>> ret = dealerSettingService.getCustomerIntentionLevel(dealerId);
        return result.format(ResultCode.Success, ret);
    }
    
    /**
     * 获取经销商订单分配规则
     * @param dealerId
     * @return 
     */
    @RequestMapping(value = "/distribution", method = RequestMethod.GET)
    public Result distribution(
            @Check(value = "dealer_id", required = true) long dealerId
    ) {
        DealerClueDistributeSetting info = dealerClueDistributeSettingService.getDistribution(dealerId);
        return result.format(ResultCode.Success, info);
    }
    
    /**
     * 修改经销商订单分配规则
     * @param dealerId
     * @param dealerDistribute
     * @param brokerDistribute
     * @param brokerIds
     * @return 
     */
    @RequestMapping(value = "/distribution/update")
    public Result updateDistribution(
            @Check(value = "dealer_id", required = true) long dealerId,
            @Check(value = "dealer_distribute", required = true) int dealerDistribute,
            @Check(value = "broker_distribute", required = true) int brokerDistribute,
            @Check(value = "broker_ids", required = true) List<Long> brokerIds
    ) {
        dealerClueDistributeSettingService.updateDistribution(dealerId, dealerDistribute, brokerDistribute, brokerIds);
        DealerClueDistributeSetting info = dealerClueDistributeSettingService.getDistribution(dealerId);
        return result.format(ResultCode.Success, info);
    }
}