package com.xyauto.interact.broker.server.dao;

import com.xyauto.interact.broker.server.model.vo.City;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ICityDao {

    public List<City> getCityByParent(@Param(value = "parent") int parent);

    public City get(@Param(value = "city_id") int cityId);

    public List<City> getMaps(@Param(value = "ids") List<Integer> ids);

    public int matchMobileCity(@Param(value = "mobile") String mobile);
}
