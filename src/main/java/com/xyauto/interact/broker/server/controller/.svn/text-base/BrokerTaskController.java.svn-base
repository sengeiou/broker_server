package com.xyauto.interact.broker.server.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.xyauto.interact.broker.server.exceptions.ResultException;
import com.xyauto.interact.broker.server.service.cloud.ApiServiceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by shiqm on 2018-02-01.
 */

@RestController
@RequestMapping(value = "/broker/task/")
public class BrokerTaskController {


    @Autowired
    private ApiServiceFactory brokerAssistCloud;



    @RequestMapping( value = "dealer",method =RequestMethod.GET)
    public Object listByDealer(Long dealer_id, String time) throws ResultException {
        return brokerAssistCloud.BrokerAssistService().opBrokerInfoList(dealer_id,time);
    }


    @RequestMapping(value = "get",method =RequestMethod.GET)
    public Object get(Long broker_id, String time) throws ResultException{
        return brokerAssistCloud.BrokerAssistService().opBrokerInfo(broker_id,time);
    }


    @RequestMapping(value = "listpoint")
    public Object listPoint()throws ResultException{
        return brokerAssistCloud.BrokerAssistService().listPoint();
    }

    /**
     * app吃米线任务列表
     * @param broker_id
     * @param time
     * @return
     */
    @RequestMapping(value = "exttasklist")
    public Object extTaskList(long broker_id,String time) throws ResultException{
        return brokerAssistCloud.BrokerAssistService().extTaskList(broker_id,time);
    }


    @RequestMapping(value = "dealerschedulelist")
    public Object   dealerScheduleList(Long dealer_id)throws ResultException{
        return brokerAssistCloud.BrokerAssistService().dealerScheduleList(dealer_id);
    }


    @RequestMapping(value = "cluedetail")
    public Object  clueDetail(long broker_id,long target_id)throws ResultException{
        return brokerAssistCloud.BrokerTaskService().clueDetail(broker_id,target_id);
    }
}
