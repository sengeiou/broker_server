package com.xyauto.interact.broker.server.service;

import com.google.common.collect.Maps;
import com.xyauto.interact.broker.server.dao.proxy.ProvinceDaoProxy;
import com.xyauto.interact.broker.server.model.vo.Province;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProvinceService {
    
    @Autowired
    ProvinceDaoProxy dao;
    
    public Province get(int provinceId) {
        return dao.get(provinceId);
    }

    /**
     * 获取省份键值对列表
     * @param provinceIds
     * @return 
     */
    public Map<Integer, Province> getMaps(List<Integer> ids) {
        Map<Integer, Province> data = Maps.newConcurrentMap();
        if (ids.isEmpty()) {
            return data;
        }
        List<Province> list = dao.getMaps(ids);
        list.forEach(province -> {
            data.put(province.getId(), province);
        });
        return data;
    }
}
