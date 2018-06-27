package com.xyauto.interact.broker.server.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IDealerCarDao {

    public List<Integer> getListByDealer(@Param(value = "dealerId") long dealerId);

    public List<Integer> getSeriesIdsByDealer(@Param(value = "dealerId") long dealerId);

}
