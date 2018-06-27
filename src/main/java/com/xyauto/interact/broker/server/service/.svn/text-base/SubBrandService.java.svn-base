package com.xyauto.interact.broker.server.service;

import com.google.common.collect.Maps;
import com.xyauto.interact.broker.server.dao.proxy.SubBrandDaoProxy;
import com.xyauto.interact.broker.server.model.vo.SubBrand;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class SubBrandService {

    @Autowired
    SubBrandDaoProxy dao;

    public SubBrand getSubBrand(int subBrandId) {
        return dao.getSubBrand(subBrandId);
    }

    /**
     * 获取子品牌键值对列表
     * @param subBrandIds
     * @return 
     */
    public Map<Integer, SubBrand> getMaps(List<Integer> ids) {
        Map<Integer, SubBrand> data = Maps.newConcurrentMap();
        if (ids.isEmpty()) {
            return data;
        }
        List<SubBrand> list = dao.getMaps(ids);
        list.forEach(subBrand -> {
            data.put(subBrand.getSubBrandId(), subBrand);
        });
        return data;
    }

}
