package com.xyauto.interact.broker.server.dao;

import com.xyauto.interact.broker.server.model.vo.Car;
import com.xyauto.interact.broker.server.model.vo.Dealer;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IDealerDao {

    public List<Long> getBrokerIds(@Param(value = "dealer_id") long dealerId);

    public List<Integer> getDealerBrandIds(@Param(value = "dealer_id") long dealerId);

    public Dealer get(@Param(value = "dealer_id") long dealerId);

    public List<Car> getDealerCars(@Param(value = "dealer_id") long dealerId);

    public String getDealerCityBrands(@Param(value = "city_id") int cityId);

    public List<Integer> getDealerCitySeries(@Param(value = "city_id") int cityId, @Param(value = "brand_id") int brandId);

    public List<Dealer> getDealersByWeigth(@Param(value = "offset") int page,@Param(value = "limit") int limit);

    public List<Dealer> getMaps(@Param(value = "ids")List<Long> ids);

    public Dealer getAlways(@Param(value = "dealer_id")long dealerId);
    
    public int inster(@Param(value = "record")Dealer dealer);
    
    public int  update(@Param(value = "record")Dealer dealer);
}
