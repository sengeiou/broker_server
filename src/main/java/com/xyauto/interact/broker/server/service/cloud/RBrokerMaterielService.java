package com.xyauto.interact.broker.server.service.cloud;

import com.mcp.fastcloud.annotation.ServerName;
import com.xyauto.interact.broker.server.util.Result;
import feign.RequestLine;

@ServerName(value = "qcdq-interact-sp", applyClass = ApiServiceInterceptor.class)
public interface RBrokerMaterielService {

    @RequestLine("GET /task/list")
    Result list();

    @RequestLine("GET /task/plugin/list")
    Result pluginList();

}