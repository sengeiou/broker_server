package com.xyauto.interact.broker.server.service.cloud;

import com.xyauto.interact.broker.server.util.Result;
import feign.Param;
import feign.RequestLine;

public interface RClueHandleService {


    @RequestLine("GET /api/bussiness/updateBussinessStatus?opportunityRelDealerId={clueId}&dealerId={dealerId}")
    Result handle(@Param("clueId") long clueId, @Param("dealerId") long dealerId);
    
    @RequestLine("GET /api/bussiness/updateBatchBussinessStatus?opportunityRelDealerIds={clueId}&dealerId={dealerId}")
    Result handleList(@Param("clueId") String clueId, @Param("dealerId") long dealerId);

}
