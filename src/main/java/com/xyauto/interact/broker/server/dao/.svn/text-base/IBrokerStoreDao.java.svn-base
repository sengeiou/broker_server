package com.xyauto.interact.broker.server.dao;

import com.xyauto.interact.broker.server.model.vo.BrokerStore;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IBrokerStoreDao {

    Integer updateParamByStoreId(@Param(value = "record") BrokerStore record);

    Integer updateParamByBrokerId(@Param(value = "record") BrokerStore record);

    BrokerStore get(@Param(value = "brokerId") long brokerId);

    //#{limit} offset #{page}
    List<BrokerStore> getByDealerId(@Param(value = "dealerId") long dealerId,
                                    @Param(value = "limit") long limit,
                                    @Param(value = "page") long page);

    Integer create(@Param(value = "record") BrokerStore brokerStore);
    
    int getCountByDealerId(@Param(value = "dealerId") long dealerId);

}
