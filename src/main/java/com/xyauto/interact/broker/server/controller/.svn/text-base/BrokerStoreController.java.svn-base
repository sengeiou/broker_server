package com.xyauto.interact.broker.server.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.mcp.validate.annotation.Check;
import com.xyauto.interact.broker.server.enums.BrokerTypeEnum;
import com.xyauto.interact.broker.server.enums.ResultCode;
import com.xyauto.interact.broker.server.exceptions.ResultException;
import com.xyauto.interact.broker.server.model.vo.*;
import com.xyauto.interact.broker.server.service.*;
import com.xyauto.interact.broker.server.service.cloud.ApiServiceFactory;
import com.xyauto.interact.broker.server.util.RedisCache;
import com.xyauto.interact.broker.server.util.Result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * 消息设置
 */
@RestController
@RequestMapping(value = "/broker/store/")
public class BrokerStoreController extends BaseController {

    @Autowired
    BrokerStoreService brokerStoreService;

    @Autowired
    BrokerTemplateService brokerTemplateService;

    @Autowired
    BrokerService brokerService;

    @Autowired
    DealerCarService dealerCarService;

    @Autowired
    ApiServiceFactory apiService;

    @Autowired
    RedisCache redisCache;

    /**
     * 创建经纪人微店
     *
     * @param title 标题
     * @param introduction 导语
     * @param qrCode 二维码
     * @param brokerId 经纪人id
     * @param storeUrl 商店url
     * @param dealerId 经销商id
     * @return
     */
    @RequestMapping(value = "/createstore", method = RequestMethod.POST)
    public Result createStore(
            @Check(value = "title", required = false) String title,
            @Check(value = "introduction", required = false) String introduction,
            @Check(value = "qr_code", required = false) String qrCode,
            @Check(value = "broker_id", required = true) long brokerId,
            @Check(value = "store_url", required = false) String storeUrl,
            @Check(value = "dealer_id", required = true) long dealerId) {
        BrokerStore model = new BrokerStore();
        model.setTitle(title);
        model.setIntroduction(introduction);
        model.setQrCode(qrCode);
        model.setBrokerId(brokerId);
        model.setStoreUrl(storeUrl);
        model.setDealerId(dealerId);

        //获取销售人员
        Broker broker = brokerService.getSalePerson(brokerId);
        if (broker == null) {
            return result.format(ResultCode.BrokerNotFound);
        }

        int suc = brokerStoreService.create(model);
        if (suc > 0) {
            return result.format(ResultCode.Success, model);
        }
        return result.format(ResultCode.UnKnownError);
    }

    /**
     * 获取微店列表（区分经理和经纪人权限）
     *
     * @param brokerId
     * @param targetBrokerId
     * @param page
     * @param limit
     * @return
     * @throws com.xyauto.interact.broker.server.exceptions.ResultException
     */
    @RequestMapping(value = "/getlist", method = RequestMethod.GET)
    public Result getBrokerStoreList(
            @Check(value = "broker_id", defaultValue = "0") long brokerId,
            @Check(value = "target_broker_id", defaultValue = "0") long targetBrokerId,
            @Check(value = "page", defaultValue = "1") int page,
            @Check(value = "limit") int limit) throws ResultException {
        PagedList pageList = new PagedList();
        Broker targetBroker = brokerService.get(targetBrokerId, true);
        Broker broker = brokerService.get(brokerId, true);
        if (targetBroker == null) {
            return result.format(ResultCode.BrokerNotFound);
        }
        List<BrokerStore> model = Lists.newArrayList();
        int total = 0;
        if (brokerId == 0 && targetBroker.getType() != BrokerTypeEnum.Employee.getValue()) {
            page = page <= 0 ? 1 : page;
            List<Long> brokerIds = brokerService.getSalePersonListIdsByPage(targetBroker.getDealerId(), page, limit);
            model = brokerStoreService.getBrokerStoreByDealerIds(brokerIds);
            total = brokerService.getSaleBrokerCountByDealer(targetBroker.getDealerId());
        } else {
            if (broker == null) {
                return result.format(ResultCode.BrokerNotFound);
            }
            //验证查询权限
            if (broker != null && targetBroker.getDealerId() != broker.getDealerId()) {
                return result.format(ResultCode.NoPermission);
            }
            model.add(brokerStoreService.get(brokerId, true));
            total = 1;
        }
        //获取微店关于经纪人信息
        for (BrokerStore brokerStore : model) {
            brokerStore.setBroker(brokerService.get(brokerStore.getBrokerId(), true));
        }

        pageList.setList(model);
        pageList.setCount(total);
        pageList.setLimit(limit);
        pageList.setPage(page);

        return result.format(ResultCode.Success, pageList);
    }

