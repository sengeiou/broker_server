package com.xyauto.interact.broker.server.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.xyauto.interact.broker.server.dao.proxy.BrokerClueDaoProxy;
import com.xyauto.interact.broker.server.enums.BrokerTypeEnum;
import com.xyauto.interact.broker.server.model.vo.*;
import com.xyauto.interact.broker.server.cluemq.ClueEntity;
import com.xyauto.interact.broker.server.cluemq.ClueLog;
import com.xyauto.interact.broker.server.cluemq.ClueMessageEntity;
import com.xyauto.interact.broker.server.cluemq.ClueSyncAdd;
import com.xyauto.interact.broker.server.enums.BrokerClueHandleTypeEnum;
import com.xyauto.interact.broker.server.enums.BrokerEnum;
import com.xyauto.interact.broker.server.enums.BrokerLogEnum;
import com.xyauto.interact.broker.server.enums.ResultCode;
import com.xyauto.interact.broker.server.exceptions.ResultException;
import com.xyauto.interact.broker.server.model.po.BrokerClueSearchParameters;
import com.xyauto.interact.broker.server.service.cloud.ApiServiceFactory;
import com.xyauto.interact.broker.server.service.es.clue.BrokerClueEsService;
import com.xyauto.interact.broker.server.util.ILogger;
import com.xyauto.interact.broker.server.util.Result;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.javassist.expr.NewArray;
import org.elasticsearch.index.query.IdsQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Scope("prototype")
public class BrokerClueService implements ILogger {

    @Autowired
    private BrokerService brokerService;

    @Autowired
    private BrokerClueDaoProxy brokerClueDao;

    @Autowired
    private BrandService brandService;

    @Autowired
    private SubBrandService subBrandService;

    @Autowired
    private SeriesService seriesService;

    @Autowired
    private CarService carService;

    @Autowired
    private ProvinceService provinceService;

    @Autowired
    private BrokerClueEsService brokerClueEsService;

    @Autowired
    @Lazy
    private BrokerCustomerService brokerCustomerService;

    @Autowired
    private CityService cityService;

    @Autowired
    private ApiServiceFactory apiService;

    @Autowired
    private BrokerLogService logService;

    @Autowired
    private DataAttachService dataAttachService;

    @Autowired
    private ClueLog clueLog;

    @Autowired
    private BrokerLogService brokerLogService;

    @Autowired
    @Lazy
    private ClueSyncAdd clueSyncHandle;

    @Autowired
    DealerService dealerService;

    @Autowired
    Result result;
    
    public List<BrokerClue> getClueList(List<Long> ids) {
        List<BrokerClue> list = Lists.newArrayList();
        if (ids.isEmpty()) {
            return list;
        }
        list = brokerClueDao.getClueList(ids);
        list = this.attachProperties(list);
        list = this.attachStatisticsData(list);
        return list;
    } 
    
    public List<BrokerClue> getBasicClueList(List<Long> ids) {
        List<BrokerClue> list = Lists.newArrayList();
        if (ids.isEmpty()) {
            return list;
        }
        list = brokerClueDao.getClueList(ids);
        return list;
    }

    public List<Long> searchListIds(BrokerClueSearchParameters params, String max, int limit) throws IOException {
        return brokerClueEsService.search(params, max, limit);
    }

    public List<Long> searchListIds(BrokerClueSearchParameters params, int page, int limit) throws IOException {
        return brokerClueEsService.search(params, page, limit);
    }

    public int searchListCount(BrokerClueSearchParameters params, String max) throws IOException {
        return brokerClueEsService.searchListCount(params, max);
    }

    public Map<String, Object> searchGroupByMobile(BrokerClueSearchParameters params) throws IOException {
        return brokerClueEsService.serarchGroupByMobile(params);
    }

    public int searchGroupByMobileTotal(BrokerClueSearchParameters params) throws IOException {
        return brokerClueEsService.serarchCountGroupByMobile(params);
    }

    public long getNextAllottingBrokerId(long dealerId, List<Long> brokerIds) {
        long brokerId = brokerClueDao.getNextAllottingBrokerId(dealerId, brokerIds);
        return brokerId;
    }

    public long addNetClue(ClueEntity entity) {
        return brokerClueDao.addNetClue(entity);
    }

