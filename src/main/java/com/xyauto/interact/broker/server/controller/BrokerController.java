package com.xyauto.interact.broker.server.controller;

import ch.qos.logback.classic.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xyauto.interact.broker.server.model.vo.*;
import com.xyauto.interact.broker.server.enums.BrokerEnum;
import com.xyauto.interact.broker.server.enums.BrokerTypeEnum;
import com.xyauto.interact.broker.server.enums.ResultCode;
import com.mcp.validate.annotation.Check;
import com.xyauto.interact.broker.server.enums.BrokerLogEnum;
import com.xyauto.interact.broker.server.enums.BrokerSortTypeEnum;
import com.xyauto.interact.broker.server.exceptions.ResultException;
import com.xyauto.interact.broker.server.model.po.BrokerPo;
import com.xyauto.interact.broker.server.model.po.BrokerSearchParameters;
import com.xyauto.interact.broker.server.service.BrokerLogService;
import com.xyauto.interact.broker.server.service.BrokerService;
import com.xyauto.interact.broker.server.service.CityService;
import com.xyauto.interact.broker.server.service.DealerCarService;
import com.xyauto.interact.broker.server.service.cloud.ApiServiceFactory;
import com.xyauto.interact.broker.server.service.es.broker.BrokerEsService;
import com.xyauto.interact.broker.server.util.DateStyle;
import com.xyauto.interact.broker.server.util.DateUtil;
import com.xyauto.interact.broker.server.util.ILogger;
import com.xyauto.interact.broker.server.util.Result;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonJsonParser;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

import org.elasticsearch.common.Strings;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping(value = "/broker")
public class BrokerController extends BaseController implements ILogger {

    @Autowired
    BrokerService brokerService;

    @Autowired
    BrokerEsService brokerEsService;

    @Autowired
    CityService cityService;

    @Autowired
    BrokerLogService brokerLogService;

    @Autowired
    private ApiServiceFactory apiService;

    @Autowired
    private DealerCarService dealerCarService;

    /**
     * 获取经纪人详情
     *
     * @param brokerId
     * @return
     * @throws ResultException
     */
    @RequestMapping(value = "/{broker_id}", method = RequestMethod.GET)
    public Result info(
            @PathVariable(value = "broker_id", required = true) long brokerId)
            throws ResultException {
        Broker broker = brokerService.get(brokerId, false);
        return result.format(ResultCode.Success, broker);
    }

    /**
     * 根据指定经纪人id获取经纪人列表
     *
     * @param brokerIds
     * @return
     * @throws ResultException
     */
    @RequestMapping(value = "/list/batch", method = RequestMethod.GET)
    public Result getList(
            @RequestParam(value = "broker_ids", required = true) List<Long> brokerIds)
            throws ResultException {
        List<Broker> list = brokerService.getBatch(brokerIds);
        return result.format(ResultCode.Success, list);
    }

    /**
     * 顾问列表 9
     *
     * @param dealerId
     * @param brokerId
     * @param max
     * @param limit
     * @return result
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result getList(
            @Check(value = "dealer_id", required = true) long dealerId,
            @Check(value = "broker_id", required = false, defaultValue = "0") long brokerId,
            @Check(value = "max", required = false, defaultValue = "0") String max,
            @Check(value = "limit", required = false, defaultValue = "20") int limit) {
        FlowPagedList pageList = new FlowPagedList();
        List<Long> ids = null;
        try {
            ids = brokerService.getListIds(brokerId, dealerId, max, limit);
        } catch (ResultException e) {
            return result.format(e.getResult());
        }
        List<Broker> list = Lists.newArrayList();
        if (ids.size() > 0) {
            list = brokerService.getList(ids);
            pageList.setList(list);
        }
        int total = 0;
        int pagedCount = 0;
        pageList.setCount(total);
        pageList.setHas_more(pagedCount > limit ? 1 : 0);
        if (list.size() > 0) {
            pageList.setNext_max(list.get(list.size() - 1).getSort());
        }
        pageList.setLimit(limit);
        return result.format(ResultCode.Success, pageList);
    }

    /**
     * 顾问列表 9
     *
     * @param dealerId
     * @param brokerId
     * @param page
     * @param limit
     * @return result
     * @throws com.xyauto.interact.broker.server.exceptions.ResultException
     */
    @RequestMapping(value = "/plist", method = RequestMethod.GET)
    public Result getList(
            @Check(value = "dealer_id", required = true) long dealerId,
            @Check(value = "broker_id", required = false, defaultValue = "0") long brokerId,
            @Check(value = "page", required = false, defaultValue = "1") int page,
            @Check(value = "limit", required = false, defaultValue = "20") int limit)
            throws ResultException {
        PagedList<Broker> pageList = new PagedList<>();
        List<Long> ids = brokerService.getListIds(brokerId, dealerId, page,
                limit);
        // List<Broker> list = brokerService.getList(ids);
        List<Broker> list = new ArrayList<Broker>();
        if (ids.size() > 0) {
            list = brokerService.getList(ids);
            pageList.setList(list);
        }

        int total = 0;
        pageList.setCount(total);
        pageList.setLimit(limit);
        pageList.setPage(page);
        return result.format(ResultCode.Success, pageList);
    }

