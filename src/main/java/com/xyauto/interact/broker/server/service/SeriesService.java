package com.xyauto.interact.broker.server.service;

import com.google.common.collect.Maps;
import com.xyauto.interact.broker.server.dao.proxy.SeriesDaoProxy;
import com.xyauto.interact.broker.server.model.vo.Series;
import com.xyauto.interact.broker.server.util.ImageHelper;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class SeriesService {

    @Autowired
    SeriesDaoProxy dao;
    
    @Autowired
    ImageHelper imageHelper;

    public Series getSeries(int seriesId) {
        Series series = dao.getSeries(seriesId);
        if (series!=null) {
            series.setImage(imageHelper.getImageUrl(series.getImage()));
        }
        return series;
    }

    public String getSeriesImage(String url){
        if (url.toLowerCase().startsWith("http") || url.toLowerCase().startsWith("https")) {
        } else {
            if (url.startsWith("/group1")) {
                url = "http://img1.qcdqcdn.com" + url;
            } else
            if (url.startsWith("/group2")) {
                url = "http://img2.qcdqcdn.com" + url;
            } else
            if (url.startsWith("group1")) {
                url = "http://img1.qcdqcdn.com/" + url;
            } else
            if (url.startsWith("group2")) {
                url = "http://img2.qcdqcdn.com/" + url;
            }
        }
        return url;
    }

    public List<Series> getSeriesList(List<Integer> seriesIds) {
        List<Series> list = dao.getSeriesList(seriesIds);
        list.forEach((series) -> {
            series.setImage(imageHelper.getImageUrl(series.getImage()));
        });
        return list;
    }

    /**
     * 根据urlspall 获取车型
     *
     * @param urlspell
     * @return
     */
    public Series getSeriesByUrlSpell(String urlspell) {
        Series series = dao.getSeriesByUrlSpell(urlspell);
        if (series!=null) {
            series.setImage(imageHelper.getImageUrl(series.getImage()));
        }
        return series;
    }

    /**
     * 获取车系键值对列表
     * @return 
     */
    public Map<Integer, Series> getMaps(List<Integer> ids) {
        Map<Integer, Series> data = Maps.newConcurrentMap();
        if (ids.isEmpty()) {
            return data;
        }
        List<Series> list = dao.getMaps(ids);
        list.forEach(series -> {
            data.put(series.getSeriesId(), series);
        });
        return data;
    }

}
