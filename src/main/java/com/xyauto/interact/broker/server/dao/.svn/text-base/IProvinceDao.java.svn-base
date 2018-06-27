package com.xyauto.interact.broker.server.dao;

import com.xyauto.interact.broker.server.model.vo.Province;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IProvinceDao {

    public Province get(@Param(value = "province_id") int provinceId);

    public List<Province> getMaps(@Param(value = "ids") List<Integer> ids);
}
