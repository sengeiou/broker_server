package com.xyauto.interact.broker.server.service;

import com.xyauto.interact.broker.server.dao.proxy.BrokerCustomerCarsDaoProxy;
import com.xyauto.interact.broker.server.enums.ResultCode;
import com.xyauto.interact.broker.server.exceptions.ResultException;
import com.xyauto.interact.broker.server.model.po.BrokerCustomerCarsPersistant;
import com.xyauto.interact.broker.server.model.vo.BrokerCustomerCars;
import com.xyauto.interact.broker.server.model.vo.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.StyledEditorKit;
import java.util.List;

@Service
public class BrokerCustomerCarsSerivce {

    @Autowired
    BrokerCustomerCarsDaoProxy dao;

    @Autowired
    CarService carService;

    @Autowired
    SeriesService seriesService;

    public List<BrokerCustomerCars> getCustomerCarsByCustomerId(long customerId)  throws ResultException {
        List<BrokerCustomerCars> model = dao.getCustomerCarsByCustomerId(customerId);
        if(model!=null) {
            int index = 0;
            for (BrokerCustomerCars car : model) {
                model.get(index).setCarInfo(carService.getCar(car.getCarId()));
                model.get(index).setSeriesInfo(seriesService.getSeries(car.getSeriesId()));
                index++;
            }
        }
        return model;
    }

    public  Integer addCar(BrokerCustomerCarsPersistant car){
        return  dao.addCar(car);
    }

    public  Integer updateCar(BrokerCustomerCarsPersistant car){
        return  dao.updateCar(car);
    }

    public BrokerCustomerCars getByPrimary(long brokerCustomerCarsId){
        return  dao.getByPrimary(brokerCustomerCarsId);
    }

    public BrokerCustomerCars getByPrimaryWitCarInfo(long brokerCustomerCarsId){
        BrokerCustomerCars brokerCustomerCars =  dao.getByPrimary(brokerCustomerCarsId);
        if(brokerCustomerCars!=null && brokerCustomerCars.getCarId()>0) {
            Car car = carService.getCar(brokerCustomerCars.getCarId());
            if(car!=null) {
                brokerCustomerCars.setCarInfo(car);
                brokerCustomerCars.setSeriesInfo(seriesService.getSeries(car.getSeriesId()));
            }
        }
        return brokerCustomerCars;
    }

    public int deleteCar(long brokerId, long brokerCustomerCarId) {
        return dao.deleteCar(brokerId, brokerCustomerCarId);
    }
}
