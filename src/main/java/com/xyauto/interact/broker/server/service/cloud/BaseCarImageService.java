package com.xyauto.interact.broker.server.service.cloud;

import com.xyauto.interact.broker.server.util.Result;
import feign.Param;
import feign.RequestLine;
import org.springframework.stereotype.Component;

@Component
public interface BaseCarImageService {


    /**
     * 获取车型白纸图
     * @param serialIds
     * @return
     */
    @RequestLine("GET /serialpic/queryBottomPicBySerialIds/{serialIds}")
    Result getBaseSerialWhitePic(@Param("serialIds") String serialIds);

}
