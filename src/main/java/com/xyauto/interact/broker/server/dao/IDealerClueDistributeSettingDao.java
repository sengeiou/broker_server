package com.xyauto.interact.broker.server.dao;

import com.xyauto.interact.broker.server.model.vo.DealerClueDistributeSetting;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IDealerClueDistributeSettingDao {

    public DealerClueDistributeSetting getDistribution(@Param("dealer_id") long dealerId);

    public void updateDistribution(@Param("dealer_id") long dealerId, @Param("dealer_distribute") int dealerDistribute, @Param("broker_distribute") int brokerDistribute, @Param("broker_ids") String brokerIds);

}
