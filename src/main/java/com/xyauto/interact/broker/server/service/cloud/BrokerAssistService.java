package com.xyauto.interact.broker.server.service.cloud;

import com.mcp.fastcloud.util.Result;
import feign.Param;
import feign.RequestLine;

public interface BrokerAssistService {

    @RequestLine("GET /task/list")
    Result list();

    @RequestLine("GET /task/plugin/list")
    Result pluginList();

    /**
     * 通过经销商ID获取所有经纪人任务完成度列表
     *
     * @return
     */
    @RequestLine("GET /task/op/dealer/list?dealer_id={dealer_id}&time={time}")
    Result opBrokerInfoList(@Param("dealer_id") Long dealer_id, @Param("time") String time);


    /**
     * 通过经纪人ID获取任务信息
     *
     * @return
     */
    @RequestLine("GET /task/op/ext/list?uid={uid}&time={time}")
    Result opBrokerInfo(@Param("uid") Long broker_id, @Param("time") String time);


    /**
     * 获取积分任务列表
     *
     * @return
     */
    @RequestLine("GET /task/list/point")
    Result listPoint();


    /**
     * app 吃米线任务列表
     * @return
     */
    @RequestLine("GET /task/ext/list?uid={broker_id}&time={time}")
    Result extTaskList(@Param("broker_id") long broker_id,@Param("time") String time);


    /**
     * 经销商排期列表
     * @param dealer_id
     * @return
     */
    @RequestLine("GET /task/export/dealer/Schedule/list?dealer_id={dealer_id}")
    Result  dealerScheduleList(@Param("dealer_id") Long dealer_id);
}