    /**
     * 系统自动分配线索到经纪人
     *
     * @param brokerClueId
     * @param brokerId
     * @return
     */
    public int allot(long brokerId, long brokerClueId) {
        try {
            int src = brokerClueDao.allot(brokerClueId, brokerId);
            if (src > 0) {
                BrokerClue brokerClue = brokerClueDao.get(brokerClueId);
                //更新索引
                brokerClue.setBrokerId(brokerId);
                brokerClueEsService.add(Lists.newArrayList(brokerClue));
            }
            return src;
        } catch (IOException e) {
            this.error("线索分配经纪人错误:" + e.getMessage());
            return 0;
        }
    }

    /**
     * 批量分配线索到经纪人
     *
     * @param targetBrokerId
     * @param brokerId
     * @param brokerClueList
     * @return
     */
    public int updateAllotList(long brokerId, long targetBrokerId, List<BrokerClue> brokerClueList ) {
        try {
            if(brokerClueList == null || brokerClueList.size() == 0){
                return  0;
            }
            Broker broker = brokerService.get(brokerId);
            Broker targetBroker = brokerService.get(targetBrokerId);
            if (broker == null || targetBroker == null) {
                return 0;
            }
            //获取被分配线索的ids
            List<Long> clueIds = Lists.newArrayList();
            brokerClueList.forEach(item ->{
                List<Long> ids =this.updateAllot(brokerId,item);
                if(!ids.isEmpty())
                {
                    clueIds.addAll(ids);
                }
            });
            //int ret = brokerClueDao.updateAllotList(clueIds, brokerId);
            if (clueIds.size() > 0) {
                List<BrokerClue> Clues = brokerClueDao.getClueList(clueIds);
                //记录日志
                for (BrokerClue brokerClue : Clues) {
                    brokerLogService.log(brokerClue.getBrokerId(), brokerClue.getDealerId(), brokerClue.getBrokerClueId(), BrokerLogEnum.BrokerClueAllotAdd, brokerClue, targetBroker.getName(), broker.getName());
                }
                //更新索引
                brokerClueEsService.add(Clues);
            }
            return clueIds.size();
        } catch (IOException e) {
            this.error("线索分配经纪人错误:" + e.getMessage());
            return 0;
        }
    }

    /**
     * 把当前线索（手机号）相关线索分配给同一个经纪人
     * @param brokerId
     * @param brokerClue
     * @return
     */
    public List<Long> updateAllot(long brokerId, BrokerClue brokerClue ) {
        if(brokerClue== null ){
            return Lists.newArrayList();
        }
        //检查是否需要把线索对应客户分配给 其他 经纪人
        if(brokerClue.getBrokerCustomerId()> 0) {
            BrokerCustomer brokerCustomer = brokerCustomerService.getCustomer(brokerClue.getBrokerCustomerId(), brokerClue.getBrokerId());
            if (brokerClue!=null){
                brokerService.updateCustomerBroker(Lists.newArrayList(brokerClue.getBrokerCustomerId()), brokerId);
            }
        }
        //店铺线索经纪人id 为0  查询经纪人手中客户手机号匹配的所有线索
        List<Long> ids = brokerClueDao.getClueIdsByMobileBrokerId(brokerClue.getBrokerId(),brokerClue.getMobile());
        int ret = brokerClueDao.updateAllotList(ids,brokerId);
        if(ret>0){
            return ids;
        }else {
            return  Lists.newArrayList();
        }
    }

    public int getAllCount() {
        return brokerClueDao.getAllCount();
    }

    public List<BrokerClue> getBatchBrokerList(int page, int limit) {
        List<BrokerClue> list = brokerClueDao.getBatchBrokerList((page - 1) * limit, limit);
        return list;
    }

    public BrokerClue get(long brokerClueId) {
        return this.get(brokerClueId, true);
    }

