package com.xyauto.interact.broker.server.controller;

import com.google.common.collect.Lists;
import com.xyauto.interact.broker.server.enums.*;
import com.xyauto.interact.broker.server.exceptions.ResultException;
import com.xyauto.interact.broker.server.model.vo.*;
import com.mcp.validate.annotation.Check;
import com.xyauto.interact.broker.server.annotation.ExcludeZero;
import com.xyauto.interact.broker.server.model.po.BrokerCustomerSearchParameters;
import com.xyauto.interact.broker.server.model.po.BrokerCustomerUpdateParameters;
import com.xyauto.interact.broker.server.service.*;
import com.xyauto.interact.broker.server.service.cloud.ApiServiceFactory;
import com.xyauto.interact.broker.server.service.es.customer.BrokerCustomerEsService;
import com.xyauto.interact.broker.server.util.*;
import com.xyauto.interact.broker.server.util.excel.ExcelUtils;
import com.xyauto.interact.broker.server.util.excel.ExcelVo;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

/**
 * 经纪人客户
 *
 * @author liucx
 */
@RestController
@RequestMapping("/broker/customer")
public class BrokerCustomerController extends BaseController implements ILogger {

    @Autowired
    BrokerCustomerService brokerCustomerService;

    @Autowired
    BrokerCustomerCarsWillService brokerCustomerCarsWillService;

    @Autowired
    BrokerService brokerService;

    @Autowired
    BrokerCustomerCarsSerivce customerCarsSerivce;

    @Autowired
    CarService carService;

    @Autowired
    SeriesService seriesService;

    @Autowired
    BrokerReceiptService brokerReceiptService;

    @Autowired
    BrokerLogService brokerLogService;

    @Autowired
    BrokerCustomerEsService brokerCustomerEsService;

    @Autowired
    ApiServiceFactory apiServiceFactory;

    @Autowired
    BrokerClueService brokerClueService;

    @Autowired
    CityService cityService;

    /**
     * 根据id获取客户信息
     *
     * @param brokerCustomerId
     * @return
     * @throws com.xyauto.interact.broker.server.exceptions.ResultException
     */
    @RequestMapping(value = "/{broker_customer_id}", method = RequestMethod.GET)
    public Result get(
            @PathVariable(value = "broker_customer_id", required = true) long brokerCustomerId
    ) throws ResultException {
        BrokerCustomer customer = brokerCustomerService.get(brokerCustomerId, false);
        if (customer == null) {
            return result.format(ResultCode.CustomerNotFound);
        }
        customer.setCustomerCars(customerCarsSerivce.getCustomerCarsByCustomerId(customer.getBrokerCustomerId()));
        return result.format(ResultCode.Success, customer);
    }

    /**
     * 根据客户手机号和经销商id获取客户信息
     *
     * @param mobile
     * @param dealerId
     * @return
     * @throws ResultException
     */
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public Result get(
            @Check(value = "mobile", required = true) String mobile,
            @Check(value = "dealer_id", required = true) long dealerId
    ) throws ResultException {
        BrokerCustomer customer = brokerCustomerService.get(mobile, dealerId);
        return result.format(ResultCode.Success, customer);
    }

    /**
     * 经纪人联系客户
     *
     * @param brokerCustomerId
     * @param brokerId
     * @param type 联系类型 1电话 2短信
     * @return
     */
    @RequestMapping(value = "/contact", method = RequestMethod.GET)
    public Result contact(
            @Check(value = "broker_customer_id", required = true) long brokerCustomerId,
            @Check(value = "broker_id", required = true) long brokerId,
            @Check(value = "type", required = true) short type
    ) {
        Broker broker = brokerService.get(brokerId);
        BrokerCustomer brokerCustomer = brokerCustomerService.get(brokerCustomerId);
        if (broker != null && brokerCustomer != null) {
            if (type == 1) {
                brokerLogService.log(brokerId, broker.getDealerId(), brokerCustomerId, BrokerLogEnum.BrokerCustomerPhoneContact, brokerCustomer, broker.getName(), brokerCustomer.getUserName());
            }
            if (type == 2) {
                brokerLogService.log(brokerId, broker.getDealerId(), brokerCustomerId, BrokerLogEnum.BrokerCustomerSmsContact, brokerCustomer, broker.getName(), brokerCustomer.getUserName());
            }
        }
        return result.format(ResultCode.Success);
    }

