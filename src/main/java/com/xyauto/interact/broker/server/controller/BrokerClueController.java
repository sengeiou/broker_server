package com.xyauto.interact.broker.server.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xyauto.interact.broker.server.enums.BrokerTypeEnum;
import com.xyauto.interact.broker.server.model.vo.*;
import com.mcp.validate.annotation.Check;
import com.xyauto.interact.broker.server.annotation.ExcludeZero;
import com.xyauto.interact.broker.server.enums.BrokerClueHandleTypeEnum;
import com.xyauto.interact.broker.server.enums.ResultCode;
import com.xyauto.interact.broker.server.exceptions.ResultException;
import com.xyauto.interact.broker.server.model.po.BrokerClueSearchParameters;
import com.xyauto.interact.broker.server.service.BrokerClueService;
import com.xyauto.interact.broker.server.service.BrokerService;
import com.xyauto.interact.broker.server.service.DealerService;
import com.xyauto.interact.broker.server.service.cloud.ApiServiceFactory;
import com.xyauto.interact.broker.server.service.es.clue.BrokerClueEsService;
import com.xyauto.interact.broker.server.util.*;
import com.xyauto.interact.broker.server.util.excel.ExcelUtils;
import com.xyauto.interact.broker.server.util.excel.ExcelVo;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.*;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/broker/clue")
public class BrokerClueController extends BaseController implements ILogger {

    @Autowired
    BrokerClueService brokerClueService;

    @Autowired
    BrokerService brokerService;

    @Autowired
    BrokerClueEsService brokerClueEsService;

    @Autowired
    ApiServiceFactory apiServiceFactory;

    @Autowired
    DealerService dealerService;

    /**
     * 获取线索详细
     *
     * @param brokerClueId
     * @param targetBrokerId
     * @return
     * @throws ResultException
     */
    @RequestMapping(value = "/{broker_clue_id}/{target_broker_id}", method = RequestMethod.GET)
    public Result getForOne(
            @PathVariable(value = "broker_clue_id", required = true) long brokerClueId,
            @PathVariable(value = "target_broker_id", required = true) long targetBrokerId
    ) throws ResultException {
        BrokerClue brokerClue = brokerClueService.get(brokerClueId, false);
        if (brokerClue == null) {
            return result.format(ResultCode.BrokerClueNotFound);
        }
        //查看线索完成任务
        try {

            //apiServiceFactory.BrokerTaskService().clueDetail(targetBrokerId, brokerClueId);
            brokerClueService.handle(targetBrokerId, brokerClueId, BrokerClueHandleTypeEnum.ClueDetail);
        } catch (ResultException | IOException e) {
            this.error("完成任务及处理线索失败:" + e.getMessage());
        }

        return result.format(ResultCode.Success, brokerClue);
    }

    /**
     * 获取线索详细
     *
     * @param brokerClueId
     * @param brokerId
     * @return
     * @throws ResultException
     * @throws java.io.IOException
     */
    @RequestMapping(value = "/{broker_clue_id}", method = RequestMethod.GET)
    public Result get(
            @PathVariable(value = "broker_clue_id", required = true) long brokerClueId,
            @Check(value = "broker_id", required = false, defaultValue = "0") long brokerId
    ) throws ResultException, IOException {
        BrokerClue brokerClue = brokerClueService.get(brokerClueId, false);
        if (brokerClue == null) {
            return result.format(ResultCode.BrokerClueNotFound);
        }
        //线索被处理
        brokerClueService.handle(brokerId, brokerClueId, BrokerClueHandleTypeEnum.Other);
        return result.format(ResultCode.Success, brokerClue);
    }

