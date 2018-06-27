package com.xyauto.interact.broker.server.dao;

import com.xyauto.interact.broker.server.model.po.BrokerCustomerCarsPersistant;
import com.xyauto.interact.broker.server.model.vo.Car;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ICarDao {

    Car getCar(@Param(value = "carId") int carId);

    List<Car> getCarList(@Param(value = "serialId") int serialId);

    public List<Car> getMaps(@Param(value="ids")List<Integer> ids);

}
