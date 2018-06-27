package com.xyauto.interact.broker.server.service;

import com.google.common.collect.Maps;
import com.xyauto.interact.broker.server.dao.proxy.BrokerCustomerCarsDaoProxy;
import com.xyauto.interact.broker.server.dao.proxy.BrokerCustomerCarsWillDaoProxy;
import com.xyauto.interact.broker.server.enums.BrokerLogEnum;
import com.xyauto.interact.broker.server.model.po.BrokerCustomerCarsPersistant;
import com.xyauto.interact.broker.server.model.po.BrokerCustomerUpdateParameters;
import com.xyauto.interact.broker.server.model.vo.Broker;
import com.xyauto.interact.broker.server.model.vo.BrokerCustomer;
import com.xyauto.interact.broker.server.model.vo.BrokerCustomerCarsWill;
import com.xyauto.interact.broker.server.model.vo.BrokerDealRecord;
import com.xyauto.interact.broker.server.model.vo.BrokerDealVo;
import com.xyauto.interact.broker.server.model.vo.Car;
import com.xyauto.interact.broker.server.model.vo.Series;
import java.util.AbstractMap;
import java.util.AbstractMap.SimpleEntry;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class BrokerCustomerCarsWillService {

    @Autowired
    private BrokerCustomerCarsWillDaoProxy dao;
    
    @Autowired
    private BrokerReceiptService brokerReceiptService;

    @Autowired
    private BrandService brandService;

    @Autowired
    private SubBrandService subBrandService;

    @Autowired
    private SeriesService seriesService;

    @Autowired
    private CarService carService;
    
    @Autowired
    private BrokerService brokerService;
    
    @Autowired
    private BrokerLogService brokerLogService;
    
    @Autowired
    @Lazy
    private BrokerCustomerService brokerCustomerService;

    @Autowired
    BrokerCustomerCarsDaoProxy brokerCustomerCarsDaoProxy;
    /**
     * 通过线索ID获取意向车款
     *
     * @param clueId
     * @return
     */
    public BrokerCustomerCarsWill getCustomerCarsByClueID(long clueId) {
        return dao.getCarsWillByClueId(clueId);
    }

    /**
     * 根据主键获取意向车型
     * @param brokerCustomerCarsWillId
     * @return
     */
    public BrokerCustomerCarsWill getCarsWillByPrimaryKey(long brokerCustomerCarsWillId){
        return dao.getCarsWillByPrimaryKey(brokerCustomerCarsWillId);
    }

    /**
     * 购车完成设置为保有车辆
     * @param brokerCustomerCarsWillId
     * @return
     */
    public int BuyCarFinsh(long brokerCustomerCarsWillId){
        int suc = dao.BuyCarFinsh(brokerCustomerCarsWillId);
        //设置意向车型为保有车辆
        if(suc>0){
            BrokerCustomerCarsWill brokerCustomerCarsWill = this.getCarsWillByPrimaryKey(brokerCustomerCarsWillId);
            BrokerCustomerCarsPersistant car = new BrokerCustomerCarsPersistant();
            Car c = carService.getCar(brokerCustomerCarsWill.getCarId());
            car.setBrandId(c.getBrandId());
            car.setSeriesId(c.getSeriesId());
            car.setSubBrandId(c.getSubBrandId());
            car.setCarId(c.getCarId());
            car.setBrokerCustomerId(brokerCustomerCarsWill.getBrokerCustomerId());
            car.setBrokerCustomerCarsWillId(brokerCustomerCarsWill.getBrokerCustomerCarsWillId());
            brokerCustomerCarsDaoProxy.addCar(car);
        }
        return  suc;
    }

    /**
     * 根据主键获取客户意向车型
     *
     * @param carsWillId z主键
     * @param brokerCustomerId
     * @return
     */
    public BrokerCustomerCarsWill getCustomerCarsByID(long carsWillId, long brokerCustomerId) {
        return dao.getCustomerCarsByID(carsWillId, brokerCustomerId);
    }

    /**
     * 根据客户id 查看意向车型 (最后一条意向车型)
     *
     * @param brokerCustomerId
     * @return
     */
    BrokerCustomerCarsWill getCarsWillByCustomerId(long brokerCustomerId) {
        return dao.getCarsWillByCustomerId(brokerCustomerId);
    }

    public Integer updateParamByCustomerId(BrokerCustomerCarsWill record) {
        return dao.updateParamByCustomerId(record);
    }

    public List<BrokerDealRecord> getDealRecords(List<Long> brokerIds) {
        List<BrokerDealRecord> list = dao.getDealRecords(brokerIds);
        list.forEach(item -> {
            item.setBrand(brandService.getBrand(item.getBrandId()));
            item.setSubBrand(subBrandService.getSubBrand(item.getSubBrandId()));
            item.setSeries(seriesService.getSeries(item.getSeriesId()));
            item.setCar(carService.getCar(item.getCarId()));
        });
        return list;
    }

    /**
     * 创建客户意向车型
     *
     * @param model
     * @return
     */
    public int create(BrokerCustomerCarsWill model) {
        return dao.create(model);
    }

    /**
     * 获取经纪人时间段售卖数量
     *
     * @param brokerId
     * @param dealTime
     * @return
     */
    public int getBrokerDealCount(long brokerId, String dealTime) {
        return dao.getBrokerDealCount(brokerId, dealTime);
    }

    public List<BrokerDealVo> getDealRecords(Long dealerId, Long brokerId, Integer brandId,
            Integer seriesId, Integer carId, Date startTime, Date endTime,
            Integer status, Integer page, Integer limit) {
        return dao.getDealRecords(dealerId, brokerId, brandId, seriesId, carId,
                startTime, endTime, status, page, limit);
    }

    public int getDealRecordsCount(Long dealerId, Long brokerId, Integer brandId,
            Integer seriesId, Integer carId, Date startTime, Date endTime,
            Integer status) {
        return dao.getDealRecordsCount(dealerId, brokerId, brandId, seriesId, carId,
                startTime, endTime, status);
    }

    /**
     * 新增或修改客户意向车型
     *
     * @param params
     */
    public int update(BrokerCustomerUpdateParameters params) {
        int ret = dao.update(params);
        if (ret>0) {
            Broker broker = brokerService.get(params.getBrokerId());
            BrokerCustomer brokerCustomer = brokerCustomerService.get(params.getBrokerCustomerId());
            if (params.getCarId()>0) {
                Car car = carService.getCar(params.getCarId());
                if (car!=null) {
                    brokerLogService.log(broker.getBrokerId(), broker.getDealerId(), brokerCustomer.getBrokerCustomerId(), BrokerLogEnum.BrokerCustomerCarsWillUpdate, brokerCustomer, broker.getName(), car.getName());
                }
            }
        }
        return ret;
    }

    /**
     * 获取最新未成交的意向车型
     *
     * @param brokerId
     * @param brokerCustomerId
     * @return
     */
    public BrokerCustomerCarsWill getByBrokerCustomerId(long brokerId, long brokerCustomerId) {
        BrokerCustomerCarsWill carsWill = dao.getCarsWillByCustomerId(brokerCustomerId);
        if (carsWill == null) {
            return null;
        }
        Car car = carService.getCar(carsWill.getCarId());
        if (car!=null) {
            carsWill.setCarInfo(car);
            Series series = seriesService.getSeries(car.getSeriesId());
            if (series != null) {
                carsWill.setSeriesInfo(series);
                carsWill.setBrandInfo(brandService.getBrand(series.getBrandId()));
            }
        }
        carsWill.setBrokerReceipt(brokerReceiptService.getBrokerReceipt(brokerCustomerId, brokerId, carsWill.getBrokerCustomerCarsWillId()));
        return carsWill;
    }

    /**
     * 获取意向车型键值对列表
     * @param keys
     * @return 
     */
    Map<SimpleEntry<Long, Long>, BrokerCustomerCarsWill> getMaps(List<SimpleEntry<Long, Long>> keys) {
        Map<SimpleEntry<Long, Long>, BrokerCustomerCarsWill> data = Maps.newConcurrentMap();
        if (keys.isEmpty()) {
            return data;
        }
        List<BrokerCustomerCarsWill> list = dao.getMaps(keys);
        list.forEach(item -> {
            data.put(new SimpleEntry<Long, Long>(item.getBrokerId(), item.getBrokerCustomerId()), item);
        });
        return data;
    }
}
