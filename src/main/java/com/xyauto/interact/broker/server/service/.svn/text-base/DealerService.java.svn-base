package com.xyauto.interact.broker.server.service;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mysql.jdbc.StringUtils;
import com.xyauto.interact.broker.server.dao.proxy.DealerDaoProxy;
import com.xyauto.interact.broker.server.enums.ResultCode;
import com.xyauto.interact.broker.server.model.po.BrokerSearchParameters;
import com.xyauto.interact.broker.server.model.vo.Brand;
import com.xyauto.interact.broker.server.model.vo.Car;
import com.xyauto.interact.broker.server.model.vo.Dealer;
import com.xyauto.interact.broker.server.model.vo.Series;
import com.xyauto.interact.broker.server.service.cloud.ApiServiceFactory;
import com.xyauto.interact.broker.server.service.es.broker.BrokerEsService;
import com.xyauto.interact.broker.server.util.ILogger;
import com.xyauto.interact.broker.server.util.Result;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.elasticsearch.common.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DealerService implements ILogger{
    
    @Autowired
    DealerDaoProxy dao;
    
    @Autowired
    BrandService brandService;
    
    @Autowired
    SeriesService seriesService;
    
    @Autowired
    CityService cityService;
    
    @Autowired
    BrokerEsService brokerEsService;
    
    @Autowired
    ProvinceService provinceService;
    @Autowired
    private ApiServiceFactory apiService;

    public Dealer getSingle(long dealerId){
        return dao.get(dealerId);
    }

    public Dealer get(long dealerId) {
        Dealer dealer = dao.get(dealerId);
        if (dealer!=null && dealer.getBrandIds().length()>0) {
            List<String> brandIdStr = StringUtils.split(dealer.getBrandIds(), ",", true);
            List<Integer> brandIds = Lists.newArrayList();
            brandIdStr.forEach(item -> {
                brandIds.add(Integer.valueOf(item));
            });
            List<Brand> brandList = brandService.getBrandListByBrandIds(brandIds);
            dealer.setBrands(brandList);
            //获取在售车型
            List<Car> carList = dao.getDealerCars(dealerId);
            List<Integer> seriesIdList = Lists.newArrayList();
            List<Series> seriesList = Lists.newArrayList();
            carList.forEach(item->{
                if (seriesIdList.contains(item.getSeriesId())==false) {
                    Series series = seriesService.getSeries(item.getSeriesId());
                    seriesList.add(series);
                    seriesIdList.add(item.getSeriesId());
                }
            });
            try {
                dealer.setSeries(seriesList);
                //获取城市信息
                dealer.setCity(cityService.get(dealer.getCityId()));
                //获取省份信息
                dealer.setProvince(provinceService.get(dealer.getProvinceId()));
                //获取区信息
                dealer.setDistrict(cityService.get(dealer.getDistrictId()));
            }catch (Exception e){}

        }
        return dealer;
    }

    public List<Car> getDealerCars(long dealerId) {
        return dao.getDealerCars(dealerId);
    }


    public List<Brand> getDealerCityBrands(int cityId) {
        String brandIdsStr = dao.getDealerCityBrands(cityId);
        List<Integer> brandIds = Lists.newArrayList();
        Strings.splitStringToSet(brandIdsStr, ',').forEach(item -> {
            if (brandIds.contains(Integer.valueOf(item))==false) {
                brandIds.add(Integer.valueOf(item));
            }
        });
        return brandService.getBrandListByBrandIds(brandIds);
    }

    public List<JSONObject> getDealerCitySeries(int cityId, int brandId) throws IOException {
        List<Integer>  seriesIds = dao.getDealerCitySeries(cityId, brandId);
        if (seriesIds.isEmpty()){
            return Lists.newArrayList();
        }
        List<JSONObject> ret = Lists.newArrayList();
        List<Series> list = seriesService.getSeriesList(Lists.newArrayList(new HashSet<>(seriesIds)));
        for(Series item : list) {
            JSONObject json = JSONObject.parseObject(JSONObject.toJSONString(item));
            BrokerSearchParameters params = new BrokerSearchParameters();
            params.setCityId(cityId);
            params.setSeriesId(item.getSeriesId());
            int brokerCount = brokerEsService.searchListCount(params, "0");
            json.put("broker_count", brokerCount);
            ret.add(json);
        }
        return ret;
    }

    /**
     * 获取经销商在售车款列表
     * @param dealerId
     * @return 
     */
    public List<Car> getSaleCars(long dealerId) {
        return dao.getSaleCars(dealerId);
    }

    /**
     * 获取经销商售卖品牌ids
     * @param dealerId
     * @return
     */
    public List<Integer> getDealerBrandIds( long dealerId){
        return  dao.getDealerBrandIds( dealerId);
    }

    /**
     * 根据经销商权重排序
     * @param page
     * @param limit
     * @return
     */
    public List<Dealer> getDealersByWeigth(int page,int limit ){
        return  dao.getDealersByWeigth( page,limit);
    }

    /**
     * 获取经销商键值对列表
     * @param dealerIds
     * @return 
     */
    Map<Long, Dealer> getMaps(List<Long> ids) {
        Map<Long, Dealer> data = Maps.newConcurrentMap();
        if (ids.isEmpty()) {
            return data;
        }
        List<Dealer> list = dao.getMaps(ids);
        list.forEach(item->{
            data.put(item.getDealerId(), item);
        });
        return data;
    }

    public Dealer getAlways(long dealerId) {
        return dao.getAlways(dealerId);
    }
    /**
     * 同步主库经销商数据
     * @return
     */
    public Dealer syncDealer(long dealerId){
    	Dealer dealer=null;
    	Result ret =null;
    	try {
    		ret=apiService.BaseDealerService().getInfo(dealerId);
    		if (ret.getCode() != ResultCode.Success.getCode()) {
    			this.log("获取经销商信息失败，获取数是："+JSONObject.toJSONString(ret));
    		}
    		dealer=this.getAlways(dealerId);
    		boolean is_insert=false;
    		if (dealer==null) {
    			is_insert=true;
    			dealer=new Dealer();
			}   		
    		String str=JSONObject.toJSONString(ret.getData());
    		JSONObject data=JSONObject.parseObject(str);
    		dealerId=data.getLong("dealerId");
    		String short_name=data.getString("dealerShortName");
    		String dealer_name=data.getString("dealerFullName");
    		short type=data.getShort("businessModelId");
    		String address=data.getString("dealerContactAddress");
    		double  latitude=data.getDouble("latitude");
    		double longitude=data.getDouble("longitude");
    		int corporation_id=data.getInteger("groupId");
    		int province_id=data.getInteger("provinceId");
    		int city_id=data.getInteger("cityId");
    		String brand_ids=data.getString("mainBrandIds");
    		
    		dealer.setDealerId(dealerId);
    		dealer.setShortName(short_name);
    		dealer.setName(dealer_name);
    		dealer.setType(type);
    		dealer.setAddress(address);
    		dealer.setLatitude(latitude);
    		dealer.setLongitude(longitude);
    		dealer.setCorporationId(corporation_id);
    		dealer.setProvinceId(province_id);
    		dealer.setCityId(city_id);
    		dealer.setBrandIds(brand_ids);
    		if (is_insert) {
				//插入
    			dao.inster(dealer);
    			return this.getAlways(dealerId);
			}else{
				//更新
				dao.update(dealer);				
			}    		    		
		} catch (Exception e) {
			log("同步经销商主库信息失败，同步数据是："+ret);
			return null;
		}    	
    	return dealer;
    }

}
