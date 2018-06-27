package com.xyauto.interact.broker.server.service;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.xyauto.interact.broker.server.dao.proxy.*;
import com.xyauto.interact.broker.server.enums.*;
import com.xyauto.interact.broker.server.model.po.BrokerClueSearchParameters;
import com.xyauto.interact.broker.server.model.po.BrokerCustomerCarsPersistant;
import com.xyauto.interact.broker.server.model.vo.*;
import com.xyauto.interact.broker.server.service.cloud.ApiServiceFactory;
import com.xyauto.interact.broker.server.service.es.clue.BrokerClueEsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.xyauto.interact.broker.server.exceptions.ResultException;
import com.xyauto.interact.broker.server.model.po.BrokerCustomerSearchParameters;
import com.xyauto.interact.broker.server.model.po.BrokerCustomerUpdateParameters;
import com.xyauto.interact.broker.server.service.es.customer.BrokerCustomerEsService;
import com.xyauto.interact.broker.server.util.DateStyle;
import com.xyauto.interact.broker.server.util.DateUtil;
import com.xyauto.interact.broker.server.util.ILogger;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BrokerCustomerService implements ILogger {

    @Autowired
    BrokerCustomerDaoProxy dao;

    @Autowired
    BrokerCustomerCarsWillService carsWillDao;

    @Autowired
    BrokerClueService clueDao;

    @Autowired
    CarService carDao;

    @Autowired
    SeriesService seriesDao;

    @Autowired
    BrandService brandService;

    @Autowired
    BrokerCustomerCarsSerivce brokerCarsSerivce;

    @Autowired
    CityService cityService;

    @Autowired
    ProvinceService provinceService;

    @Autowired
    BrokerReceiptService brokerReceiptService;

    @Autowired
    BrokerLogService brokerLogService;

    @Autowired
    BrokerDaoProxy brokerDaoProxy;

    @Autowired
    BrokerClueDaoProxy brokerClueDaoProxy;

    @Autowired
    BrokerCustomerEsService brokerCustomerEsService;

    @Autowired
    BrokerClueEsService brokerClueEsService;

    @Autowired
    BrokerCustomerCarsWillService brokerCustomerCarsWillService;

    @Autowired
    private BrokerClueService brokerClueService;

    @Autowired
    private BrokerService brokerService;

    @Autowired
    DataAttachService dataAttachService;

    @Autowired
    ApiServiceFactory apiServiceFactory;

    /**
     * 获取当前经纪人所有客户 无分页
     *
     * @param brokerId
     * @return
     */
    public List<Long> getListIdsNoPage(long brokerId) {
        return dao.getListIdsNoPage(brokerId);
    }

    public List<Long> getListIds(List<Short> steps, List<Short> categories, List<Short> level, long brokerId, long targetBrokerId, long dealerId, int cityId, int provinceId, int mobile, String userName, String beginTime, String endTime, String max, int limit) {
        return dao.getListIds(steps, categories, level, brokerId, targetBrokerId, dealerId, cityId, provinceId, mobile, userName, beginTime, endTime, max, limit);
    }

    public List<Long> getListIds(List<Short> steps, List<Short> categories, List<Short> level, long brokerId, long targetBrokerId, long dealerId, int cityId, int district_id, int mobile, String userName, String beginTime, String endTime, int page, int limit) {
        return dao.getListIdsByPage(steps, categories, level, brokerId, targetBrokerId, dealerId, cityId, district_id, mobile, userName, beginTime, endTime, (page - 1) * limit, limit);
    }

    public List<BrokerCustomer> getList(List<Long> ids) {
        List<BrokerCustomer> list = Lists.newArrayList();
        if (ids.isEmpty()) {
            return list;
        }
        list = dao.getList(ids);
        list = this.attachProperties(list);
        return list;
    }

    public BrokerCustomer get(long brokerCustomerId) {
        return this.get(brokerCustomerId, true);
    }

    public BrokerCustomer get(long brokerCustomerId, boolean simple) {
        BrokerCustomer brokerCustomer = dao.get(brokerCustomerId);
        if (brokerCustomer == null || simple == true) {
            return brokerCustomer;
        }
        //附加属性
        brokerCustomer = this.attachProperties(Lists.newArrayList(brokerCustomer)).get(0);
        //设置附能数据
        brokerCustomer = this.attachStatisticsData(brokerCustomer);
        return brokerCustomer;
    }

    public BrokerCustomer existsCustomer(String mobile, long dealerId) {
        long brokerCustomerId = dao.getCustomerIdByMobile(mobile, dealerId);
        return this.get(brokerCustomerId);
    }

    public BrokerCustomer get(String mobile, long dealerId) {
        long brokerCustomerId = dao.getCustomerIdByMobile(mobile, dealerId);
        return this.get(brokerCustomerId, false);
    }

    public long getExistsCustomerId(String mobile, long dealerId) {
        try {
            long brokerCustomerId = brokerCustomerEsService.getExistsCustomerId(mobile, dealerId);
            return brokerCustomerId;
        } catch (IOException ex) {
            return 0;
        }
    }

    public int getTodayNewCount(List<Long> dealerIds) {
        return dao.getTodayNewCount(dealerIds);
    }

    public long getFirstCustomerByClueId(long clueId) {
        long customerId = dao.getFirstCustomerIdByClueId(clueId);
        return customerId;
    }

    public BrokerCustomer getCustomer(long customerId, long brokerId) {
        BrokerCustomer customer = dao.getCustomer(customerId, brokerId);
        if (customer == null) {
            return null;
        }
        customer = this.attachProperties(Lists.newArrayList(customer)).get(0);
        customer = this.attachStatisticsData(customer);
        return customer;
    }

    public List<BrokerCustomer> getCustomerList(String mobile, String userName, long brokerId, String max, int limit) throws ResultException {
        List<BrokerCustomer> model = dao.getCustomerListByMobileOrNameMax(mobile, userName, brokerId, max, limit);
        for (BrokerCustomer c : model) {
            BrokerClue clue = clueDao.get(c.getBrokerClueIdLastest());
            if (clue != null) {
                c.setClue(clue);
            }
        }
        return model;
    }

    public List<BrokerCustomer> getCustomerList(String mobile, String userName, long brokerId, int page, int limit) throws ResultException {
        List<BrokerCustomer> model = dao.getCustomerListByMobileOrNamePage(mobile, userName, brokerId, (page - 1), limit);
        for (BrokerCustomer c : model) {
            BrokerClue clue = clueDao.get(c.getBrokerClueIdLastest());
            if (clue != null) {
                c.setClue(clue);
            }
        }
        return model;
    }

    public Integer updateParmByCustomerId(BrokerCustomer record) throws ResultException {
        if (record == null) {
            throw new ResultException(ResultCode.ERROR_PARAMS);
        }
        if (record.getBrokerCustomerId() <= 0) {
            throw new ResultException(ResultCode.ERROR_PARAMS);
        }
        //客户完成交易
        /*if(record.getStep()== CustomerStepEnum.deal.getValue()){
            this.CustomerDeal(record.getBrokerCustomerId());
        }*/
        return dao.updateParmByCustomerId(record);
    }

    /**
     * 客户成交关联操作
     *
     * @param customerId
     */
    public void CustomerDeal(long customerId) throws ResultException, IOException {
        //操作
        //  1. 客户状态完成时  意向车型变成保有车辆
        //  2. 保有车辆变成已成交
        //  3. 需要添加发票信息

        BrokerCustomer customer = dao.get(customerId);
        BrokerCustomerCarsWill carsWill = carsWillDao.getCustomerCarsByClueID(customer.getBrokerClueIdLastest());
        //生成发票
        BrokerReceipt receipt = new BrokerReceipt();
        receipt.setBrokerCustomerId(customer.getBrokerCustomerId());
        receipt.setCreateTime(new Date());
        receipt.setBrokerId(customer.getBrokerId());
        receipt.setBrokerCustomerCarsWillId(carsWill.getBrokerCustomerCarsWillId());
        receipt.setStatus(Short.valueOf("-2"));//发票为上传
        brokerReceiptService.Create(receipt);
        //设置保有车辆完成购买
        carsWill.setIsDeal(Short.valueOf("1"));
        carsWillDao.updateParamByCustomerId(carsWill);

        //新增保有车辆
        BrokerCustomerCarsPersistant brokerCarModel = new BrokerCustomerCarsPersistant();
        brokerCarModel.setCarId(carsWill.getCarId());
        brokerCarModel.setSeriesId(carsWill.getSeriesId());
        brokerCarModel.setSubBrandId(carsWill.getSubBrandId());
        brokerCarModel.setBrandId(carsWill.getBrandId());
        brokerCarModel.setBrokerCustomerId(customerId);
        //brokerCarModel.setCreateTime(new Date());
        brokerCarModel.setBrokerCustomerCarsWillId(carsWill.getBrokerCustomerCarsWillId());

        brokerCarsSerivce.addCar(brokerCarModel);
    }

    /**
     * 创建客户
     *
     * @param brokerCustomer
     * @return
     */
    public int create(BrokerCustomer brokerCustomer) throws ResultException, IOException {
        int ret = dao.create(brokerCustomer);
        if (ret > 0) {
            brokerCustomer = this.get(brokerCustomer.getBrokerCustomerId(), false);
            //线索建卡
            if (brokerCustomer.getBrokerClueId() > 0) {
                //记录日志
                brokerLogService.log(brokerCustomer.getBrokerId(), brokerCustomer.getDealerId(), brokerCustomer.getBrokerCustomerId(), BrokerLogEnum.BrokerClueCustomerAdd, brokerCustomer, brokerCustomer.getBrokerInfo().getName());
                //处理线索
                brokerClueService.handle(brokerCustomer.getBrokerId(), brokerCustomer.getBrokerClueId(), BrokerClueHandleTypeEnum.ConvertToCustomer);
            } else {
                //记录日志
                brokerLogService.log(brokerCustomer.getBrokerId(), brokerCustomer.getDealerId(), brokerCustomer.getBrokerCustomerId(), BrokerLogEnum.BrokerCustomerAdd, brokerCustomer, brokerCustomer.getBrokerInfo().getName());
            }
            //创建客户 --  任务
            try {
                apiServiceFactory.BrokerTaskService().addCustomer(brokerCustomer.getBrokerId(), brokerCustomer.getBrokerCustomerId());
            } catch (Exception e) {
            }
        }
        return ret;
    }

    /**
     * 创建客户
     *
     * @param brokerCustomer
     * @param brokerCustomerCarsWill
     * @param carIds
     * @return
     * @throws com.xyauto.interact.broker.server.exceptions.ResultException
     * @throws java.io.IOException
     */
    @Transactional
    public int createCustomerAndCarsWill(BrokerCustomer brokerCustomer, BrokerCustomerCarsWill brokerCustomerCarsWill, List<Integer> carIds, BrokerClueTypeEnum type) throws ResultException, IOException {
        int ret = this.create(brokerCustomer);
        if (ret > 0) {
            if (!carIds.isEmpty()) {
                for (int id : carIds) {
                    BrokerCustomerCarsPersistant customerCarsPersistant = new BrokerCustomerCarsPersistant();
                    Car car = carDao.getCar(id);
                    if (car != null) {
                        customerCarsPersistant.setCarId(id);
                        customerCarsPersistant.setSeriesId(car.getSeriesId());
                        customerCarsPersistant.setBrandId(car.getBrandId());
                        customerCarsPersistant.setSubBrandId(car.getSubBrandId());
                        customerCarsPersistant.setBrokerCustomerId(brokerCustomer.getBrokerCustomerId());
                        customerCarsPersistant.setBrokerCustomerCarsWillId(Long.valueOf("0"));
                        brokerCarsSerivce.addCar(customerCarsPersistant);
                    }
                }
            }
            //给线索 添加客户信息
            if (brokerCustomer.getBrokerClueId() > 0) {
                //查找历史线索
                ExecutorService fixedThreadPool = Executors.newFixedThreadPool(40);
                List<Long> historyList =new ArrayList<>();

                if(type.equals(BrokerClueTypeEnum.TalkClue)){
                    BrokerClueSearchParameters params = new BrokerClueSearchParameters();
                    params.setDealerIds(Lists.newArrayList(brokerCustomer.getDealerId()));
                    params.setIsdelete(Short.valueOf("0"));
                    //callerphonenumber
                    params.setCallerPhoneNumber(brokerCustomer.getMobile());
                    historyList = brokerClueEsService.search(params,1,100);

                }else {
                    historyList = brokerClueService.getNotCustomerByMobilelDealerId(brokerCustomer.getMobile(), brokerCustomer.getDealerId());
                }
                //增加当前线索id  当数据为话单时，客户主叫号可能为 座机号获取其他号码，同时防止创建客户是修改手机号导致无法处理数据
                historyList.add(brokerCustomer.getBrokerClueId());
                if (historyList.isEmpty() == false) {
                    historyList.forEach(item -> {
                        fixedThreadPool.execute(() -> {
                            try {
                                //更新线索数据
                                brokerClueDaoProxy.updateClueCustomer(brokerCustomer.getBrokerCustomerId(), item);
                                //更新线索及客户索引
                                brokerClueEsService.update(item);
                            } catch (Exception e) {
                                this.error("回溯历史线索客户失败:" + item + ":" + e.getMessage());
                            }
                        });
                    });
                }
                fixedThreadPool.shutdown();
            }
            //更新经纪人客户索引
            brokerCustomerEsService.update(brokerCustomer.getBrokerCustomerId());
            brokerCustomerCarsWill.setBrokerCustomerId(brokerCustomer.getBrokerCustomerId());
            ret = carsWillDao.create(brokerCustomerCarsWill);
        }
        return ret;
    }

    public int getListIdsCount(List<Short> steps, List<Short> categories, List<Short> level, long brokerId, long targetBrokerId,
            long dealerId, int cityId, int provinceId, int mobile, String userName, String beginTime, String endTime, String max) {
        return dao.getListIdsCount(steps, categories, level, brokerId, targetBrokerId, dealerId, cityId, provinceId, mobile,
                userName, beginTime, endTime, max);
    }

    public int getTimeoutCustomerCount(long dealerId, long brokerId) {
        return dao.getTimeoutCustomerCount(dealerId, brokerId);
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
     * 搜索客户列表
     *
     * @param params
     * @param max
     * @param limit
     * @return
     * @throws java.io.IOException
     */
    public List<Long> searchListIds(BrokerCustomerSearchParameters params, String max, int limit) throws IOException {
        return brokerCustomerEsService.search(params, max, limit);
    }

    /**
     * 搜索客户列表
     *
     * @param params
     * @param page
     * @param limit
     * @return
     * @throws java.io.IOException
     */
    public List<Long> searchListIds(BrokerCustomerSearchParameters params, int page, int limit) throws IOException {
        return brokerCustomerEsService.search(params, page, limit);
    }

    /**
     * 搜索客户列表总数
     *
     * @param params
     * @param max
     *
     * @return
     * @throws java.io.IOException
     */
    public int searchListCount(BrokerCustomerSearchParameters params, String max) throws IOException {
        return brokerCustomerEsService.searchListCount(params, max);
    }

    /**
     * 获取所有客户总数
     *
     * @return
     */
    public int getAllCount() {
        return dao.getAllCount();
    }

    /**
     * 批量获取经纪人客户
     *
     * @param page
     * @param limit
     * @return
     */
    public List<Long> getBatchBrokerList(int page, int limit) {
        return dao.getBatchBrokerList((page - 1) * limit, limit);
    }

    /**
     * 修改经纪人客户基础信息
     *
     * @param params
     * @return
     */
    public int update(BrokerCustomerUpdateParameters params) {
        int ret = dao.update(params);
        if (ret > 0) {
            Broker broker = brokerService.get(params.getBrokerId());
            BrokerCustomer brokerCustomer = this.get(params.getBrokerCustomerId());
            //记录日志
            if (params.getStep() != 0) {
                brokerLogService.log(broker.getBrokerId(), broker.getDealerId(), brokerCustomer.getBrokerCustomerId(), BrokerLogEnum.BrokerCustomerStepUpdate, brokerCustomer, broker.getName(), brokerCustomer.getStepDesc());
            }
            if (params.getNextContactTime() > 0) {
                brokerLogService.log(broker.getBrokerId(), broker.getDealerId(), brokerCustomer.getBrokerCustomerId(), BrokerLogEnum.BrokerCustomerContactTimeUpdate, brokerCustomer, broker.getName(), DateUtil.timeStamp2Date(String.valueOf(params.getNextContactTime()), DateStyle.YYYY_MM_DD_HH_MM_SS_CN.getValue()));
            }
            if (params.getLevel() > 0) {
                brokerLogService.log(broker.getBrokerId(), broker.getDealerId(), brokerCustomer.getBrokerCustomerId(), BrokerLogEnum.BrokerCustomerLevelUpdate, brokerCustomer, broker.getName(), brokerCustomer.getLevelDesc());
            }
            if (params.getExchangeType() > 0) {
                brokerLogService.log(broker.getBrokerId(), broker.getDealerId(), brokerCustomer.getBrokerCustomerId(), BrokerLogEnum.BrokerCustomerExchangeUpdate, brokerCustomer);
            }
            if (params.getGender() > 0) {
                brokerLogService.log(broker.getBrokerId(), broker.getDealerId(), brokerCustomer.getBrokerCustomerId(), BrokerLogEnum.BrokerCustomerGenderUpdate, brokerCustomer);
            }
            if (params.getIsAllopatryRegister() > -1) {
                brokerLogService.log(broker.getBrokerId(), broker.getDealerId(), brokerCustomer.getBrokerCustomerId(), BrokerLogEnum.BrokerCustomerAllopatryUpdate, brokerCustomer);
            }
            if (params.getPayType() > 0) {
                brokerLogService.log(broker.getBrokerId(), broker.getDealerId(), brokerCustomer.getBrokerCustomerId(), BrokerLogEnum.BrokerCustomerPayTypeUpdate, brokerCustomer);
            }
            if (params.getRemark().isEmpty() == false) {
                brokerLogService.log(broker.getBrokerId(), broker.getDealerId(), brokerCustomer.getBrokerCustomerId(), BrokerLogEnum.BrokerCustomerRemarkUpdate, brokerCustomer, broker.getName(), params.getRemark());
            }
            if (params.getUsername().isEmpty() == false) {
                brokerLogService.log(broker.getBrokerId(), broker.getDealerId(), brokerCustomer.getBrokerCustomerId(), BrokerLogEnum.BrokerCustomerUserNameUpdate, brokerCustomer);
            }
        }
        return ret;
    }

    /**
     * 线索附能
     *
     * @param
     * @return
     */
    private List<BrokerCustomer> attachStatisticsData(List<BrokerCustomer> list) {
        if (list.isEmpty()) {
            return list;
        }
        list.forEach(item -> {
            item = this.attachStatisticsData(item);
        });
        return list;
    }

    /**
     * 线索附能
     *
     * @param brokerCustomer
     * @return
     */
    private BrokerCustomer attachStatisticsData(BrokerCustomer brokerCustomer) {
        //处理线索附能
        if (brokerCustomer.getBrokerClueId() > 0) {
            BrokerClue brokerClue = clueDao.get(brokerCustomer.getBrokerClueId());
            if (brokerClue != null) {
                //获取数据指数
                Map<String, Double> points = dataAttachService.getDataAttachPoints(brokerClue.getCustomerId(), brokerCustomer.getDealerId());
                brokerCustomer.setCarPurchasingIndex(points.getOrDefault("carPurchasingIndex", Double.valueOf("0")));
                brokerCustomer.setConfirm(points.getOrDefault("confirm", Double.valueOf("0")));
                brokerCustomer.setSatisfaction(points.getOrDefault("satisfaction", Double.valueOf("0")));
                brokerCustomer.setSensitive(points.getOrDefault("sensitive", Double.valueOf("0")));
                brokerCustomer.setFreshness(points.getOrDefault("freshness", Double.valueOf("0")));
                brokerCustomer.setUrgency(points.getOrDefault("urgency", Double.valueOf("0")));
                //处理关注车型
                List<JSONObject> followCars = dataAttachService.getDataAttachFollowCars(brokerClue.getCustomerId());
                brokerCustomer.setFollowSeries(followCars);
                //获取外呼线索购车标签
                if (brokerCustomer.getMobile().isEmpty() == false) {
                    brokerCustomer.setTags(dataAttachService.getDataAttachTags(brokerClue.getCustomerId(), brokerClue.getMobile()));
                }
                //获取购车预算
                brokerCustomer.setBudget(dataAttachService.getPurchaseBudget(brokerClue.getCustomerId()));
            }
        }

        return brokerCustomer;
    }

    /**
     * 装配线索附带信息
     *
     * @param list
     * @return
     */
    private List<BrokerCustomer> attachProperties(List<BrokerCustomer> list) {
        List<Long> brokerIds = Lists.newArrayList();
        List<Integer> cityIds = Lists.newArrayList();
        List<Integer> provinceIds = Lists.newArrayList();
        List<Integer> brandIds = Lists.newArrayList();
        if (list.isEmpty()) {
            return list;
        }
        list.forEach(item -> {
            if (brokerIds.contains(item.getBrokerId()) == false) {
                brokerIds.add(item.getBrokerId());
            }
            if (cityIds.contains(item.getCityId()) == false && item.getCityId() > 0) {
                cityIds.add(item.getCityId());
            }
            if (provinceIds.contains(item.getProvinceId()) == false && item.getProvinceId() > 0) {
                provinceIds.add(item.getProvinceId());
            }
        });
        //经纪人信息
        Map<Long, Broker> brokerMaps = brokerService.getMaps(brokerIds);
        //获取省份信息
        Map<Integer, Province> provinceMaps = provinceService.getMaps(provinceIds);
        //获取城市信息
        Map<Integer, City> cityMaps = cityService.getMaps(cityIds);
        //获取品牌信息
        Map<Integer, Brand> brandMaps = brandService.getMaps(brandIds);

        list.forEach(item -> {
            item.setBrokerInfo(brokerMaps.get(item.getBrokerId()));
            item.setCity(cityMaps.get(item.getCityId()));
            item.setProvince(provinceMaps.get(item.getProvinceId()));
            //获取意向车型信息
            BrokerCustomerCarsWill carsWill = carsWillDao.getByBrokerCustomerId(item.getBrokerId(), item.getBrokerCustomerId());
            if (carsWill != null && carsWill.getCarId() > 0) {
                item.setCarsWill(carsWill);
            }
            try {
                item.setCustomerCars(brokerCarsSerivce.getCustomerCarsByCustomerId(item.getBrokerCustomerId()));
            } catch (Exception e) {
            }

        });
        return list;
    }

}