    /**
     * 搜索顾问列表
     *
     * @param dealerId
     * @param dealerName
     * @param dealerType
     * @param dealerStatus
     * @param brokerId
     * @param brokerName
     * @param provinceId
     * @param cityId
     * @param district_id
     * @param brandId
     * @param seriesId
     * @param car_id
     * @param latitude
     * @param longitude
     * @param mobile
     * @param sort 排序方式 0为权重+创建时间倒序 1为权重+距离倒序排列
     * @param max
     * @param limit
     * @return result
     * @throws com.xyauto.interact.broker.server.exceptions.ResultException
     * @throws java.io.IOException
     */
    @RequestMapping(value = "/list/search", method = RequestMethod.GET)
    public Object search(
            @Check(value = "dealer_id", required = false, defaultValue = "0") long dealerId,
            @Check(value = "dealer_name", required = false, defaultValue = "") String dealerName,
            @Check(value = "dealer_type", required = false, defaultValue = "0") int dealerType,
            @Check(value = "dealer_status", required = false, defaultValue = "0") int dealerStatus,
            @Check(value = "broker_id", required = false, defaultValue = "0") long brokerId,
            @Check(value = "broker_name", required = false, defaultValue = "") String brokerName,
            @Check(value = "province_id", required = false, defaultValue = "0") int provinceId,
            @Check(value = "city_id", required = false, defaultValue = "0") int cityId,
            @Check(value = "district_id", required = false, defaultValue = "0") int district_id,
            @Check(value = "brand_id", required = false, defaultValue = "0") int brandId,
            @Check(value = "series_id", required = false, defaultValue = "0") int seriesId,
            @Check(value = "car_id", required = false, defaultValue = "0") int car_id,
            @Check(value = "lat", required = false, defaultValue = "0") double latitude,
            @Check(value = "lng", required = false, defaultValue = "0") double longitude,
            @Check(value = "mobile", required = false, defaultValue = "") String mobile,
            @Check(value = "sort", required = false, defaultValue = "0") short sort,
            @Check(value = "max", required = false, defaultValue = "0") String max,
            @Check(value = "limit", required = false, defaultValue = "20") int limit)
            throws ResultException, IOException {
        BrokerSearchParameters params = new BrokerSearchParameters();
        params.setCarId(car_id);
        params.setCityId(cityId);
        params.setProvinceId(provinceId);
        params.setBrokerName(brokerName);
        params.setDealerName(dealerName);
        params.setDealerType(dealerType);
        params.setDealerStatus(dealerStatus);
        params.setBrandId(brandId);
        params.setSeriesId(seriesId);
        params.setLongitude(longitude);
        params.setLatitude(latitude);
        params.setDealerId(dealerId);
        params.setBrokerId(brokerId);
        params.setDistrictId(district_id);
        params.setMobile(mobile);
        params.setSort(BrokerSortTypeEnum.find(sort));
        FlowPagedList pageList = new FlowPagedList();
        List<Long> ids = brokerService.searchListIds(params, max, limit);
        if (ids.isEmpty()) {
            pageList.setList(Lists.newArrayList());
        } else {
            List<Broker> list = brokerService.getList(ids);
            pageList.setList(list);
            pageList.setNext_max(list.get(list.size() - 1).getSort());
        }
        int total = brokerService.searchListCount(params, "0");
        int pagedCount = brokerService.searchListCount(params, max);
        pageList.setCount(total);
        pageList.setHas_more(pagedCount > limit ? 1 : 0);
        pageList.setLimit(limit);
        return result.format(ResultCode.Success, pageList);
    }

