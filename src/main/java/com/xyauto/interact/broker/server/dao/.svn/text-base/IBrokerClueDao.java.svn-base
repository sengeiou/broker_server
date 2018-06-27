package com.xyauto.interact.broker.server.dao;

import com.xyauto.interact.broker.server.cluemq.ClueEntity;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.xyauto.interact.broker.server.model.vo.BrokerClue;

import java.util.Map;

@Mapper
public interface IBrokerClueDao {

    BrokerClue get(@Param("broker_clue_id") long brokerClueId);

    List<Long> getClueListIds(
            @Param("category") int category,
            @Param("sources") List<Integer> sources,
            @Param("brokerIds") List<Long> brokerIds,
            @Param("target_broker_id") long targetBrokerId,
            @Param("has_enregister") short hasEnregister,
            @Param("max") String max,
            @Param("limit") int limit
    );

    /**
     * 根据经销商或者经纪人获取当前手机号线索数据
     *
     * @param brokerId
     * @param dealerId
     * @param mobile
     * @return
     */
    List<BrokerClue> getClueDealerClue(@Param("brokerId") long brokerId,
                                       @Param("dealerId") long dealerId,
                                       @Param("mobile") String mobile);

    /**
     * 话单数据主键
     *
     * @param brokerIds
     * @param dealerId
     * @param username
     * @param mobile
     * @param cityId
     * @param province_id
     * @param seriesId
     * @param car_id
     * @param type
     * @param begin
     * @param end
     * @param limit
     * @param page
     * @return
     */
    List<Long> getClueByCall(@Param("brokerIds") List<Long> brokerIds,
                             @Param("dealerId") Long dealerId,
                             @Param("username") String username,
                             @Param("mobile") String mobile,
                             @Param("cityId") Integer cityId,
                             @Param("province_id") Integer province_id,
                             @Param("seriesId") Integer seriesId,
                             @Param("car_id") Integer car_id,
                             @Param("type") Short type,
                             @Param("begin") String begin,
                             @Param("end") String end,
                             @Param("limit") Integer limit,
                             @Param("page") Integer page);

    Integer getClueByCallCount(@Param("brokerIds") List<Long> brokerIds,
                               @Param("dealerId") Long dealerId,
                               @Param("username") String username,
                               @Param("mobile") String mobile,
                               @Param("cityId") Integer cityId,
                               @Param("province_id") Integer province_id,
                               @Param("seriesId") Integer seriesId,
                               @Param("car_id") Integer car_id,
                               @Param("type") Short type,
                               @Param("begin") String begin,
                               @Param("end") String end);

    List<Long> getClueListIdsByPage(
            @Param("category") int category,
            @Param("sources") List<Integer> sources,
            @Param("brokerIds") List<Long> brokerIds,
            @Param("target_broker_id") long targetBrokerId,
            @Param("has_enregister") short hasEnregister,
            @Param("page") int page,
            @Param("limit") int limit
    );

    public List<BrokerClue> getClueList(@Param("ids") List<Long> ids);

    /**
     * 获取客户历史线索 无分页
     *
     * @param clueId
     * @param customerId
     * @param brokerId
     * @return
     */
    public List<BrokerClue> getClueHistoryNoPage(
            @Param(value = "clueid") long clueId,
            @Param(value = "customerid") long customerId,
            @Param(value = "brokerid") long brokerId);


    /**
     * 获取历史线索
     *
     * @param clueId
     * @param customerId
     * @param brokerId
     * @param page
     * @param limit
     * @return
     */
    public List<BrokerClue> getClueHistoryByPage(
            @Param(value = "clueid") long clueId,
            @Param(value = "customerid") long customerId,
            @Param(value = "brokerid") long brokerId,
            @Param(value = "page") int page,
            @Param(value = "limit") int limit);

    /**
     * 获取历史线索
     *
     * @param clueId
     * @param customerId
     * @param brokerId
     * @param max
     * @param limit
     * @return
     */
    public List<BrokerClue> getClueHistory(
            @Param(value = "clueid") long clueId,
            @Param(value = "customerid") long customerId,
            @Param(value = "brokerid") long brokerId,
            @Param(value = "max") String max,
            @Param(value = "limit") int limit);

    /**
     * 给线索设置经纪人
     *
     * @param customerId
     * @param
     * @return
     */
    public Integer updateClueBroker(@Param(value = "customerid") long customerId, @Param(value = "customerBrokerId") long customerBrokerId ,
                                    @Param(value = "brokerId") long toBrokerId);

    /**
     * 修改客户线索信息
     *
     * @param customerId
     * @param brokerClueId
     * @return
     */
    public Integer updateClueCustomer(@Param(value = "customerId") long customerId, @Param(value = "brokerClueId") long brokerClueId);

    /**
     * 获取待分配线索经纪人id
     *
     * @param dealerId
     * @param brokerIds
     * @return
     */
    public long getNextAllottingBrokerId(@Param(value = "dealer_id") long dealerId, @Param(value = "broker_ids") List<Long> brokerIds);

    /**
     * 添加经纪人网单线索数据
     *
     * @param entity
     * @return
     */
    public long addNetClue(ClueEntity entity);

