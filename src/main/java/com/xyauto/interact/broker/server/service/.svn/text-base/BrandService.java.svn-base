package com.xyauto.interact.broker.server.service;

import com.google.common.collect.Maps;
import com.xyauto.interact.broker.server.dao.proxy.BrandDaoProxy;
import com.xyauto.interact.broker.server.model.vo.Brand;
import com.xyauto.interact.broker.server.util.ImageHelper;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BrandService {

    @Autowired
    BrandDaoProxy dao;
    
    @Autowired
    ImageHelper imageHelper;

    public Brand getBrand(int brandId){
        Brand brand =  dao.getBrand(brandId);
        if(brand!=null) {
            brand.setLogo(imageHelper.getImageUrl(brand.getLogo()));
        }
        return brand;
    }
    
    public List<Brand> getBrandList() {
        List<Brand> list = dao.getBrandList();
        list.forEach(brand -> {
            brand.setLogo(imageHelper.getImageUrl(brand.getLogo()));
        });
        return list;
    }

    List<Brand> getBrandListByBrandIds(List<Integer> brandIds) {
        if(brandIds ==null || brandIds.size()==0){
            return  null;
        }
        List<Brand> list = dao.getBrandListByBrandIds(brandIds);
        list.forEach(brand -> {
            brand.setLogo(imageHelper.getImageUrl(brand.getLogo()));
        });
        return list;
    }

    /**
     * 获取品牌键值对列表
     * @param brandIds
     * @return 
     */
    public Map<Integer, Brand> getMaps(List<Integer> ids) {
        Map<Integer, Brand> data = Maps.newConcurrentMap();
        if (ids.isEmpty()) {
            return data;
        }
        List<Brand> list = dao.getMaps(ids);
        list.forEach(brand -> {
            data.put(brand.getBrandId(), brand);
        });
        return data;
    }
    
}
