package com.xyauto.interact.broker.server.controller;

import com.mcp.validate.annotation.Check;

import com.xyauto.interact.broker.server.enums.ResultCode;
import com.xyauto.interact.broker.server.exceptions.ResultException;
import com.xyauto.interact.broker.server.model.po.BrokerCustomerCarsPersistant;
import com.xyauto.interact.broker.server.model.vo.BrokerCustomerCars;
import com.xyauto.interact.broker.server.service.BrokerCustomerCarsSerivce;
import com.xyauto.interact.broker.server.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author liucx
 * @date 2018-02-06
 */
@RestController
@RequestMapping("/customer/car")
public class BrokerCustomerCarController extends BaseController {

    @Autowired
    BrokerCustomerCarsSerivce carService;

    /**
     * 16 修改用户保有车辆信息
     *
     * @param customerCarId 主键保有车辆id
     * @param customerId 客户id
     * @param willId 客户意向id
     * @param brandId 品牌id
     * @param subBrandId 子品牌id
     * @param seriesId 车型id
     * @param carId 车款id
     * @param plateNumber 车牌号码
     * @param vin
     * @param ven 发动机码
     * @param registerTime 注册时间
     * @param nextMainTime 下次保养时间
     * @param nextMainKM 下次保养里程
     * @param insurer 保险公司
     * @param tciexpireTime 交强险到期时间
     * @param ciexpireTime 商业险到期时间
     * @return
     * @throws com.xyauto.interact.broker.server.exceptions.ResultException
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Result updateCustomerOwnCar(@Check(value = "customer_car_id", number = true, required = true) Long customerCarId,
            @Check(value = "customer_id", number = true, required = true) Long customerId,
            @Check(value = "car_will_id", number = true, required = false, defaultValue = "0") Long willId,
            @Check(value = "brand_id", number = true, required = false, defaultValue = "0") Integer brandId,
            @Check(value = "sub_brand_id", number = true, required = false, defaultValue = "0") Integer subBrandId,
            @Check(value = "series_id", number = true, required = true) Integer seriesId,
            @Check(value = "car_id", number = true, required = true) Integer carId,
            @Check(value = "plate_number", required = false, defaultValue = "") String plateNumber,
            @Check(value = "vin", required = false, defaultValue = "") String vin,
            @Check(value = "ven", required = false, defaultValue = "") String ven,
            @Check(value = "register_time", required = false,defaultValue = "0") long registerTime,
            @Check(value = "next_main_time", required = false,defaultValue = "0") long nextMainTime,
            @Check(value = "next_main_km", required = false, defaultValue = "0") Integer nextMainKM,
            @Check(value = "insurer", required = false, defaultValue = "") String insurer,
            @Check(value = "tci_expire_time", required = false,defaultValue = "0") long tciexpireTime,
            @Check(value = "ciexpire_time", required = false,defaultValue = "0") long ciexpireTime
    ) throws ResultException {
        BrokerCustomerCarsPersistant car = new BrokerCustomerCarsPersistant();
        car.setBrandId(brandId);
        car.setBrokerCustomerCarsId(customerCarId);
        car.setBrokerCustomerCarsWillId(willId);
        car.setBrokerCustomerId(customerId);
        car.setCarId(carId);
        //car.setCiExpireTime(ciEndTime/1000);
        car.setCiExpireTime(String.valueOf(ciexpireTime).length()==10  ? ciexpireTime/1000:ciexpireTime);
        //car.setCreateTime(String.valueOf(ciEndTime).length()==10 /1000);
        car.setInsurer(insurer);
        car.setNextMaintenanceKm(nextMainKM);
        car.setNextMaintenanceTime(String.valueOf(nextMainTime).length()==10 ?nextMainTime:nextMainTime/1000);
        car.setPlateNumber(plateNumber);
        car.setRegisterTime(String.valueOf(registerTime).length()==10 ?registerTime:registerTime/1000);
        //car.setRegisterTime(registerTime/1000);
        car.setSeriesId(seriesId);
        car.setSubBrandId(subBrandId);
        //car.setTciExpireTime(tciEndTime/1000);
        car.setTciExpireTime(String.valueOf(tciexpireTime).length()==10 ?tciexpireTime:tciexpireTime/1000);
        car.setVen(ven);
        car.setVin(vin);
        int suc = 0;
        try {
             suc = carService.updateCar(car);
        }catch (Exception ex){
            return result.format(ResultCode.UnKnownError,ex.getMessage());
        }

        if (suc > 0) {
            BrokerCustomerCars brokerCustomerCars = carService.getByPrimaryWitCarInfo(customerCarId);
            return result.format(ResultCode.Success, brokerCustomerCars);
        }
        return result.format(ResultCode.UnKnownError);
    }


    /**
     * 16 添加用户保有车辆信息
     *
     * @param customerId 客户id
     * @param willId 客户意向id
     * @param brandId 品牌id
     * @param subBrandId 子品牌id
     * @param seriesId 车型id
     * @param carId 车款id
     * @param plateNumber 车牌号码
     * @param vin
     * @param ven 发动机码
     * @param registerTime 注册时间
     * @param nextMainTime 下次保养时间
     * @param nextMainKM 下次保养里程
     * @param insurer 保险公司
     * @param tciExpireTime
     * @param CiexpireTime
     * @return
     * @throws com.xyauto.interact.broker.server.exceptions.ResultException
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result addCustomerOwnCar(@Check(value = "customer_id", number = true, required = true) long customerId,
            @Check(value = "car_will_id", number = true, required = false, defaultValue = "0") long willId,
            @Check(value = "brand_id", number = true, required = false, defaultValue = "0") int brandId,
            @Check(value = "sub_brand_id", number = true, required = false, defaultValue = "0") int subBrandId,
            @Check(value = "series_id", number = true, required = true) int seriesId,
            @Check(value = "car_id", number = true, required = true) int carId,
            @Check(value = "plate_number", required = false, defaultValue = "") String plateNumber,
            @Check(value = "vin", required = false, defaultValue = "") String vin,
            @Check(value = "ven", required = false, defaultValue = "") String ven,
            @Check(value = "register_time", required = false,defaultValue = "0") long registerTime,
            @Check(value = "next_main_time", required = false,defaultValue = "0") long nextMainTime,
            @Check(value = "next_main_km", required = false, defaultValue = "0") int nextMainKM,
            @Check(value = "insurer", required = false, defaultValue = "") String insurer,
            @Check(value = "tci_expire_time", required = false,defaultValue = "0") long tciExpireTime,
            @Check(value = "ciexpire_time", required = false,defaultValue = "0") long CiexpireTime
    ) throws ResultException {
        BrokerCustomerCarsPersistant car = new BrokerCustomerCarsPersistant();
        car.setBrandId(brandId);
        car.setBrokerCustomerCarsWillId(willId);
        car.setBrokerCustomerId(customerId);
        car.setCarId(carId);

        car.setCiExpireTime(String.valueOf(CiexpireTime).length()==10? CiexpireTime/1000:CiexpireTime);
        car.setInsurer(insurer);
        car.setNextMaintenanceKm(nextMainKM);
        car.setNextMaintenanceTime(String.valueOf(nextMainTime).length()==10 ? nextMainTime/1000:nextMainTime);
        car.setPlateNumber(plateNumber);
        car.setRegisterTime(String.valueOf(registerTime).length()==10? registerTime/1000:registerTime);
        car.setSeriesId(seriesId);
        car.setSubBrandId(subBrandId);
        car.setTciExpireTime(String.valueOf(tciExpireTime).length()== 10 ?tciExpireTime/1000:tciExpireTime);
        car.setVen(ven);
        car.setVin(vin);
        int suc = carService.addCar(car);
        if (suc > 0) {
            //List<BrokerCustomerCars> brokerCustomerCars = carService.getCustomerCarsByCustomerId(customerId);
            //BrokerCustomerCars brokerCustomerCar = brokerCustomerCars.sort(1); Collections.sort(1)
            //BrokerCustomerCars brokerCustomerCars = carService.getByPrimary(car.getBrokerCustomerCarsId());
            BrokerCustomerCars brokerCustomerCars = carService.getByPrimaryWitCarInfo(car.getBrokerCustomerCarsId());
            return result.format(ResultCode.Success, brokerCustomerCars);
        }
        return result.format(ResultCode.UnKnownError);
    }

    /**
     * 删除客户保有车辆
     *
     * @param brokerId
     * @param brokerCustomerCarId
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Result deleteCustomerCar(
            @Check(value = "broker_id", required = true) long brokerId,
            @Check(value = "broker_customer_car_id", required = true) long brokerCustomerCarId
    ) {
        //删除操作;
        int ret = carService.deleteCar(brokerId, brokerCustomerCarId);
        if (ret > 0) {
            return result.format(ResultCode.Success);
        }
        return result.format(ResultCode.UnKnownError);
    }

}
