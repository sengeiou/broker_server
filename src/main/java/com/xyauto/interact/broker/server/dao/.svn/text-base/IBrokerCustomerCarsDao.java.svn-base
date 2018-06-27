package com.xyauto.interact.broker.server.dao;

import com.xyauto.interact.broker.server.model.po.BrokerCustomerCarsPersistant;
import com.xyauto.interact.broker.server.model.vo.BrokerCustomerCars;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IBrokerCustomerCarsDao {

    List<BrokerCustomerCars> getCustomerCarsByCustomerId(@Param(value = "customerId") long customerId);

    int addCar(BrokerCustomerCarsPersistant car);

    int updateCar(BrokerCustomerCarsPersistant car);

    int deleteCar(@Param(value = "customerCarId") Long customerCarId);

    BrokerCustomerCars getByPrimary(@Param(value = "brokerCustomerCarsId") long brokerCustomerCarsId);

    public int deleteCar(@Param(value = "broker_id") long brokerId, @Param(value = "broker_customer_car_id") long brokerCustomerCarId);
}
