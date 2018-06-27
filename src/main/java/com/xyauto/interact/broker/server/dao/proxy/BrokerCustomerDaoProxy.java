package com.xyauto.interact.broker.server.dao.proxy;

import com.xyauto.interact.broker.server.dao.IBrokerCustomerDao;
import com.xyauto.interact.broker.server.model.po.BrokerCustomerUpdateParameters;
import com.xyauto.interact.broker.server.model.vo.BrokerCustomer;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BrokerCustomerDaoProxy {

    @Autowired
    IBrokerCustomerDao dao;

    public BrokerCustomer get(long brokerCustomerId) {
        return dao.get(brokerCustomerId);
    }

    public List<BrokerCustomer> getList(List<Long> ids) {
        return dao.getList(ids);
    }

    public List<Long> getListIds(List<Short> steps, List<Short> categories, List<Short> level, long brokerId, long targetBrokerId, long dealerId, int cityId, int provinceId, int mobile, String userName, String beginTime, String endTime, String max, int limit) {

        return dao.getListIds(steps, categories, level, brokerId, dealerId, cityId, provinceId, mobile, userName, beginTime, endTime, max, limit);
    }

    /**
     * 获取当前经纪人所有客户 无分页
     *
     * @param brokerId
     * @return
     */
    public List<Long> getListIdsNoPage(long brokerId) {
        return dao.getListIdsNoPage(brokerId);
    }

    public List<Long> getListIdsByPage(List<Short> steps, List<Short> categories, List<Short> level, long brokerId, long targetBrokerId, long dealerId, int cityId, int provinceId, int mobile, String userName, String beginTime, String endTime, int offset, int limit) {
        return dao.getListIdsByPage(steps, categories, level, brokerId, dealerId, cityId, provinceId, mobile, userName, beginTime, endTime, offset, limit);
    }

    public BrokerCustomer getCustomer(long brokerCustomerId, long brokerId) {
        return dao.getCustomer(brokerCustomerId, brokerId);
    }

    public int getTodayNewCount(List<Long> dealerIds) {
        return dao.getTodayNewCount(dealerIds);
    }

    public long getFirstCustomerIdByClueId(long clueId) {
        return dao.getFirstCustomerIdByClueId(clueId);
    }

    /**
     * 根据用户名或者 手机号获取客户资料
     *
     * @param mobile
     * @param userName
     * @return
     */
    public List<BrokerCustomer> getCustomerListByMobileOrNamePage(String mobile, String userName, long brokerId, int offset, int limit) {
        return dao.getCustomerListByMobileOrNamePage(mobile, userName, brokerId, offset, limit);
    }

    /**
     * 根据用户名或者 手机号获取客户资料
     *
     * @param mobile
     * @param userName
     * @return
     */
    public List<BrokerCustomer> getCustomerListByMobileOrNameMax(String mobile, String userName, long brokerId, String max, int limit) {
        return dao.getCustomerListByMobileOrNameMax(mobile, userName, brokerId, max, limit);
    }

    //分配客户
    public Integer updateCustomerBroker(List<Long> customerIds, long brokerId) {

        return dao.updateCustomerBroker(customerIds, brokerId);
    }

    public Integer updateParmByCustomerId(BrokerCustomer record) {
        return dao.updateParmByCustomerId(record);
    }

    public long getCustomerIdByMobile(String mobile, long dealerId) {
        return dao.getCustomerIdByMobile(mobile, dealerId);
    }

    public int create(BrokerCustomer brokerCustomer) {
        return dao.create(brokerCustomer);
    }

    public int getListIdsCount(List<Short> steps, List<Short> categories, List<Short> level, long brokerId, long targetBrokerId,
            long dealerId, int cityId, int provinceId, int mobile, String userName, String beginTime, String endTime, String max) {

        return dao.getListIdsCount(steps, categories, level, brokerId, dealerId, cityId, provinceId, mobile, userName, beginTime,
                endTime, max);
    }

    public int getTimeoutCustomerCount(long dealerId, long brokerId) {
        return 0;
        //return dao.getTimeoutCustomerCount(dealerId, brokerId);
    }

    /**
     * 获取10分钟后即将需要联系的客户ids
     *
     * @return
     */
    public List<Map<String, Object>> getAwaitContactList() {
        return dao.getAwaitContactList();
    }

    /**
     * 获取所有客户总数
     * @return 
     */
    public int getAllCount() {
        return dao.getAllCount();
    }

    /**
     * 批量获取经纪人客户
     * @param offset
     * @param limit
     * @return 
     */
    public List<Long> getBatchBrokerList(long offset, int limit) {
        return dao.getBatchBrokerList(offset, limit);
    }
    
    /**
     * 更新经纪人客户基础信息
     * @param params
     * @return 
     */
    public int update(BrokerCustomerUpdateParameters params) {
        return dao.update(params);
    }
    
    
}
