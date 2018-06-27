package com.xyauto.interact.broker.server.dao;

import com.xyauto.interact.broker.server.model.vo.EnumEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import sun.awt.SunHints;

import java.util.List;

@Mapper
public interface IEnumEntityDao {

    public List<EnumEntity> getAllByType(@Param(value = "type") short type);

    public List<EnumEntity> getBrokerTagsListByType(@Param(value ="ids") List<Short> ids);
}
