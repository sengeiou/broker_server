package com.xyauto.interact.broker.server.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface IDealerSettingDao {

    public List<Map<String, Object>> getCustomerIntentionLevel(@Param(value = "dealer_id") long dealerId);

    public void setCustomerIntentionLevel(@Param(value = "dealer_id") long dealerId, @Param(value = "type") int type, @Param(value = "days") int days);

    public void resetCustomerIntentionLevel(@Param(value = "dealer_id") long dealerId);
}
