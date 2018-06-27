package com.xyauto.interact.broker.server.service.cloud;

import com.xyauto.interact.broker.server.util.Result;
import feign.Param;
import feign.RequestLine;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public interface InfomationService {

    ///promotionNews/queryDealerSerialFavorablePrice?dealerId=100319412&serialIds=2381,2417,2421,2445,2791,2999,3624,3844,4707
    @RequestLine("GET /promotionNews/queryDealerSerialFavorablePrice?dealerId={dealerId}&serialIds={serialIds}")
    Result queryDealerSerialFavorablePrice(@Param("dealerId") long broker_id,@Param("serialIds") String serialIds);
}
