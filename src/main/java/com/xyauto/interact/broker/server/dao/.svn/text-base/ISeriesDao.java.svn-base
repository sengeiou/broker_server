package com.xyauto.interact.broker.server.dao;

import com.xyauto.interact.broker.server.model.vo.Series;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ISeriesDao {

    /*    Series GetSerial(@Param(value = "SerialId") int serialId);*/
    Series getSeries(@Param(value = "series_id") int seriesId);

    Series getSeriesByUrlSpell(@Param(value = "urlspell") String urlspell);

    public List<Series> getSeriesList(@Param(value = "series_ids") List<Integer> seriesIds);

    public List<Series> getMaps(@Param(value="ids")List<Integer> ids);


}