    /**
     * 根据手机号获取线索详细
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
        BrokerClue brokerClue = brokerClueService.getByMobileDealerId(mobile, dealerId);
        if (brokerClue == null) {
            return result.format(ResultCode.BrokerClueNotFound);
        }
        return result.format(ResultCode.Success, brokerClue);
    }

    /**
     * 搜索线索列表
     *
     * @param dealerIds
     * @param brokerIds
     * @param username
     * @param dealerName
     * @param brokerClueId
     * @param clueId
     * @param cityId
     * @param province_id
     * @param brandId
     * @param seriesId
     * @param car_id
     * @param is_handled
     * @param begin
     * @param end
     * @param type 线索类型，1话单线索，2网单线索
     * @param isDeleted
     * @param categories
     * @param sources
     * @param isCustomer
     * @param mobile
     * @param max
     * @param limit
     * @return
     * @throws ResultException
     * @throws IOException
     */
    @RequestMapping(value = "/list/search", method = {RequestMethod.GET, RequestMethod.POST})
    public Result search(
            @Check(value = "dealer_ids", required = false, defaultValue = "") @ExcludeZero List<Long> dealerIds,
            @Check(value = "broker_ids", required = false, defaultValue = "") @ExcludeZero List<Long> brokerIds,
            @Check(value = "username", required = false, defaultValue = "") String username,
            @Check(value = "dealer_name", required = false, defaultValue = "") String dealerName,
            @Check(value = "broker_clue_id", required = false, defaultValue = "0") long brokerClueId,
            @Check(value = "clue_id", required = false, defaultValue = "0") long clueId,
            @Check(value = "city_id", required = false, defaultValue = "0") int cityId,
            @Check(value = "province_id", required = false, defaultValue = "0") int province_id,
            @Check(value = "brand_id", required = false, defaultValue = "0") int brandId,
            @Check(value = "series_id", required = false, defaultValue = "0") int seriesId,
            @Check(value = "car_id", required = false, defaultValue = "0") int car_id,
            @Check(value = "is_handled", required = false, defaultValue = "-1") short is_handled,
            @Check(value = "begin", required = false, defaultValue = "0") long begin,
            @Check(value = "end", required = false, defaultValue = "0") long end,
            @Check(value = "max", required = false, defaultValue = "0") String max,
            @Check(value = "is_deleted", required = false, defaultValue = "-1") short isDeleted,
            @Check(value = "categories", required = false, defaultValue = "0") @ExcludeZero List<Integer> categories,
            @Check(value = "sources", required = false, defaultValue = "0") @ExcludeZero List<Integer> sources,
            @Check(value = "is_customer", required = false, defaultValue = "-1") short isCustomer,
            @Check(value = "mobile", required = false, defaultValue = "") String mobile,
            @Check(value = "type", required = false, defaultValue = "2") short type,
            @Check(value = "limit", required = false, defaultValue = "20") int limit
    ) throws ResultException, IOException {
        if (dealerIds.isEmpty()) {
            return null;
        }
        try {
            BrokerClueSearchParameters params = new BrokerClueSearchParameters();
            //新车
            if (categories.equals(Lists.newArrayList(1))) {
                categories = Lists.newArrayList(0, 1, 2, 6);
            }
            //二手车
            if (categories.equals(Lists.newArrayList(2))) {
                categories = Lists.newArrayList(3, 4, 5);
            }
            //优先处理手机号
            params.setMobile(mobile);
            if(!username.isEmpty() && username.length()<=11 && NumberUtil.isNumber(username) && mobile.isEmpty())
            {
                params.setMobile(username);
            }
            else {
                params.setUsername(username);
            }
            params.setBegin(begin);
            params.setBrandId(brandId);
            params.setBrokerIds(brokerIds);
            params.setCarId(car_id);
            params.setCityId(cityId);
            params.setBrokerClueId(brokerClueId);
            params.setClueId(clueId);
            params.setDealerIds(dealerIds);
            params.setDealerName(dealerName);
            params.setEnd(end);
            params.setIsHandled(is_handled);
            params.setLimit(limit);
            params.setType(type);
            params.setMax(max);
            params.setProvinceId(province_id);
            params.setSeriesId(seriesId);
            //params.setUsername(username);
            params.setIsdelete(isDeleted);
            params.setCategories(categories);
            params.setSources(sources);
            params.setIsCustomer(isCustomer);
            params.setLimit(limit);
            params.setMax(max);
            FlowPagedList pageList = new FlowPagedList();
            Map<String, Object> ret = brokerClueService.searchGroupByMobile(params);
            List<Long> ids = (List<Long>) ret.get("ids");
            //List<Long> ids = brokerClueService.searchListIds(params, max, limit);
            List<BrokerClue> list = Lists.newArrayList();
            int total = brokerClueService.searchGroupByMobileTotal(params);
            //int total = brokerClueService.searchListCount(params, "0");
            //int pagedCount = brokerClueService.searchListCount(params, max);
            int pagedCount = (int) ret.get("total");
            if (pagedCount == 0) {
                pagedCount = total;
            }
            if (ids.isEmpty() == false) {
                list = brokerClueService.getClueList(ids);
                pageList.setCount(total);

                pageList.setHas_more(pagedCount > limit ? 1 : 0);
                if (list.isEmpty() == false) {
                    pageList.setNext_max(String.valueOf(list.get(list.size() - 1).getCreateTime().getTime()));
                }
            }
            pageList.setList(list);
            pageList.setLimit(limit);
            return result.format(ResultCode.Success, pageList);
        } catch (IOException e) {
            e.printStackTrace();
            this.error("获取线索列表信息失败:" + e.getMessage());
            return null;
        }
    }

