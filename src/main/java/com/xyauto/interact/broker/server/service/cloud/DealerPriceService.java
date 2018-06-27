package com.xyauto.interact.broker.server.service.cloud;

import com.xyauto.interact.broker.server.util.Result;
import feign.Param;
import feign.RequestLine;
import org.springframework.stereotype.Component;

@Component
public interface DealerPriceService {

    //QCDQ-DEALER-PRICE-SERVER
    //pc/qcdq/serials/section?app_id=5d81db99484c0019cab240b3d04e9a4a
    @RequestLine("GET /pc/qcdq/serials/section?dealerId={dealerId}&serialIds={serialIds}&cityId=0")
    Result getDearlerPrice(@Param("dealerId") long broker_id, @Param("serialIds") String serialIds);
}
