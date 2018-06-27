package com.xyauto.interact.broker.server.dao;

import com.xyauto.interact.broker.server.model.vo.BrokerNoticeSetting;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


@Mapper
public interface IBrokerNoticeSettingDao {

    public BrokerNoticeSetting getSettingByBrokerId(@Param(value = "brokerId") long brokerId);

    public Integer updateByParam(BrokerNoticeSetting brokerNoticeSetting);
}
