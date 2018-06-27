package com.xyauto.interact.broker.server.service;

import com.xyauto.interact.broker.server.dao.proxy.DealerCarDaoProxy;
import com.xyauto.interact.broker.server.model.vo.Brand;
import com.xyauto.interact.broker.server.model.vo.DealerCar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DealerCarService {

    @Autowired
    BrandService brandService;

    @Autowired
    DealerCarDaoProxy dealerCarDaoProxy;

    /**
     * 获取经销商售卖品牌
     * @param dealerId
     * @return
     * */
    public  List<Brand>getListByDealer(long dealerId){
        List<Integer> brandIds = dealerCarDaoProxy.getListByDealer(dealerId);
        return  brandService.getBrandListByBrandIds(brandIds);
    }

    /**
     * 获取经销商售卖品牌
     * @param dealerId
     * @return
     * */
    public List<Integer> getSeriesIdsByDealer(long dealerId){
        return dealerCarDaoProxy.getSeriesIdsByDealer(dealerId);
    }
}
