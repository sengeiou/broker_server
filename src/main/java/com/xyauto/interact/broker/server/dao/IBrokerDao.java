package com.xyauto.interact.broker.server.dao;

import com.xyauto.interact.broker.server.model.po.BrokerPo;
import com.xyauto.interact.broker.server.model.po.BrokerSearchParameters;
import com.xyauto.interact.broker.server.model.po.PagedCommonParameters;
import com.xyauto.interact.broker.server.model.vo.Broker;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IBrokerDao {

    public List<Broker> getByDealerId(@Param(value = "dealer_id") long dealerId);

    public Broker getTop1ServerCountByDealerId(@Param(value = "dealer_id") long dealerId);

    public Broker get(@Param(value = "broker_id") long brokerId);

    public Broker getSalePerson(@Param(value = "broker_id") long brokerId);

    public List<Long> getListIds(@Param(value = "dealer_id") long dealerId, @Param(value = "max") String max, @Param(value = "limit") int limit);

    public List<Long> getSearchListIds(PagedCommonParameters<BrokerSearchParameters> pagedCommonParameters);

    public List<Long> getSearchListIdsByPage(PagedCommonParameters<BrokerSearchParameters> pagedCommonParameters);

    public List<Long> getListIdsByPage(@Param(value = "dealer_id") long dealerId, @Param(value = "offset") long offset, @Param(value = "limit") int limit);

    public List<Long> getSalePersonListIdsByPage(@Param(value = "dealer_id") long dealerId, @Param(value = "offset") long offset, @Param(value = "limit") int limit);

    public List<Broker> getList(@Param(value = "ids") List<Long> ids);

    public List<Long> getDealerBrokerIds(@Param(value = "dealer_id") long dealerId);

    public int getDealerBrokerCount(@Param(value = "dealer_id") long dealerId);

    public  int getSaleBrokerCountByDealer(@Param(value = "dealer_id") long dealerId);

    public Broker getBrokerByMobile(@Param(value = "dealer_id") long dealer_id,@Param(value = "mobile") String mobile);

    public int getAllCount();

    public List<Broker> getBatchBrokerList(@Param(value = "offset") long offset, @Param(value = "limit") int limit);

    public List<Long> getPrizeList(@Param(value = "begin") String begin, @Param(value = "end") String end);

    public int updateByParam(@Param(value = "record") BrokerPo record);

    public int updateBrokerPo(@Param(value = "record") BrokerPo record);
    
    public int insert(@Param(value = "record") BrokerPo record);

    public List<Long> getPagedBrokerIds(@Param(value = "offset") long offset, @Param(value = "limit") int limit);

    public long getBrokerIdByUid(@Param(value = "uid")long uid);

    /**
     * 根据经销商获取随机经纪人数据
     * @param dealerids
     * @param offset
     * @param limit
     * @return
     */
    public List<Long> getBrokerByDealerIds(
            @Param("dealerids") List<Long> dealerids,
            @Param("offset") int offset,
            @Param("limit") int limit);


    /**
     * 获取存在经纪人的经销商数量
     * @param dealerids
     * @return
     */
    public Integer getCountByDealerIds(@Param("dealerids") List<Long> dealerids);
    
    public int deleteBroker(long borker_id);

    public Broker getAlways(@Param("broker_id")long brokerId);
}
