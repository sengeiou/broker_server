package com.xyauto.interact.broker.server.service;

import com.xyauto.interact.broker.server.dao.proxy.EnumEntityDaoProxy;
import com.xyauto.interact.broker.server.model.vo.EnumEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnumEntityService {

    @Autowired
    EnumEntityDaoProxy enumEntityDaoProxy;

    /**
     * 获取所有标签
     * @param type
     * @return
     */
    public List<EnumEntity> getAllByType(short type){
        return enumEntityDaoProxy.getAllByType(type);
    }



    /**
     * 根据id 获取标签数据
     * @param ids
     * @return
     */
    public List<EnumEntity> getBrokerTagsListByType(List<Short> ids){
        return enumEntityDaoProxy.getBrokerTagsListByType(ids);
    }
}
