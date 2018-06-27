package com.xyauto.interact.broker.server.controller;

import com.alibaba.fastjson.JSONObject;
import com.mcp.validate.annotation.Check;
import com.xyauto.interact.broker.server.enums.BrokerTypeEnum;
import com.xyauto.interact.broker.server.enums.InvoiceEnum.InvoiceStatus;
import com.xyauto.interact.broker.server.enums.ResultCode;
import com.xyauto.interact.broker.server.exceptions.ResultException;
import com.xyauto.interact.broker.server.model.vo.Broker;
import com.xyauto.interact.broker.server.model.vo.BrokerCustomerCarsWill;
import com.xyauto.interact.broker.server.model.vo.BrokerDealVo;
import com.xyauto.interact.broker.server.model.vo.Car;
import com.xyauto.interact.broker.server.model.vo.PagedList;
import com.xyauto.interact.broker.server.service.BrokerCustomerCarsWillService;
import com.xyauto.interact.broker.server.service.BrokerLogService;
import com.xyauto.interact.broker.server.service.BrokerService;
import com.xyauto.interact.broker.server.service.CarService;
import com.xyauto.interact.broker.server.util.BrokerLogUtil;
import com.xyauto.interact.broker.server.util.DateStyle;
import com.xyauto.interact.broker.server.util.DateUtil;
import com.xyauto.interact.broker.server.util.Result;
import com.xyauto.interact.broker.server.util.excel.ExcelUtils;
import com.xyauto.interact.broker.server.util.excel.ExcelVo;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 客户意向车型
 */
@RestController
@RequestMapping("/customer/carwill")
public class BrokerCustomerCarsWillController extends BaseController {

    @Autowired
    BrokerCustomerCarsWillService carsWillService;

    @Autowired
    CarService carService;

    @Autowired
    BrokerService brokerService;

    @Autowired
    BrokerLogService brokerLogService;

    /**
     * 根据参数修改客户意向车型信息
     *
     * @param brokerClueId 任务id
     * @param carId 车款id
     * @param category 购车类型，1新车，2二手车
     * @param payType 支付方式，1全款，2贷款
     * @param exchangeType 置换方式，1非置换，2置换
     * @param isAllopatryRegister 是否异地上牌，0否，1是
     * @param CustomerId 客户id
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Result UpdateCustomerCarWill(
            @Check(value = "brokerClueId", required = false) Long brokerClueId,
            @Check(value = "car_id", required = false) Integer carId,
            @Check(value = "category", required = false) Short category,
            @Check(value = "pay_type", required = false) Short payType,
            @Check(value = "exchange_type", required = false) Short exchangeType,
            @Check(value = "allopatry_register", required = false) Short isAllopatryRegister,
            @Check(value = "customer_id", required = true) Long CustomerId,
            @Check(value = "target_broker_id", required = true, defaultValue = "0") Long targetBrokerId) {
        BrokerCustomerCarsWill model = new BrokerCustomerCarsWill();
        Broker broker = null;
        if (targetBrokerId != null && targetBrokerId > 0) {
            broker = brokerService.get(targetBrokerId);
        }
        if (CustomerId <= 0) {
            return result.format(ResultCode.ERROR_PARAMS);
        }

        model.setBrokerClueId(brokerClueId);
        model.setCarId(carId);
        if (carId != null && carId > 0) {
            Car car = carService.getCar(carId);
            if (car != null) {
                model.setBrandId(car.getBrandId());
                model.setSubBrandId(car.getSubBrandId());
                model.setSeriesId(car.getSeriesId());
            }
        }
        model.setCategory(category);
        model.setPayType(payType);
        model.setExchangeType(exchangeType);
        model.setIsAllopatryRegister(isAllopatryRegister);
        model.setBrokerCustomerId(CustomerId);
        int suc = carsWillService.updateParamByCustomerId(model);
        if (suc > 0) {
            return result.format(ResultCode.Success, model);
        }
        return result.format(ResultCode.UnKnownError);
    }

    /**
     * 获取成交记录列表
     *
     * @param brokerId
     * @return
     */
    @RequestMapping(value = "/deal", method = RequestMethod.GET)
    public Result dealList(
            @Check(value = "user_id", required = true) Long userId,
            @Check(value = "broker_id", required = false,defaultValue = "0") Long brokerId,
            @Check(value = "brand_id", required = false,defaultValue = "0") Integer brandId,
            @Check(value = "series_id", required = false,defaultValue = "0") Integer seriesId,
            @Check(value = "car_id", required = false,defaultValue = "0") Integer carId,
            @Check(value = "startTime", required = false,defaultValue = "0") Long startTime,
            @Check(value = "endTime", required = false,defaultValue = "0") Long endTime,
            @Check(value = "status", required = false) Integer status,
            @Check(value = "page", required = false, defaultValue = "1") Integer page,
            @Check(value = "limit", required = false, defaultValue = "20") Integer limit)
            throws ResultException {
        // 获取当前登录的经纪人信息
        Broker broker = brokerService.getBasicBroker(userId);
        if (broker == null) {
            return result.format(ResultCode.BrokerNotFound);
        }
        // 如果当前用户不是销售顾问，则根据dealer_id获取成交记录，否则只获取当前用户的数据
        if (broker.getType() == BrokerTypeEnum.Employee.getValue()) {
            brokerId = userId;
        }
        PagedList<BrokerDealVo> pageList = new PagedList<BrokerDealVo>();
        int total = carsWillService.getDealRecordsCount(
                broker.getDealerId(), brokerId, brandId, seriesId, carId,
                DateUtil.timeStamp2Date(startTime,null),
                DateUtil.timeStamp2Date(endTime, null), status);
        if (total > 0) {
            List<BrokerDealVo> list = carsWillService.getDealRecords(
                    broker.getDealerId(), brokerId, brandId, seriesId, carId,
                    DateUtil.timeStamp2Date(startTime, null),
                    DateUtil.timeStamp2Date(endTime, null),
                    status, page, limit);
            pageList.setList(list);
        }
        pageList.setCount(total);
        pageList.setLimit(limit);
        pageList.setPage(page);
        return result.format(ResultCode.Success, pageList);
    }

