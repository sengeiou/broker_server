package com.xyauto.interact.broker.server.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.primitives.Longs;
import com.xyauto.interact.broker.server.dao.proxy.BrokerDaoProxy;
import com.xyauto.interact.broker.server.dao.proxy.BrokerStoreDaoProxy;
import com.xyauto.interact.broker.server.dao.proxy.BrokerStoreTemplateProxy;
import com.xyauto.interact.broker.server.enums.BrokerLogEnum;
import com.xyauto.interact.broker.server.enums.LogDesc;
import com.xyauto.interact.broker.server.exceptions.ResultException;
import com.xyauto.interact.broker.server.model.po.BrokerClueSearchParameters;
import com.xyauto.interact.broker.server.model.vo.*;
import com.xyauto.interact.broker.server.service.cloud.ApiServiceFactory;
import com.xyauto.interact.broker.server.service.es.broker.BrokerEsService;
import com.xyauto.interact.broker.server.service.es.clue.BrokerClueEsService;
import com.xyauto.interact.broker.server.util.DateStyle;
import com.xyauto.interact.broker.server.util.DateUtil;
import org.apache.ibatis.annotations.Param;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.font.TrueTypeFont;

import java.util.*;
import org.apache.commons.lang.StringUtils;

@Service
public class BrokerStoreService {

    @Autowired
    BrokerStoreDaoProxy brokerStoreDaoProxy;

    @Autowired
    BrokerStoreTemplateProxy brokerStoreTemplateProxy;

    @Autowired
    BrokerService brokerService;
    
    @Autowired
    BrokerDaoProxy brokerDao;

    @Autowired
    BrokerCustomerService brokerCustomerService;

    @Autowired
    BrokerCustomerCarsWillService brokerCustomerCarsWillService;

    @Autowired
    DealerCarService dealerCarService;

    @Autowired
    BrokerTemplateService brokerTemplateService;

    @Autowired
    DealerService dealerService;

    @Autowired
    BrokerClueEsService brokerClueEsService;
    
    @Autowired
    BrokerLogService brokerLogService;
    
    @Autowired
    ApiServiceFactory apiService;

    public Integer updateParamByStoreId(BrokerStore record) {
        return brokerStoreDaoProxy.updateParamByStoreId(record);
    }

    public Integer updateParamByBrokerId(BrokerStore record) throws ResultException {
        return brokerStoreDaoProxy.updateParamByBrokerId(record);
    }

    public List<BrokerStore> getBrokerStoreByDealerIds(List<Long> brokerIds) {
        List<BrokerStore> brokerStoreList = new ArrayList<>();
        for (long brokerId : brokerIds) {
            BrokerStore tempStore = new BrokerStore();
            try {
                tempStore = get(brokerId, false);
                //初始化微店标题导语信息
                List<BrokerTemplate> defaultTemplate = this.initBrokerStoreTitleAndIntroduction(brokerId);
            } catch (Exception e) {
            }
            brokerStoreList.add(tempStore);
        }
        return brokerStoreList;
    }

