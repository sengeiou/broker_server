package com.xyauto.interact.broker.server.dao.proxy;

import com.xyauto.interact.broker.server.dao.IBrokerDao;
import com.xyauto.interact.broker.server.model.po.BrokerPo;
import com.xyauto.interact.broker.server.model.po.BrokerSearchParameters;
import com.xyauto.interact.broker.server.model.po.PagedCommonParameters;
import com.xyauto.interact.broker.server.model.vo.Broker;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BrokerDaoProxy {
    
    @Autowired
    IBrokerDao dao;

    public List<Broker> getByDealerId(long dealerId){
        return dao.getByDealerId(dealerId);
    }

    public Broker getTop1ServerCountByDealerId( long dealerId){
        return dao.getTop1ServerCountByDealerId(dealerId);
    }

    public Broker get(long brokerId) {
        return dao.get(brokerId);
    }

    /**
     * 获取销售人员
     * @param brokerId
     * @return
     */
    public Broker getSalePerson(long brokerId){
        return dao.getSalePerson(brokerId);
    }

    public List<Long> getListIds(long dealerId, String max, int limit) {
        return dao.getListIds(dealerId, max, limit);
    }

    public List<Long> getListIdsByPage(long dealerId, long offset, int limit) {
        return dao.getListIdsByPage(dealerId, offset, limit);
    }

    public List<Long> getSalePersonListIdsByPage( long dealerId,  long offset,  int limit){
        return dao.getSalePersonListIdsByPage(dealerId, offset, limit);
    }
    
    public List<Long> getSearchListIds(PagedCommonParameters<BrokerSearchParameters> pagedCommonParameters) {
        return dao.getSearchListIds(pagedCommonParameters);
    }

    public List<Long> getSearchListIdsByPage(PagedCommonParameters<BrokerSearchParameters> pagedCommonParameters) {
        return dao.getSearchListIdsByPage(pagedCommonParameters);
    }
    
    public List<Broker> getList(List<Long> ids) {
        return dao.getList(ids);
    }

    public List<Long> getDealerBrokerIds(long dealerId) {
        return dao.getDealerBrokerIds(dealerId);
    }

    public int getDealerBrokerCount(long dealerId){
        return dao.getDealerBrokerCount(dealerId);
    }

    public  int getSaleBrokerCountByDealer(long dealerId){
        return dao.getSaleBrokerCountByDealer(dealerId);
    }

    public Broker getBrokerByMobile(long dealer_id, String mobile){
        return dao.getBrokerByMobile(dealer_id, mobile);
    }

    public int getAllCount() {
        return dao.getAllCount();
    }
    
    public List<Broker> getBatchBrokerList(long offset, int limit) {
        return dao.getBatchBrokerList(offset, limit);
    }


    public List<Long> getPrizeList(String begin, String end){
        return dao.getPrizeList(begin,end);
    }

    public int updateByParam(BrokerPo record){
        return dao.updateByParam(record);
    }
    public int updateBrokerPo(BrokerPo record){
        return dao.updateBrokerPo(record);
    }
    public int insert(BrokerPo record){
        return dao.insert(record);
    }

    public List<Long> getPagedBrokerIds(long offset, int limit) {
        return dao.getPagedBrokerIds(offset, limit);
    }

    public long getBrokerIdByUid(long uid) {
        return dao.getBrokerIdByUid(uid);
    }


    /**
     * 根据经销商获取随机经纪人数据
     * @param dealerids
     * @param page
     * @param limit
     * @return
     */
    public List<Long> getBrokerByDealerIds(List<Long> dealerids, int page, int limit){
        return  dao.getBrokerByDealerIds(dealerids,page,limit );
    }

    /**
     * 获取存在经纪人的经销商数量
     * @param dealerids
     * @return
     */
    public Integer getCountByDealerIds(List<Long> dealerids){
        return  dao.getCountByDealerIds(dealerids);
    }
    
    public int delete(long borker_id){
    	return  dao.deleteBroker(borker_id);
    }

    public Broker getAlways(long brokerId) {
        return dao.getAlways(brokerId);
    }
}