    /**
     * 搜索线索列表
     *
     * @param targetBrokerId
     * @param dealerIds
     * @param brokerIds
     * @param username
     * @param dealerName
     * @param brokerClueId
     * @param clueId
     * @param cityId
     * @param province_id
     * @param brandId
     * @param seriesId
     * @param car_id
     * @param is_handled
     * @param begin
     * @param end
     * @param isDeleted
     * @param categories
     * @param sources
     * @param isCustomer
     * @param mobile
     * @param type
     * @param page
     * @param limit
     * @param export
     * @param response
     * @return
     * @throws ResultException
     * @throws IOException
     */
    @RequestMapping(value = "/plist/search", method = {RequestMethod.GET, RequestMethod.POST})
    public Result search(
            @Check(value = "taget_broker_id", required = false, defaultValue = "0") long targetBrokerId,
            @Check(value = "dealer_ids", required = false, defaultValue = "0") @ExcludeZero List<Long> dealerIds,
            @Check(value = "broker_ids", required = false, defaultValue = "0") @ExcludeZero List<Long> brokerIds,
            @Check(value = "username", required = false, defaultValue = "") String username,
            @Check(value = "dealer_name", required = false, defaultValue = "") String dealerName,
            @Check(value = "broker_clue_id", required = false, defaultValue = "0") long brokerClueId,
            @Check(value = "clue_id", required = false, defaultValue = "0") long clueId,
            @Check(value = "city_id", required = false, defaultValue = "0") int cityId,
            @Check(value = "province_id", required = false, defaultValue = "0") int province_id,
            @Check(value = "brand_id", required = false, defaultValue = "0") int brandId,
            @Check(value = "series_id", required = false, defaultValue = "0") int seriesId,
            @Check(value = "car_id", required = false, defaultValue = "0") int car_id,
            @Check(value = "is_handled", required = false, defaultValue = "-1") short is_handled,
            @Check(value = "begin", required = false, defaultValue = "0") long begin,
            @Check(value = "end", required = false, defaultValue = "0") long end,
            @Check(value = "is_deleted", required = false, defaultValue = "-1") Short isDeleted,
            @Check(value = "categories", required = false, defaultValue = "0") @ExcludeZero List<Integer> categories,
            @Check(value = "sources", required = false, defaultValue = "0") @ExcludeZero List<Integer> sources,
            @Check(value = "is_customer", required = false, defaultValue = "-1") short isCustomer,
            @Check(value = "mobile", required = false, defaultValue = "") String mobile,
            @Check(value = "type", required = false, defaultValue = "2") short type,
            @Check(value = "page", required = false, defaultValue = "1") int page,
            @Check(value = "limit", required = false, defaultValue = "20") int limit,
            @Check(value = "export", required = false, defaultValue = "0") int export,
            HttpServletResponse response
    ) throws ResultException, IOException {
        if (dealerIds.isEmpty()) {
            return null;
        }
        BrokerClueSearchParameters params = new BrokerClueSearchParameters();
        //新车
        if (categories.equals(Lists.newArrayList(1))) {
            categories = Lists.newArrayList(0, 1, 2, 6);
        }
        //二手车
        if (categories.equals(Lists.newArrayList(2))) {
            categories = Lists.newArrayList(3, 4, 5);
        }
        //优先处理手机号
        params.setMobile(mobile);
        if(!username.isEmpty() && username.length()<=11 && NumberUtil.isNumber(username) && mobile.isEmpty())
        {
            params.setMobile(username);
        }
        else {
            params.setUsername(username);
        }
        params.setBegin(begin);
        params.setBrandId(brandId);
        params.setBrokerIds(brokerIds);
        params.setCarId(car_id);
        params.setCityId(cityId);
        params.setBrokerClueId(brokerClueId);
        params.setClueId(clueId);
        params.setDealerIds(dealerIds);
        params.setDealerName(dealerName);
        params.setEnd(end);
        params.setIsHandled(is_handled);
        params.setLimit(limit);
        params.setOffset((page - 1) * limit);
        params.setProvinceId(province_id);
        params.setSeriesId(seriesId);
        //params.setUsername(username);
        params.setIsdelete(isDeleted);
        params.setCategories(categories);
        params.setSources(sources);
        params.setIsCustomer(isCustomer);
        params.setType(type);
        CluePagedList pageList = new CluePagedList();
        /**
         * 如果是导出功能则，导出筛选条件下的所有值，分组过滤size=0查询全部
         */
        /*if (export == 1) {
            params.setLimit(0);
            params.setOffset(0);
        }*/
        
        List<BrokerClue> list = Lists.newArrayList();   
        List<Long> ids =Lists.newArrayList(); 
        //未去重的总数
        int clue_total = brokerClueService.searchListCount(params,"0");
        if (clue_total<=0) {
            pageList.setList( new ArrayList());
        	return result.format(ResultCode.Success, pageList);
		}
        if (export == 1) {
        	//未分组的线索
        	ids=brokerClueService.searchListIds(params,1,5000);
        	List<Long> noHandleids =new ArrayList<Long>(); 
        	if (is_handled==-1) {
				//查询全部时需要区分出未处理的
    	    	params.setIsHandled(0);
//    	    	Map<String, Object> result = brokerClueService.searchGroupByMobile(params);
//    	    	noHandleids = (List<Long>) result.get("ids");
    	    	//未处理的线索
    	    	noHandleids=brokerClueService.searchListIds(params,1,5000);
			}else if (is_handled==0) {
				noHandleids=ids;					
			}
    	    if (noHandleids.size()>0) {
    	    	//更新数据库        	    	
        		brokerClueService.updateClueHandle(noHandleids); 
			}    	    
    	    //查询所有线索，返回结果
    	    list = brokerClueService.getClueList(ids);  
            if (noHandleids.size()>0) {
            	List<BrokerClue> noHandelList=brokerClueService.getBasicClueList(noHandleids);
            	brokerClueEsService.add(noHandelList);
            	List<Long> relDealerIdsList=new ArrayList<Long>();
            	for (BrokerClue brokerClue : noHandelList) {
            		relDealerIdsList.add(brokerClue.getClueRefDealerId());
				}               	
            	brokerClueService.sendClueHandle(dealerIds.get(0), relDealerIdsList);
			}
		}else{
			Map<String, Object> ret = brokerClueService.searchGroupByMobile(params);
	        ids = (List<Long>) ret.get("ids");
			//去重的总数
	        int count = brokerClueService.searchGroupByMobileTotal(params);	        
			list = brokerClueService.getClueList(ids);		
			pageList.setCount(count);			
		}  
        pageList.setClue_count(clue_total);
        pageList.setList(list);
        pageList.setPage(page);
        pageList.setLimit(limit);
        return result.format(ResultCode.Success, pageList);
    }

