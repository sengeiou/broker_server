package com.xyauto.interact.broker.server.dao.proxy;

import com.xyauto.interact.broker.server.dao.IBrokerStoreTemplateDao;
import com.xyauto.interact.broker.server.model.vo.BrokerStoreTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BrokerStoreTemplateProxy  {


    @Autowired
    IBrokerStoreTemplateDao brokerStoreTemplateDao;

    public List<BrokerStoreTemplate> getAll(){
        return brokerStoreTemplateDao.getAll();
    }

    public BrokerStoreTemplate getById(long brokerTemplateId){
        return brokerStoreTemplateDao.getById(brokerTemplateId);
    }

    public BrokerStoreTemplate getDefault(){
        return brokerStoreTemplateDao.getDefault();
    }
}
