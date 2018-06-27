package com.xyauto.interact.broker.server.service.cloud;

import com.xyauto.interact.broker.server.util.Result;
import feign.Param;
import feign.RequestLine;
import org.springframework.stereotype.Component;

@Component
public interface RDataCenterService {

    @RequestLine("GET /api/fnclue/queryScores?customerId={customerId}&dealerId={dealerId}")
    public Result queryScores(@Param("customerId") long customerId, @Param("dealerId") long dealerId);

    @RequestLine("GET /api/fnclue/querySerialProportion?customerId={customerId}")
    public Result querySerialProportion(@Param("customerId") long customerId);
    
    @RequestLine("GET /api/fnclue/queryCustomerPurchaseBudget?customerId={customerId}")
    public Result queryPurchaseBudget(@Param("customerId") long customerId);
}