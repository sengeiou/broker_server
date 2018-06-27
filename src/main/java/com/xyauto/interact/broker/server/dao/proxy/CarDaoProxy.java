package com.xyauto.interact.broker.server.dao.proxy;

import com.xyauto.interact.broker.server.dao.ICarDao;
import com.xyauto.interact.broker.server.model.vo.Car;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class CarDaoProxy {
    @Autowired
    ICarDao carDao;

    @Cacheable(value="car", key ="'car'.concat(#carId.toString())")
    public Car getCar(int carId){
        return carDao.getCar(carId);
    }

    /**
     * 获取车款列表
     * @param ids
     * @return 
     */
    public List<Car> getMaps(List<Integer> ids) {
        return carDao.getMaps(ids);
    }


}
