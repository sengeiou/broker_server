package com.xyauto.interact.broker.server.dao;

import com.xyauto.interact.broker.server.model.vo.BrokerIntegralLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IBrokerIntegralLogDao {


    public List<BrokerIntegralLog> getPageListByBrokerId(
            @Param(value = "brokerId") long brokerId,
            @Param(value = "limit") int limit,
            @Param(value = "page") int page,
            @Param(value = "begin") int begin);

    public List<BrokerIntegralLog> getListByBrokerId(
            @Param(value = "brokerId") long brokerId,
            @Param(value = "limit") int limit,
            @Param(value = "max") String max,
            @Param(value = "begin") int begin);

    public int getListCountByBrokerId(
            @Param(value = "brokerId") long brokerId,
            @Param(value = "max") String max,
            @Param(value = "begin") int begin);

    public int getPageListCountByBrokerId(
            @Param(value = "brokerId") long brokerId,
            @Param(value = "begin") int begin);

    public int create(BrokerIntegralLog brokerIntegralLog);
}
