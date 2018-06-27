package com.xyauto.interact.broker.server.service.cloud;

import com.xyauto.interact.broker.server.util.Result;
import feign.Param;
import feign.RequestLine;
import org.springframework.stereotype.Component;

@Component
public interface CallCenterService {
    
    @RequestLine("GET /call/number/{brokerId}/1")
    public Result getBrokerPhone(@Param("brokerId") long brokerId);
    
}

