package com.xyauto.interact.broker.server.service.cloud;

import com.xyauto.interact.broker.server.util.Result;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

public interface RMissionService {

    @RequestLine("GET /task/list?page={page}&limit={limit}&status=1")
    Result list(@Param("page") int page, @Param("limit") int limit);

    @RequestLine("GET /task/export/broker/day?uid={broker_id}")
    Result getCurrentTaskProgress(@Param("broker_id") long brokerId);

    @RequestLine("POST /task/export/broker/day")
    @Headers("Content-Type: multipart/form-data")
    Result getBatchTaskProgress(@Param("uid") String brokerIds);

}