    /**
     * 根据客户手机号和经济人id获取客户信息
     *
     * @param mobile
     * @param brokerId
     * @return
     * @throws ResultException
     */
    @RequestMapping(value = "/info/ext", method = RequestMethod.GET)
    public Result getByBrokerId(
            @Check(value = "mobile", required = true) String mobile,
            @Check(value = "broker_id", required = true) long brokerId
    ) throws ResultException {
        Broker broker = brokerService.get(brokerId);
        if (broker == null) {
            return result.format(ResultCode.BrokerNotFound);
        }
        BrokerCustomer customer = brokerCustomerService.get(mobile, broker.getDealerId());
        return result.format(ResultCode.Success, customer);
    }

    /**
     * 获取相关经销商今日新增客户数（手机号排重）
     *
     * @param dealerIds
     * @return
     */
    @RequestMapping(value = "/count/today", method = RequestMethod.GET)
    public Result getTodayNewCount(
            @Check(value = "dealer_ids", required = true) List<Long> dealerIds
    ) {
        int count = brokerCustomerService.getTodayNewCount(dealerIds);
        return result.format(ResultCode.Success, count);
    }

    /**
     * 根据线索id获取第一条客户信息
     *
     * @param clueId
     * @return
     * @throws com.xyauto.interact.broker.server.exceptions.ResultException
     */
    @RequestMapping(value = "/search/clue")
    public Result getFirstCustomerByClueId(
            @Check(value = "clue_id", required = true) long clueId
    ) throws ResultException {
        long brokerCustomerId = brokerCustomerService.getFirstCustomerByClueId(clueId);
        if (brokerCustomerId == 0) {
            return result.format(ResultCode.CustomerNotFound);
        }
        BrokerCustomer customer = brokerCustomerService.get(brokerCustomerId, false);
        return result.format(ResultCode.Success, customer);
    }