    /**
     * 获取微店详细信息
     *
     * @param brokerId
     * @return
     */
    @RequestMapping(value = "/getinfo", method = RequestMethod.GET)
    public Result getBrokerStoreList(@Check(value = "broker_id") long brokerId)
            throws ResultException {
        List<BrokerStore> model = Lists.newArrayList();
        Broker broker = brokerService.getSalePerson(brokerId);
        if (broker == null) {
            return result.format(ResultCode.BrokerNotFound);
        }
        model.add(brokerStoreService.get(brokerId, true));
        return result.format(ResultCode.Success, model);
    }

    /**
     * 创建标题导语
     *
     * @param title 标题
     * @param introduction 导语
     * @param brokerid 经纪人id
     * @param type 标题导语 3 标题 4 导语
     * @param isDefault 是否默认
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Result addDesription(
            @Check(value = "title", required = false) String title,
            @Check(value = "introduction", required = false) String introduction,
            @Check(value = "broker_id", required = true) long brokerid,
            @Check(value = "type", required = true) short type,
            @Check(value = "is_default") short isDefault) {
        if (title.equals(null) || introduction.equals(null)) {
            return result.format(ResultCode.ERROR_PARAMS);
        }
        BrokerTemplate template = new BrokerTemplate();
        template.setIsDefault(isDefault);
        template.setType(type);
        if (type == 3) {
            template.setName(title);
        } else if (type == 4) {
            template.setContent(introduction);
        }
        template.setBrokerId(brokerid);
        int suc = brokerTemplateService.create(template);
        if (suc > 0) {
            return result.format(ResultCode.Success, template);
        }
        return result.format(ResultCode.UnKnownError);
    }

    /**
     * 批量设置经纪人微店标题导语
     *
     * @param brokerids
     * @param taget_broker_id
     * @param titleTemplateId
     * @param introductionTemplateId
     * @return
     * @throws com.xyauto.interact.broker.server.exceptions.ResultException
     */
    @RequestMapping(value = "/settitleintroduction", method = RequestMethod.POST)
    public Result setTitleIntroduction(
            @Check(value = "broker_ids", required = true) List<Long> brokerids,
            @Check(value = "taget_broker_id", required = true) Long taget_broker_id,
            @Check(value = "title_template_id", required = true) long titleTemplateId,
            @Check(value = "introduction_template_id", required = true) long introductionTemplateId)
            throws ResultException {
        // 获取模板数据
        BrokerTemplate titleTemplate = brokerTemplateService
                .GetModelByTemplateId(titleTemplateId);
        BrokerTemplate introductionTemplate = brokerTemplateService
                .GetModelByTemplateId(introductionTemplateId);

        if (titleTemplate == null || introductionTemplate == null) {
            return result.format(ResultCode.ERROR_PARAMS);
        }
        Broker targetBroker = brokerService.get(taget_broker_id, true);
        if (targetBroker == null) {
            return result.format(ResultCode.TargetBrokerNotFound);
        }
        //销售经理才能修改
        /* if(targetBroker.getType() = BrokerTypeEnum.Employee.getValue()){
            return result.format(ResultCode.NoPermission);
        }*/
        //批量修改经纪人 标题导语
        int suc = 0;
        for (long brokerid : brokerids) {
            // 获取微店
            BrokerStore model = brokerStoreService.get(brokerid, false);
            //判断当前微店是否存在
            if (model == null) {
                continue;
            }
            //验证当前修改权限
            if (targetBroker.getDealerId() != model.getDealerId()) {
                continue;
            }
            // 设置标题
            model.setTitle(titleTemplate.getName());
            // 设置导语
            model.setIntroduction(introductionTemplate.getContent());
            suc = brokerStoreService.updateParamByStoreId(model);
        }
        if (suc > 0) {
            //和产品沟通没修修改完不需要 设置成默认模板
            /*brokerTemplateService.updateSetIsDefaultTemplate(
                    titleTemplate.getType(), titleTemplateId, brokerid);
            brokerTemplateService.updateSetIsDefaultTemplate(
                    titleTemplate.getType(), introductionTemplateId, brokerid);*/
            return result.format(ResultCode.Success);
        }
        return result.format(ResultCode.UnKnownError);
    }

    /**
     * 修改微店信息
     *
     * @param title
     * @param introduction
     * @param qrCode
     * @param brokerId
     * @param storeUrl
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Result updateParamByStoreId(
            @Check(value = "title", required = false) String title,
            @Check(value = "introduction", required = false) String introduction,
            @Check(value = "qr_code", required = false) String qrCode,
            @Check(value = "broker_id", required = true) Long brokerId,
            @Check(value = "store_url", required = false) String storeUrl) {
        BrokerStore model = new BrokerStore();
        model.setBrokerId(brokerId);
        model.setIntroduction(introduction);
        model.setTitle(title);
        model.setStoreUrl(storeUrl);
        model.setQrCode(qrCode);
        int suc = brokerStoreService.updateParamByStoreId(model);
        if (suc > 0) {
            return result.format(ResultCode.Success);
        }
        return result.format(ResultCode.UnKnownError);
    }

    /**
     * 微店-- 获取所有模板列表
     *
     * @return
     */
    @RequestMapping(value = "/alltemplate", method = RequestMethod.GET)
    public Result getAllTemplate() {
        List<BrokerStoreTemplate> model = brokerStoreService.getAllTemplate();
        return result.format(ResultCode.Success, model);
    }

