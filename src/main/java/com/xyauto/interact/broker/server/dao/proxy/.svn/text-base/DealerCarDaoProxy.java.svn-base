package com.xyauto.interact.broker.server.dao.proxy;

import com.xyauto.interact.broker.server.dao.IDealerCarDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DealerCarDaoProxy {

    @Autowired
    IDealerCarDao dealerCarDao;

    /**
     * 获取经销商售卖品牌
     * @param dealerId
     * @return
     * */
    public List<Integer> getListByDealer(long dealerId){
        return dealerCarDao.getListByDealer(dealerId);
    }

    public List<Integer> getSeriesIdsByDealer(long dealerId){
        return dealerCarDao.getSeriesIdsByDealer(dealerId);
    }
}
