package com.xyauto.interact.broker.server.dao;

import com.xyauto.interact.broker.server.model.vo.BrokerIntegral;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


@Mapper
public interface IBrokerIntegralDao {

    int getBalance(@Param(value = "broker_id") long brokerId);

    BrokerIntegral getInfoByBrokerId(@Param(value = "broker_id") long brokerId);

    int updateByParam(@Param(value = "record") BrokerIntegral brokerIntegral);

    int initBrokerIntegral(@Param(value = "record") BrokerIntegral brokerIntegral);

}