    /**
     * 客户搜索
     *
     * @param dealerIds
     * @param brokerIds
     * @param level
     * @param step
     * @param categories
     * @param seriesId
     * @param carId
     * @param begin
     * @param end
     * @param provinceId
     * @param cityId
     * @param mobile
     * @param username
     * @param max
     * @param limit
     * @param export
     * @param response
     * @return
     * @throws com.xyauto.interact.broker.server.exceptions.ResultException
     * @throws java.io.IOException
     */
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public Result search(
            @Check(value = "dealer_ids", required = false, defaultValue = "0") @ExcludeZero List<Long> dealerIds,
            @Check(value = "broker_ids", required = false, defaultValue = "0") @ExcludeZero List<Long> brokerIds,
            @Check(value = "level", required = false, defaultValue = "0") @ExcludeZero List<Integer> level,
            @Check(value = "step", required = false, defaultValue = "1,2") @ExcludeZero List<Integer> step,
            @Check(value = "categories", required = false, defaultValue = "0") @ExcludeZero List<Integer> categories,
            @Check(value = "series_id", required = false, defaultValue = "0") int seriesId,
            @Check(value = "car_id", required = false, defaultValue = "0") int carId,
            @Check(value = "begin", required = false, defaultValue = "0") long begin,
            @Check(value = "end", required = false, defaultValue = "0") long end,
            @Check(value = "province_id", required = false, defaultValue = "0") int provinceId,
            @Check(value = "city_id", required = false, defaultValue = "0") int cityId,
            @Check(value = "mobile", required = false, defaultValue = "") String mobile,
            @Check(value = "username", required = false, defaultValue = "") String username,
            @Check(value = "max", required = false, defaultValue = "0") String max,
            @Check(value = "limit", required = false, defaultValue = "20") int limit,
            @Check(value = "export", required = false, defaultValue = "0") int export,
            HttpServletResponse response
    ) throws ResultException, IOException {
        if (step.equals(Lists.newArrayList())) {
            step = Lists.newArrayList(1, 2);
        }
        if (username.isEmpty()==false || mobile.isEmpty()==false) {
            step = Lists.newArrayList();
        }
        BrokerCustomerSearchParameters params = new BrokerCustomerSearchParameters();
        params.setBegin(begin);
        params.setBrokerIds(brokerIds);
        params.setCarId(carId);
        params.setCityId(cityId);
        params.setDealerIds(dealerIds);
        params.setEnd(end);
        params.setLevel(level);
        params.setMobile(mobile);
        params.setProvinceId(provinceId);
        params.setSeriesId(seriesId);
        params.setStep(step);
        params.setUsername(username);
        params.setCategories(categories);
        FlowPagedList<BrokerCustomer> pageList = new FlowPagedList();
        if (export == 1) {
            max = "0";
            limit = 0;
        }
        List<Long> ids = brokerCustomerService.searchListIds(params, max, limit);
        List<BrokerCustomer> list = Lists.newArrayList();
        if (ids.isEmpty() == false) {
            int total = brokerCustomerService.searchListCount(params, String.valueOf(0));
            int pagedCount = brokerCustomerService.searchListCount(params, max);
            pageList.setCount(total);
            pageList.setHas_more(pagedCount > limit ? 1 : 0);
            list = brokerCustomerService.getList(ids);
            if (list.size() > 0) {
                pageList.setNext_max(list.get(list.size() - 1).getSort());
            }
        }
        if (export == 1) {
            //导出
            List<Object[]> dataList = new ArrayList<Object[]>();
            Object[] objs = null;
            String title = "客户管理";
            String excelName = "客户管理.xls";
            String[] headers = new String[]{"客户等级", "客户姓名", "客户手机号", "销售负责人", "跟进状态", "创建时间", "意向车型"};
            for (BrokerCustomer brokerCustomer : list) {
                objs = new Object[headers.length];
                objs[0] = CustomerLevelEnum.getCustomerLevelName(brokerCustomer.getLevel());
                objs[1] = brokerCustomer.getUserName();
                objs[2] = brokerCustomer.getMobile();
                objs[3] = brokerCustomer.getBrokerInfo().getName();
                //objs[3] = brokerCustomer.setStep();
                //1待跟进，2到店，5成交，-1无效，-2战败
                switch (brokerCustomer.getStep()) {
                    case 1:
                        objs[4] = "待跟进";
                        break;
                    case 2:
                        objs[4] = "到点";
                        break;
                    case 5:
                        objs[4] = "成交";
                        break;
                    case -1:
                        objs[4] = "无效";
                        break;
                    case -2:
                        objs[4] = "战败";
                        break;
                }
                objs[5] = brokerCustomer.getCreateTime();
                if (brokerCustomer.getCarsWill() == null) {
                    objs[6] = "";
                } else {
                    objs[6] = brokerCustomer.getCarsWill().getSeriesInfo().getName() + brokerCustomer.getCarsWill().getCarInfo().getName();
                }
                //objs[6] = brokerCustomer.;
                dataList.add(objs);
            }
            ExcelVo excelVo = new ExcelVo();
            excelVo.setTitle(title);
            excelVo.setRowName(headers);
            excelVo.setDataList(dataList);
            List<ExcelVo> excelList = new ArrayList<ExcelVo>();
            excelList.add(excelVo);
            ExcelUtils.exportExcel(excelName, excelList, response);
            return result.format(ResultCode.Success, "导出客户信息");
        }
        pageList.setList(list);
        pageList.setLimit(limit);
        return result.format(ResultCode.Success, pageList);
    }

