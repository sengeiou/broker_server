package com.xyauto.interact.broker.server.cluemq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.xyauto.interact.broker.server.exceptions.ResultException;
import com.xyauto.interact.broker.server.model.po.BrokerLogEntity;
import com.xyauto.interact.broker.server.model.vo.BrokerClue;
import com.xyauto.interact.broker.server.model.vo.BrokerCustomer;
import com.xyauto.interact.broker.server.model.vo.Car;
import com.xyauto.interact.broker.server.service.*;
import com.xyauto.interact.broker.server.service.es.clue.BrokerClueEsService;
import com.xyauto.interact.broker.server.util.ILogger;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 话单线索同步导入处理
 *
 * @author joe
 */
@Component
public class CallClueSyncAdd implements ILogger {

    @Autowired
    BrokerClueService brokerClueService;

    @Autowired
    BrokerClueEsService brokerClueEsService;

    @Autowired
    ClueLog log;

    @Autowired
    BrokerLogService brokerLogService;

    @Autowired
    CarService carService;

    @Autowired
    BrokerCustomerService brokerCustomerService;

    @Autowired
    BrokerService brokerService;

    @RabbitListener(queues = "qcdq-bussiness-syn-callorder-queue")
    public void process(Message message) throws UnsupportedEncodingException, IOException, ResultException {
        JSONObject jsonObj = JSON.parseObject(new String(message.getBody(), "utf-8"));
        CallClueEntity callEntity = JSON.parseObject(jsonObj.toJSONString(), CallClueEntity.class);
        this.info("处理话单线索:"+callEntity.getCalllogid());
        ClueEntity entity = new ClueEntity();
        entity.setType(1);
        if (callEntity.getCarid() > 0) {
            entity.setCarId(callEntity.getCarid());
            Car car = carService.getCar(callEntity.getCarid());
            if (car != null) {
                entity.setBrandId(car.getBrandId());
                entity.setSubBrandId(car.getSubBrandId());
                entity.setSerialId(car.getSeriesId());
            }
        }
        entity.setOrderTime(callEntity.getCallbegintime().getTime()/1000);
        if (callEntity.getDealerid() > 0) {
            entity.setDealerId(callEntity.getDealerid());
        }
        entity.setId(callEntity.getCalllogid());
        if (callEntity.getSourceid() > 0) {
            entity.setSourceId(callEntity.getSourceid());
        }
        entity.setCall(callEntity);
        entity.setExtension(jsonObj.toJSONString());
        if (callEntity.getUsertypeid() == 1) {
            entity.setDistributedAdviserId(callEntity.getUserid());
        }
        if (callEntity.getUsertypeid() == 4) {
            entity.setDealerId(callEntity.getUserid());
        }
        if(callEntity.getCallerphonenumber()!=null&&callEntity.getCallerphonenumber().length()==11 ){
            try {
                com.xyauto.interact.broker.server.model.vo.Broker broker = brokerService.getBrokerByMobile(entity.getDealerId(), callEntity.getCallerphonenumber());
                if (broker != null) {
                    entity.setBrokerId(broker.getBrokerId());
                }
            }catch (Exception e){}
        }
        //线索存储
        long ret = this.saveClue(entity);
        if (ret == 0) {
            //线索重复
            return;
        }
    }

    /**
     * 存储话单线索
     *
     * @param entity
     * @return
     */
    private long saveClue(ClueEntity entity) throws IOException, ResultException {
        if (brokerClueService.duplicateCheck(entity.getId(), entity.getType(), entity.getDealerId()) > 0) {
            return 0;
        }
        //验证当前线索是否存在客户（经销商）
        BrokerCustomer brokerCustomer = brokerCustomerService.existsCustomer(entity.getCustomerContact(),entity.getDealerId());
        if(brokerCustomer!=null){
             entity.setBrokerCustomerId(brokerCustomer.getBrokerCustomerId());
        }

        //新增线索
        long brokerClueId = brokerClueService.addNetClue(entity);
        //更新索引
        BrokerClue brokerClue = brokerClueService.get(entity.getBrokerClueId());
        brokerClueEsService.add(Lists.newArrayList(brokerClue));
        //添加总线索分配日志
        JSONObject extra = new JSONObject();
        extra.put("series_id", brokerClue.getSeriesId());
        extra.put("brand_id", brokerClue.getBrandId());
        extra.put("car_id", brokerClue.getCarId());
        extra.put("phone", brokerClue.getMobile());
        extra.put("source", brokerClue.getSource());
        extra.put("city_id", brokerClue.getCityId());
        extra.put("province_id", brokerClue.getProvinceId());
        BrokerLogEntity logEntity = new BrokerLogEntity("broker_clue_add", brokerClue.getBrokerId(), brokerClue.getDealerId(), brokerClue.getClueId(), brokerClue.getClueId() + "线索分配到经纪人" + brokerClue.getBrokerId(), extra);
        logEntity.setCreateTime(Long.valueOf(entity.getOrderTime().toString()));
        brokerLogService.log(logEntity);
        if (brokerClue.getBrokerId() > 0) {
            //添加个人话单分配日志
            logEntity = new BrokerLogEntity("broker_clue_call_person_add", brokerClue.getBrokerId(), brokerClue.getDealerId(), brokerClue.getClueId(), brokerClue.getClueId() + "线索分配到经纪人" + brokerClue.getBrokerId(), extra);
            logEntity.setCreateTime(Long.valueOf(entity.getOrderTime().toString()));
            brokerLogService.log(logEntity);
        }
        if (brokerClue.getBrokerId() == 0 && brokerClue.getDealerId() > 0) {
            //添加店铺话单分配日志
            logEntity = new BrokerLogEntity("broker_clue_call_dealer_add", brokerClue.getBrokerId(), brokerClue.getDealerId(), brokerClue.getClueId(), brokerClue.getClueId() + "线索分配到经纪人" + brokerClue.getBrokerId(), extra);
            logEntity.setCreateTime(Long.valueOf(entity.getOrderTime().toString()));
            brokerLogService.log(logEntity);
        }
        return brokerClueId;
    }
}
