package com.xyauto.interact.broker.server.controller;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xyauto.interact.broker.server.enums.ResultCode;
import com.xyauto.interact.broker.server.model.vo.Brand;
import com.xyauto.interact.broker.server.model.vo.BrokerClue;
import com.xyauto.interact.broker.server.model.vo.Car;
import com.xyauto.interact.broker.server.model.vo.Dealer;
import com.xyauto.interact.broker.server.service.BrokerClueService;
import com.xyauto.interact.broker.server.service.DealerService;
import com.xyauto.interact.broker.server.service.es.clue.BrokerClueEsService;
import com.xyauto.interact.broker.server.util.ILogger;
import com.xyauto.interact.broker.server.util.Result;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/dealer")
public class DealerController extends BaseController implements ILogger {

    @Autowired
    DealerService dealerService;
    @Autowired
    BrokerClueEsService brokerClueEsService;
    @Autowired
    BrokerClueService brokerClueService;

    @RequestMapping(value = "/{dealer_id}", method = RequestMethod.GET)
    public Result get(
            @PathVariable(value = "dealer_id", required = true) long dealerId
    ) {
        Dealer dealer = dealerService.get(dealerId);
        if (dealer == null) {
            return result.format(ResultCode.DealerNotFound);
        }
        return result.format(ResultCode.Success, dealer);
    }

    /**
     * 获取经销商在售车款
     *
     * @param dealerId
     * @return
     */
    @RequestMapping(value = "/cars/sale", method = RequestMethod.GET)
    public Result getSaleCars(
            @RequestParam(value = "dealer_id", required = true) long dealerId
    ) {
        Map<Integer, List<Integer>> cars = Maps.newConcurrentMap();
        List<Car> list = dealerService.getSaleCars(dealerId);
        for (Car car : list) {
            if (cars.containsKey(car.getSeriesId()) == false) {
                cars.put(car.getSeriesId(), Lists.newArrayList());
            }
            cars.get(car.getSeriesId()).add(car.getCarId());
        }
        return result.format(ResultCode.Success, cars);
    }

    @RequestMapping(value = "/city/brands", method = RequestMethod.GET)
    public Result getCityBrands(
            @RequestParam(value = "city_id", required = true) int cityId
    ) {
        List<Brand> list = dealerService.getDealerCityBrands(cityId);
        return result.format(ResultCode.Success, list);

    }

    @RequestMapping(value = "/city/series", method = RequestMethod.GET)
    public Result getCitySeries(
            @RequestParam(value = "city_id", required = true) int cityId,
            @RequestParam(value = "brand_id", required = true) int brandId
    ) throws IOException {
        List<JSONObject> list = dealerService.getDealerCitySeries(cityId, brandId);
        return result.format(ResultCode.Success, list);
    }

    @RequestMapping("/brokerclue/sync")
    public Result brokerClueSyncByDealerId(@RequestParam(value = "dealer_id", required = true) long dealerId) {
        try {
            List<BrokerClue> list = brokerClueService.brokerClueSyncByDealerId(dealerId);
            brokerClueEsService.add(list);
        } catch (Exception e) {
            this.info("同步经销商es数据失败，经销商id是：" + dealerId + ":" + e.getMessage());
            return result.format(ResultCode.UnKnownError);
        }
        return result.format(ResultCode.Success);
    }

}
