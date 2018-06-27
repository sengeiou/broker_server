package com.xyauto.interact.broker.server.dao.proxy;

import com.xyauto.interact.broker.server.dao.IBrandDao;
import com.xyauto.interact.broker.server.model.vo.Brand;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class BrandDaoProxy {

    @Autowired
    IBrandDao brandDao;

    /**
     * 获取品牌
     * @param brandId
     * @return
     */
    @Cacheable(value="brand", key ="'brand'.concat(#brandId.toString())")
    public Brand getBrand(int brandId){
        return brandDao.getBrand(brandId);
    }
    
    @Cacheable(value="brand", key ="brandlist") 
    public List<Brand> getBrandList(){
        return brandDao.getBrandList();
    }

    public List<Brand> getBrandListByBrandIds(List<Integer> brandIds) {
        return brandDao.getBrandListByBrandIds(brandIds);
    }

    /**
     * 获取品牌列表
     * @param ids
     * @return 
     */
    public List<Brand> getMaps(List<Integer> ids) {
        return brandDao.getMaps(ids);
    }

}