    /**
     * 流失线索导出数据
     *
     * @param list
     * @return
     */
    public ExcelVo clueExcelAll(List<BrokerClue> list, long brokerTagetBrokerId) {
        List<Object[]> dataList = Lists.newArrayList();
        Object[] objs = null;
        String title = "线索数据";
        List<Long> clueIds = new ArrayList<>();
        String[] headers = new String[]{"客户信息", "购车指标数", "建卡状态", "销售负责人", "线索获得时间", "线索来源", "意向车型"};
        for (BrokerClue brokerClue : list) {
            objs = new Object[headers.length];
            String cityInfo = "";
            if (brokerClue.getCity() != null) {
                cityInfo = brokerClue.getCity().getName() + " " + brokerClue.getProvince().getName();
            }
            objs[0] = brokerClue.getCustomer().getUserName() + " " + cityInfo;
            objs[1] = brokerClue.getCarPurchasingIndex();
            objs[2] = brokerClue.getCustomerId() > 0 ? "是" : "否";
            objs[3] = brokerClue.getBroker().getName();
            objs[4] = brokerClue.getCreateTime();
            //objs[5] =brokerClue.getType()==1?"话单":"网单" ;
            //线索来源 1 店铺线索，2 微店线索， 3问答线索，4头条线索 ， 5公共线索
            switch (brokerClue.getSource()) {
                case 1:
                    objs[5] = "店铺线索";
                    break;
                case 2:
                    objs[5] = "微店线索";
                    break;
                case 3:
                    objs[5] = "问答线索";
                    break;
                case 4:
                    objs[5] = "头条线索";
                    break;
                case 5:
                    objs[5] = "公共线索";
                    break;
                default:
                    objs[5] = "";
                    break;
            }
            objs[6] = brokerClue.getSeries().getName() + brokerClue.getCar().getName();
            dataList.add(objs);
            clueIds.add(brokerClue.getBrokerClueId());
        }
        ExcelVo excelVo = new ExcelVo();
        excelVo.setTitle(title);
        excelVo.setRowName(headers);
        excelVo.setDataList(dataList);
//        brokerClueService.handle(brokerTagetBrokerId, clueIds, BrokerClueHandleTypeEnum.Expert);
        return excelVo;
    }

    /**
     * 流失线索导出数据
     *
     * @param list
     * @param brokerTagetBrokerId
     * @return
     */
    public ExcelVo clueExcelPassed(List<BrokerClue> list, Long brokerTagetBrokerId) {
        List<Object[]> dataList = Lists.newArrayList();
        Object[] objs = null;
        String title = "线索数据";
        List<Long> clueIds = new ArrayList<>();
        String[] headers = new String[]{"客户信息", "商机信息", "销售负责人", "下单时间", "流失时间"};
        for (BrokerClue brokerClue : list) {
            objs = new Object[headers.length];
            objs[0] = brokerClue.getCustomer().getUserName();
            objs[1] = brokerClue.getSeries().getName() + brokerClue.getCar().getName();
            objs[2] = brokerClue.getBroker().getName();
            objs[3] = brokerClue.getCreateTime();
            objs[4] = brokerClue.getDeleteTime();
            dataList.add(objs);
            clueIds.add(brokerClue.getBrokerClueId());
        }
        ExcelVo excelVo = new ExcelVo();
        excelVo.setTitle(title);
        excelVo.setRowName(headers);
        excelVo.setDataList(dataList);
        brokerClueService.handle(brokerTagetBrokerId, clueIds, BrokerClueHandleTypeEnum.Expert);
        return excelVo;
    }

