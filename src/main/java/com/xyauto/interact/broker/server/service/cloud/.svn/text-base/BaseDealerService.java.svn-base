package com.xyauto.interact.broker.server.service.cloud;

import com.xyauto.interact.broker.server.util.Result;
import feign.Param;
import feign.RequestLine;
import org.springframework.stereotype.Component;

@Component
public interface BaseDealerService {

    @RequestLine("GET /dealerinfo/listbycity?cityId={cityId}&serialId={serialId}&carId={carId}&orderType={orderType}&queryType={queryType}&locationId={locationId}&pageSize={pageSize}&pageNo={pageNo}")
    public Result getBaseDealerRule(
            @Param("cityId") Integer cityId,
            @Param("serialId") Integer serialId,
            @Param("carId") Integer carId,
            @Param("orderType") Integer modelId,
            @Param("queryType") Integer queryType,
            @Param("locationId") Integer locationId,
            @Param("pageSize") Integer pageSize,
            @Param("pageNo") Integer pageNo);

    @RequestLine("GET /dealerdetail/info/{dealerId}")
    public Result getInfo(@Param("dealerId") long dealerId);
}
