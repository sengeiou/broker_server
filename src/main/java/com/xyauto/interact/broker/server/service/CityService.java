package com.xyauto.interact.broker.server.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;

import com.xyauto.interact.broker.server.dao.proxy.CityDaoProxy;
import com.xyauto.interact.broker.server.model.vo.City;
import com.xyauto.interact.broker.server.model.vo.Series;
import com.xyauto.interact.broker.server.util.EsUtil;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;

@Service
public class CityService {

    //后期优化掉。使用方法错误  不应该在server 中调用es
    private final static String TypeName = "broker";
    
    @Value("${xyauto.broker.search.index}")
    private String indexName;

    @Autowired
    CityDaoProxy dao;

    @Autowired
    EsUtil esUtil;

    @Autowired
    SeriesService seriesService;

    public City get(int cityId) {
        return dao.get(cityId);
    }

    public List<City> getCityByParent(int parent) {
        return dao.getCityByParent(parent);
    }

    public JSONArray getBrokerCountByCity(List<City> citys, String urlSpell) throws IOException {
        SearchSourceBuilder query = new SearchSourceBuilder();
        HashMap map = new HashMap();
        JSONArray jsonArray = new JSONArray();
        Series series = new Series();

        if (urlSpell.isEmpty() == false) {
            series = seriesService.getSeriesByUrlSpell(urlSpell);
        }
        if (series.getSeriesId() > 0) {
            query.query(QueryBuilders.termsQuery("seriesIds", new int[]{series.getSeriesId()}));
        }

        JSONObject allTemp = new JSONObject();
        allTemp.put("cityid", 0);
        allTemp.put("cityname", "全部");
        allTemp.put("brokercount", 0);
        jsonArray.add(allTemp);
        int totalCount = 0;
        for (City c : citys) {
            if (citys.size() == 1) {
                query.query(QueryBuilders.termQuery("cityId", c.getId()));
            }
            if (citys.size() > 1) {
                query.query(QueryBuilders.termQuery("districtId", c.getId()));
            }

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("cityid", c.getId());
            jsonObject.put("cityname", c.getName());
            int brokerCount = esUtil.count(indexName, TypeName, query);
            jsonObject.put("brokercount", brokerCount);
            //计算经纪人总数
            totalCount = totalCount + brokerCount;
            jsonArray.add(jsonObject);
        }
        allTemp.put("brokercount", totalCount);
        jsonArray.set(0, allTemp);
        return jsonArray;
    }

    /**
     * 获取城市键值对列表
     * @param ids
     * @return 
     */
    Map<Integer, City> getMaps(List<Integer> ids) {
        Map<Integer, City> data = Maps.newConcurrentMap();
        if (ids.isEmpty()) {
            return data;
        }
        List<City> list = dao.getMaps(ids);
        list.forEach(city -> {
            data.put(city.getId(), city);
        });
        return data;
    }

    /**
     * 手机号匹配城市信息
     * @param phone
     * @return 
     */
    public City matchMobileCity(String phone) {
        int cityId = dao.matchMobileCity(phone.substring(0, 7));
        if (cityId!=0) {
            return this.get(cityId);
        }
        return null;
    }

    /**
     * @return the indexName
     */
    public String getIndexName() {
        return indexName;
    }

    /**
     * @param indexName the indexName to set
     */
    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }
}