    public BrokerClue get(long brokerClueId, boolean simple) {
        BrokerClue brokerClue = brokerClueDao.get(brokerClueId);
        if (simple || brokerClue == null) {
            return brokerClue;
        }
        brokerClue = this.attachProperties(Lists.newArrayList(brokerClue)).get(0);
        brokerClue = this.attachStatisticsData(brokerClue);
        if (brokerClue.getBrokerCustomerId() > 0) {
            brokerClue.setCustomer(brokerCustomerService.get(brokerClue.getBrokerCustomerId(), false));
        } else {
            //补充通过手机号验证线索是否已建卡
            BrokerCustomer brokerCustomer = brokerCustomerService.existsCustomer(brokerClue.getMobile(), brokerClue.getDealerId());
            if (brokerCustomer != null) {
                //设置线索绑定客户
                brokerClue.setBrokerCustomerId(brokerCustomer.getBrokerCustomerId());
                brokerClue.setCustomer(brokerCustomerService.get(brokerCustomer.getBrokerCustomerId(), false));
            }
        }
        BrokerClueByCall brokerClueByCall = brokerClue.getCallInfo();
        if (brokerClueByCall != null) {
            try {
                Broker tBroker = brokerService.getBrokerByMobile(brokerClue.getDealerId(), brokerClueByCall.getCalleenumber());
                if (tBroker != null) {

                    brokerClueByCall.setCallerBroker(tBroker);
                    brokerClue.setCallInfo(JSONObject.toJSONString(brokerClueByCall));
                }
            } catch (Exception ex) {
                this.error(ex.getMessage());
            }
        }
        return brokerClue;
    }

