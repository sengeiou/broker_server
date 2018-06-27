package com.xyauto.interact.broker.server.cluemq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.rabbitmq.client.Channel;
import com.xyauto.interact.broker.server.enums.BrokerLogEnum;
import com.xyauto.interact.broker.server.enums.PushMessage;
import com.xyauto.interact.broker.server.exceptions.ResultException;
import com.xyauto.interact.broker.server.model.vo.Broker;
import com.xyauto.interact.broker.server.model.vo.BrokerClue;
import com.xyauto.interact.broker.server.model.vo.BrokerCustomer;
import com.xyauto.interact.broker.server.model.vo.Car;
import com.xyauto.interact.broker.server.model.vo.DealerClueDistributeSetting;
import com.xyauto.interact.broker.server.service.BrokerClueService;
import com.xyauto.interact.broker.server.service.BrokerCustomerService;
import com.xyauto.interact.broker.server.service.BrokerLogService;
import com.xyauto.interact.broker.server.service.BrokerService;
import com.xyauto.interact.broker.server.service.CarService;
import com.xyauto.interact.broker.server.service.DealerClueDistributeSettingService;
import com.xyauto.interact.broker.server.service.cloud.ApiServiceFactory;
import com.xyauto.interact.broker.server.service.es.clue.BrokerClueEsService;
import com.xyauto.interact.broker.server.util.ILogger;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 线索同步导入处理
 *
 * @author joe
 */
@Component
public class ClueSyncAdd implements ILogger {

    @Autowired
    BrokerClueService brokerClueService;

    @Autowired
    BrokerClueEsService brokerClueEsService;

    @Autowired
    ClueLog log;

    @Autowired
    BrokerLogService brokerLogService;

    @Autowired
    ApiServiceFactory apiService;

    @Autowired
    BrokerService brokerService;

    @Autowired
    BrokerCustomerService brokerCustomerService;

    @Autowired
    DealerClueDistributeSettingService distributeSettingServiced;

    @Autowired
    CarService carService;
    
    static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(1000);

    @RabbitListener(queues = "qcdq-bussiness-syn-data-queue")
    public void add(Message message, Channel channel)
            throws UnsupportedEncodingException, IOException, ResultException {
        fixedThreadPool.execute(() -> {
            try {
                JSONObject jsonObj = JSON.parseObject(new String(message.getBody(),
                        "utf-8"));
                ClueEntity entity = JSON.parseObject(jsonObj.toJSONString(),
                        ClueEntity.class);
                if (entity == null || entity.getId() <= 0) {
                    this.error("线索信息异常,队列商机id不合法：" + JSON.toJSONString(jsonObj.toJSONString()));
                    return;
                }
                this.info("进入线索处理:" + entity.getId());
                log.log(new ClueMessageEntity(entity.getId(), "【线索分配】线索进入分配流转，服务器("
                        + InetAddress.getLocalHost() + ")【成功】"));
                entity.setExtension(jsonObj.toJSONString());
                entity.setOrderTime(0);
                entity.setType(2);
                entity.setSourceId(this.sourceAdapte(entity.getSourceId()));
                // 查询车型id
                Car car = carService.getCar(entity.getCarId());
                if (car != null) {
                    entity.setSerialId(car.getSeriesId());
                }
                // 线索存储
                long ret = this.saveClue(entity);
                if (ret == 0) {
                    // 线索重复
                    log.log(new ClueMessageEntity(entity.getId(), "【线索分配】线索分配重复流转【失败】"));
                    return;
                }
                // 线索分配
                if (entity.getDistributedAdviserId() > 0) {
                    setBrokerClue(entity);
                } else {
                    setDealerClue(entity);
                }
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
                log.log(new ClueMessageEntity(entity.getId(), "【线索分配】线索分配流转结束【成功】"));
            } catch (IOException | ResultException ex) {
                this.info("线索分配处理异常:" + ex.getMessage());
            }
        });
    }