    @RequestMapping(value = "/exportdeal", method = RequestMethod.GET)
    public Result exportdealList(
            @Check(value = "user_id", required = true) Long userId,
            @Check(value = "broker_id", required = false,defaultValue = "0") Long brokerId,
            @Check(value = "brand_id", required = false,defaultValue = "0") Integer brandId,
            @Check(value = "series_id", required = false,defaultValue = "0") Integer seriesId,
            @Check(value = "car_id", required = false,defaultValue = "0") Integer carId,
            @Check(value = "startTime", required = false,defaultValue = "0") Long startTime,
            @Check(value = "endTime", required = false,defaultValue = "0") Long endTime,
            @Check(value = "status", required = false) Integer status, HttpServletResponse response) {
    	PagedList<BrokerDealVo> pageList = new PagedList<BrokerDealVo>();
    	try {
    		Broker broker = brokerService.getBasicBroker(userId);
            
            if (broker == null) {
            	  return result.format(ResultCode.BrokerNotFound);
            }
            // 如果当前用户不是销售顾问，则根据dealer_id获取成交记录，否则只获取当前用户的数据
            if (broker.getType() == BrokerTypeEnum.Employee.getValue()) {
                brokerId = userId;
            }       
            int total = carsWillService.getDealRecordsCount(
                    broker.getDealerId(), brokerId, brandId, seriesId, carId,
                    DateUtil.timeStamp2Date(startTime, null),
                    DateUtil.timeStamp2Date(endTime,null), status);
            if (total <= 0) {
            	 pageList.setList(new ArrayList<BrokerDealVo>());
            	 return result.format(ResultCode.Success, pageList);
    		}
            List<BrokerDealVo> list = carsWillService.getDealRecords(
                    broker.getDealerId(), brokerId, brandId, seriesId, carId,
                    DateUtil.timeStamp2Date(startTime,null),
                    DateUtil.timeStamp2Date(endTime,null),
                    status, 1, 0); 
            pageList.setList(list);
            pageList.setLimit(1);
            pageList.setCount(total);
		} catch (Exception e) {
			e.printStackTrace();
		}
                     
        return result.format(ResultCode.Success, pageList);
    }
}