    /**
     * 客户搜索
     *
     * @param dealerIds
     * @param brokerIds
     * @param level
     * @param step
     * @param categories
     * @param seriesId
     * @param begin
     * @param carId
     * @param cityId
     * @param mobile
     * @param username
     * @param end
     * @param provinceId
     * @param page
     * @param limit
     * @return
     * @throws com.xyauto.interact.broker.server.exceptions.ResultException
     * @throws java.io.IOException
     * @throws com.xyauto.interact.broker.server.exceptions
     */
    @RequestMapping(value = "/psearch", method = RequestMethod.GET)
    public Result search(
            @Check(value = "dealer_ids", required = false, defaultValue = "0") @ExcludeZero List<Long> dealerIds,
            @Check(value = "broker_ids", required = false, defaultValue = "0") @ExcludeZero List<Long> brokerIds,
            @Check(value = "level", required = false, defaultValue = "0") @ExcludeZero List<Integer> level,
            @Check(value = "step", required = false, defaultValue = "1,2") @ExcludeZero List<Integer> step,
            @Check(value = "categories", required = false, defaultValue = "0") @ExcludeZero List<Integer> categories,
            @Check(value = "series_id", required = false, defaultValue = "0") int seriesId,
            @Check(value = "car_id", required = false, defaultValue = "0") int carId,
            @Check(value = "begin", required = false, defaultValue = "0") long begin,
            @Check(value = "end", required = false, defaultValue = "0") long end,
            @Check(value = "province_id", required = false, defaultValue = "0") int provinceId,
            @Check(value = "city_id", required = false, defaultValue = "0") int cityId,
            @Check(value = "mobile", required = false, defaultValue = "") String mobile,
            @Check(value = "username", required = false, defaultValue = "") String username,
            @Check(value = "page", required = false, defaultValue = "1") int page,
            @Check(value = "limit", required = false, defaultValue = "20") int limit
    ) throws ResultException, IOException {
        if (step.equals(Lists.newArrayList())) {
            step = Lists.newArrayList(1, 2, 5, -1, -2);
        }
        BrokerCustomerSearchParameters params = new BrokerCustomerSearchParameters();
        params.setBegin(begin);
        params.setBrokerIds(brokerIds);
        params.setCarId(carId);
        params.setCityId(cityId);
        params.setCategories(categories);
        params.setDealerIds(dealerIds);
        params.setEnd(end);
        params.setLevel(level);
        params.setMobile(mobile);
        params.setProvinceId(provinceId);
        params.setSeriesId(seriesId);
        params.setStep(step);
        params.setUsername(username);
        PagedList<BrokerCustomer> pageList = new PagedList();
        List<Long> ids = brokerCustomerService.searchListIds(params, page, limit);
        List<BrokerCustomer> list = Lists.newArrayList();
        if (ids.isEmpty() == false) {
            int total = brokerCustomerService.searchListCount(params, String.valueOf(0));
            pageList.setCount(total);
            list = brokerCustomerService.getList(ids);
            pageList.setList(list);
        }
        //pageList.setList(list);
        pageList.setList(list == null ? new ArrayList():list);
        pageList.setLimit(limit);
        return result.format(ResultCode.Success, pageList);
    }