    @RabbitListener(queues = "qcdq-bussiness-syn-data-expire-data-queue")
    public void miss(Message message, Channel channel)
            throws UnsupportedEncodingException, IOException {
        fixedThreadPool.execute(() -> {
            try {
                JSONObject jsonObj = JSON.parseObject(new String(message.getBody(),
                        "utf-8"));
                ClueEntity entity = JSON.parseObject(jsonObj.toJSONString(),
                        ClueEntity.class);
                this.info("进入线索流失处理:" + entity.getId());
                log.log(new ClueMessageEntity(entity.getId(), "【线索分配】线索进入分配流失，服务器("
                        + InetAddress.getLocalHost() + ")【成功】"));
                long clueId = entity.getId();
                long dealerId = entity.getDealerId();
                brokerClueService.miss(clueId, dealerId);
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            } catch (IOException e) {
                this.error("处理线索流失失败:" + e.getMessage());
            }
        });
    }

    /**
     * 存储网单线索
     *
     * @param entity
     * @return
     */
    private long saveClue(ClueEntity entity) throws IOException,
            ResultException {
        if (brokerClueService.duplicateCheck(entity.getId(), entity.getType(),
                entity.getDealerId()) > 0) {
            return 0;
        }
        // 验证当前线索是否存在客户（经销商）
        BrokerCustomer brokerCustomer = brokerCustomerService.existsCustomer(
                entity.getCustomerContact(), entity.getDealerId());
        if (brokerCustomer != null) {
            // 设置线索绑定客户
            entity.setBrokerCustomerId(brokerCustomer.getBrokerCustomerId());
        }
        long brokerClueId = brokerClueService.addNetClue(entity);
        if (brokerClueId > 0) {
            brokerClueId = entity.getBrokerClueId();
            // 更新索引
            BrokerClue brokerClue = brokerClueService.get(entity
                    .getBrokerClueId());
            brokerClueEsService.add(Lists.newArrayList(brokerClue));
            log.log(new ClueMessageEntity(entity.getId(),
                    "【线索分配】线索分配流转进入待分配【成功】"));
        }
        return brokerClueId;
    }

    /**
     * 设置经纪人线索(分配到个人)
     */
    private void setBrokerClue(ClueEntity entity) throws IOException,
            ResultException {
        log.log(new ClueMessageEntity(entity.getId(), "【线索分配】线索分配流转进入个人分配【成功】"));
        // 检查经销商设置
        DealerClueDistributeSetting setting = distributeSettingServiced
                .getDistribution(entity.getDealerId());
        if (setting.getBrokerDistribute() == 0) {
            log.log(new ClueMessageEntity(entity.getId(),
                    "【线索分配】经销商设置了个人不可自动分配，进入经销商自动分配模式【成功】"));
            this.setDealerClue(entity);
            return;
        }
        brokerClueService.allot(entity.getDistributedAdviserId(),
                entity.getBrokerClueId());
        // 更新索引
        BrokerClue brokerClue = brokerClueService.get(entity.getBrokerClueId(),
                false);
        brokerClueEsService.add(Lists.newArrayList(brokerClue));
        log.log(new ClueMessageEntity(entity.getId(), "【线索分配】线索分配流到经纪人"
                + entity.getDistributedAdviserId() + "【成功】"));
        this.info("线索分配进入个人分配:" + entity.getDistributedAdviserId());
        // 推送push
        Broker broker = brokerService.get(brokerClue.getBrokerId());
        apiService.pushService().push(broker.getToken(),
                PushMessage.NewClue.getValue(), new HashMap<String, String>() {
            {
                put("url", PushMessage.NewClue.getLink());
            }
        });
        // 添加总线索分配日志
        brokerLogService.log(brokerClue.getBrokerId(), brokerClue.getDealerId(),
                        brokerClue.getBrokerClueId(),
                        BrokerLogEnum.BrokerCluePersonAdd, brokerClue,
                        broker.getName());
    }