    /**
     * 认领线索（公共线索)
     *
     * @param brokerId
     * @param cluePoolId
     * @return
     */
    @RequestMapping(value = "/pickup", method = RequestMethod.GET)
    public Result pickup(
            @Check(value = "broker_id", required = false, defaultValue = "0") long brokerId,
            @Check(value = "clue_pool_id", required = false, defaultValue = "0") long cluePoolId
    ) {
        try {
            /*int ret = brokerClueService.pickup(cluePoolId, brokerId);
            if (ret > 0) {
                return result.format(ResultCode.Success, ret);
            }*/
            return brokerClueService.pickup(cluePoolId, brokerId);
        } catch (ResultException | IOException ex) {
            this.error("线索认领失败:" + cluePoolId + ":" + brokerId + ":" + ex.getMessage());
        }
        return result.format(ResultCode.BokerCluePickUpError);
    }

    /**
     * 获取手机号的历史线索
     *
     * @param mobile
     * @param dealerIds
     * @param limit
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/list/history/limit", method = RequestMethod.GET)
    public Result getList(
            @Check(value = "mobile", required = true, length = 11) String mobile,
            @Check(value = "dealer_ids", required = false, defaultValue = "0") @ExcludeZero List<Long> dealerIds,
            @Check(value = "limit", required = false, defaultValue = "20") int limit
    ) throws IOException {
        BrokerClueSearchParameters params = new BrokerClueSearchParameters();
        params.setDealerIds(dealerIds);
        params.setIsdelete(Short.valueOf("0"));
        params.setMobile(mobile);
        List<Long> ids = brokerClueService.searchListIds(params, 1, limit);
        List<BrokerClue> list = Lists.newArrayList();
        if (ids.isEmpty() == false) {
            list = brokerClueService.getClueList(ids);
        }
        return result.format(ResultCode.Success, list);
    }

    /**
     * 根据手机号批量查看历史线索
     *
     * @param mobileList
     * @param dealerId
     * @param page
     * @param limit
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/plist/history/batch", method = RequestMethod.GET)
    public Result getBatchClueHistoryList(
            @Check(value = "mobile", required = true) @ExcludeZero List<String> mobileList,
            @Check(value = "dealer_id", required = true) long dealerId,
            @Check(value = "broker_id", required = false,defaultValue = "0") long brokerId,
            @Check(value = "page", required = false, defaultValue = "1") int page,
            @Check(value = "limit", required = false, defaultValue = "100") int limit
    ) throws IOException {
        Map<String, List<BrokerClue>> data = Maps.newConcurrentMap();
        BrokerClueSearchParameters params = new BrokerClueSearchParameters();
        params.setDealerIds(Lists.newArrayList(dealerId));
        params.setIsdelete(Short.valueOf("0"));
        params.setIsHandled(Short.valueOf("-1"));
        params.setIsCustomer(Short.valueOf("-1"));
        params.setMobileList(mobileList);
        if(brokerId>0) {
            params.setBrokerIds(Lists.newArrayList(brokerId));
        }
        List<Long> ids = brokerClueService.searchListIds(params, page, limit);
        if (ids.size() > 0) {
             List<BrokerClue> list = brokerClueService.getClueList(ids);
            list.forEach(item -> {
                if (data.containsKey(item.getMobile())==false) {
                    data.put(item.getMobile(), Lists.newArrayList());
                }
                data.get(item.getMobile()).add(item);
            });
            //查询未处理线索id,并更新线索处理状态
            params.setIsHandled(0);
            List<Long> noHandleids = brokerClueService.searchListIds(params, page, limit);
            if (noHandleids != null && noHandleids.size() > 0) {
                brokerClueService.updateClueHandle(noHandleids);
                List<BrokerClue> noHandelList = brokerClueService.getBasicClueList(noHandleids);
                brokerClueEsService.add(noHandelList);
                List<Long> relDealerIdsList = new ArrayList<Long>();
                for (BrokerClue brokerClue : noHandelList) {
                    relDealerIdsList.add(brokerClue.getClueRefDealerId());
                }
                brokerClueService.sendClueHandle(dealerId, relDealerIdsList);
            }
        }
        return result.format(ResultCode.Success, data);
    }

    /**
     * 获取手机号的历史线索
     *
     * @param mobile
     * @param dealerIds
     * @param max
     * @param limit
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/list/history", method = RequestMethod.GET)
    public Result getList(
            @Check(value = "mobile", required = true, length = 11) String mobile,
            @Check(value = "dealer_ids", required = false, defaultValue = "0") @ExcludeZero List<Long> dealerIds,
            @Check(value = "broker_id", required = false,defaultValue = "0") long brokerId,
            @Check(value = "max", required = false, defaultValue = "0") String max,
            @Check(value = "limit", required = false, defaultValue = "20") int limit
    ) throws IOException {
        FlowPagedList<BrokerClue> flowPagedList = new FlowPagedList<>();
        BrokerClueSearchParameters params = new BrokerClueSearchParameters();
        params.setDealerIds(dealerIds);
        params.setIsdelete(Short.valueOf("0"));
        params.setMobile(mobile);
        params.setIsHandled(Short.valueOf("-1"));
        if(brokerId>0) {
            params.setBrokerIds(Lists.newArrayList(brokerId));
        }
        List<Long> ids = brokerClueService.searchListIds(params, max, limit);
        List<BrokerClue> list = Lists.newArrayList();
        flowPagedList.setLimit(limit);
        if (ids.size() > 0) {
            list = brokerClueService.getClueList(ids);
            int total = brokerClueService.searchListCount(params, String.valueOf(0));
            int pagedCount = brokerClueService.searchListCount(params, max);
            flowPagedList.setCount(total);
            flowPagedList.setHas_more(pagedCount > limit ? 1 : 0);
            flowPagedList.setNext_max(list.get(list.size() - 1).getSort());
            //查询未处理线索id,并更新线索处理状态
            params.setIsHandled(0);
            List<Long> noHandleids = brokerClueService.searchListIds(params, max, limit);
            if (noHandleids != null && noHandleids.size() > 0) {
                brokerClueService.updateClueHandle(noHandleids);
                List<BrokerClue> noHandelList = brokerClueService.getBasicClueList(noHandleids);
                brokerClueEsService.add(noHandelList);
                List<Long> relDealerIdsList = new ArrayList<Long>();
                for (BrokerClue brokerClue : noHandelList) {
                    relDealerIdsList.add(brokerClue.getClueRefDealerId());
                }
                brokerClueService.sendClueHandle(dealerIds.get(0), relDealerIdsList);
//            	brokerClueService.handleList(dealerIds.get(0), relDealerIdsList, BrokerClueHandleTypeEnum.ClueDetail);
            }
        }
        flowPagedList.setList(list);
        return result.format(ResultCode.Success, flowPagedList);
    }

    /**
     * 获取手机号的历史线索
     *
     * @param mobile
     * @param dealerIds
     * @param page
     * @param limit
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/plist/history", method = RequestMethod.GET)
    public Result getList(
            @Check(value = "mobile", required = true, length = 11) String mobile,
            @Check(value = "dealer_ids", required = false, defaultValue = "0") @ExcludeZero List<Long> dealerIds,
            @Check(value = "broker_id", required = false,defaultValue = "0") long brokerId,
            @Check(value = "page", required = false, defaultValue = "1") int page,
            @Check(value = "limit", required = false, defaultValue = "20") int limit
    ) throws IOException {
        PagedList<BrokerClue> pagedList = new PagedList<>();
        BrokerClueSearchParameters params = new BrokerClueSearchParameters();
        params.setDealerIds(dealerIds);
        params.setIsdelete(Short.valueOf("0"));
        params.setIsHandled(Short.valueOf("-1"));
        params.setIsCustomer(Short.valueOf("-1"));
        params.setMobile(mobile);
        if(brokerId>0) {
            params.setBrokerIds(Lists.newArrayList(brokerId));
        }
        List<Long> ids = brokerClueService.searchListIds(params, page, limit);
        List<BrokerClue> list = Lists.newArrayList();
        pagedList.setLimit(limit);
        if (ids != null && ids.size() > 0) {
            list = brokerClueService.getClueList(ids);
            int total = brokerClueService.searchListCount(params, String.valueOf(0));
            pagedList.setCount(total);

            //查询未处理线索id,并更新线索处理状态
            params.setIsHandled(0);
            List<Long> noHandleids = brokerClueService.searchListIds(params, page, limit);
            if (noHandleids != null && noHandleids.size() > 0) {
                brokerClueService.updateClueHandle(noHandleids);
                List<BrokerClue> noHandelList = brokerClueService.getBasicClueList(noHandleids);
                brokerClueEsService.add(noHandelList);
                List<Long> relDealerIdsList = new ArrayList<Long>();
                for (BrokerClue brokerClue : noHandelList) {
                    relDealerIdsList.add(brokerClue.getClueRefDealerId());
                }
                brokerClueService.sendClueHandle(dealerIds.get(0), relDealerIdsList);
//            	brokerClueService.handleList(dealerIds.get(0), relDealerIdsList, BrokerClueHandleTypeEnum.ClueDetail);
            }
        }
        pagedList.setPage(page);
        pagedList.setList(list);
        return result.format(ResultCode.Success, pagedList);
    }

    /**
     * 来电话单
     *
     * @param brokerIds
     * @param dealerIds
     * @param isCustomer
     * @param seriseId
     * @param carId
     * @param cityId
     * @param provinceId
     * @param username
     * @param limit
     * @param callerPhoneNumber
     * @param calleeRealNumber
     * @param callstatus
     * @param callbegintime
     * @param callendtime
     * @param mobile
     * @param page
     * @param export
     * @param response
     * @return
     * @throws com.xyauto.interact.broker.server.exceptions.ResultException
     * @throws java.io.IOException
     */
    @RequestMapping(value = "/list/call", method = RequestMethod.GET)
    public Result getList(
            @Check(value = "broker_id", required = false, defaultValue = "0") @ExcludeZero List<Long> brokerIds,
            @Check(value = "dealer_id", required = false, defaultValue = "0") @ExcludeZero List<Long> dealerIds,
            @Check(value = "is_customer", required = false, defaultValue = "-1") short isCustomer,
            @Check(value = "series_id", required = false, defaultValue = "0") int seriseId,
            @Check(value = "car_id", required = false, defaultValue = "0") int carId,
            @Check(value = "city_id", required = false, defaultValue = "0") int cityId,
            @Check(value = "province_id", required = false, defaultValue = "0") int provinceId,
            @Check(value = "mobile", required = false, defaultValue = "") String mobile,
            @Check(value = "username", required = false, defaultValue = "") String username,
            @Check(value = "caller_phone_number", required = false, defaultValue = "") String callerPhoneNumber,
            @Check(value = "callee_real_number", required = false, defaultValue = "") String calleeRealNumber,
            @Check(value = "callstatus_name", required = false, defaultValue = "0") int callstatus,
            @Check(value = "callbegintime", required = false, defaultValue = "0") long callbegintime,
            @Check(value = "callendtime", required = false, defaultValue = "0") long callendtime,
            @Check(value = "limit", required = false, defaultValue = "20") int limit,
            @Check(value = "page", required = false, defaultValue = "1") int page,
            @Check(value = "export", required = false, defaultValue = "0") int export,
            HttpServletResponse response
    ) throws ResultException, IOException {
        dealerIds = ListUtil.clearZero(dealerIds);
        brokerIds = ListUtil.clearZero(brokerIds);
        BrokerClueSearchParameters params = new BrokerClueSearchParameters();
        params.setBrokerIds(brokerIds);
        params.setCarId(carId);
        params.setCityId(cityId);
        params.setIsCustomer(isCustomer);
        params.setDealerIds(dealerIds);
        params.setMobile(mobile);
        params.setProvinceId(provinceId);
        params.setSeriesId(seriseId);
        params.setUsername(username);
        params.setCallerPhoneNumber(callerPhoneNumber);
        params.setCalleeRealNumber(calleeRealNumber);
        params.setCallstatus(callstatus);
        params.setBegin(callbegintime);
        params.setEnd(callendtime);
        params.setType(Short.valueOf("1")); //话单设置
        params.setIsdelete(Short.valueOf("0"));
        params.setIsHandled(Short.valueOf("-1"));

        PagedList pageList = new PagedList();
        if (export == 1) {
            limit = 0;
            page = 0;
        }
        params.setLimit(limit);
        params.setOffset((page - 1) * limit);
        List<Long> ids = brokerClueService.searchListIds(params, page, limit);
        int total = brokerClueService.searchListCount(params, "0");;
        List<BrokerClue> list = Lists.newArrayList();
        if (ids.size() > 0) {
            //ids = ids.subList((page-1)*limit, page*limit>total?limit:total);
            list = brokerClueService.getClueList(ids);
        }
        if (export == 1) {
            //导出
            List<Object[]> dataList = new ArrayList<Object[]>();
            Object[] objs = null;
            String title = "来电话单";
            String excelName = "来电话单.xls";
            String[] headers = new String[]{"客户信息", "被叫号", "建卡状态", "通话时长", "等待时长", "来电状态", "来电时间"};
            for (BrokerClue brokerClue : list) {
                objs = new Object[headers.length];
                BrokerClueByCall brokerClueByCall = brokerClue.getCallInfo();
                objs[0] = brokerClueByCall.getCallerphonenumber() + brokerClueByCall.getCallerlocationname();
                objs[1] = brokerClueByCall.getCalleerealnumber() + (brokerClueByCall.getCallerBroker() == null ? "" : brokerClueByCall.getCallerBroker().getName());
                objs[2] = brokerClue.getCustomerId() > 0 ? "是" : "否";
                objs[3] = brokerClueByCall.getCalleedurationdescription();
                objs[4] = brokerClueByCall.getCallwaittimedesc();
                objs[5] = brokerClueByCall.getCallstatusname().equals("CANCEL") ? "已接通" : "未接通";
                objs[6] = DateUtil.DateToString(new Date(brokerClueByCall.getCallbegintime()), DateStyle.YYYY_MM_DD_HH_MM_SS);
                dataList.add(objs);
            }
            ExcelVo excelVo = new ExcelVo();
            excelVo.setTitle(title);
            excelVo.setRowName(headers);
            excelVo.setDataList(dataList);
            List<ExcelVo> excelList = new ArrayList<ExcelVo>();
            excelList.add(excelVo);
            ExcelUtils.exportExcel(excelName, excelList, response);
            return result.format(ResultCode.Success, "导出来电话单信息");
        }
        pageList.setList(list==null ? Lists.newArrayList():list);
        pageList.setCount(total);
        pageList.setPage(page);
        pageList.setLimit(limit);
        return result.format(ResultCode.Success, pageList);
    }

