package com.xyauto.interact.broker.server.service.cloud;

import com.xyauto.interact.broker.server.util.Result;
import feign.Param;
import feign.RequestLine;

public interface RClueService {

    @RequestLine("GET /api/bussiness/grabBusinessOpportunity?businessPoolId={businessPoolId}&dealerId={dealerId}")
    Result grabBusinessOpportunity(@Param("businessPoolId") long businessPoolId, @Param("dealerId") long dealerId);

    @RequestLine("GET /api/outcall/getCoutCallFeedBackInfo?customerid={customerId}")
    Result getCoutCallFeedBackInfo(@Param("customerId") long customerId);

    @RequestLine("GET /api/bussiness/getBussinessInfoByBusinessPoolId?businessPoolId={cluePoolId}")
    Result getInfoByPoolId(@Param("cluePoolId") long cluePoolId);
}
