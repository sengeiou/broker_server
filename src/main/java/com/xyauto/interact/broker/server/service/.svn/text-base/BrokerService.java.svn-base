package com.xyauto.interact.broker.server.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.rabbitmq.client.AMQP;
import com.xyauto.interact.broker.server.dao.proxy.*;
import com.xyauto.interact.broker.server.enums.BrokerEnum;
import com.xyauto.interact.broker.server.enums.BrokerLogEnum;
import com.xyauto.interact.broker.server.enums.BrokerTypeEnum;
import com.xyauto.interact.broker.server.model.vo.*;
import com.xyauto.interact.broker.server.enums.ResultCode;
import com.xyauto.interact.broker.server.exceptions.ResultException;
import com.xyauto.interact.broker.server.model.po.BrokerPo;
import com.xyauto.interact.broker.server.model.po.BrokerSearchParameters;
import com.xyauto.interact.broker.server.service.cloud.ApiServiceFactory;
import com.xyauto.interact.broker.server.service.es.broker.BrokerEsService;
import com.xyauto.interact.broker.server.service.es.clue.BrokerClueEsService;
import com.xyauto.interact.broker.server.service.es.customer.BrokerCustomerEsService;
import com.xyauto.interact.broker.server.util.ILogger;
import com.xyauto.interact.broker.server.util.ImageHelper;
import com.xyauto.interact.broker.server.util.QRCodeUtil;
import com.xyauto.interact.broker.server.util.RedisCache;
import com.xyauto.interact.broker.server.util.Result;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import org.springframework.context.annotation.Lazy;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BrokerService implements ILogger {

    @Autowired
    BrokerDaoProxy dao;

    @Autowired
    DealerDaoProxy dealerDao;

    @Autowired
    DealerService dealerService;

    @Autowired
    BrokerClueDaoProxy brokerClueDao;

    @Autowired
    BrokerCustomerDaoProxy brokerCustomerDao;

    @Autowired
    BrokerEsService brokerEsService;

    @Autowired
    BrokerCustomerEsService brokerCustomerEsService;
    
    @Autowired
    BrokerIntegralService brokerIntegralService;

    @Autowired
    ApiServiceFactory apiService;

    @Autowired
    FileService fileService;

    @Autowired
    ImageHelper imageHelper;

    @Autowired
    RedisCache cache;

    @Autowired
    BrokerReceiptDaoProxy brokerReceiptDaoProxy;
    
    @Autowired
    @Lazy
    BrokerStoreService brokerStoreService;

    @Autowired
    private BrokerLogService logService;

    @Autowired
    private BrokerClueEsService brokerClueEsService;

    public List<Long> getDealerBrokerIds(long dealerId) {
        return dao.getDealerBrokerIds(dealerId);
    }

    public int getDealerBrokerCount(long dealerId) {
        return dao.getDealerBrokerCount(dealerId);
    }

    public int getSaleBrokerCountByDealer(long dealerId) {
        return dao.getSaleBrokerCountByDealer(dealerId);
    }

    public Broker getBrokerByMobile(long dealer_id, String mobile) {
        return dao.getBrokerByMobile(dealer_id, mobile);
    }

    public List<Long> getListIds(long brokerId, long dealerId, String max, int limit) throws ResultException {
        List<Long> ids = Lists.newArrayList();
        if (brokerId > 0) {
            Broker broker = get(brokerId);
            if (broker != null && broker.getType() == BrokerTypeEnum.Employee.getValue()) {
                return Lists.newArrayList(brokerId);
            }
        }
        if (dealerId > 0) {
            Dealer dealer = dealerDao.get(dealerId);
            if (dealer == null) {
                return ids;
            }
        } else {
            return ids;
        }
        ids = dao.getListIds(dealerId, max, limit);
        return ids;
    }

    public List<Long> getListIds(long brokerId, long dealerId, int page, int limit) throws ResultException {
        List<Long> ids = Lists.newArrayList();
        if (brokerId > 0) {
            Broker broker = get(brokerId);
            if (broker != null && broker.getType() == BrokerTypeEnum.Employee.getValue()) {
                return Lists.newArrayList(brokerId);
            }
        }
        if (dealerId > 0) {
            Dealer dealer = dealerDao.get(dealerId);
            if (dealer == null) {
                return ids;
            }
        } else {
            return ids;
        }
        ids = dao.getListIdsByPage(dealerId, (page - 1) * limit, limit);
        return ids;
    }

    public List<Long> getListPageByDealer(long dealerId, int page, int limit) {
        return dao.getListIdsByPage(dealerId, (page - 1) * limit, limit);
    }

    public List<Long> getSalePersonListIdsByPage(long dealerId, long page, int limit) {
        return dao.getSalePersonListIdsByPage(dealerId, (page - 1) * limit, limit);
    }

    public List<Long> searchListIds(BrokerSearchParameters params, String max, int limit) throws IOException {
        List<Long> list = brokerEsService.search(params, max, limit);
        return list;
        //return dao.getSearchListIds(new PagedCommonParameters<>(params, max, limit));
    }

    public List<Long> searchListIds(BrokerSearchParameters params, int page, int limit) throws IOException {
        List<Long> list = brokerEsService.search(params, page, limit);
        return list;
        //return dao.getSearchListIdsByPage(new PagedCommonParameters<>(params, (page-1)*limit, limit));
    }

    public List<Broker> getList(List<Long> ids) {
        if (ids.isEmpty()) {
            return Lists.newArrayList();
        }
        List<Broker> list = dao.getList(ids);
        list = this.attachProperties(list);
        return list;
    }



    public List<Broker> getList(List<Long> ids,boolean reailPhone) {
        if (ids.isEmpty()) {
            return Lists.newArrayList();
        }
        List<Broker> list = dao.getList(ids);
        list = this.attachProperties(list,reailPhone);
        return list;
    }

    public List<Broker> getByDealerId(long dealerId) {
        List<Broker> list = dao.getByDealerId(dealerId);
        list.forEach(item -> {
            item = this.attachServicePhone(item);
        });
        return list;
    }

    /**
     * 获取该经销商下 服务人数最多的经纪人
     *
     * @param dealerId
     * @return
     */
    public Broker getTop1ServerCountByDealerId(long dealerId) {
        Broker broker = dao.getTop1ServerCountByDealerId(dealerId);
        return attachServicePhone(broker);
    }

    /**
     * 获取该经销商下 金牌经纪人
     *
     * @param dealerId
     * @return
     */
    public Broker getBestBrokerByDealerId(long dealerId) {
        //todo 如果没有金牌经纪人 则获取改经销商 服务人数最多的一个经纪人
        //todo 缺少获取金牌经纪人逻辑
        Broker broker = getTop1ServerCountByDealerId(dealerId);
        broker = attachServicePhone(broker);
        return get(broker.getBrokerId());
    }

    /**
     * 根据经销商获取随机经纪人
     *
     * @param dealerId
     * @return
     */
    public Broker getRandomBrokerByDealerId(long dealerId) {
        List<Broker> brokers = new ArrayList<>();
        brokers = dao.getByDealerId(dealerId);
        if (brokers == null || brokers.size() == 0) {
            return null;
        }

        Random rand = new Random();
        Broker temp = brokers.get(rand.nextInt(brokers.size() - 1));
        temp = attachServicePhone(temp);
        return get(temp.getBrokerId());

    }

    public Broker getSingle(long brokerId) {
        Broker broker = dao.get(brokerId);
        return attachServicePhone(broker);
    }

    /**
     * 获取销售权限人员
     *
     * @param brokerId
     * @return
     */
    public Broker getSalePerson(long brokerId) {
        Broker broker = dao.getSalePerson(brokerId);
        if (broker != null) {
            try {
                broker.setQrCode(imageHelper.getImageUrl(broker.getQrCode()));
                broker.setDealer(dealerService.get(broker.getDealerId()));
            } catch (Exception e) {
            }
        }
        return attachServicePhone(broker);
    }

    public Broker get(long brokerId) {
        try {
            Broker broker = this.get(brokerId, true);
            return attachServicePhone(broker);
        } catch (Exception e) {
            this.error("获取经纪人信息失败:" + e.getMessage());
            return null;
        }
    }

    public Broker getTiny(long brokerId) {
        Broker broker = dao.get(brokerId);
        if (broker != null && StringUtils.isBlank(broker.getAvatar())) {
            broker.setAvatar("http://img1.qcdqcdn.com/group1/M00/1E/3A/o4YBAFrEnluAaI26AAAmX0O2kJ4372.png");
        }
        return broker;
    }

    public Broker getAlways(long brokerId) {
        Broker broker = dao.getAlways(brokerId);
        return broker;
    }

    public Broker get(long brokerId, boolean isSimple) {
        Broker broker = dao.get(brokerId);
        if (broker != null) {
            try {
                if (StringUtils.isBlank(broker.getAvatar())) {
                    broker.setAvatar("http://img1.qcdqcdn.com/group1/M00/1E/3A/o4YBAFrEnluAaI26AAAmX0O2kJ4372.png");
                }
                broker.setQrCode(imageHelper.getImageUrl(broker.getQrCode()));
                broker.setDealer(dealerService.get(broker.getDealerId()));
                broker = attachServicePhone(broker);
                //补充分享链接请求
                BrokerStore store = brokerStoreService.get(brokerId, false);
                String url = "huisj://share?title="+URLEncoder.encode(store.getTitle(), "UTF-8")+"&desc="+URLEncoder.encode(store.getIntroduction(), "UTF-8")+"&img_url="+URLEncoder.encode(broker.getAvatar(), "UTF-8")+"&link="+URLEncoder.encode(broker.getWebsite(), "UTF-8")+"&platform=01234";
                broker.setShare(url);
            } catch (Exception e) {
            }
        }

        if (broker != null && isSimple == false) {

            //获取积分
            broker.setIntegral(brokerIntegralService.getBalance(brokerId));
            //获取任务完成度
            try {
                Result ret = apiService.missionService().getCurrentTaskProgress(brokerId);
                if (ret != null && ret.getCode() == ResultCode.Success.getCode() && ret.getData() != null) {
                    List<Map> progressList = (List<Map>) ret.getData();
                    if (progressList.isEmpty() == false) {
                        Double progress = Double.valueOf(progressList.get(0).get("progress").toString()) * 100;
                        broker.setMissionPercent(progress.intValue());
                    }
                }
            } catch (ResultException | NumberFormatException e) {
                this.error("获取任务完成度失败");
            }

            //获取经纪人周统计
            List<String> ops = Lists.newArrayList();
            ops.add("broker_clue_add");
            ops.add("broker_clue_handle");
            ops.add("broker_customer_add");
            ops.add("broker_materiel_view");
            ops.add("broker_website_view");
            Map<String, Object> data = Maps.newConcurrentMap();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
            Calendar caledar = Calendar.getInstance(Locale.CHINA);
            caledar.setFirstDayOfWeek(Calendar.MONDAY);
            caledar.setTimeInMillis(System.currentTimeMillis());
            caledar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            String begin = format.format(caledar.getTime());
            caledar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
            String end = format.format(caledar.getTime());
            try {
                if (broker.getType() == 2) {
                    data = apiService.brokerLogService().durationSum(begin, end, String.valueOf(broker.getDealerId()), StringUtils.EMPTY, StringUtils.join(ops, ','));
                } else {
                    data = apiService.brokerLogService().durationSum(begin, end, StringUtils.EMPTY, String.valueOf(brokerId), StringUtils.join(ops, ','));
                }
                JSONObject json = (JSONObject) JSON.toJSON(data);
                //获取周业绩-新增客户
                broker.setWeekNewCustomerCount(json.getIntValue("broker_customer_add"));
                //获取周业绩-新增线索
                broker.setWeekNewClueCount(json.getIntValue("broker_clue_add"));
                //获取周业绩-头条浏览量
                broker.setWeekMaterielViewCount(json.getIntValue("broker_materiel_view"));
                //获取周业绩-微店浏览量
                broker.setWeekWebsiteViewCount(json.getIntValue("broker_website_view"));
            } catch (ResultException e) {
                this.error("获取经纪人周统计失败");
            }

            //获取是否在有效排期内
            try {
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Result ret = apiService.platformService().missionEnable(brokerId, df.format(new Date()));
                if (ret.getCode() == ResultCode.Success.getCode()) {
                    int missionEnable = Boolean.valueOf(ret.getData().toString()) ? 1 : 0;
                    broker.setMissionEnable(missionEnable);
                }
            } catch (ResultException e) {
                this.error("获取经销商排期信息失败");
            }
        }
        return broker;
    }

    /**
     * 给经纪人分配客户（同时带上客户的线索信息）
     * @param customerIds
     * @param brokerId
     * @param targetBrokerId
     * @return
     * @throws ResultException
     */
    @Transactional
    public Boolean setCustomerToBroker(List<Long> customerIds, long brokerId, long targetBrokerId) throws ResultException {
        //验证当前操作人（经纪人）操作权限
        Broker targetBroker = get(targetBrokerId);
        if (targetBroker == null) {
            return false;
            //throw new ResultException(ResultCode.TargetBrokerNotFound);
        }
        //权限验证
        if (targetBroker.getType() == BrokerTypeEnum.Employee.getValue()) {
            return false;
            //throw new ResultException(ResultCode.NoPermission);
        }
        //验证被分配经纪人合法性
        Broker broker = get(brokerId);
        if (broker == null) {
            return false;
        }
        //非同店经纪人不能进行分配   只能分配个同店铺的 销售经理 和销售专员
        if (broker.getDealerId() != targetBroker.getDealerId()) {
            return false;
        }
        //只能分配给经纪人和 客户经理
        if (!(broker.getType() == BrokerTypeEnum.Manager.getValue() || broker.getType() == BrokerTypeEnum.Employee.getValue())) {
            return false;
        }

        //与操作人 是否同一个经销
        if (broker.getDealerId() != targetBroker.getDealerId()) {
            throw new ResultException(ResultCode.NoPermissionByBroker);
        }
/*        List<BrokerCustomer> brokerCustomer =brokerCustomerDao.getList(customerIds);
        if(brokerCustomer==null || brokerCustomer.size() != customerIds.size() ){
            return  false;
        }*/

        try {
            //要跟新索引的线索
            List<BrokerClue> brokerClues= new  ArrayList<>();
            //要跟新索引的客户
            List<BrokerCustomer> brokerCustomerList = new  ArrayList<>();
            customerIds.forEach(customerId -> {

                BrokerCustomer brokerCustomer =brokerCustomerDao.get(customerId);
                brokerCustomerList.add(brokerCustomer);
                //原客户经纪人
                Broker beAllotBroker = this.get(brokerCustomer.getBrokerId());
                //记录处理日志
                logService.log(targetBrokerId, targetBroker.getDealerId(),
                        customerId, BrokerLogEnum.BrokerCustomerAllot, brokerCustomer, targetBroker.getName(), beAllotBroker == null ? "未知经纪人" : beAllotBroker.getName(), broker.getName());
                brokerReceiptDaoProxy.AllotCustomerToBtoker(brokerId, brokerCustomer.getBrokerId(), customerId);
                brokerCustomerEsService.update(customerId);

                //记录要更新的线索
                brokerClues.addAll(brokerClueDao.getClueDealerClue(brokerCustomer.getBrokerId(),brokerCustomer.getDealerId() ,brokerCustomer.getMobile()) );
                // 转移当经纪人负责客户的所有线索
                int suc = brokerClueDao.updateClueBroker(customerId,brokerCustomer.getBrokerId(), brokerId);

            });
            //更新线索索引
            brokerClueEsService.add(brokerClues);
            int ret = this.updateCustomerBroker(customerIds, brokerId);
            brokerCustomerEsService.add(brokerCustomerList);
            return  ret>0 ?true:false;
        } catch (Exception e) {
            return false;
        }
        //return true;
    }

    public int updateCustomerBroker(List<Long> customerIds, long brokerId){
        return brokerCustomerDao.updateCustomerBroker(customerIds, brokerId);
    }

    //同步es使用
    public int getAllCount() {
        return dao.getAllCount();
    }

    //同步es使用
    public List<Broker> getBatchBrokerList(int page, int limit) {
        return dao.getBatchBrokerList((page - 1) * limit, limit);
    }

    /**
     * 获取经纪人获奖名单
     *
     * @return
     */
    public List<Long> getPrizeList(String begin, String end) {
        return dao.getPrizeList(begin, end);
    }

    /**
     * 获取搜索经纪人数量
     *
     * @param params
     * @param max
     * @return
     * @throws IOException
     */
    public int searchListCount(BrokerSearchParameters params, String max) throws IOException {
        return brokerEsService.searchListCount(params, max);
    }

    /**
     * 获取批量经纪人
     *
     * @param brokerIds
     * @return
     */
    public List<Broker> getBatch(List<Long> brokerIds) throws ResultException {
        List<Broker> list = Lists.newArrayList();
        for (long brokerId : brokerIds) {
            Broker broker = this.get(brokerId);
            if (broker != null) {
                broker = attachServicePhone(broker);
                list.add(broker);
            }
        }
        return list;
    }

    /**
     * 根据实体参数修改经纪人信息
     *
     * @param record
     * @return
     */
    public int updateByParam(BrokerPo record) {
        return dao.updateByParam(record);
    }
    public int updateBrokerPo(BrokerPo record) {
        return dao.updateBrokerPo(record);
    }

    /**
     * 根据实体参数修改经纪人信息
     *
     * @param record
     * @return
     */
    public int insert(BrokerPo record) {
        return dao.insert(record);
    }

    /**
     *
     * @param broker_id:经纪人id
     * @param url:微店地址
     * @param avatar：头像地址
     * @return
     */
    public String createRQCode(Long broker_id, Long dealer_id, String url, String avatar) throws Exception {
        if (StringUtils.isBlank(url)) {
            url = String.format(BrokerEnum.website, broker_id, dealer_id);
        }
        BufferedImage image = QRCodeUtil.encodeImage(url, avatar, true);
        return fileService.uploadFile(image, "jpg");
    }

    /**
     * 获取分页经纪人ids
     *
     * @param page
     * @param limit
     * @return
     */
    public List<Long> getPagedBrokerIds(int page, int limit) {
        return dao.getPagedBrokerIds((page - 1) * limit, limit);
    }

    public Broker getBasicBroker(long brokerId) {
        Broker broker = dao.get(brokerId);
        broker = attachServicePhone(broker);
        return broker;
    }

    /**
     * 根据大全用户id获取经纪人信息
     *
     * @param uid
     * @return
     */
    public Broker getByUid(long uid) {
        long brokerId = dao.getBrokerIdByUid(uid);
        if (brokerId > 0) {
            return this.get(brokerId);
        }
        return null;
    }

    /**
     * 根据经销商获取随机经纪人数据
     *
     * @param dealerids
     * @param page
     * @param limit
     * @return
     */
    public List<Long> getBrokerByDealerIds(List<Long> dealerids, int page, int limit) {
        return dao.getBrokerByDealerIds(dealerids, page, limit);
    }

    /**
     * 获取存在经纪人的经销商数量
     *
     * @param dealerids
     * @return
     */
    public Integer getCountByDealerIds(List<Long> dealerids) {
        return dao.getCountByDealerIds(dealerids);
    }

    /**
     * 获取经纪人键值对列表
     *
     * @param ids
     * @return
     */
    Map<Long, Broker> getMaps(List<Long> ids) {
        Map<Long, Broker> data = Maps.newConcurrentMap();
        if (ids.isEmpty()) {
            return data;
        }
        List<Broker> list = this.getList(ids);
        list.forEach(item -> {
            if (data.containsKey(item.getBrokerId()) == false) {
                //item = attachServicePhone(item);
                data.put(item.getBrokerId(), item);
            }
        });
        return data;
    }


    /**
     * 装配经纪人附带信息
     * @param list
     * @param realPhone 是否显示真实手机号
     * @return
     */
    private List<Broker> attachProperties(List<Broker> list,boolean realPhone) {
        if (list.isEmpty()) {
            return list;
        }
        List<Long> dealerIds = Lists.newArrayList();
        list.forEach(item -> {
            if (dealerIds.contains(item.getDealerId()) == false) {
                dealerIds.add(item.getDealerId());
            }
        });
        Map<Long, Dealer> dealerMaps = dealerService.getMaps(dealerIds);
        list.forEach(item -> {
            item.setDealer(dealerMaps.get(item.getDealerId()));
            item.setQrCode(imageHelper.getImageUrl(item.getQrCode()));
            if(!realPhone) {
                item = this.attachServicePhone(item);
            }
        });
        return list;
    }

    /**
     * 装配经纪人附带信息
     *
     * @param list
     * @return
     */
    private List<Broker> attachProperties(List<Broker> list) {
        if (list.isEmpty()) {
            return list;
        }
        List<Long> dealerIds = Lists.newArrayList();
        list.forEach(item -> {
            if (dealerIds.contains(item.getDealerId()) == false) {
                dealerIds.add(item.getDealerId());
            }
        });
        Map<Long, Dealer> dealerMaps = dealerService.getMaps(dealerIds);
        list.forEach(item -> {
            item.setDealer(dealerMaps.get(item.getDealerId()));
            item.setQrCode(imageHelper.getImageUrl(item.getQrCode()));
            item = this.attachServicePhone(item);
        });
        return list;
    }

    public int delete(long broker_id) {

        return dao.delete(broker_id);
    }

    public Broker attachServicePhone(Broker broker) {
        if (broker == null) {
            return broker;
        }
        try {
            String key = "broker:service:phone:" + broker.getBrokerId();
            if (cache.exists(key)) {
                broker.setMobile(cache.get(key));
            } else {
                Result ret = apiService.callCenter().getBrokerPhone(broker.getBrokerId());
                if (ret.getCode() == ResultCode.Success.getCode()) {
                    broker.setMobile(ret.getData().toString());
                    cache.set(key, ret.getData().toString(), 60 * 60L);
                }
            }
        } catch (ResultException ex) {
            this.error("获取经纪人服务电话失败:" + ex.getMessage());
        }
        return broker;
    }

}
