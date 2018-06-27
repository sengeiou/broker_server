package com.xyauto.interact.broker.server.dao.proxy;

import com.xyauto.interact.broker.server.dao.IBrokerStoreDao;
import com.xyauto.interact.broker.server.model.vo.BrokerStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BrokerStoreDaoProxy {

    @Autowired
    IBrokerStoreDao brokerStoreDao;

    public Integer updateParamByStoreId(BrokerStore record){
        return brokerStoreDao.updateParamByStoreId(record);
    }

    public Integer updateParamByBrokerId(BrokerStore record){
        return brokerStoreDao.updateParamByBrokerId(record);
    }

    public BrokerStore get(long brokerId){
        return  brokerStoreDao.get(brokerId);
    }

    public List<BrokerStore> getByDealerId(long dealerId,long limit ,long page){
        return  brokerStoreDao.getByDealerId(dealerId , limit , page);
    }

    public Integer create( BrokerStore brokerStore){
        return  brokerStoreDao.create(brokerStore);
    }
    
    public int getCountByDealerId(long dealerId){
        return  brokerStoreDao.getCountByDealerId(dealerId );
    }
}