    /**
     * 手工分配线索
     *
     * @param brokerId
     * @param brokerClueIds
     * @param targetBrokerId
     * @return
     */
    @RequestMapping(value = "/distribute", method = RequestMethod.POST)
    public Result distributeClue(
            @Check(value = "broker_id", required = true) long brokerId,
            @Check(value = "target_broker_id", required = true) long targetBrokerId,
            @Check(value = "broker_clue_id", required = true) List<Long> brokerClueIds
    ) {
        Broker targetBroker = brokerService.get(targetBrokerId, true);
        Broker broker = brokerService.get(brokerId, true);
        //验证被分配经纪人信息
        if (broker == null || targetBroker == null) {
            return result.format(ResultCode.BrokerNotFound);
        }
        //销售顾问无分配权限
        if (targetBroker.getType() == BrokerTypeEnum.Employee.getValue()) {
            return result.format(ResultCode.NoPermission);
        }

        //非同店经纪人不能进行分配   只能分配个同店铺的 销售经理 和销售专员
        if (broker.getDealerId() != targetBroker.getDealerId()) {
            return result.format(ResultCode.NoPermission);
        }

        if (!((broker.getType() == BrokerTypeEnum.Manager.getValue() || broker.getType() == BrokerTypeEnum.Employee.getValue()))) {
            return result.format(ResultCode.NoPermission);
        }
        //检查线索是否属于经销商
        List<BrokerClue> belongTo = brokerClueService.getBrokerClueByDealerIdAndClueIds(broker.getDealerId(), brokerClueIds);
        if (belongTo == null || belongTo.size() == 0 || belongTo.size() != brokerClueIds.size()) {
            return result.format(ResultCode.NoPermission);
        }

        int ret = brokerClueService.updateAllotList(brokerId, targetBrokerId, belongTo);
        if (ret > 0) {
            //线索被处理
            brokerClueIds.forEach(item -> {
                try {
                    brokerClueService.handle(brokerId, item, BrokerClueHandleTypeEnum.Other);
                } catch (ResultException | IOException ex) {
                    this.error("线索处理失败:" + ex.getMessage());
                }
            });
            return result.format(ResultCode.Success);
        }
        return result.format(ResultCode.BrokerClueDistributeError);

    }

