package com.xyauto.interact.broker.server.dao.proxy;

import com.xyauto.interact.broker.server.dao.IProvinceDao;
import com.xyauto.interact.broker.server.model.vo.Province;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class ProvinceDaoProxy {

    @Autowired
    IProvinceDao dao;

    @Cacheable(value = "province", key = "'province'.concat(#provinceId.toString())")
    public Province get(int provinceId) {
        return dao.get(provinceId);
    }

    /**
     * 获取省份列表
     * @param ids
     * @return 
     */
    public List<Province> getMaps(List<Integer> ids) {
        return dao.getMaps(ids);
    }
}