    /**
     * 设置经销商线索(分配到店)
     *
     * @param entity
     * @throws java.io.IOException
     * @throws com.xyauto.interact.broker.server.exceptions.ResultException
     */
    public void setDealerClue(ClueEntity entity) throws IOException,
            ResultException {
        log.log(new ClueMessageEntity(entity.getId(), "【线索分配】线索分配流转进入店铺分配【成功】"));
        // 检查经销商设置
        DealerClueDistributeSetting setting = distributeSettingServiced
                .getDistribution(entity.getDealerId());
        if (setting.getDealerDistribute() == 0) {
            log.log(new ClueMessageEntity(entity.getId(), "【线索分配】经销商设置了不可自动分配，进入手动分配模式【成功】"));
            return;
        }
        List<Long> autoDistributeBrokerList = Lists.newArrayList();
        for (Object broker : setting.getBrokers()) {
            JSONObject jsonBroker = (JSONObject) broker;
            if (jsonBroker.getIntValue("distribute") == 1) {
                autoDistributeBrokerList.add(jsonBroker.getLong("brokerId"));
            }
        }
        if (autoDistributeBrokerList.isEmpty()) {
            log.log(new ClueMessageEntity(entity.getId(), "【线索分配】经销商设置可自动分配经纪人为空，进入手动分配模式【成功】"));
            return;
        }
        long brokerId = 0;
        // 判定是否已有建卡客户，如果客户所属经纪人在自动分配范围内，就直落数据到该经纪人下
        BrokerCustomer brokerCustomer = brokerCustomerService.existsCustomer(entity.getCustomerContact(), entity.getDealerId());
        if (brokerCustomer != null && autoDistributeBrokerList.contains(brokerCustomer.getBrokerId())) {
            // 设置线索绑定客户
            brokerId = brokerCustomer.getBrokerId();
        } else {
            // 判定同号码线索之前是否已经分配到人，并且该经纪人在自动分配范围内，就直落数据到该经纪人下
            long lastAllotBrokerId = brokerClueService.getLastAllotBrokerId(entity.getCustomerContact(), entity.getDealerId());
            if (lastAllotBrokerId > 0 && autoDistributeBrokerList.contains(lastAllotBrokerId)) {
                brokerId = lastAllotBrokerId;
            } else {
                // 采用平均分配原则，谁当前线索少分配给谁
                brokerId = brokerClueService.getNextAllottingBrokerId(entity.getDealerId(), autoDistributeBrokerList);
            }
        }
        if (brokerId == 0) {
            // todo:无法分配,进行回执
            log.log(new ClueMessageEntity(entity.getId(), "【线索分配】线索分配流到经纪人"
                    + brokerId + "失败,原因:未找到可分配经纪人【失败】"));
            return;
        }
        brokerClueService.allot(brokerId, entity.getBrokerClueId());
        // 更新索引
        BrokerClue brokerClue = brokerClueService.get(entity.getBrokerClueId(),
                false);
        if (brokerClue == null) {
            return;
        }
        brokerClueEsService.add(Lists.newArrayList(brokerClue));
        log.log(new ClueMessageEntity(entity.getId(), "【线索分配】线索分配流到经纪人"
                + brokerId + "【成功】"));
        this.info("线索分配进入店铺自动分配:" + brokerId);
        // 推送push
        Broker broker = brokerService.get(brokerClue.getBrokerId());
        apiService.pushService().push(broker.getToken(),
                PushMessage.NewClue.getValue(), new HashMap<String, String>() {
            {
                put("url", PushMessage.NewClue.getLink());
            }
        });
        // 添加总线索分配日志
        brokerLogService.log(brokerClue.getBrokerId(), brokerClue.getDealerId(),
                        brokerClue.getBrokerClueId(),
                        BrokerLogEnum.BrokerClueDealerAdd, brokerClue,
                        broker.getName());
    }

    /**
     * 线索落店进行来源适配
     *
     * @param source
     * @return
     */
    private int sourceAdapte(long source) {
        if (source == 8) {
            // 适配为微店线索
            return 2;
        }
        if (source == 28) {
            // 适配为头条线索
            return 4;
        }
        // 默认都为店铺线索
        return 1;
    }
}
