package com.xyauto.interact.broker.server.dao.proxy;

import com.xyauto.interact.broker.server.cluemq.ClueEntity;
import com.xyauto.interact.broker.server.dao.IBrokerClueDao;
import com.xyauto.interact.broker.server.model.vo.BrokerClue;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BrokerClueDaoProxy {

    @Autowired
    IBrokerClueDao dao;

    public BrokerClue get(long brokerClueId) {
        return dao.get(brokerClueId);
    }

    public List<Long> getClueListIds(
            int category, List<Integer> sources, List<Long> brokerIds, long dealerId, short hasEnregister, String max, int limit
    ) {
        return dao.getClueListIds(category, sources, brokerIds, dealerId, hasEnregister, max, limit);
    }

    /**
     * 根据经销商或者经纪人获取当前手机号线索数据
     * @param brokerId
     * @param dealerId
     * @param mobile
     * @return     */
    public List<BrokerClue> getClueDealerClue( long brokerId, long dealerId, String mobile){
        return dao.getClueDealerClue(brokerId, dealerId, mobile);
    }

    public List<Long> getClueByCall(List<Long> brokerIds, Long dealerId, String username, String mobile, Integer cityId, Integer province_id, Integer seriesId, Integer car_id, Short type, String begin, String end, Integer limit, Integer page){
        return dao.getClueByCall(brokerIds,  dealerId,  username,  mobile,  cityId,  province_id,  seriesId,  car_id,  type,  begin,  end,  limit,  page);
    }
    public Integer getClueByCallCount(List<Long> brokerIds, Long dealerId, String username, String mobile, Integer cityId, Integer province_id, Integer seriesId, Integer car_id, Short type, String begin, String end){
        return dao.getClueByCallCount(brokerIds,  dealerId,  username,  mobile,  cityId,  province_id,  seriesId,  car_id,  type,  begin,  end);
    }

    public List<Long> getClueListIdsByPage(
            int category, List<Integer> sources, List<Long> brokerIds, long dealerId, short hasEnregister,  int page, int limit
    ) {
        return dao.getClueListIdsByPage(category, sources, brokerIds, dealerId, hasEnregister, page, limit);
    }

    public List<BrokerClue> getClueList(List<Long> ids) {
        return dao.getClueList(ids);
    }


    /**
     * 获取客户历史线索 无分页
     * @param clueId
     * @param customerId
     * @param brokerId
     * @return
     */
    public List<BrokerClue> getClueHistoryNoPage(long clueId, long customerId , long brokerId){
        return dao.getClueHistoryNoPage(clueId,customerId,brokerId);
    }


    /**
     * 获取历史线索
     * @param clueId
     * @param customer_id
     * @param brokerId
     * @param page
     * @param limit
     * @return
     */
    public List<BrokerClue> getClueHistoryByPage(long clueId, long customer_id , long brokerId, int page, int limit){
        return  dao.getClueHistoryByPage(clueId,  customer_id ,  brokerId,  page,  limit);
    }
    /**
     * 获取历史线索
     * @param clueId
     * @param customer_id
     * @param brokerId
     * @param max
     * @param limit
     * @return
     */
    public List<BrokerClue> getClueHistory(long clueId, long customer_id , long brokerId, String max, int limit){
        return  dao.getClueHistory(clueId,  customer_id ,  brokerId,  max,  limit);
    }


    /**
     * 转移当经纪人负责客户的所有线索
     * @param customerId 当前客户id
     * @param customerBrokerId 当前客户所属经纪人id
     * @param toBrokerId  被转移的经纪人id
     * @return
     */
    public Integer updateClueBroker( long customerId ,long customerBrokerId ,long toBrokerId){
        return dao.updateClueBroker(customerId,customerBrokerId,toBrokerId);
    }

    /**
     * 修改客户线索信息
     * @param customerId
     * @param brokerClueId
     * @return
     */
    public Integer updateClueCustomer(long customerId,  long brokerClueId){
        return dao.updateClueCustomer(customerId, brokerClueId);
    }

    /**
     * 获取待分配线索经纪人id
     * @param dealerId
     * @return 
     */
    public long getNextAllottingBrokerId(long dealerId, List<Long> brokerIds) {
        return dao.getNextAllottingBrokerId(dealerId, brokerIds);
    }

    /**
     * 添加经纪人线索数据
     * @param entity
     * @return 
     */
    public long addNetClue(ClueEntity entity) {
        return dao.addNetClue(entity);
    }

    /**
     * 分配线索到经纪人
     * @param brokerClueId
     * @param brokerId 
     */
    public Integer allot(long brokerClueId, long brokerId) {
        return dao.allot(brokerClueId, brokerId);
    }

    /**
     * 批量分配线索到经纪人
     *
     * @param ClueIds
     * @param brokerId
     */
    public Integer updateAllotList(List<Long> ClueIds, long brokerId){
        return dao.updateAllotList(ClueIds, brokerId);
    }

    /**
     * 将OldBroker经纪人mobile的线索全部分配个broker_id
     * @param broker_id
     * @param OldBrokerId
     * @param mobile
     * @return
     */
    public Integer updateAllotByMobile( Long broker_id,  long OldBrokerId, String mobile){
        return dao.updateAllotByMobile(broker_id,   OldBrokerId,  mobile);
    }

    /**
     * 获取该经纪人下对应手机号所有线索
     * @param broker_id
     * @param mobile
     * @return
     */
    public List<Long> getClueIdsByMobileBrokerId( Long broker_id, String mobile)
    {
        return dao.getClueIdsByMobileBrokerId(broker_id,  mobile);
    }

    /**
     * 获取经纪人线索总量(es)
     * @return 
     */
    public int getAllCount() {
        return dao.getAllCount();
    }

    /**
     * 获取批次经纪人线索数据(es)
     * @param
     * @param limit
     * @return 
     */
    public List<BrokerClue> getBatchBrokerList(long offset, int limit) {
        return dao.getBatchBrokerList(offset, limit);
    }

    /**
     * 通过公共线索池id获取抢单经纪人线索统计
     * @param cluePoolId
     * @return 
     */
    public Map<String, Long> getByCluePoolId(long cluePoolId) {
        return dao.getByCluePoolId(cluePoolId);
    }

    /**
     * 获取待处理线索数
     * @param dealerId
     * @param brokerId
     * @return 
     */
    public int getNotHandleCount(long dealerId, long brokerId) {
        return dao.getNotHandleCount(dealerId, brokerId);
    }

    /**
     * 获取待建卡线索数
     * @param dealerId
     * @param brokerId
     * @return 
     */
    public int getNotToCustomerCount(long dealerId, long brokerId) {
        return dao.getNotToCustomerCount(dealerId, brokerId);
    }

    /**
     * 获取待分配线索
     * @param dealerId
     * @return 
     */
    public int getNotDistributeCount(long dealerId) {
        return dao.getNotDistributeCount(dealerId);
    }

    /**
     * 检查线索是否分配重复
     * @param clueId
     * @param type
     * @param dealerId
     * @return 
     */
    public int duplicateCheck(long clueId, int type, long dealerId) {
        return dao.duplicateCheck(clueId, type, dealerId);
    }

    /**
     * 根据原始线索公共池id查询
     * @param cluePoolId
     * @return 
     */
    public BrokerClue getClueByPoolId(long cluePoolId) {
        return dao.getClueByPoolId(cluePoolId);
    }

    /**
     * 处理线索
     * @param brokerId
     * @param brokerClueId
     * @return 
     */
    public int handle(long brokerId, long brokerClueId) {
        return dao.handle(brokerId, brokerClueId);
    }

    /**
     * 获取可以被丢失的线索
     * @param clueId
     * @param dealerId
     * @return 
     */
    public List<BrokerClue> getClueByMiss(long clueId, long dealerId) {
        return dao.getClueByMiss(clueId, dealerId);
    }

    /**
     * 丢失线索处理
     * @param brokerClueId
     * @return 
     */
    public int miss(long brokerClueId) {
        return dao.miss(brokerClueId);
    }

    /**
     * 根据手机号获取最新一条线索id
     * @param mobile
     * @param dealerId
     * @return 
     */
    public long getLastestByMobileDealerId(String mobile, long dealerId) {
        return dao.getlastestByMobileDealerId(mobile, dealerId);
    }

    /**
     * 检查抢单唯一性
     * @param clueId
     * @return 
     */
    public int dunplicatePickUpCheck(long clueId) {
        return dao.dunplicatePickUpCheck(clueId);
    }

    /**
     * 检查当前经销商是否有抢单限制
     * @param dealerId
     * @return 
     */
    public int limitPickUpCheck(long dealerId) {
        return dao.limitPickUpCheck(dealerId);
    }

    public  List<BrokerClue> getbrokerClueByDealerId(long dealerId){
    	return dao.getbrokerClueByDealerId(dealerId);
    }

    public List<Long> getNotCustomerByMobileDealerId(String mobile, long dealerId) {
        return dao.getNotCustomerByMobileDealerId(mobile, dealerId);
    }

    /**
     * 获取手机号上次分配到的经纪人id
     * 
     * @param mobile
     * @param dealerId
     * @return 
     */
    public long getLastAllotBrokerId(String mobile, long dealerId) {
        return dao.getLastAllotBrokerId(mobile, dealerId);
    }
    
    /**
     * 批量更新线索处理状态
     * @param broker_clue_ids
     * @return
     */
    public  int updateClueHandle(List<Long> broker_clue_ids){
    	
    	return dao.updateClueHandle(broker_clue_ids);
    }

    /**
     * 批量修改前 -- 检查改线索是否属于经销商
     * @param dealerId
     * @param brokerClueId
     * @return
     */
    public   List<BrokerClue> getBrokerClueByDealerIdAndClueIds(long dealerId,List<Long> brokerClueId){
        return dao.getBrokerClueByDealerIdAndClueIds(dealerId,brokerClueId);
    }
}
