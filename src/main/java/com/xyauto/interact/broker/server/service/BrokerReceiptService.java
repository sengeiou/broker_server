package com.xyauto.interact.broker.server.service;

import com.xyauto.interact.broker.server.dao.proxy.BrokerCustomerDaoProxy;
import com.xyauto.interact.broker.server.dao.proxy.BrokerReceiptDaoProxy;
import com.xyauto.interact.broker.server.enums.BrokerLogEnum;
import com.xyauto.interact.broker.server.exceptions.ResultException;
import com.xyauto.interact.broker.server.model.vo.Broker;
import com.xyauto.interact.broker.server.model.vo.BrokerCustomerCarsWill;
import com.xyauto.interact.broker.server.model.vo.BrokerReceipt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class BrokerReceiptService {

    @Autowired
    BrokerReceiptDaoProxy brokerReceiptDao;

    @Autowired
    CarService carService;

    @Autowired
    SeriesService seriesService;

    @Autowired
    BrokerCustomerCarsWillService brokerCustomerCarsWillService;

    @Autowired
    BrokerCustomerDaoProxy brokerCustomerDao;

    @Autowired
    DealerService dealerService;
    
    @Autowired
    BrokerService brokerService;
    
    @Autowired
    BrokerLogService brokerLogService;

    /**
     * 根据客户id + 经纪人id 获取发票信息
     *
     * @param customerId
     * @param brokerId
     * @param broker_customer_cars_will_id
     * @return
     */
    public BrokerReceipt getBrokerReceipt(long customerId, long brokerId, long broker_customer_cars_will_id) {
        return brokerReceiptDao.getByCustomerIdAndBrokerId(customerId, brokerId, broker_customer_cars_will_id);
    }

    /**
     * 根据客户id + 经纪人id 获取发票信息
     *
     * @param customerId
     * @param brokerId
     * @return
     */
    public BrokerReceipt getByCustomerIdAndBrokerId(long customerId, long brokerId, long broker_customer_cars_will_id) {
        BrokerReceipt model = brokerReceiptDao.getByCustomerIdAndBrokerId(customerId, brokerId, broker_customer_cars_will_id);
        if (model == null) {
            return null;
        }
        //获取当前意向车型
        if (broker_customer_cars_will_id > 0) {
            BrokerCustomerCarsWill willCar = brokerCustomerCarsWillService.getCustomerCarsByID(broker_customer_cars_will_id, customerId);
            if (willCar != null) {
                model.setBrokerCustomerCarsWill(willCar);
            }
        }
        model.setBrokerCustomer(brokerCustomerDao.get(customerId));
        return model;
    }

    public List<BrokerReceipt> getInfoByBrokerId(long brokerId, String begin, String end) {
        List<BrokerReceipt> brokerReceiptList = brokerReceiptDao.getInfoByBrokerId(brokerId, begin, end);
        return brokerReceiptList;
    }

    public BrokerReceipt getByPrimaryKey(long brokerReceiptId) {
        return brokerReceiptDao.getByPrimaryKey(brokerReceiptId);
    }

    /**
     * 上传发票
     *
     * @param customerId
     * @param brokerId
     * @param carsWillId
     * @param filePath
     * @return
     */
    public boolean upLoadInvoice(long customerId, long brokerId, long carsWillId, String filePath) {
        int suc = 0;
        suc = brokerReceiptDao.upLoadInvoice(customerId, brokerId, carsWillId, filePath);
        if (suc > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     *  分配客户后处理后 处理发票信息
     * @param allotBrokerCustomer
     * @param brokerId
     * @param customerId
     * @return
     */
    public Integer AllotCustomerToBtoker(long allotBrokerCustomer,long brokerId, long customerId){
        return brokerReceiptDao.AllotCustomerToBtoker(allotBrokerCustomer,  brokerId,  customerId);
    }

    public Integer Create(BrokerReceipt record) throws ResultException, IOException {
        int ret = 0;
        if (record.getBrokerReceiptId() > 0) {
            ret = brokerReceiptDao.upLoadInvoice(record.getBrokerCustomerId(), record.getBrokerId(), record.getBrokerCustomerCarsWillId(), record.getImages());
        } else {
            ret = brokerReceiptDao.Create(record);
        }
        if (ret>0) {
            BrokerReceipt receipt = brokerReceiptDao.getByPrimaryKey(record.getBrokerReceiptId());
            Broker broker = brokerService.get(record.getBrokerId());
            if (broker!=null) {
                brokerLogService.log(broker.getBrokerId(), broker.getDealerId(), record.getBrokerCustomerId(), BrokerLogEnum.BrokerReceiptAdd, receipt, broker.getName());
            }
        }
        return ret;
    }

}
