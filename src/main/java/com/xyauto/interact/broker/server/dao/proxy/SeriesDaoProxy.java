package com.xyauto.interact.broker.server.dao.proxy;

import com.xyauto.interact.broker.server.dao.ISeriesDao;
import com.xyauto.interact.broker.server.model.vo.Series;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class SeriesDaoProxy {

    @Autowired
    ISeriesDao dao;

    /**
     * 根据urlspall 获取车型
     *
     * @param urlspell
     * @return
     */
    @Cacheable(value="series", key = "'series'.concat(#urlspell)")
    public Series getSeriesByUrlSpell(String urlspell) {
        return dao.getSeriesByUrlSpell(urlspell);
    }

    @Cacheable(value="series", key = "'series'.concat(#seriesId.toString())")
    public Series getSeries(int seriesId) {
        return dao.getSeries(seriesId);
    }

    public List<Series> getSeriesList(List<Integer> seriesIds) {
        return dao.getSeriesList(seriesIds);
    }

    /**
     * 获取车系列表
     * @param ids
     * @return 
     */
    public List<Series> getMaps(List<Integer> ids) {
        return dao.getMaps(ids);
    }

}
