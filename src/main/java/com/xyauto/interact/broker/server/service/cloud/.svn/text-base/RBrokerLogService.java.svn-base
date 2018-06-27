package com.xyauto.interact.broker.server.service.cloud;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.springframework.stereotype.Component;

@Component
public interface RBrokerLogService {

    @RequestLine("GET /broker/logs/list?broker_id={broker_id}&target_id={target_id}")
    List<Map<String, Object>> logList(@Param("broker_id") long brokerId, @Param("target_id") long targetId);

    @RequestLine("POST /broker/logs/duration")
    @Headers("Content-Type: multipart/form-data")
    public TreeMap<String, Object> duration(@Param("begin") String begin, @Param("end") String end, @Param("dealer_ids") String dealerIds, @Param("broker_ids") String brokerIds, @Param("ops") String ops);

    @RequestLine("POST /broker/logs/point/sum")
    @Headers("Content-Type: multipart/form-data")
    public Map<String, Object> pointSum(@Param("date") String date, @Param("dealer_ids") String dealerIds, @Param("broker_ids") String brokerIds, @Param("ops") String ops);

    @RequestLine("POST /broker/logs/exec")
    @Headers("Content-Type: multipart/form-data")
    public List<Map<String, Object>> exec(@Param("sql") String sql);

    @RequestLine("POST /broker/logs/duration/sum")
    @Headers("Content-Type: multipart/form-data")
    public Map<String, Object> durationSum(@Param("begin") String begin, @Param("end") String end, @Param("dealer_ids") String dealerIds, @Param("broker_ids") String brokerIds, @Param("ops") String ops);

}