    /**
     *
     * @param brokerClueId
     * @param brokerId
     * @param dealerId
     * @param userName
     * @param gender
     * @param phone
     * @param provinceId
     * @param cityId
     * @param step
     * @param nextContactTime 下次联系时间
     * @param level 意向等级，1H，2A, 3B,4C,5D
     * @param carId 车款ID
     * @param category 购车类型，1新车，2二手车
     * @param registerProvinceId 上牌省份id
     * @param registerCityId 上牌城市id
     * @param payType 支付方式，1全款，2贷款
     * @param exchangeType 置换方式，1非置换，2置换
     * @param buyDesc 购买说明，1首次购车，2增购，3换购
     * @param budget 购车预算，例如10万以内
     * @param isAllopatryRegister 是否异地上牌，0否，1是
     * @param remark
     * @param carIds 客户保有车辆ids
     * @return
     * @throws com.xyauto.interact.broker.server.exceptions.ResultException
     * @throws java.io.IOException
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Result addCustomer(
            @Check(value = "broker_clue_id", required = false, defaultValue = "0") long brokerClueId,
            @Check(value = "broker_id", required = true) long brokerId,
            @Check(value = "dealer_id", required = true) long dealerId,
            @Check(value = "username", required = true) String userName,
            @Check(value = "gender", required = false, defaultValue = "0") short gender,
            @Check(value = "mobile", length = 11, required = true) String phone,
            @Check(value = "province_id", required = false, defaultValue = "0") int provinceId,
            @Check(value = "city_id", required = false, defaultValue = "0") int cityId,
            @Check(value = "step", required = false, defaultValue = "1") short step,
            @Check(value = "next_contact_time", required = false, defaultValue = "") String nextContactTime,
            @Check(value = "level", required = true) short level,
            @Check(value = "car_id", required = false, defaultValue = "0") int carId,
            @Check(value = "category", required = false, defaultValue = "0") short category,
            @Check(value = "register_province_id", required = false, defaultValue = "0") int registerProvinceId,
            @Check(value = "register_city_id", required = false, defaultValue = "0") int registerCityId,
            @Check(value = "pay_type", required = false, defaultValue = "0") short payType,
            @Check(value = "exchange_type", required = false, defaultValue = "0") short exchangeType,
            @Check(value = "buy_desc", required = false, defaultValue = "0") short buyDesc,
            @Check(value = "budget", required = false, defaultValue = "") String budget,
            @Check(value = "is_allopatry_register", required = false, defaultValue = "-1") short isAllopatryRegister,
            @Check(value = "remark", required = false, defaultValue = "") String remark,
            @Check(value = "car_ids", required = false, defaultValue = "0") @ExcludeZero List<Integer> carIds
    ) throws ResultException, IOException {
        try {
            if (userName.length() == 0 || userName.length() > 6 || StringUtil.isSpecialChar(userName)) {
                return result.format(ResultCode.ERROR_PARAMS, "用户名必须是1-6位汉字且不包含特殊字符");
            }
            if (level <= 0) {
                return result.format(ResultCode.ERROR_PARAMS, "意向等级参数错误");
            }
            BrokerCustomer brokerCustomer = brokerCustomerService.existsCustomer(phone, dealerId);
            Broker broker = brokerService.get(brokerId);
            //Broker broker = brokerService.get();
            if (broker == null) {
                return result.format(ResultCode.BrokerNotFound);
            }
            //只有销售经理和销售能建卡
            if (broker.getType() == BrokerTypeEnum.MarketManager.getValue() || broker.getType() == BrokerTypeEnum.MarketPersonne.getValue() || broker.getType() == BrokerTypeEnum.StoreManager.getValue()) {
                return result.format(ResultCode.NoPermission);
            }

            //验证客户是否已经建卡
            if (brokerCustomer != null) {
                broker = brokerService.get(brokerCustomer.getBrokerId());
                if (broker != null) {
                    return result.format(ResultCode.CustomerExists, "该手机号已建卡，跟进人为" + broker.getName());
                }
                return result.format(ResultCode.CustomerExists, "该手机号已建卡。");
            }
            brokerCustomer = new BrokerCustomer();
            BrokerCustomerCarsWill carsWill = new BrokerCustomerCarsWill();
            //默认意向车型为新车
            carsWill.setCategory(Short.valueOf("1"));
            //通过线索建卡 --
            BrokerClueTypeEnum clueType = BrokerClueTypeEnum.NetClue ;
            if (brokerClueId > 0) {
                //验证线索是否有效
                BrokerClue brokerClue = brokerClueService.get(brokerClueId, false);
                if (brokerClue == null) {
                    return result.format(ResultCode.BrokerClueNotFound);
                }
                //如果是网单则验证权限  话单(销售经理，销售顾问)权限都可以建卡
                if (brokerClue.getType() == BrokerClueTypeEnum.NetClue.getValue()) {
                    if (brokerClue.getCategory() > 0) {
                        carsWill.setCategory(Short.valueOf(String.valueOf(brokerClue.getCategory())));
                    }
                    //是当前店铺线索 且 是经理
                    if (brokerClue.getBrokerId() == 0 && broker.getType() == BrokerTypeEnum.Manager.getValue() && brokerClue.getDealerId() == broker.getDealerId()) {
                        //先将店铺线索分配给经理
                        int ret = brokerClueService.allot(brokerId, brokerClueId);
                        if (ret > 0) {
                            brokerClue = brokerClueService.get(brokerClueId, false);
                            //添加线索个人自动分配日志
                            brokerLogService.log(brokerClue.getBrokerId(), brokerClue.getDealerId(), brokerClue.getBrokerClueId(), BrokerLogEnum.BrokerClueAllotAdd, brokerClue, brokerClue.getBroker().getName(), brokerClue.getBroker().getName());
                        }
                    } else if (brokerClue.getBrokerId() != brokerId) {
                        return result.format(ResultCode.NoPermission, "线索已分配，暂无建卡权限");
                    }
                } else if(brokerClue.getType() == BrokerClueTypeEnum.TalkClue.getValue()){
                    clueType = BrokerClueTypeEnum.TalkClue ;
                    //验证话单(销售经理，销售顾问)权限
                    if(broker.getType() == BrokerTypeEnum.Employee.getValue() ||  broker.getType() == BrokerTypeEnum.Manager.getValue())
                    {
                        //话单线索分配给创建者
                        int ret = brokerClueService.allot(brokerId, brokerClueId);
                        //添加线索个人自动分配日志
                        brokerLogService.log(brokerClue.getBrokerId(), brokerClue.getDealerId(), brokerClue.getBrokerClueId(), BrokerLogEnum.BrokerClueAllotAdd, brokerClue, brokerClue.getBroker().getName(), brokerClue.getBroker().getName());
                    }else {
                        return result.format(ResultCode.NoPermission);
                    }

                }else {
                    //不属于任何 话单和网单
                    return result.format(ResultCode.UnKnownError);
                }
                //获取线索 手机城市省份
                if (brokerClue.getMobileCityId() > 0) {
                    brokerCustomer.setCityId(brokerClue.getMobileCityId());
                    brokerCustomer.setProvinceId(brokerClue.getMobileCityId());
                } else {
                    brokerCustomer.setCityId(brokerClue.getCityId());
                    brokerCustomer.setProvinceId(brokerClue.getProvinceId());
                }
            } else {
                //自主建卡采用数据库匹配手机号定位城市
                City city = cityService.matchMobileCity(phone);
                if (city != null) {
                    brokerCustomer.setCityId(city.getId());
                    brokerCustomer.setProvinceId(city.getParent());
                }
            }
            //设置客户基础信息
            brokerCustomer.setBrokerClueId(brokerClueId);
            brokerCustomer.setCreateTime(new Date());
            brokerCustomer.setBrokerClueIdLastest(brokerClueId);
            brokerCustomer.setBrokerId(brokerId);
            brokerCustomer.setDealerId(dealerId);
            brokerCustomer.setUserName(userName);
            brokerCustomer.setGender(gender);
            brokerCustomer.setMobile(phone);
            brokerCustomer.setStep(step);
            brokerCustomer.setNextContactTime(DateUtil.StringToDate(nextContactTime, DateStyle.YYYY_MM_DD_HH_MM_SS));
            brokerCustomer.setLevel(level);
            brokerCustomer.setRemark(remark);
            //设置意向车型信息

            if (carId > 0) {
                Car car = carService.getCar(carId);
                if (car != null) {
                    carsWill.setCarId(carId);
                    carsWill.setSeriesId(car.getSeriesId());
                    Series series = seriesService.getSeries(car.getSeriesId());
                    if (series != null) {
                        carsWill.setSeriesId(series.getSeriesId());
                        carsWill.setSubBrandId(series.getSubBrandId());
                        carsWill.setBrandId(series.getBrandId());
                    }
                }
            }
            carsWill.setRegisterProvinceId(registerProvinceId);
            carsWill.setRegisterCityId(registerCityId);
            carsWill.setPayType(payType);
            carsWill.setExchangeType(exchangeType);
            carsWill.setBuyDesc(buyDesc);
            carsWill.setBudget(budget);
            carsWill.setIsAllopatryRegister(isAllopatryRegister);
            //创建客户，意向车型，保有车辆
            int ret = brokerCustomerService.createCustomerAndCarsWill(brokerCustomer, carsWill, carIds,clueType);
            if (ret > 0) {
                if (brokerClueId > 0) {
                    brokerClueService.handle(brokerId, brokerClueId, BrokerClueHandleTypeEnum.Other);
                }
                return result.format(ResultCode.Success, brokerCustomer);
            }
            return result.format(ResultCode.UnKnownError);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据参数修改 客户信息
     *
     * @param brokerId
     * @param brokerCustomerId
     * @param step 意向阶段，1待跟进，2将到店，5成交，-1无效，-2战败
     * @param nextContactTime 下次联系时间
     * @param level 意向等级，1H，2A, 3B,4C,5D
     * @param userName 用户名称
     * @param gender 0未知，1男，2女
     * @param carId 意向车款id
     * @param payType 支付类型 1全款 2贷款
     * @param exchangeType 置换 1非置换，2置换
     * @param isAllopatryRegister 是否本地上牌
     * @param remark 客户备注
     * @return
     * @throws com.xyauto.interact.broker.server.exceptions.ResultException
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Result update(
            @Check(value = "broker_id", required = true) long brokerId,
            @Check(value = "broker_customer_id", required = true) long brokerCustomerId,
            @Check(value = "step", required = false, defaultValue = "0") short step,
            @Check(value = "next_contact_time", required = false, defaultValue = "0") long nextContactTime,
            @Check(value = "level", required = false, defaultValue = "0") short level,
            @Check(value = "username", required = false, defaultValue = "") String userName,
            @Check(value = "gender", required = false, defaultValue = "0") short gender,
            @Check(value = "car_id", required = false, defaultValue = "0") int carId,
            @Check(value = "pay_type", required = false, defaultValue = "0") short payType,
            @Check(value = "exchange_type", required = false, defaultValue = "0") short exchangeType,
            @Check(value = "is_allopatry_register", required = false, defaultValue = "-1") short isAllopatryRegister,
            @Check(value = "remark", required = false, defaultValue = "") String remark
    ) throws ResultException {
        Broker broker = brokerService.get(brokerId);
        if (broker == null) {
            return result.format(ResultCode.BrokerNotFound);
        }
        BrokerCustomer customer = brokerCustomerService.get(brokerCustomerId, false);
        if (customer == null) {
            return result.format(ResultCode.CustomerNotFound);
        }
        //1 经纪人只能处理自己的数据
        //2 销售经理，市场经理，市场专员可以处理当前经销商数据
        if(broker.getType() == BrokerTypeEnum.Employee.getValue() && broker.getBrokerId() != customer.getBrokerId())
        {
            return result.format(ResultCode.NoPermission);
        }else if(broker.getDealerId() != customer.getDealerId())
        {
            return result.format(ResultCode.NoPermission);
        }
        /*if ((broker.getType() != BrokerTypeEnum.Manager.getValue() && broker.getBrokerId() != customer.getBrokerId())
                || (broker.getType() == BrokerTypeEnum.Manager.getValue() && broker.getDealerId() != customer.getDealerId())) {
            return result.format(ResultCode.NoPermission);
        }*/
        BrokerCustomerUpdateParameters params = new BrokerCustomerUpdateParameters();
        if (carId > 0) {
            Car car = carService.getCar(carId);
            if (car != null) {
                params.setCarId(carId);
                params.setSeriesId(car.getSeriesId());
                Series series = seriesService.getSeries(car.getSeriesId());
                if (series != null) {
                    params.setSeriesId(series.getSeriesId());
                    params.setSubBrandId(series.getSubBrandId());
                    params.setBrandId(series.getBrandId());
                }
            }
        }

