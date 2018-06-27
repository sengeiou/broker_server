package com.xyauto.interact.broker.server.service.cloud;

import com.xyauto.interact.broker.server.util.Result;
import feign.RequestLine;
import feign.Param;
import org.springframework.stereotype.Component;

@Component
public interface BaseCarService {

    @RequestLine("GET /serial/getBaseCarSerials?serialIds={serialIds}")
    Result getBaseCarSerials(@Param("serialIds") String serialIds);
}
