package com.xyauto.interact.broker.server.dao.proxy;

import com.xyauto.interact.broker.server.dao.ICityDao;
import com.xyauto.interact.broker.server.model.vo.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import org.springframework.cache.annotation.Cacheable;

@Component
public class CityDaoProxy {

    @Autowired
    ICityDao dao;

    @Cacheable(value="listcity", key ="'listcity'.concat(#parent.toString())")
    public List<City> getCityByParent(int parent ){
        return  dao.getCityByParent(parent);
    }

    @Cacheable(value="city", key ="'city'.concat(#cityId.toString())")
    public City get(int cityId ){
        return  dao.get(cityId);
    }

    public List<City> getMaps(List<Integer> ids) {
        return dao.getMaps(ids);
    }

    /**
     * 匹配手机城市
     * @param substring
     * @return 
     */
    public int matchMobileCity(String mobile) {
        return dao.matchMobileCity(mobile);
    }
}
