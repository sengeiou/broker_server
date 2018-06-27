package com.xyauto.interact.broker.server.dao;

import com.xyauto.interact.broker.server.model.vo.SubBrand;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ISubBrandDao {

    SubBrand getSubBrand(@Param(value = "subbrand_id") int subBrandId);

    public List<SubBrand> getMaps(@Param(value = "ids") List<Integer> ids);

}