    /**
     * 批量设置默认模板
     *
     * @param brokerIds 经纪人
     * @param taget_broker_id
     * @param storeTemplateId 经纪人模板
     * @return
     * @throws com.xyauto.interact.broker.server.exceptions.ResultException
     */
    @RequestMapping(value = "/storesettemplate", method = RequestMethod.POST)
    public Result setDefaultTemplate(
            @Check(value = "broker_ids", required = true) List<Long> brokerIds,
            @Check(value = "taget_broker_id", required = true) Long taget_broker_id,
            @Check(value = "store_template_id", required = true) long storeTemplateId) throws ResultException {

        Broker targetBroker = brokerService.get(taget_broker_id, true);
        if (targetBroker == null) {
            return result.format(ResultCode.TargetBrokerNotFound);
        }
        //销售顾问只能 修改自己
        if (targetBroker.getType() == BrokerTypeEnum.Employee.getValue()
                && brokerIds.size() != 1
                && brokerIds.get(0) != targetBroker.getBrokerId()) {
            return result.format(ResultCode.NoPermission);
        }
        int suc = 0;
        for (long brokerId : brokerIds) {
            Broker broker = brokerService.get(brokerId, true);
            if (broker.getDealerId() != targetBroker.getDealerId()) {
                continue;
            }
            BrokerStore model = new BrokerStore();
            model.setBrokerId(brokerId);
            model.setTemplateId(storeTemplateId);
            suc = brokerStoreService.updateParamByBrokerId(model);
        }
        if (suc > 0) {
            return result.format(ResultCode.Success);
        }
        return result.format(ResultCode.UnKnownError);
    }

    /**
     * 批量微店设置热销的三款车型
     *
     * @param brokerIds
     * @param taget_broker_id
     * @param first
     * @param second
     * @param third
     * @return
     * @throws com.xyauto.interact.broker.server.exceptions.ResultException
     */
    @RequestMapping(value = "/sethotseries", method = RequestMethod.POST)
    public Result setHotSeries(@Check(value = "broker_ids", required = true) List<Long> brokerIds,
            @Check(value = "taget_broker_id", required = true) Long taget_broker_id,
            @Check(value = "first_series", required = false) String first,
            @Check(value = "second_series", required = false) String second,
            @Check(value = "third_series", required = false) String third) throws ResultException {

        Broker targetBroker = brokerService.get(taget_broker_id, true);
        //销售顾问只能 修改自己
        if (targetBroker.getType() == BrokerTypeEnum.Employee.getValue()
                && brokerIds.size() != 1
                && brokerIds.get(0) != targetBroker.getBrokerId()) {
            return result.format(ResultCode.NoPermission);
        }
        int suc = 0;
        for (long brokerId : brokerIds) {
            Broker broker = brokerService.get(brokerId, true);
            if (broker.getDealerId() != targetBroker.getDealerId()) {
                continue;
            }
            BrokerStore brokerStore = new BrokerStore();
            brokerStore.setBrokerId(brokerId);
            brokerStore.setSaleSeries(first + "," + second + "," + third);
            suc = brokerStoreService.updateParamByBrokerId(brokerStore);
        }
        if (suc > 0) {
            return result.format(ResultCode.Success);
        }
        return result.format(ResultCode.UnKnownError);
    }

    @RequestMapping(value = "/gethotseries", method = RequestMethod.GET)
    public Result getHotSeries(@Check(value = "broker_id", required = true) long brokerId) {
        BrokerStore brokerStore = new BrokerStore();
        try {
            brokerStore = brokerStoreService.get(brokerId, false);
            String seriseIds = "";//2775,2793,2776,2783
            if (brokerStore.getSaleSeries() != null && !brokerStore.getSaleSeries().isEmpty()) {
                String[] tempStr = brokerStore.getSaleSeries().split(",");
                seriseIds = Joiner.on(",").join(tempStr);
            } else {
                List<Integer> seriseidsList = dealerCarService.getSeriesIdsByDealer(brokerStore.getDealerId());
                seriseIds = Joiner.on(",").join(seriseidsList);
            }
            String key = "broker_server" + seriseIds + brokerStore.getBrokerId();
            List<Map> carInfo = new ArrayList<>();
            if (redisCache.get(key) == null) {
                carInfo = getSerilInfo(seriseIds, brokerStore.getDealerId());
                if (carInfo != null) {
                    //15分钟缓存
                    redisCache.set(key, JSONObject.toJSONString(carInfo), Long.valueOf(60 * 15));
                }
            } else {
                JSONArray array = JSONArray.parseArray(redisCache.get(key));
                return result.format(ResultCode.Success, array);
            }

            return result.format(ResultCode.Success, carInfo == null ? new ArrayList() : carInfo);
        } catch (Exception e) {
            return result.format(ResultCode.UnKnownError, e.getMessage());
        }
    }