    /**
     * 搜索顾问列表
     *
     * @param dealerId
     * @param dealerName
     * @param dealerType
     * @param dealerStatus
     * @param brokerId
     * @param brokerName
     * @param provinceId
     * @param cityId
     * @param brandId
     * @param seriesId
     * @param district_id
     * @param car_id
     * @param latitude
     * @param longitude
     * @param mobile
     * @param sort 排序方式 0为权重+创建时间倒序 1为权重+距离倒序排列
     * @param page
     * @param limit
     * @return result
     * @throws com.xyauto.interact.broker.server.exceptions.ResultException
     * @throws java.io.IOException
     */
    @RequestMapping(value = "/plist/search", method = RequestMethod.GET)
    public Object search(
            @Check(value = "dealer_id", required = false, defaultValue = "0") long dealerId,
            @Check(value = "dealer_name", required = false, defaultValue = "") String dealerName,
            @Check(value = "dealer_type", required = false, defaultValue = "0") int dealerType,
            @Check(value = "dealer_status", required = false, defaultValue = "0") int dealerStatus,
            @Check(value = "broker_id", required = false, defaultValue = "0") long brokerId,
            @Check(value = "broker_name", required = false, defaultValue = "") String brokerName,
            @Check(value = "province_id", required = false, defaultValue = "0") int provinceId,
            @Check(value = "city_id", required = false, defaultValue = "0") int cityId,
            @Check(value = "district_id", required = false, defaultValue = "0") int district_id,
            @Check(value = "brand_id", required = false, defaultValue = "0") int brandId,
            @Check(value = "series_id", required = false, defaultValue = "0") int seriesId,
            @Check(value = "car_id", required = false, defaultValue = "0") int car_id,
            @Check(value = "lat", required = false, defaultValue = "0") double latitude,
            @Check(value = "lng", required = false, defaultValue = "0") double longitude,
            @Check(value = "mobile", required = false, defaultValue = "") String mobile,
            @Check(value = "sort", required = false, defaultValue = "0") short sort,
            @Check(value = "page", required = false, defaultValue = "1") int page,
            @Check(value = "limit", required = false, defaultValue = "20") int limit)
            throws ResultException, IOException {

        BrokerSearchParameters params = new BrokerSearchParameters();
        params.setCarId(car_id);
        params.setCityId(cityId);
        params.setProvinceId(provinceId);
        params.setBrokerName(brokerName);
        params.setDealerName(dealerName);
        params.setDealerType(dealerType);
        params.setDealerStatus(dealerStatus);
        params.setBrandId(brandId);
        params.setSeriesId(seriesId);
        params.setLongitude(longitude);
        params.setLatitude(latitude);
        params.setDealerId(dealerId);
        params.setBrokerId(brokerId);
        params.setDistrictId(district_id);
        params.setMobile(mobile);
        params.setSort(BrokerSortTypeEnum.find(sort));
        PagedList<Broker> pageList = new PagedList<>();
        List<Long> ids = brokerService.searchListIds(params, page, limit);
        if (ids.isEmpty()) {
            pageList.setList(Lists.newArrayList());
        } else {
            List<Broker> list = brokerService.getList(ids);
            pageList.setList(list);
        }
        int total = brokerService.searchListCount(params, "0");
        pageList.setCount(total);
        pageList.setLimit(limit);
        pageList.setPage(page);
        return result.format(ResultCode.Success, pageList);
    }

    /**
     * 分配顾问给客户 10
     *
     * @param customerId
     * @param brokerId
     * @param targetBrokerId
     * @return
     */
    @RequestMapping(value = "/setbroker", method = RequestMethod.POST)
    public Result setCustomerToBroker(
            @Check(value = "customer_id") List<Long> customerId,
            @Check(value = "broker_id") long brokerId,
            @Check(value = "taget_broker_id") long targetBrokerId)
            throws ResultException {
        boolean suc = brokerService.setCustomerToBroker(customerId, brokerId,
                targetBrokerId);
        if (suc) {
            return result.format(ResultCode.Success);
        } else {
            return result.format(ResultCode.UnKnownError);
        }
    }

