package com.xyauto.interact.broker.server.dao.proxy;

import com.xyauto.interact.broker.server.dao.ISubBrandDao;
import com.xyauto.interact.broker.server.model.vo.SubBrand;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class SubBrandDaoProxy {

    @Autowired
    ISubBrandDao subBrandDao;

    /**
     * 获取子品牌
     *
     * @param subBrandId
     * @return
     */
    @Cacheable(value = "subbrand", key = "'subbrand'.concat(#subBrandId.toString())")
    public SubBrand getSubBrand(int subBrandId) {
        return subBrandDao.getSubBrand(subBrandId);
    }

    /**
     * 获取子品牌列表
     * @param ids
     * @return 
     */
    public List<SubBrand> getMaps(List<Integer> ids) {
        return subBrandDao.getMaps(ids);
    }

}