    /**
     * 获取微店详细信息
     *
     * @param brokerId
     * @param isdetail 是否获取扩展信息
     * @return
     */
    public BrokerStore get(long brokerId, boolean isdetail) throws ResultException {
        try {
            BrokerStore brokerStore = brokerStoreDaoProxy.get(brokerId);
            if (brokerStore == null) {
                brokerStore = this.initBrokerStoreInfo(brokerId);
            }
            if (brokerStore == null) {
                return null;
            }
            BrokerStoreTemplate template = brokerStoreTemplateProxy.getById(brokerStore.getTemplateId());
            if (template != null) {
                brokerStore.setBrokerStoreTemplate(template);
            }

            //获取微店扩展信息
            if (isdetail) {
                Broker broker =  brokerService.get(brokerId);
                //修改服务客户数
                String sql = "select count(distinct (extra::jsonb->>'mobile')) from broker_logs where type in (5,6) and broker_id="+brokerId;
                List<Map<String, Object>> data = apiService.brokerLogService().exec(sql);
                JSONArray json = (JSONArray) JSON.toJSON(data);
                int count = json.getJSONObject(0).getIntValue("count");
                broker.setServiceCount(count);
                //获取经纪人相关信息
                brokerStore.setBroker(broker);
                //成交车辆 近90天 开始
                brokerStore.setDealCount(getBrokerDealCountByTime(brokerId, -90));
                //服务率响应率
                try {
                    BrokerClueSearchParameters parameters = new BrokerClueSearchParameters();
                    parameters.setType(Short.valueOf("1"));
                    parameters.setIsdelete(-1);
                    parameters.setIsCustomer(Short.valueOf("-1"));
                    parameters.setIsHandled(-1);
                    long times = new Date().getTime();
                    //30天内通话接通率

                    parameters.setBrokerIds(Arrays.asList(brokerId));
                    parameters.setBegin(times - (1000 * 60 * 60 * 24 * 30));
                    parameters.setEnd(times);
                    int callTotalCount = brokerClueEsService.searchListCount(parameters, "0");
                    parameters.setCallstatus(1);
                    int callCount = brokerClueEsService.searchListCount(parameters, "0");
                    brokerStore.setServiceRate((int) (((float) callCount / callTotalCount) * 100));
                } catch (Exception e) {
                }
                //售卖品牌
                List<Brand> brands = dealerCarService.getListByDealer(brokerStore.getDealerId());
                if (brands != null) {
                    brokerStore.setBrands(brands);
                }
                brokerStore.setDealer(dealerService.get(brokerStore.getDealerId()));
            }
            //记录访问微店日志
            brokerLogService.log(brokerId, brokerStore.getDealerId(), BrokerLogEnum.BrokerWebSiteView);
            return brokerStore;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int getBrokerDealCountByTime(long brokerId, int day) {
        List<Long> customerIds = brokerCustomerService.getListIdsNoPage(brokerId);
        Date d = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.add(Calendar.DAY_OF_MONTH, day);
        int dealCount = brokerCustomerCarsWillService.getBrokerDealCount(brokerId, DateUtil.DateToString(calendar.getTime(), DateStyle.YYYY_MM_DD_HH_MM_SS));
        return dealCount;
    }

    /**
     * 根据经销商获取微店列表
     *
     * @param dealerId
     * @return
     */
    public List<BrokerStore> getByDealerId(long dealerId, long limit, long page) {
        return brokerStoreDaoProxy.getByDealerId(dealerId, limit, page);
    }

    /**
     * 创建经纪人微店
     *
     * @param brokerStore
     * @return
     */
    public Integer create(BrokerStore brokerStore) {

        int suc = brokerStoreDaoProxy.create(brokerStore);
        if (suc > 0) {
            //创建微店后 新增默认标题导语
            if (brokerStore.getBrokerId() != null) {
                this.initBrokerStoreTitleAndIntroduction(brokerStore.getBrokerId());
            }
        }
        return suc;
    }

    /**
     * 初始化微店标题导语
     */
    @Transactional
    public BrokerStore initBrokerStoreInfo(long brokerId) {
        BrokerStore brokerStore = new BrokerStore();
        BrokerStoreTemplate brokerStoreTemplate = brokerStoreTemplateProxy.getDefault();
        Broker broker = brokerDao.get(brokerId);
        if (broker == null) {
            return null;
        }

        //初始化微店信息
        brokerStore.setBrokerId(brokerId);
        brokerStore.setDealerId(broker.getDealerId());
        brokerStore.setStoreUrl(broker.getWebsite());   //微店url
        //brokerStore.setQrCode(broker.getQrCode());     //二维码地址
        if(String.format(LogDesc.StoreTitle,broker.getName()).length() > 25) {
            brokerStore.setTitle(String.format(LogDesc.StoreTitle,""));
        }else {
            brokerStore.setTitle(String.format(LogDesc.StoreTitle, broker.getName()));
        }
        brokerStore.setIntroduction(LogDesc.StoreIntroduction);
/*        for (BrokerTemplate model : defaultTemplate) {
            if (model.getType() == Short.valueOf("3")) {
                brokerStore.setTitle(model.getName());      //设置标题
            } else {
                brokerStore.setIntroduction(model.getContent());   //设置导语
            }
        }*/
        brokerStore.setTemplateId(brokerStoreTemplate.getBrokerTemplateId());
        if(brokerStoreDaoProxy.get(brokerId)==null) {
            brokerStoreDaoProxy.create(brokerStore);
        }
        return brokerStore;
    }

    /**
     * 初始化微店标题导语
     *
     * @return 返回默认的标题导语
     */
    @Transactional
    public List<BrokerTemplate> initBrokerStoreTitleAndIntroduction(long brokerId) {
        Broker broker = brokerService.get(brokerId);
        List<BrokerTemplate> defaultBrokerTemplate = new ArrayList<BrokerTemplate>();
        BrokerTemplate brokerTemplate = new BrokerTemplate();
        List<BrokerTemplate> templist = brokerTemplateService.GetListByTypes(Lists.newArrayList(Short.valueOf("4")), brokerId);
        if (templist == null || templist.size() == 0) {
            brokerTemplate.setBrokerId(brokerId);
            //type 3 标题  4 导语
            brokerTemplate.setIsDefault(Short.valueOf("1"));
            brokerTemplate.setType(Short.valueOf("3"));
            brokerTemplate.setName("您的专属车顾问-" + broker.getName() + "竭诚为您服务");
            //创建默认标题
            brokerTemplateService.create(brokerTemplate);
            defaultBrokerTemplate.add(brokerTemplate);
        }
        List<BrokerTemplate> templistContent = brokerTemplateService.GetListByTypes(Lists.newArrayList(Short.valueOf("4")), brokerId);
        if (templistContent == null || templistContent.size() == 0) {
            brokerTemplate = new BrokerTemplate();
            brokerTemplate.setBrokerId(brokerId);
            //创建默认导语
            brokerTemplate.setIsDefault(Short.valueOf("1"));
            brokerTemplate.setName("");
            brokerTemplate.setType(Short.valueOf("4"));
            brokerTemplate.setContent("专业购车服务，直接给您底价，1对1服务让您的购车之旅更加舒心");
            brokerTemplateService.create(brokerTemplate);
            defaultBrokerTemplate.add(brokerTemplate);
        }
        return defaultBrokerTemplate;
    }

    public List<BrokerStoreTemplate> getAllTemplate() {
        return brokerStoreTemplateProxy.getAll();
    }

    /**
     * 根据经销商获取微店列表--总数
     *
     * @param dealerId
     * @return
     */
    public int getCountByDealerId(long dealerId) {
        return brokerStoreDaoProxy.getCountByDealerId(dealerId);
    }

    public BrokerStoreTemplate getById(long brokerTemplateId) {
        return brokerStoreTemplateProxy.getById(brokerTemplateId);
    }
}