    /**
     * 获取经纪人获奖列表
     *
     * @param begin
     * @param end
     * @return
     */
    @RequestMapping(value = "/prize/list", method = RequestMethod.GET)
    public Result getPrizeList(
            @Check(value = "begin", defaultValue = "0", required = false) long begin,
            @Check(value = "end", defaultValue = "0", required = false) long end) {
        String beinTime = "", endTime = "";
        if (begin > 0 && end > 0) {
            beinTime = DateUtil.DateToString(new Date(begin),
                    DateStyle.YYYY_MM_DD_HH_MM_SS);
            endTime = DateUtil.DateToString(new Date(end),
                    DateStyle.YYYY_MM_DD_HH_MM_SS);

        }
        List<Long> ids = brokerService.getPrizeList(beinTime, endTime);
        if (ids.isEmpty()) {
            return result.format(ResultCode.Success, Lists.newArrayList());
        }
        //显示真实手机号
        List<Broker> list = brokerService.getList(ids,true);
        return result.format(ResultCode.Success, list);
    }

    /**
     * 获取城市下区对应 经纪人数量
     *
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/getbrokertotallist", method = RequestMethod.GET)
    public Result getBrokerTotalList(
            /*@Check(value = "serial_spell", required = false) String serial_spell,
            @Check(value = "city_id", required = false) Integer CityId,
             */
            @Check(value = "city_id") int cityId,
            @Check(value = "serial_id", defaultValue = "0") int serialId,
            @Check(value = "car_id", defaultValue = "0") int carId,
            @Check(value = "order_type", defaultValue = "0") int orderType,
            @Check(value = "county_id", defaultValue = "0") int countyId,
            @Check(value = "count", defaultValue = "200") int requestTotal)
            throws IOException {

        Result dealerResult = new Result();
        try {
            dealerResult = apiService.BaseDealerService().getBaseDealerRule(
                    cityId, serialId, carId, orderType, 0, countyId, requestTotal, 1);
        } catch (ResultException e) {
            return result.format(e.getResult());
        }
        if (dealerResult.getCode() != ResultCode.Success.getCode()) {
            return result.format(ResultCode.UnKnownError);
        }
        Map<String, Object> map = (Map<String, Object>) dealerResult.getData();
        ArrayList model = (ArrayList) map.get("dataList");
        JSONArray array = new JSONArray();
        JSONObject jObject = new JSONObject();
        model.forEach(item -> {
            Map<String, String> tmpMap = (Map<String, String>) item;
            String tempdealer = jObject.get(tmpMap.get("locationId")) == null ? "" : String.valueOf(jObject.get(String.valueOf(tmpMap.get("locationId"))));
            jObject.put(String.valueOf(tmpMap.get("locationId")), (tempdealer + String.valueOf(tmpMap.get("dealerId")) + ","));
        });
        List<City> cityList = cityService.getCityByParent(cityId);
        JSONObject tempAll = new JSONObject();
        tempAll.put("cityid", 0);
        tempAll.put("cityname", "全部");
        tempAll.put("brokercount", 0);
        array.add(tempAll);
        cityList.forEach(c -> {
            if (jObject.containsKey(String.valueOf(c.getId()))) {
                JSONObject tempJson = new JSONObject();
                tempJson.put("cityid", c.getId());
                tempJson.put("cityname", c.getName());
                int dealerCount = 0;
                if (String.valueOf(jObject.get(String.valueOf(c.getId()))).isEmpty() == false) {
                    List<String> idstr = Strings.splitSmart(String.valueOf(jObject.get(String.valueOf(c.getId()))), ",", false);
                    List<Long> ids = Lists.newArrayList(0L);
                    idstr.forEach(item -> {
                        if (ids.contains(Long.valueOf(item)) == false) {
                            ids.add(Long.valueOf(item));
                        }
                    });
                    dealerCount = brokerService.getCountByDealerIds(ids);
                }
                tempJson.put("brokercount", dealerCount);
                tempAll.put("brokercount", Integer.valueOf(tempAll.get("brokercount").toString()) + dealerCount);
                array.add(tempJson);
            }
        });
        return result.format(ResultCode.Success, array);
    }

    @RequestMapping(value = "/add")
    public Result addBroker(
            @Check(value = "broker_id", required = true) long brokerId,
            @Check(value = "uid", required = false) long uid,
            @Check(value = "dealer_id", required = true) long dealerId,
            @Check(value = "broker_name", required = true) String brokerName,            
            @Check(value = "mobile", required = false) String mobile,
            @Check(value = "email", required = false) String email,
            @Check(value = "type", required = true) short type,
            @Check(value = "bussiness_type", required = false,defaultValue = "") String bussinessType,
            @Check(value = "gender", required = false,defaultValue = "0") short gender,
            @Check(value = "work_year", required = false, defaultValue = "0") int workYear,
            @Check(value = "avatar", required = false, defaultValue = "") String avatar) {
    	BrokerPo broker = new BrokerPo();
        try {       	
            broker.setBrokerId(brokerId);
            broker.setUid(uid);
            broker.setDealerId(dealerId);
            broker.setName(brokerName);
            broker.setMobile(mobile);
            broker.setGender(gender);
            broker.setEmail(email);
            broker.setType(type);
            broker.setBussinessType(bussinessType);
            broker.setWorkTime(DateUtil.addYear(new Date(), workYear == 0 ? 0
                    : -workYear));
            broker.setAvatar(avatar);
            broker.setWebsite(String.format(BrokerEnum.website, brokerId,dealerId));
            String qrcode = brokerService.createRQCode(brokerId,dealerId, null, avatar);
            broker.setQrCode(qrcode == null ? "" : qrcode);
            int ret = brokerService.insert(broker);
            if (ret > 0) {
                brokerEsService.update(brokerId);
            }
        } catch (Exception e) {
        	this.log("添加用户失败，参数是："+JSONObject.toJSONString(broker)+"---异常信息是："+e.toString());
            return result.format(ResultCode.UnKnownError);
        }
        return result.format(ResultCode.Success);
    }

    /**
     * 更新经纪人
     *
     * @param brokerId
     * @param brokerName
     * @param gender
     * @param mobile
     * @param email
     * @param type
     * @param bussiness_type
     * @param work_year
     * @param avatar
     * @return
     */
    /*@RequestMapping(value = "/update")
    public Result updateBroker(
            @Check(value = "broker_id", required = true) long brokerId,
            @Check(value = "uid", required = false) long uid,
            @Check(value = "dealer_id", required = true) long dealerId,
            @Check(value = "broker_name", required = false) String brokerName,
            @Check(value = "gender", required = false, defaultValue = "0") short gender,
            @Check(value = "mobile", required = false) String mobile,
            @Check(value = "email", required = false) String email,
            @Check(value = "type", required = true, defaultValue = "-1") short type,
            @Check(value = "bussiness_type", required = false) String bussiness_type,
            @Check(value = "work_year", required = false, defaultValue = "0") int work_year,
            @Check(value = "avatar", required = false,defaultValue = "") String avatar) {
    	BrokerPo brokerPo=new BrokerPo();
        try {
            Broker broker = brokerService.get(brokerId);            
            // 是否是修改
            boolean is_update = true;
            if (broker == null) {               
                is_update = false;
            }            
            brokerPo.setBrokerId(brokerId);
            brokerPo.setName(brokerName);
            brokerPo.setMobile(mobile);
            brokerPo.setGender(gender);
            brokerPo.setEmail(email);
            brokerPo.setType(type);
            brokerPo.setBussinessType(bussiness_type);
            brokerPo.setWorkTime(DateUtil.addYear(new Date(), -work_year));
            brokerPo.setAvatar(avatar);
            brokerPo.setUid(uid);
            brokerPo.setDealerId(dealerId);
            *//**
             * 检查是否需要更新二维码以及微店地址
             * 检查规则，用户身份是销售身份(销售顾问，销售经理)头像地址变化或者是二维码不存在
             *//*
            if (type==BrokerTypeEnum.Employee.getValue()||type==BrokerTypeEnum.Manager.getValue()) {
            	if (StringUtils.isBlank(avatar)) {
            		avatar=broker.getAvatar();
            		brokerPo.setAvatar(avatar);
				}
            	if (StringUtils.isBlank(broker.getWebsite())) {
            		brokerPo.setWebsite(String.format(BrokerEnum.website, brokerId,dealerId));
				}            	
            	if (!avatar.equals(broker.getAvatar())
            			||StringUtils.isBlank(broker.getQrCode())
            			||StringUtils.isBlank(broker.getWebsite())) {
                	String qrcode = brokerService.createRQCode(brokerId,dealerId, null,
                            avatar);
                	brokerPo.setQrCode(qrcode == null ? "" : qrcode);
    			}           	
			}                                     
            if (!is_update) {
                if (StringUtils.isBlank(brokerName) || StringUtils.isBlank(mobile)
                        || StringUtils.isBlank(email) || StringUtils.isBlank(bussiness_type)) {
                    return result.format(ResultCode.UnKnownError, "用户身份变更，新增用户缺少参数");
                }                
                brokerService.insert(brokerPo);
            } else {
                brokerService.updateByParam(brokerPo);
                try {
                    JSONObject extension = new JSONObject();
                    extension.put("broker_id", brokerId);
                    extension.put("broker_name", brokerName);
                    extension.put("gender", gender);
                    extension.put("mobile", mobile);
                    extension.put("email", email);
                    extension.put("type", type);
                    extension.put("bussiness_type", bussiness_type);
                    extension.put("work_year", work_year);
                    extension.put("avatar", avatar);
                    brokerLogService.log(brokerId, broker.getDealerId(),
                            brokerId, BrokerLogEnum.BrokerUpdate, extension);
                } catch (Exception e) {
                    this.info("保存修改经纪人日志信息失败");
                }
            }
            brokerEsService.update(brokerId);
        } catch (Exception e) {
        	this.log("修改用户失败，参数是："+JSONObject.toJSONString(brokerPo)+"---异常信息是："+e.toString());          
            return result.format(ResultCode.UnKnownError);
        }
        return result.format(ResultCode.Success);
    }*/
    
    @RequestMapping(value = "/update")
    public Result updateBroker(
            @Check(value = "broker_id", required = true) long brokerId) {
    	/**
    	 * 因为接收到消息时，王方方数据还没有操作完成，因此主线程睡眠3s
    	 */    	
    	this.log("接收到用户消息");
    	BrokerPo brokerPo=new BrokerPo();
        try {
        	//获取经纪人详情
        	Result ret = apiService.platformService().brokerDetailInfo(brokerId);
        	if (ret.getCode() == ResultCode.Success.getCode()) {        		
        		String str=JSONObject.toJSONString(ret.getData());
        		this.log("获取详情数据是："+str);
        		JSONObject obj=JSONObject.parseObject(str).getJSONObject("account");
        		JSONObject user=JSONObject.parseObject(str).getJSONObject("daquan");
        		long uid=0;
        		String user_token="";
        		if (user!=null) {
        			uid=user.getLong("dasUserId");
        			user_token=user.getString("dasUserToken");
				}       		
                String name=obj.getString("nickName");
        		short type = BrokerEnum.chageBrokerType(obj.getShort("accountTypeId"));
        		long dealerId=obj.getLong("dealerId");
        		String email=obj.getString("email");
                String mobile=obj.getString("mobile");
                String avatar=obj.getString("photoUrl");
                int work_year= obj.getInteger("workYear");
                short gender=obj.getShort("sex")==(short)0?2:(short)1;                
                Broker broker = brokerService.get(brokerId);
                String website=String.format(BrokerEnum.website, brokerId,dealerId);
                boolean is_update = true;
                if (broker == null) {               
                    is_update = false;
                }else{
                	if (broker.getType()==BrokerTypeEnum.Employee.getValue()
                			||broker.getType()==BrokerTypeEnum.Manager.getValue()) {                		
                        //如果头像有变更或者是二维码没有，则需要更新下二维码
                    	if (!broker.getAvatar().equals(avatar)||StringUtils.isBlank(broker.getQrCode())) {
                    		String qrcode = brokerService.createRQCode(brokerId,dealerId, null,avatar);
                        	brokerPo.setQrCode(qrcode == null ? "" : qrcode);
        				}
					}                	
                }
                brokerPo.setBrokerId(brokerId);
                brokerPo.setUid(uid);
                brokerPo.setName(name==null?"":name);
                brokerPo.setType(type);
                brokerPo.setDealerId(dealerId);
                brokerPo.setEmail(email==null?"":email);
                brokerPo.setMobile(mobile==null?"":mobile);
                brokerPo.setAvatar(avatar==null?"":avatar);
                brokerPo.setWorkTime(DateUtil.addYear(new Date(), -work_year));
                brokerPo.setGender(gender);
                brokerPo.setToken(user_token==null?"":user_token);
                brokerPo.setWebsite(website);               
                if (!is_update) {                                    
                    brokerService.insert(brokerPo);
                } else {
                    brokerService.updateByParam(brokerPo);
                    try {
                        JSONObject extension = new JSONObject();
                        extension.put("broker_id", brokerId);
                        extension.put("broker_name", name);
                        extension.put("gender", gender);
                        extension.put("mobile", mobile);
                        extension.put("email", email);
                        extension.put("type", type);                        
                        extension.put("work_year", work_year);
                        extension.put("avatar", avatar);
                        brokerLogService.log(brokerId, broker.getDealerId(),
                                brokerId, BrokerLogEnum.BrokerUpdate, extension);
                    } catch (Exception e) {
                        this.log("保存修改经纪人日志信息失败");
                    }
                }
                try {
                	brokerEsService.update(brokerId);
				} catch (Exception e) {
					this.log("添加索引失败"+e.getMessage());
				}                
                return result.format(ResultCode.Success);
            } else {
                this.info("获取经纪人详情失败，返回结果是：" +JSONObject.toJSONString(ret));
                return result.format(ResultCode.BrokerNotFound,brokerId);
            }                    
            
        } catch (Exception e) {
        	e.printStackTrace();
        	this.log("获取用户详情失败："+JSONObject.toJSONString(brokerId)+"---异常信息是："+e.getMessage());          
            return result.format(ResultCode.UnKnownError);
        }        
    }

    /**
     * 问答发送询价推送
     *
     * @param brokerId
     * @param targetId
     * @param type
     * @param message
     * @return
     */
    @RequestMapping(value = "/push")
    public Result pushMessage(
            @RequestParam(value = "broker_id", required = true) long brokerId,
            @RequestParam(value = "target_id", required = true) String targetId,
            @RequestParam(value = "type", required = true) int type,
            @RequestParam(value = "message", required = true) String message
    ) {
        Broker broker = brokerService.get(brokerId);
        if (broker == null || broker.getToken().isEmpty()) {
            return result.format(ResultCode.BrokerNotFound);
        }
        HashMap map = Maps.newHashMap();
        switch (type) {
            case 1:
                map.put("url", "huisj://inquiry?id=" + targetId);
                break;
            case 2:
                String[] arr = targetId.split("@");
                map.put("url", String.format("huisj://qaDetail?question_id=%s&answer_id=%s", arr[1], arr[0]));
                break;
            case 3:
                map.put("url", String.format("huisj://xxx%s", targetId));
                break;
        }
        apiService.pushService().push(broker.getToken(), message, map);
        return result.format(ResultCode.Success);
    }

    /**
     *
     * @param dealerIds
     * @param SortRuleType 1 随机排序 2 金牌获取金牌经纪人优先
     * @param limit 获取指定数量
     * @return
     */
    @RequestMapping(value = "/getrandbroker")
    public Result getBrokerByDealerId(
            @Check(value = "dealer_ids") List<Long> dealerIds,
            @Check(value = "sort_type", defaultValue = "1") int SortRuleType,
            @Check(value = "limit", defaultValue = "0") int limit) {
        List<Broker> resultBrokers = new ArrayList<>();
        for (long dealerId : dealerIds) {
            // 获取随机经纪人
            if (SortRuleType == 1) {
                Broker tempBroker = brokerService
                        .getRandomBrokerByDealerId(dealerId);
                if (tempBroker != null) {
                    resultBrokers.add(tempBroker);
                }
            }
            // 获取金牌经纪人 （不存在 按照服务人数取第一个）
            if (SortRuleType == 2) {
                Broker tempBroker = brokerService
                        .getBestBrokerByDealerId(dealerId);
                if (tempBroker != null) {
                    resultBrokers.add(tempBroker);
                }
            }
            // 获取到对应数量返回
            if (limit > 0 && limit == resultBrokers.size()) {
                break;
            }
        }
        return result.format(ResultCode.Success, resultBrokers);
    }

    /**
     * 获取经纪人信息及在售车型，同步问答车顾问信息用
     *
     * @param uid
     * @return
     */
    @RequestMapping(value = "/sync")
    public Result sync(@Check(value = "uid", required = true) long uid) {
        // 获取经纪人信息
        JSONObject obj = new JSONObject();
        Broker broker = brokerService.getByUid(uid);
        if (broker == null) {
            return result.format(ResultCode.Success, obj);
        }
        obj.put("broker", broker);
        // 获取经纪人在售车型
        List<Integer> seriesIds = dealerCarService.getSeriesIdsByDealer(broker
                .getDealerId());
        obj.put("series", seriesIds);
        return result.format(ResultCode.Success, obj);
    }

    @RequestMapping(value = "/getbrokerbydealerrule")
    public Result getBrokerByDealerRule(@Check(value = "city_id") int cityId,
            @Check(value = "serial_id", defaultValue = "0") int serialId,
            @Check(value = "car_id", defaultValue = "0") int carId,
            @Check(value = "order_type", defaultValue = "0") int orderType,
            @Check(value = "county_id", defaultValue = "0") int countyId,
            @Check(value = "page", defaultValue = "1") int page,
            @Check(value = "limit", defaultValue = "7") int limit,
            @Check(value = "count", defaultValue = "200") int requestTotal) {
        Result dealerResult = new Result();
        try {
            dealerResult = apiService.BaseDealerService().getBaseDealerRule(
                    cityId, serialId, carId, orderType, 0, countyId, requestTotal, 1);
        } catch (ResultException e) {
            return result.format(e.getResult());
        }

        if (dealerResult.getCode() != ResultCode.Success.getCode()) {
            return result.format(ResultCode.UnKnownError);
        }

        PagedList<Broker> pageList = new PagedList<>();
        Map<String, Object> map = (Map<String, Object>) dealerResult.getData();
        ArrayList model = (ArrayList) map.get("dataList");
        List<Long> ids = Lists.newArrayList(0L);
        model.forEach(item -> {
            Map<String, String> tmpMap = (Map<String, String>) item;
            long dealerId = Long.valueOf(String.valueOf(tmpMap.getOrDefault(
                    "dealerId", "0")));
            ids.add(dealerId);
        });
        List<Long> brokerIds = brokerService.getBrokerByDealerIds(ids,
                (page - 1) * limit, limit);
        List<Broker> brokers = Lists.newArrayList();
        if (!brokerIds.isEmpty()) {
            brokers = brokerService.getList(brokerIds);
        }
        pageList.setList(brokers);
        pageList.setPage(page);
        pageList.setLimit(limit);
        pageList.setCount(brokerService.getCountByDealerIds(ids));

        return result.format(ResultCode.Success, pageList);

    }

    @RequestMapping(value = "/del")
    public Result delete(@Check(value = "broker_id", required = true) long brokerId) {
        // 获取经纪人信息
        try {
            Broker broker = brokerService.get(brokerId);
            if (broker == null) {
                return result.format(ResultCode.Success, "经纪人不存在,不用删除:" + brokerId);
            }
            brokerService.delete(brokerId);
            brokerEsService.update(brokerId);
        } catch (Exception e) {
        	this.log("删除用户失败，参数是："+brokerId+"---异常信息是："+e.toString());
            return result.format(ResultCode.UnKnownError);
        }
        return result.format(ResultCode.Success);
    }

    /*@RequestMapping(value = "/updateQacode")
    void updateQacode() {
        int count = brokerService.getAllCount();
        int limit = 500;
        int page = count % limit == 0 ? count / limit : count / limit + 1;
        for (int i = 1; i <= page; i++) {
            List<Broker> list = brokerService.getBatchBrokerList(i, limit);
            for (Broker broker : list) {
                try {
                	BrokerPo brokerPo=new BrokerPo();
                	brokerPo.setBrokerId(broker.getBrokerId());
                    if (StringUtils.isBlank(broker.getQrCode())) {
                        String qrcode = brokerService.createRQCode(broker.getBrokerId(),broker.getDealerId(), broker.getWebsite(),
                                broker.getAvatar());
                        if (StringUtils.isBlank(qrcode)) {
                            this.error("二维码生成失败：" + broker.getBrokerId());
                        }
                        brokerPo.setQrCode(qrcode);
                        brokerService.updateByParam(brokerPo);
                    }
                } catch (Exception e) {
                    this.error("生成二维码失败,用户id是：{}" + broker.getBrokerId() + ":" + e.getMessage());
                    continue;
                }
            }
            this.info("生成二维码,当前第" + i + "页");
        }

    }*/
    

}
