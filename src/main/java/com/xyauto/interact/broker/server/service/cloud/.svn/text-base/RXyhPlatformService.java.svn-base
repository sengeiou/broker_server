package com.xyauto.interact.broker.server.service.cloud;

import com.xyauto.interact.broker.server.util.Result;
import feign.Param;
import feign.RequestLine;
import org.springframework.stereotype.Component;

@Component
public interface RXyhPlatformService {

    @RequestLine("GET /api/dealer/record/isaccountactive?accountId={brokerId}&time={time}")
    Result missionEnable(@Param("brokerId") long brokerId, @Param("time") String time);
    
    @RequestLine("GET /third/getAccount?accountIds={brokerId}")
    Result brokerInfo(@Param("brokerId") long brokerId);
    
    @RequestLine("GET third/getAccountById?accountId={brokerId}")
    Result brokerDetailInfo(@Param("brokerId") long brokerId);
}