        params.setBrokerCustomerId(brokerCustomerId);
        params.setBrokerId(brokerId);
        //params.setCarId(carId);
        params.setExchangeType(exchangeType);
        params.setGender(gender);
        params.setIsAllopatryRegister(isAllopatryRegister);
        params.setLevel(level);
        params.setNextContactTime(String.valueOf(nextContactTime).length() == 10 ? nextContactTime : nextContactTime / 1000);
        params.setPayType(payType);
        params.setStep(step);
        params.setRemark(remark);
        params.setUsername(userName);
        int ret = brokerCustomerService.update(params);
        ret = brokerCustomerCarsWillService.update(params);
        if (ret > 0) {
            brokerCustomerEsService.update(brokerCustomerId);

            if (carId > 0) {
                BrokerCustomerCarsWill carsWill = brokerCustomerCarsWillService.getByBrokerCustomerId(brokerId, brokerCustomerId);
                if (carsWill != null) {
                    return result.format(ResultCode.Success, carsWill);
                }
            }
            if (level > 0 || step > 0) {
                //修改等级或者意向阶段
                try {
                    apiServiceFactory.BrokerTaskService().updateCustomerInfo(brokerId, brokerCustomerId);
                } catch (Exception e) {
                }
            }
            return result.format(ResultCode.Success);
        }
        return result.format(ResultCode.UnKnownError);
    }

}
