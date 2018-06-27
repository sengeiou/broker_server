package com.xyauto.interact.broker.server.service;

import com.google.common.collect.Maps;
import com.xyauto.interact.broker.server.dao.proxy.CarDaoProxy;
import com.xyauto.interact.broker.server.model.vo.Car;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarService {

    @Autowired
    CarDaoProxy dao;

    public Car getCar(int carId){
        return dao.getCar(carId);
    }

    /**
     * 获取车款键值对列表
     * @param carIds
     * @return 
     */
    Map<Integer, Car> getMaps(List<Integer> ids) {
        Map<Integer, Car> data = Maps.newConcurrentMap();
        if (ids.isEmpty()) {
            return data;
        }
        List<Car> list = dao.getMaps(ids);
        list.forEach(car -> {
            data.put(car.getCarId(), car);
        });
        return data;
    }
    
    

}