    /**
     * 认领线索
     *
     * @param cluePoolId
     * @param brokerId
     * @return
     * @throws ResultException
     * @throws IOException
     */
    public Result pickup(long cluePoolId, long brokerId) throws ResultException, IOException {
        boolean dealerDistribute = false;
        long dealerId = 0;
        short type = 0;
        Broker broker = brokerService.get(brokerId);
        if (broker == null) {
            //获取检查是否为经销商其他工作人员
            Result ret = apiService.platformService().brokerInfo(brokerId);
            if (ret.getCode() == ResultCode.Success.getCode()) {
                List<JSONObject> list = JSON.parseArray(JSON.toJSONString(ret.getData()), JSONObject.class);
                if (list.isEmpty()) {
                    this.info("找不到用户作为其他经销商工作人员身份，经纪人id:" + brokerId);
                    return result.format(ResultCode.BokerCluePickUpError);
                }
                //设置进入店铺线索分配
                dealerDistribute = true;
                dealerId = list.get(0).getLong("dealerId");
                type = list.get(0).getShortValue("accountTypeId");
                brokerId = list.get(0).getLong("accountId");
                //转换行圆慧平台用户身份
                type = BrokerEnum.chageBrokerType(type);
            } else {
                this.info("获取用户经销商工作人员身份失败，经纪人id:" + brokerId);
                return result.format(ResultCode.BokerCluePickUpError);
            }
        } else {
            this.info("获取数据库中存在经纪人");
            dealerId = broker.getDealerId();
            type = Short.valueOf(String.valueOf(broker.getType()));
            brokerId = broker.getBrokerId();
        }
        // 1:市场经理 2：销售顾问  3：市场专员  4：销售经理  5：店总
        if (Lists.newArrayList(
                BrokerTypeEnum.StoreManager.getValue(),
                BrokerTypeEnum.MarketPersonne.getValue(),
                BrokerTypeEnum.MarketManager.getValue()).contains(type) || type == 0) {
            dealerDistribute = true;
        }
        //获取线索数据
        Result result = apiService.clueService().getInfoByPoolId(cluePoolId);
        if (result.getCode() != ResultCode.Success.getCode()) {
            this.info("抢线索信息获取失败，经纪人id:" + brokerId);
            return result.format(ResultCode.BokerCluePickUpError);
        }
        ClueEntity clue = JSONObject.parseObject(JSON.toJSONString(result.getData()), ClueEntity.class);
        clue.setDistributedAdviserId(dealerDistribute ? 0 : brokerId);
        clue.setDealerId(dealerId);
        clue.setType(2);
        clue.setExtension(JSON.toJSONString(result.getData()));
        //设置为公共线索
        clue.setSourceId(5);

        //检查此线索已被抢
        int ret = brokerClueDao.dunplicatePickUpCheck(clue.getId());
        if (ret > 0) {
            this.info("线索已被抢，线索id:" + clue.getId());
            return result.format(ResultCode.BokerCluePickUpError,"您慢了一步，该线索已经被其他人领走啦。");
        }
        //检查当前店是否有抢单限制
        Result returnVal = apiService.BaseDealerService().getInfo(dealerId);
        if (returnVal.getCode() == ResultCode.Success.getCode()) {
            //判定经销商是否有抢单限制
            Map<String, Object> returnValData = JSON.parseObject(JSON.toJSONString(returnVal.getData()));
            //经销商0收费，1使用，-1免费，免费经销商不具备抢单功能---走彦武接口要小于0，走broker库要<2
            if (Integer.valueOf(returnValData.get("isTrail").toString()) <0) {
                this.info("所属经销商不在收费序列，不能进行抢单，经纪人id:" + brokerId);
                return result.format(ResultCode.BokerCluePickUpError,"免费会员无法认领线索，如需成为付费会员，请联系销售人员。");
            }
        } else {
        	return result.format(ResultCode.BokerCluePickUpError);
        }
        long checkResult = brokerClueDao.limitPickUpCheck(dealerId);
        if (checkResult >= 20) {
            this.info("经销商限定20个到达限制，经纪人id:" + brokerId);
            return result.format(ResultCode.BokerCluePickUpError,"每天最多领取20条，请不要重复操作。");
        }
        //设置已抢处理,todo:当接口异常，标记抢单未完成进入队列后续处理
        Result grabResult=apiService.clueService().grabBusinessOpportunity(cluePoolId, dealerId);
        if (returnVal.getCode() != ResultCode.Success.getCode()) {
        	this.info("抢线索失败,线索公共池id是："+cluePoolId+" 经销商id是："+dealerId);
        	return result.format(ResultCode.BokerCluePickUpError);        	 
		}
        int opportunityRelDealerId=(int)grabResult.getData();
        if (opportunityRelDealerId<=0) {
        	this.info("抢线索失败,线索关联id是0,线索公共池id是："+cluePoolId+" 经销商id是："+dealerId);
        	return result.format(ResultCode.BokerCluePickUpError); 
		}
    	clue.setOpportunityRelDealerId(opportunityRelDealerId);
        long retval = brokerClueDao.addNetClue(clue);

        if (retval > 0) {
            //检查客户本店是否已经存在
            BrokerCustomer brokerCustomer = brokerCustomerService.existsCustomer(
                    clue.getCustomerContact(), clue.getDealerId());
            if (brokerCustomer != null) {
                // 设置线索绑定客户
                brokerClueDao.updateClueCustomer(brokerCustomer.getBrokerCustomerId(), clue.getBrokerClueId());
            }
            if (dealerDistribute == false) {
                //直接设置分配经纪人为brokerId
                brokerClueDao.allot(clue.getBrokerClueId(), brokerId);
                //新增线索记录
                BrokerClue brokerClue = this.get(clue.getBrokerClueId());
                brokerLogService.log(brokerId, dealerId, clue.getBrokerClueId(), BrokerLogEnum.BrokerCluePickUpAdd, brokerClue, broker == null ? "异常姓名" : broker.getName());
                brokerClueEsService.update(clue.getBrokerClueId());
            } else {
                //进入店铺自动分配处理
                clueSyncHandle.setDealerClue(clue);
            }    
            return result.format(ResultCode.Success,1);             
        }
        return result.format(ResultCode.BokerCluePickUpError);
    }

    public int getNotHandleCount(long dealerId, long brokerId) {
        return brokerClueDao.getNotHandleCount(dealerId, brokerId);
    }

    public int getNotToCustomerCount(long dealerId, long brokerId) {
        return brokerClueDao.getNotToCustomerCount(dealerId, brokerId);
    }

    public int getNotDistributeCount(long dealerId) {
        return brokerClueDao.getNotDistributeCount(dealerId);
    }

    public int duplicateCheck(long clueId, int type, long dealerId) {
        return brokerClueDao.duplicateCheck(clueId, type, dealerId);
    }

    public int handle(long brokerId, List<Long> brokerClueList, BrokerClueHandleTypeEnum type) {
        Broker broker = brokerService.get(brokerId);
        if (broker == null) {
            return 0;
        }
        int suc = 0;
        for (int i = 0; i < brokerClueList.size(); i++) {
            try {
                suc = suc + this.handle(brokerId, brokerClueList.get(i), type);
            } catch (Exception e) {
            }
        }
        return suc;
    }

