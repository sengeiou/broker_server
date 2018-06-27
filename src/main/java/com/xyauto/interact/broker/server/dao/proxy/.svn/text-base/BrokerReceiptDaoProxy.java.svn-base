package com.xyauto.interact.broker.server.dao.proxy;

import com.xyauto.interact.broker.server.dao.IBrokerReceiptDao;
import com.xyauto.interact.broker.server.model.vo.BrokerReceipt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BrokerReceiptDaoProxy {

    @Autowired
    IBrokerReceiptDao brokerReceiptDao;

    public Integer upLoadInvoice( long customerId, long brokerId, long carsWillId, String filePath){
        return brokerReceiptDao.upLoadInvoice(customerId,  brokerId,  carsWillId,  filePath);
    }

    public Integer AllotCustomerToBtoker(long allotBrokerCustomer,long brokerId, long customerId){
        return brokerReceiptDao.AllotCustomerToBtoker(allotBrokerCustomer,  brokerId,  customerId);
    }

    public BrokerReceipt getByCustomerIdAndBrokerId(long customerId, long brokerId,long broker_customer_cars_will_id){
        return   brokerReceiptDao.getByCustomerIdAndBrokerId(customerId, brokerId,broker_customer_cars_will_id);
    }

    public List<BrokerReceipt> getInfoByBrokerId(long brokerId,String begin, String end){
        return  brokerReceiptDao.getInfoByBrokerId(brokerId,begin,end);
    }

    public BrokerReceipt getByPrimaryKey(long brokerReceiptId){
        return  brokerReceiptDao.getByPrimaryKey(brokerReceiptId);
    }

    public Integer Create(BrokerReceipt record){
        return  brokerReceiptDao.insert(record);
    }
}
