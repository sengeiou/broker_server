package com.xyauto.interact.broker.server.service;

import com.xyauto.interact.broker.server.model.vo.City;
import io.searchbox.core.Count;

import java.util.PrimitiveIterator;

public class CityBrokerInfo {
    /**
     * 城市信息
     */
    private City city;

    /**
     * 经纪人数量
     */
    private int brokerCount;

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public int getBrokerCount() {
        return brokerCount;
    }

    public void setBrokerCount(int brokerCount) {
        this.brokerCount = brokerCount;
    }


}
