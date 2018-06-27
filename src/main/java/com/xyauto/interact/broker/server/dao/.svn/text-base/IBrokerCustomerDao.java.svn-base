package com.xyauto.interact.broker.server.dao;

import com.xyauto.interact.broker.server.model.po.BrokerCustomerUpdateParameters;
import com.xyauto.interact.broker.server.model.vo.BrokerCustomer;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IBrokerCustomerDao {

    public BrokerCustomer get(@Param(value = "brokerCustomerId") long brokerCustomerId);

    public List<BrokerCustomer> getList(@Param(value = "ids") List<Long> ids);

    /**
     * 获取当前经纪人所有客户 无分页
     *
     * @param brokerId
     * @return
     */
    public List<Long> getListIdsNoPage(@Param(value = "brokerId") long brokerId);

    public List<Long> getListIds(
            @Param(value = "steps") List<Short> steps,
            @Param(value = "categories") List<Short> categories,
            @Param(value = "level") List<Short> level,
            @Param(value = "brokerId") long brokerId,
            @Param(value = "dealerId") long dealerId,
            @Param(value = "cityid") int cityId,
            @Param(value = "provinceId") int provinceId,
            @Param(value = "mobile") int mobile,
            @Param(value = "user_name") String userName,
            @Param(value = "begin_time") String beginTime,
            @Param(value = "end_time") String endTime,
            @Param(value = "max") String max,
            @Param(value = "limit") int limit
    );

    public List<Long> getListIdsByPage(
            @Param(value = "steps") List<Short> steps,
            @Param(value = "categories") List<Short> categories,
            @Param(value = "level") List<Short> level,
            @Param(value = "brokerId") long brokerId,
            @Param(value = "dealerId") long dealerId,
            @Param(value = "cityid") int cityId,
            @Param(value = "provinceId") int provinceId,
            @Param(value = "mobile") int mobile,
            @Param(value = "user_name") String userName,
            @Param(value = "begin_time") String beginTime,
            @Param(value = "end_time") String endTime,
            @Param(value = "offset") int offset,
            @Param(value = "limit") int limit
    );

    public BrokerCustomer getCustomer(@Param(value = "brokerCustomerId") long brokerCustomerId, @Param(value = "brokerId") long brokerId);

    public int getTodayNewCount(@Param(value = "dealerIds") List<Long> dealerIds);

    public long getFirstCustomerIdByClueId(@Param(value = "clue_id") long clueId);

    /**
     * 根据用户名或者 手机号获取客户资料
     *
     * @param mobile
     * @param userName
     * @return
     */
    public List<BrokerCustomer> getCustomerListByMobileOrNamePage(@Param(value = "mobile") String mobile,
            @Param(value = "userName") String userName,
            @Param(value = "brokerId") long brokerId,
            @Param(value = "offset") int offset,
            @Param(value = "limit") int limit);

    /**
     * 根据用户名或者 手机号获取客户资料
     *
     * @param mobile
     * @param userName
     * @return
     */
    public List<BrokerCustomer> getCustomerListByMobileOrNameMax(
            @Param(value = "mobile") String mobile,
            @Param(value = "userName") String userName,
            @Param(value = "brokerId") long brokerId,
            @Param(value = "max") String max,
            @Param(value = "limit") int limit);

    /**
     * 给客户分配 销售顾问
     *
     * @param customerIds
     * @param brokerId
     * @return
     */
    public Integer updateCustomerBroker(@Param(value = "customerids") List<Long> customerIds, @Param(value = "brokerId") long brokerId);


    /*
    根据参数修改 客户信息
     */
    public Integer updateParmByCustomerId(@Param(value = "record") BrokerCustomer record);

    /**
     * 根据手机号和经销商id获取客户id
     *
     * @param mobile
     * @param dealerId
     * @return
     */
    public long getCustomerIdByMobile(@Param(value = "mobile") String mobile, @Param(value = "dealer_id") long dealerId);

    public int create(BrokerCustomer brokerCustomer);

    public int getListIdsCount(
            @Param(value = "steps") List<Short> steps,
            @Param(value = "categories") List<Short> categories,
            @Param(value = "level") List<Short> level,
            @Param(value = "brokerId") long brokerId,
            @Param(value = "dealerId") long dealerId,
            @Param(value = "cityid") int cityId,
            @Param(value = "provinceId") int provinceId,
            @Param(value = "mobile") int mobile,
            @Param(value = "user_name") String userName,
            @Param(value = "begin_time") String beginTime,
            @Param(value = "end_time") String endTime,
            @Param(value = "max") String max
    );




    /**
     * 获取超时处理客户
     *
     * @param dealerId
     * @param brokerId
     * @return
     */
    public int getTimeoutCustomerCount(@Param("dealer_id") long dealerId, @Param("broker_id") long brokerId);

    /**
     * 获取10分钟后即将需要联系的客户ids
     *
     * @return
     */
    public List<Map<String, Object>> getAwaitContactList();

    /**
     * 获取所有客户总数
     *
     * @return
     */
    public int getAllCount();

    /**
     * 批量获取经纪人客户
     *
     * @param offset
     * @param limit
     * @return
     */
    public List<Long> getBatchBrokerList(@Param("offset") long offset, @Param("limit") int limit);

    /**
     * 更新经纪人客户基础信息
     *
     * @param params
     * @return
     */
    public int update(BrokerCustomerUpdateParameters params);
}
