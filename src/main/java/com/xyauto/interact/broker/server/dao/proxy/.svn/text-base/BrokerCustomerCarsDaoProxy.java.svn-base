package com.xyauto.interact.broker.server.dao.proxy;

import com.xyauto.interact.broker.server.dao.IBrokerCustomerCarsDao;
import com.xyauto.interact.broker.server.model.po.BrokerCustomerCarsPersistant;
import com.xyauto.interact.broker.server.model.vo.BrokerCustomer;
import com.xyauto.interact.broker.server.model.vo.BrokerCustomerCars;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BrokerCustomerCarsDaoProxy {

    @Autowired
    IBrokerCustomerCarsDao customerCarsDao;

    public List<BrokerCustomerCars> getCustomerCarsByCustomerId(@Param(value = "customerId") long customerId) {
        return customerCarsDao.getCustomerCarsByCustomerId(customerId);
    }

    public int addCar(BrokerCustomerCarsPersistant car) {
        return customerCarsDao.addCar(car);
    }

    public int updateCar(BrokerCustomerCarsPersistant car) {
        return customerCarsDao.updateCar(car);
    }

    public BrokerCustomerCars getByPrimary(long brokerCustomerCarsId) {
        return customerCarsDao.getByPrimary(brokerCustomerCarsId);
    }

    public int deleteCar(long brokerId, long brokerCustomerCarId) {
        return customerCarsDao.deleteCar(brokerId, brokerCustomerCarId);
    }
}
