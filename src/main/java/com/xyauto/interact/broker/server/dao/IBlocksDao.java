package com.xyauto.interact.broker.server.dao;

import com.xyauto.interact.broker.server.model.vo.Blocks;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IBlocksDao {
    
     Blocks get(@Param(value = "name") String name);
    
}