    /**
     * 分配线索到经纪人
     *
     * @param brokerClueId
     * @param brokerId
     */
    public Integer allot(@Param(value = "broker_clue_id") long brokerClueId, @Param(value = "broker_id") long brokerId);

    /**
     * 批量分配线索到经纪人
     *
     * @param brokerClueId
     * @param brokerId
     */
    public Integer updateAllotList(@Param(value = "ClueIds") List<Long> brokerClueId, @Param(value = "broker_id") long brokerId);

    /**
     * 将OldBroker经纪人mobile的线索全部分配个broker_id
     * @param broker_id
     * @param OldBrokerId
     * @param mobile
     * @return
     */
    public Integer updateAllotByMobile(@Param(value = "brokerId") Long broker_id, @Param(value = "OldBrokerId") long OldBrokerId,@Param(value = "mobile") String mobile);

    /**
     * 获取该经纪人下对应手机号所有线索
     * @param broker_id
     * @param mobile
     * @return
     */
    public List<Long> getClueIdsByMobileBrokerId(@Param(value = "brokerId") Long broker_id,@Param(value = "mobile") String mobile);

    /**
     * 获取经纪人线索总量(es)
     *
     * @return
     */
    public int getAllCount();

    /**
     * 获取批次经纪人线索数据(es)
     *
     * @param offset
     * @param limit
     * @return
     */
    public List<BrokerClue> getBatchBrokerList(@Param("offset") long offset, @Param("limit") int limit);

    /**
     * 通过公共线索池id获取经纪人线索统计
     *
     * @param cluePoolId
     * @return
     */
    public Map<String, Long> getByCluePoolId(@Param("clue_pool_id") long cluePoolId);

    /**
     * 获取待处理线索数
     *
     * @param dealerId
     * @param brokerId
     * @return
     */
    public int getNotHandleCount(@Param("dealer_id") long dealerId, @Param("broker_id") long brokerId);

    /**
     * 获取待建卡线索数
     *
     * @param dealerId
     * @param brokerId
     * @return
     */
    public int getNotToCustomerCount(@Param("dealer_id") long dealerId, @Param("broker_id") long brokerId);

    /**
     * 获取待分配线索数
     *
     * @param dealerId
     * @return
     */
    public int getNotDistributeCount(@Param("dealer_id") long dealerId);

    /**
     * 检查线索是否重复分配
     *
     * @param clueId
     * @param type
     * @param dealerId
     * @return
     */
    public int duplicateCheck(@Param("clue_id") long clueId, @Param("type") int type, @Param("dealer_id") long dealerId);

    /**
     * 根据公共池id查询线索
     *
     * @param cluePoolId
     * @return
     */
    public BrokerClue getClueByPoolId(@Param("clue_pool_id") long cluePoolId);

    /**
     * 处理线索
     *
     * @param brokerId
     * @param brokerClueId
     * @return
     */
    public int handle(@Param("broker_id") long brokerId, @Param("broker_clue_id") long brokerClueId);

    /**
     * 获取可以被丢失的线索
     *
     * @param clueId
     * @param dealerId
     * @return
     */
    public List<BrokerClue> getClueByMiss(@Param("clue_id") long clueId, @Param("dealer_id") long dealerId);

    /**
     * 丢失线索处理
     *
     * @param brokerClueId
     * @return
     */
    public int miss(@Param("broker_clue_id") long brokerClueId);

    /**
     * 根据手机号获取
     *
     * @param mobile
     * @param dealerId
     * @return
     */
    public long getlastestByMobileDealerId(@Param("mobile") String mobile, @Param("dealer_id") long dealerId);

    /**
     * 检查抢单唯一性
     *
     * @param clueId
     * @return
     */
    public int dunplicatePickUpCheck(@Param("clueId") long clueId);

    /**
     * 检查抢单经销商及经纪人是否有抢单限制
     *
     * @param dealerId
     * @return
     */
    public int limitPickUpCheck(@Param("dealer_id") long dealerId);

    /**
     * 根据经销商id获取线索
     *
     * @param dealerId
     * @return
     */
    public List<BrokerClue> getbrokerClueByDealerId(@Param("dealerId") long dealerId);

    /**
     * 获取历史未绑定客户线索
     *
     * @param mobile
     * @param dealerId
     * @return
     */
    public List<Long> getNotCustomerByMobileDealerId(@Param("mobile") String mobile, @Param("dealer_id") long dealerId);

    /**
     * 获取手机号最后分配经纪人id
     *
     * @param mobile
     * @param dealerId
     * @return
     */
    public long getLastAllotBrokerId(@Param("mobile") String mobile, @Param("dealer_id") long dealerId);


    /**
     * 批量更新线索已处理状态
     * @param broker_clue_ids
     * @return
     */
    public  int updateClueHandle(@Param("ids")List<Long> broker_clue_ids);


    /**
     * 批量修改前 -- 检查改线索是否属于经销商
     * @param dealerId
     * @param brokerClueId
     * @return
     */
    public  List<BrokerClue> getBrokerClueByDealerIdAndClueIds(@Param("dealer_id") long dealerId,@Param(value = "ClueIds") List<Long> brokerClueId);

}
