package com.xyauto.interact.broker.server.service;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

import com.xyauto.interact.broker.server.dao.proxy.BrokerTemplateDaoProxy;
import com.xyauto.interact.broker.server.enums.BrokerTemplateTypeEnum;
import com.xyauto.interact.broker.server.enums.BrokerTypeEnum;
import com.xyauto.interact.broker.server.enums.ResultCode;

import com.xyauto.interact.broker.server.exceptions.ResultException;
import com.xyauto.interact.broker.server.model.po.BrokerTemplatePersistant;
import com.xyauto.interact.broker.server.model.vo.Brand;
import com.xyauto.interact.broker.server.model.vo.Broker;
import com.xyauto.interact.broker.server.model.vo.BrokerTemplate;
import com.xyauto.interact.broker.server.model.vo.Dealer;
import com.xyauto.interact.broker.server.util.ILogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BrokerTemplateService implements ILogger {

    @Autowired
    BrokerTemplateDaoProxy dao;

    @Autowired
    DealerService dealerService;

    @Autowired
    BrokerService brokerService;

    @Autowired
    BrandService brandService;

    public List<Integer> getListIds(long brokerId, String max, int limit) {
        return dao.getListIds(brokerId, max, limit);
    }

    public List<Integer> getListIds(long brokerId, int page, int limit) {
        return dao.getListIdsByPage(brokerId, page, limit);
    }

    public BrokerTemplate GetModelByTemplateId(long templateId) {
        return dao.GetModelByTemplateId(templateId);
    }

    public List<BrokerTemplate> getList(List<Long> ids) {
        return dao.getListByIds(ids);
    }

    /**
     * 根据id集合取实体集合
     */
    public List<BrokerTemplate> getTemplateListByIds(List<Long> ids, long brokerId) {
        List<BrokerTemplate> brokerTemplates = dao.getTemplateListByIds(ids);
        return replaceManagerType(brokerTemplates, brokerId);
    }

    public Integer delete(BrokerTemplatePersistant temp) throws Exception {
        //逻辑删除，非物理删除，跟更新走同一个。
        BrokerTemplate template = dao.GetModelByTemplateId(temp.getBrokerTemplateId());
        long count = dao.getCountByType(temp.getBrokerId(), template.getType());
        if (template.getType() == BrokerTemplateTypeEnum.TalkMessage.getValue() && count == 1) {
            throw new ResultException(ResultCode.NoMoreTemplate);
        }
        int suc = dao.update(temp);
        if (suc > 0 && template.getIsDefault() == Short.valueOf("1")) {
            setDefaultByType(template.getBrokerId(), template.getType());
        }
        return suc;
    }

    /**
     * 删除后设置默认模板
     *
     * @param brokerId
     * @param type
     * @return
     */
    public Integer setDefaultByType(long brokerId, short type) {
        ResetDefaultByType(type, brokerId);
        return dao.setDefaultByType(brokerId, type);
    }

    public Integer update(BrokerTemplatePersistant temp) throws Exception {
        //type 1短信 2微聊  后期用枚举替换
        Short type = temp.getType();
        //短信
        if (type == 1) {
            //系统短信不可以删除和更改
            BrokerTemplate model = dao.GetModelByTemplateId(temp.getBrokerTemplateId()); //dao.getLockedId(temp.getBrokerId(), type);
            if (model.getIsLocked() == 1) {
                throw new ResultException(ResultCode.NoUpdateTemplate);
            } else {
                return dao.update(temp);
            }
        } //微聊
        else if (type == 2) {
            //删除操作
            if (temp.getIsDeleted() == 1) {
                //微聊只有1条的时候不允许删除
                long count = dao.getCountByType(temp.getBrokerId(), type);
                if (count <= 1) {
                    throw new ResultException(ResultCode.NoMoreTemplate);
                }
                return dao.update(temp);

            } else {
                //更新
                return dao.update(temp);
            }
        }
        return 0;
    }

    /**
     * 根据类型获取模板数据
     *
     * @param types
     * @param brokerId
     * @return
     */
    public List<BrokerTemplate> GetListByTypes(List<Short> types, long brokerId) {
        List<BrokerTemplate> list = dao.GetListByTypes(types, brokerId);
        Boolean isAdd = false;
        if (list == null || list.isEmpty()) {
            list = Lists.newArrayList();
            isAdd = true;
        }
        if (isAdd && types.contains(BrokerTemplateTypeEnum.SMSMessage.getValue())) {
            //创建短信模板
            list.addAll(initMSM(brokerId));
        }
        if (isAdd && types.contains(BrokerTemplateTypeEnum.TalkMessage.getValue())) {
            //创建微聊模板
            BrokerTemplate talkTemplate = initTalkMessage(brokerId);
            if (talkTemplate != null) {
                list.add(talkTemplate);
            }
        }
        Collections.sort(list, (BrokerTemplate o1, BrokerTemplate o2) -> {
            if (o1.getIsDefault() > o2.getIsDefault()) {
                return -1;
            }
            if (o1.getCreateTime().getTime() > o2.getCreateTime().getTime()) {
                return -1;
            }
            return 1;
        });
        if (types.contains(BrokerTemplateTypeEnum.SMSMessage.getValue())) {
            list = replaceManagerType(list, brokerId);
        }
        return list;
    }

    /**
     * 替换短信模板中默认职位名称
     *
     * @param templateList
     * @param brokerId
     * @return
     */
    public List<BrokerTemplate> replaceManagerType(List<BrokerTemplate> templateList, long brokerId) {
        //初始化默认模板职位
        Broker broker = brokerService.getSingle(brokerId);
        if (broker == null) {
            return templateList;
        }
        for (BrokerTemplate t : templateList) {
            if (t.getType() == BrokerTemplateTypeEnum.SMSMessage.getValue() && t.getIsLocked() == 1) {
                String managerType = broker.getType() == BrokerTypeEnum.Manager.getValue() ? "销售经理" : "销售顾问";
                t.setContent(t.getContent().replace("{managerType}", managerType));
                break;
            }
        }
        return templateList;
    }

    /**
     * 设置默认短信模板 短信默认模板文案内容： “您好，我是” + 经销商简称 + “的” + 顾问职位 +顾问姓名 +
     * “，欢迎您到店看车试驾，我的手机号是” + 顾问手机号 +“，公司地址为” + 经销商地址
     *
     * @param brokerId
     * @return
     */
    public List<BrokerTemplate> initMSM(long brokerId) {
        List<BrokerTemplate> list = Lists.newArrayList();
        Broker broker = brokerService.get(brokerId);
        if (broker == null) {
            return list;
        }
        Dealer dealer = dealerService.getSingle(broker.getDealerId());
        if (dealer == null) {
            return list;
        }
        //String managerType = broker.getType()== BrokerTypeEnum.Manager.getValue()?"销售经理":"销售顾问";
        String managerType = "{managerType}";
        String templateStr = "您好，我是" + dealer.getName() + "的" + managerType + broker.getName() + "，欢迎您到店看车试驾，我的手机号是 " + broker.getMobile() + "，公司地址为:" + dealer.getAddress();
        BrokerTemplate temp = new BrokerTemplate();
        temp.setIsLocked(Short.valueOf("1"));
        temp.setName("默认模板");
        temp.setContent(templateStr);
        temp.setBrokerId(brokerId);
        temp.setType(Short.valueOf("1"));
        temp.setIsDefault(Short.valueOf("1"));
        temp.setCreateTime(new Date());
        this.create(temp);
        list.add(temp);
        temp = temp.clone();
        temp.setIsDefault(Short.valueOf("0"));
        temp.setIsLocked(Short.valueOf("0"));
        temp.setName("自定义模板一");
        temp.setContent("");
        temp.setCreateTime(new Date());
        this.create(temp);
        list.add(temp);
        temp = temp.clone();
        temp.setName("自定义模板二");
        this.create(temp);
        temp.setCreateTime(new Date());
        list.add(temp);
        list = replaceManagerType(list, brokerId);
        return list;
    }

    /**
     * 设置默认微聊 微聊默认模板文案内容：
     * 您好，我是车顾问XXX,经营XXXX品牌（多个用顿号分隔），您想了解车型数据、特价车型、店内优惠还是其他买车咨询？我会尽快回复您。
     *
     * @param brokerId
     * @return
     */
    public BrokerTemplate initTalkMessage(long brokerId) {
        Broker broker = brokerService.get(brokerId);
        if (broker == null) {
            return null;
        }
        List<Integer> brandIds = dealerService.getDealerBrandIds(broker.getDealerId());
        List<Brand> brands = brandService.getBrandListByBrandIds(brandIds);
        List<String> brandStr = Lists.newArrayList();
        if (brands != null) {
            brands.forEach((b) -> {
                brandStr.add(b.getName());
            });
        }
        String templateStr = "您好，我是车顾问" + broker.getName()
                + (brandStr.isEmpty() == false ? (",经营" + Joiner.on(",").join(brandStr) + "品牌") : "")
                + "，您想了解车型数据、特价车型、店内优惠还是其他买车咨询？我会尽快回复您。";
        BrokerTemplate temp = new BrokerTemplate();
        temp.setIsLocked(Short.valueOf("1"));
        temp.setName("");
        temp.setContent(templateStr);
        temp.setBrokerId(brokerId);
        temp.setType(Short.valueOf("2"));
        temp.setIsDefault(Short.valueOf("1"));
        this.create(temp);
        return temp;
    }

    /**
     * 根据brokerId和type类id列表
     *
     * @param brokerId
     * @param type
     * @param max
     * @param limit
     * @return
     * @throws java.lang.Exception
     */
    public List<Long> getTemplateIdsByPage(long brokerId, short type, String max, int limit) throws Exception {
        List<Long> ids = dao.getTemplateIdsByPage(brokerId, type, max, limit);
        if (ids == null || ids.isEmpty()) {
            if (type == 1) {
                List<BrokerTemplate> templates = initMSM(brokerId);
                if (templates.isEmpty() == false) {
                    templates.forEach((t) -> {
                        ids.add(t.getBrokerTemplateId());
                    });
                }
            }
            if (type == 2) {
                BrokerTemplate template = initTalkMessage(brokerId);
                if (template != null) {
                    ids.add(template.getBrokerTemplateId());
                }
            }
        }
        return ids;
    }

    /**
     * 创建模板
     *
     * @param template
     * @return
     */
    public int create(BrokerTemplate template) {
        //是默认模板
        if (template.getIsDefault() == 1) {
            dao.ResetDefaultByType(template.getType(), template.getBrokerId());
        }
        return dao.Create(template);
    }

    /**
     * 设置 templateId 为默认模板
     *
     * @param type
     * @param templateId
     * @param brokerId
     * @return
     */
    public boolean updateSetIsDefaultTemplate(short type, long templateId, long brokerId) {
        BrokerTemplatePersistant template = new BrokerTemplatePersistant();
        template.setBrokerId(brokerId);
        template.setBrokerTemplateId(templateId);
        template.setIsDefault(Short.valueOf("1"));
        int ret = dao.update(template);
        if (ret > 0) {
            dao.ResetDefaultByType(type, brokerId);
            return true;
        }
        return false;
    }

    public int ResetDefaultByType(short type, long brokerId) {
        return dao.ResetDefaultByType(type, brokerId);
    }

}