    /**
     * 处理线索
     *
     * @param brokerId
     * @param brokerClueId
     * @param type
     * @return
     * @throws com.xyauto.interact.broker.server.exceptions.ResultException
     * @throws java.io.IOException
     */
    public int handle(long brokerId, long brokerClueId, BrokerClueHandleTypeEnum type) throws ResultException, IOException {
        Broker broker = brokerService.get(brokerId);
        BrokerClue brokerClue = this.get(brokerClueId);
        if (broker == null || brokerClue == null) {
            return 0;
        }
        int ret = brokerClueDao.handle(brokerId, brokerClueId);
        if (ret > 0) {
            try {
                //同步线索分配端线索已处理
                apiService.clueHandleService().handle(brokerClue.getClueRefDealerId(), broker.getDealerId());
            } catch (ResultException e) {
                this.error(e.getMessage());
            }
            try {
                //更新线索索引
                brokerClueEsService.update(brokerClue.getBrokerClueId());
            } catch (Exception e) {
            }
            try {
                //记录处理日志
                JSONObject extension = new JSONObject();
                extension.put("type", type.getValue());
                logService.log(brokerId, broker.getDealerId(), brokerClueId, BrokerLogEnum.BrokerClueHandle, extension);
            } catch (Exception e) {
                this.error(e.getMessage());
            }
        }
        return ret;
    }

    /**
     * 线索赋能处理
     *
     * @param list
     * @return
     */
    private List<BrokerClue> attachStatisticsData(List<BrokerClue> list) {
        if (list.isEmpty()) {
            return list;
        }
        list.forEach(item -> {
            item = this.attachStatisticsData(item);
        });
        return list;
    }

    /**
     * 线索赋能处理
     *
     * @param brokerClue
     * @return
     */
    private BrokerClue attachStatisticsData(BrokerClue brokerClue) {
        if (brokerClue == null) {
            return brokerClue;
        }
        //处理线索附能
        if (brokerClue.getCustomerId() > 0) {
            //获取数据指数
            Map<String, Double> points = dataAttachService.getDataAttachPoints(brokerClue.getCustomerId(), brokerClue.getDealerId());
            brokerClue.setCarPurchasingIndex(points.getOrDefault("carPurchasingIndex", Double.valueOf("0")));
            brokerClue.setConfirm(points.getOrDefault("confirm", Double.valueOf("0")));
            brokerClue.setSatisfaction(points.getOrDefault("satisfaction", Double.valueOf("0")));
            brokerClue.setSensitive(points.getOrDefault("sensitive", Double.valueOf("0")));
            brokerClue.setFreshness(points.getOrDefault("freshness", Double.valueOf("0")));
            brokerClue.setUrgency(points.getOrDefault("urgency", Double.valueOf("0")));
            //处理关注车型
            List<JSONObject> followCars = dataAttachService.getDataAttachFollowCars(brokerClue.getCustomerId());
            brokerClue.setFollowSeries(followCars);
            //获取外呼线索购车标签
            if (brokerClue.getMobile().isEmpty() == false) {
                String tags = dataAttachService.getDataAttachTags(brokerClue.getCustomerId(), brokerClue.getMobile());
                brokerClue.setTags(tags);
            }
            //获取购车预算
            brokerClue.setBudget(dataAttachService.getPurchaseBudget(brokerClue.getCustomerId()));
        }

        return brokerClue;
    }

    /**
     * 流失线索处理
     *
     * @param clueId
     * @param dealerId
     */
    public void miss(long clueId, long dealerId) {
        List<BrokerClue> list = brokerClueDao.getClueByMiss(clueId, dealerId);
        for (BrokerClue item : list) {
            int ret = brokerClueDao.miss(item.getBrokerClueId());
            if (ret > 0) {
                //更新索引
                brokerClueEsService.update(item.getBrokerClueId());
                //记录审计统计
                clueLog.log(new ClueMessageEntity(item.getClueId(), "【线索流失】线索流失处理完成【成功】"));
                //记录日志
                brokerLogService.log(item.getBrokerId(), item.getDealerId(), item.getBrokerClueId(), BrokerLogEnum.BrokerClueMiss, item);
            }
        }
    }