    /**
     * 处理线索
     *
     * @param brokerId 经纪人id
     * @param brokerClueId 经纪人线索id
     * @param type 1电话，2短信
     * @return
     * @throws com.xyauto.interact.broker.server.exceptions.ResultException
     * @throws java.io.IOException
     */
    @RequestMapping(value = "/handle", method = RequestMethod.GET)
    public Result contact(
            @Check(value = "broker_id", required = true) long brokerId,
            @Check(value = "broker_clue_id", required = true) List<Long> brokerClueId,
            @Check(value = "type", required = true, pattern = "[01245]") int type
    ) throws ResultException, IOException {
        int ret = brokerClueService.handle(brokerId, brokerClueId, BrokerClueHandleTypeEnum.valueOf(type));
        if (ret > 0) {
            return result.format(ResultCode.Success);
        }
        return result.format(ResultCode.BrokerClueHandleError);
    }

    /**
     * 获取线索类型数量
     *
     * @param targetBrokerId
     * @param dealerId
     * @return
     * @throws ResultException
     * @throws IOException
     */
    @RequestMapping(value = "/getcluetypecount", method = RequestMethod.GET)
    public Result getClueTypeCount(
            @Check(value = "target_broker_id", required = true) long targetBrokerId,
            @Check(value = "dealer_id", required = true) long dealerId
    ) throws ResultException, IOException {
        Broker broker = brokerService.get(targetBrokerId, true);
        if (broker == null) {
            return result.format(ResultCode.TargetBrokerNotFound);
        }
        //检查当前登录人查看的经销商是否是同一个
        if (broker.getDealerId() != dealerId) {
            return result.format(ResultCode.NoPermission);
        }
        BrokerClueSearchParameters params = new BrokerClueSearchParameters();
        params.setIsHandled(Short.valueOf("-1"));
        params.setIsCustomer(Short.valueOf("-1"));
        if (broker.getType() == BrokerTypeEnum.Employee.getValue()) {
            params.setBrokerIds(Arrays.asList(broker.getBrokerId()));
        }
        params.setDealerIds(Arrays.asList(dealerId));
        Map<String, Integer> model = new HashMap<>();
        //需求调整，暂时不动返回结构
        int total = brokerClueService.searchGroupByMobileTotal(params);
        //全部线索
        model.put("all", total);
        //待处理线索
        params.setIsHandled(0);
        total = brokerClueService.searchGroupByMobileTotal(params);
        model.put("handle", total);
        //流失线索
        params.setIsHandled(-1);
        params.setIsdelete(1);
        total = brokerClueService.searchGroupByMobileTotal(params);
        model.put("delete", total);
        return result.format(ResultCode.Success, model);
    }
}