    //获取热销车型
    public List<Map> getSerilInfo(String seriseIds, long dealerId) {
        try {
            Result carsResult = apiService.baseCarService().getBaseCarSerials(seriseIds);
            if (carsResult.getCode() != ResultCode.Success.getCode()) {
                return null;
            }
            List<Map> carInfos = (List<Map>) carsResult.getData();

            //按UV排序获取最热销的车款
            Collections.sort(carInfos, new Comparator() {
                public int compare(Object o1, Object o2) {
                    if (Integer.valueOf(((Map) o1).get("uv").toString()) > Integer.valueOf(((Map) o2).get("uv").toString())) {
                        return 1;
                    }
                    return -1;
                }
            });

            //获取三款热销车型id
            List<Map> resultMap = carInfos.size() > 3 ? carInfos.subList(0, 3) : carInfos;
            String hotserial = "";
            for (int i = 0; i < resultMap.size(); i++) {
                hotserial = hotserial + resultMap.get(i).get("id") + ",";
            }
            seriseIds = hotserial.substring(0, hotserial.length() - 1);

            Result carsImageresult = apiService.baseCarImageService().getBaseSerialWhitePic(seriseIds);
            if (carsImageresult.getCode() != ResultCode.Success.getCode()) {
                return null;
            }
            List<Map> carImages = (List<Map>) carsImageresult.getData();

            for (Map carmap : resultMap) {
                if (carImages != null) {
                    for (Map m : carImages) {
                        if (carmap.get("id").equals(m.get("serialId"))) {
                            carmap.put("images", m.get("imgUrl"));
                            carmap.put("carPreferentialPrice", m.get("0"));
                            break;
                        }
                    }
                } else {
                    carmap.put("carPreferentialPrice", "0");
                    carmap.put("images", "");
                }
            }
            //获取当前经销商车型最大优惠信息
            Result infomationResult = apiService.InfomationService().queryDealerSerialFavorablePrice(dealerId, seriseIds);
            if (infomationResult.getCode() == ResultCode.Success.getCode()) {
                List<Map> carMaxDiscountPrice = new ArrayList<>();
                if (infomationResult.getCode() == ResultCode.Success.getCode()) {
                    carMaxDiscountPrice = (List<Map>) carsImageresult.getData();
                }
                for (Map carmap : resultMap) {
                    if (carMaxDiscountPrice.isEmpty()) {
                        for (Map m : carMaxDiscountPrice) {
                            if (carmap.get("id").equals(m.get("serialId"))) {
                                if (m.get("favorablePriceStr") != null) {
                                    carmap.put("carPreferentialPrice", m.get("favorablePriceStr"));
                                } else {
                                    carmap.put("carPreferentialPrice", "0");
                                }
                            }
                        }
                    } else {
                        carmap.put("carPreferentialPrice", "0");
                    }
                }
            }

            //获取经销商对该车型最高最低报价
            /*         Result dealerResut = apiService.DealerPriceService().getDearlerPrice(dealerId,seriseIds);
            if(dealerResut.getCode()==ResultCode.Success.getCode()){
                List<Map> dealerIdSerialPrice = (List<Map>) dealerResut.getData();
                for (Map carmap : resultMap) {
                    for (Map serialPriceMap : dealerIdSerialPrice) {
                        if (carmap.get("id").equals(serialPriceMap.get("id"))){
                            carmap.put("dealermaxprice",serialPriceMap.get("maxPrice"));
                            carmap.put("dealerminprice",serialPriceMap.get("minPrice"));
                        }
                    }
                }
            }*/
            return resultMap;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取当前经纪人模板id
     *
     * @param brokerId
     * @return
     * @throws ResultException
     */
    @RequestMapping(value = "/getstoretemplate", method = RequestMethod.GET)
    public Result getStoreTemplate(@Check(value = "broker_id") long brokerId) throws ResultException {
        BrokerStore brokerStore = brokerStoreService.get(brokerId, false);
        JSONObject obj = new JSONObject();
        if (brokerStore != null) {
            obj.put("templateId", brokerStore.getTemplateId());
            obj.put("brokerId", brokerStore.getBrokerId());
        }
        return result.format(ResultCode.Success, obj);
    }

}