    /**
     * 根据手机号获取最新一条线索详情
     *
     * @param mobile
     * @param dealerId
     * @return
     */
    public BrokerClue getByMobileDealerId(String mobile, long dealerId) {
        long brokerClueId = brokerClueDao.getLastestByMobileDealerId(mobile, dealerId);
        if (brokerClueId > 0) {
            return this.get(brokerClueId, false);
        }
        return null;
    }

    /**
     * 获取历史未附加客户信息的线索
     *
     * @param mobile
     * @param dealerId
     * @return
     */
    public List<Long> getNotCustomerByMobilelDealerId(String mobile, long dealerId) {
        return brokerClueDao.getNotCustomerByMobileDealerId(mobile, dealerId);
    }

    /**
     * 装配线索附带信息
     *
     * @param list
     * @return
     */
    private List<BrokerClue> attachProperties(List<BrokerClue> list) {
        List<Long> brokerIds = Lists.newArrayList();
        List<Integer> cityIds = Lists.newArrayList();
        List<Integer> provinceIds = Lists.newArrayList();
        List<Integer> mobileCityIds = Lists.newArrayList();
        List<Integer> mobileProvinceIds = Lists.newArrayList();
        List<Integer> brandIds = Lists.newArrayList();
        List<Integer> subBrandIds = Lists.newArrayList();
        List<Integer> seriesIds = Lists.newArrayList();
        List<Integer> carIds = Lists.newArrayList();
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
            if (mobileCityIds.contains(item.getMobileCityId()) == false && item.getMobileCityId()> 0) {
                mobileCityIds.add(item.getMobileCityId());
            }
            if (mobileProvinceIds.contains(item.getMobileProvinceId()) == false && item.getMobileProvinceId()> 0) {
                mobileProvinceIds.add(item.getMobileProvinceId());
            }
            if (brandIds.contains(item.getBrandId()) == false) {
                brandIds.add(item.getBrandId());
            }
            if (subBrandIds.contains(item.getSubBrandId()) == false) {
                subBrandIds.add(item.getSubBrandId());
            }
            if (seriesIds.contains(item.getSeriesId()) == false) {
                seriesIds.add(item.getSeriesId());
            }
            if (carIds.contains(item.getCarId()) == false) {
                carIds.add(item.getCarId());
            }
        });
        //经纪人信息
        Map<Long, Broker> brokerMaps = brokerService.getMaps(brokerIds);
        //获取省份信息
        Map<Integer, Province> provinceMaps = provinceService.getMaps(provinceIds);
        //获取城市信息
        Map<Integer, City> cityMaps = cityService.getMaps(cityIds);
        //获取手机省份信息
        Map<Integer, Province> mobileProvinceMaps = provinceService.getMaps(mobileProvinceIds);
        //获取手机城市信息
        Map<Integer, City> mobileCityMaps = cityService.getMaps(mobileCityIds);
        //获取品牌信息
        Map<Integer, Brand> brandMaps = brandService.getMaps(brandIds);
        //获取子品牌信息
        Map<Integer, SubBrand> subBrandMaps = subBrandService.getMaps(subBrandIds);
        //获取车系信息
        Map<Integer, Series> seriesMaps = seriesService.getMaps(seriesIds);
        //获取车款信息
        Map<Integer, Car> carMaps = carService.getMaps(carIds);
        list.forEach(item -> {
            item.setBroker(brokerMaps.get(item.getBrokerId()));
            item.setCity(cityMaps.get(item.getCityId()));
            item.setProvince(provinceMaps.get(item.getProvinceId()));
            item.setMobileCity(mobileCityMaps.get(item.getMobileCityId()));
            item.setMobileProvince(mobileProvinceMaps.get(item.getMobileProvinceId()));
            item.setBrand(brandMaps.get(item.getBrandId()));
            item.setSubBrand(subBrandMaps.get(item.getSubBrandId()));
            item.setSeries(seriesMaps.get(item.getSeriesId()));
            item.setCar(carMaps.get(item.getCarId()));
            //查找是否已建卡
            if(item.getBrokerCustomerId() == 0) {
                item.setBrokerCustomerId(brokerCustomerService.getExistsCustomerId(item.getMobile(), item.getDealerId()));
            }
        });
        return list;
    }

    public List<BrokerClue> brokerClueSyncByDealerId(long dealerId) {

        return brokerClueDao.getbrokerClueByDealerId(dealerId);
    }

    /**
     * 获取手机号上次分配到的经纪人id
     *
     * @param mobile
     * @param dealerId
     * @return
     */
    public long getLastAllotBrokerId(String mobile, long dealerId) {
        return brokerClueDao.getLastAllotBrokerId(mobile, dealerId);
    }

    /**
     * 批量更新线索处理状态
     * @param broker_clue_ids
     * @return
     */
    public int updateClueHandle(List<Long> broker_clue_ids){
    	if (broker_clue_ids==null||broker_clue_ids.size()<=0) {
			return 0;
		}
    	return brokerClueDao.updateClueHandle(broker_clue_ids);
    }

    /**
     * 处理线索
     *
     * @param brokerId
     * @param brokerClueIdList:需要处理的线索id
     * @param type
     * @return
     * @throws com.xyauto.interact.broker.server.exceptions.ResultException
     * @throws java.io.IOException
     */
    /*public int handleList(long dealerId, List<Long> brokerClueIdList, BrokerClueHandleTypeEnum type)
    		 {
    	//更新数据库
    	int ret = this.updateClueHandle(brokerClueIdList);   	       
        if (ret > 0) {
        	List<BrokerClue> list = this.getBasicClueList(brokerClueIdList);
            if (list == null || list.size() == 0) {
                return 0;
            }
            try {
            	 //更新线索索引
                brokerClueEsService.add(list);
                sendClueHandle(dealerId, brokerClueIdList);
                
            } catch (IOException e) {
                this.error(e.getMessage());
                return 0;
            }
            //批量方法--只在查看历史订单，导出会用到，暂时未记录日志
            try {
                //记录处理日志
                JSONObject extension = new JSONObject();
                extension.put("type", type.getValue());
                logService.log(brokerId, broker.getDealerId(), brokerClueId, BrokerLogEnum.BrokerClueHandle, extension);
            } catch (Exception e) {
                this.error(e.getMessage());
            }
        }
        return ret;
    }*/
   
    
    public Result sendClueHandle(long dealerId, List<Long> clueRefDealerIdList){
    	try {    		
    		StringBuffer buffer=new StringBuffer();
            for (Long id : clueRefDealerIdList) {
            	buffer.append(id).append(",");
			}
            String ids=buffer.substring(0, buffer.length()-1);
    		//同步线索分配端线索已处理
            Result result=apiService.clueHandleService().handleList(ids, dealerId);
            if (result.getCode()!=10000) {
            	this.info("同步状态失败，参数是："+ids+" 经销商id是："+dealerId);
            	this.info("同步状态失败，返回结果是："+result);
			}            
		} catch (Exception e) {
			this.info("同步处理状态失败"+e.getMessage());
		}
    	return null;
    }
       


    /**
     * 批量修改前 -- 检查改线索是否属于经销商
     * @param dealerId
     * @param brokerClueId
     * @return
     */
    public   List<BrokerClue> getBrokerClueByDealerIdAndClueIds(long dealerId,List<Long> brokerClueId){
        return brokerClueDao.getBrokerClueByDealerIdAndClueIds(dealerId,brokerClueId);
    }

    /**
     * 将OldBroker经纪人mobile的线索全部分配个broker_id
     * @param broker_id
     * @param OldBrokerId
     * @param mobile
     * @return
     */
    public Integer updateAllotByMobile( Long broker_id,  long OldBrokerId, String mobile){
        return brokerClueDao.updateAllotByMobile(broker_id,   OldBrokerId,  mobile);
    }

    /**
     * 获取该经纪人下对应手机号所有线索
     * @param broker_id
     * @param mobile
     * @return
     */
    public List<Long> getClueIdsByMobileBrokerId( Long broker_id, String mobile)
    {
        return brokerClueDao.getClueIdsByMobileBrokerId(broker_id,  mobile);
    }
}
