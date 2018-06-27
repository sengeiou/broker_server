package com.xyauto.interact.broker.server.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mcp.validate.annotation.Check;
import com.xyauto.interact.broker.server.enums.ResultCode;
import com.xyauto.interact.broker.server.enums.BlockEnum.BlockName;
import com.xyauto.interact.broker.server.exceptions.ResultException;
import com.xyauto.interact.broker.server.service.BlocksService;
import com.xyauto.interact.broker.server.task.BrokerClueSyncEs;
import com.xyauto.interact.broker.server.task.BrokerCustomerSyncEs;
import com.xyauto.interact.broker.server.task.BrokerSyncEs;
import com.xyauto.interact.broker.server.util.Result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/util")
public class UtilController {

    @Autowired
    Result result;
    @Autowired
    BlocksService blocksService;
    
    @Autowired
    BrokerClueSyncEs clueSync;
    
    @Autowired
    BrokerCustomerSyncEs customerSync;
    
    @Autowired
    BrokerSyncEs brokerSync;

    @RequestMapping(value = "/bannerlist")
    public Result getBannerList() {
        String list= "";
        try {
            list = blocksService.getBlockByName(BlockName.home_banner.getName());
        } catch (ResultException e) {
            return result.format(e.getResult(), JSONArray.parse(list));
        } catch (Exception e) {
            return result.format(ResultCode.UnKnownError, JSONArray.parse(list));
        }
        return result.format(ResultCode.Success, JSONArray.parse(list));
    }

    /**
     * app版本号
     * @return
     */
    @RequestMapping(value = "/appversion")
    public Result getAppVersion() {
        String version = "";
        try {
            version = blocksService.getBlockByName(BlockName.app_version.getName());
        } catch (ResultException e) {
            return result.format(e.getResult(), JSONObject.parseObject(version));
        } catch (Exception e) {
            return result.format(ResultCode.UnKnownError, JSONObject.parseObject(version));
        }
        return result.format(ResultCode.Success, JSONObject.parseObject(version));
    }

}
