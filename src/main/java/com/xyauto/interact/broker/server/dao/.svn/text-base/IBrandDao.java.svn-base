package com.xyauto.interact.broker.server.dao;

import com.xyauto.interact.broker.server.model.vo.Brand;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IBrandDao {

    Brand getBrand(@Param(value = "brand_id") int brandId);

    List<Brand> getBrandList();

    List<Brand> getBrandListByBrandIds(@Param(value = "ids") List<Integer> ids);

    public List<Brand> getMaps(@Param(value = "ids") List<Integer> ids);

}
