package com.xyauto.interact.broker.server.dao.proxy;

import com.xyauto.interact.broker.server.dao.IEnumEntityDao;
import com.xyauto.interact.broker.server.model.vo.EnumEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EnumEntityDaoProxy {

    @Autowired
    IEnumEntityDao enumEntityDao;

    public List<EnumEntity> getAllByType(short type){
        return enumEntityDao.getAllByType(type);
    }


    public List<EnumEntity> getBrokerTagsListByType(List<Short> ids){
        return enumEntityDao.getBrokerTagsListByType(ids);
    }
}
