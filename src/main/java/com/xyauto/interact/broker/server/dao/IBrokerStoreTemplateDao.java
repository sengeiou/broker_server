package com.xyauto.interact.broker.server.dao;

import com.xyauto.interact.broker.server.model.vo.BrokerStoreTemplate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IBrokerStoreTemplateDao {

    List<BrokerStoreTemplate> getAll();

    BrokerStoreTemplate getDefault();

    BrokerStoreTemplate getById(@Param("brokerTemplateId") long brokerTemplateId);
    //int updateByPrimaryKeySelective(BrokerStoreTemplate model);
}
