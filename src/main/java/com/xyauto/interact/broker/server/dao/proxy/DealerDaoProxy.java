package com.xyauto.interact.broker.server.dao.proxy;

import com.xyauto.interact.broker.server.dao.IDealerDao;
import com.xyauto.interact.broker.server.model.vo.Car;
import com.xyauto.interact.broker.server.model.vo.Dealer;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DealerDaoProxy {
    
    @Autowired
    IDealerDao dao;

    public Dealer get(long dealerId) {
        return dao.get(dealerId);
    }

    public List<Car> getDealerCars(long dealerId) {
        return dao.getDealerCars(dealerId);
    }

    public String getDealerCityBrands(int cityId) {
        return dao.getDealerCityBrands(cityId);
    }

    public List<Integer> getDealerCitySeries(int cityId, int brandId) {
        return dao.getDealerCitySeries(cityId, brandId);
    }

    public List<Car> getSaleCars(long dealerId) {
        return dao.getDealerCars(dealerId);
    }


    public List<Integer> getDealerBrandIds( long dealerId){
        return  dao.getDealerBrandIds( dealerId);
    }

    public List<Dealer> getDealersByWeigth(int page,int limit ){
        return  dao.getDealersByWeigth( page,limit);
    }

    /**
     * 获取经销商列表
     * @param ids
     * @return 
     */
    public List<Dealer> getMaps(List<Long> ids) {
        return dao.getMaps(ids);
    }

    public Dealer getAlways(long dealerId) {
        return dao.getAlways(dealerId);
    }
    
    public int inster (Dealer dealer){
    	return dao.inster(dealer);
    }
    public int update (Dealer dealer){
    	return dao.update(dealer);
    }
}
