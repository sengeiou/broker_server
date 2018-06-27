package com.xyauto.interact.broker.server.controller;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.xyauto.interact.broker.server.model.po.BrokerTemplatePersistant;
import com.xyauto.interact.broker.server.model.vo.BrokerTemplateExt;
import com.xyauto.interact.broker.server.model.vo.FlowPagedList;
import com.xyauto.interact.broker.server.model.vo.BrokerTemplate;
import com.mcp.validate.annotation.Check;
import com.xyauto.interact.broker.server.enums.ResultCode;
import com.xyauto.interact.broker.server.service.BrokerTemplateService;
import com.xyauto.interact.broker.server.util.ILogger;
import com.xyauto.interact.broker.server.util.Result;

import java.rmi.server.ExportException;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/broker/template")
public class BrokerTemplateController implements ILogger {

    @Autowired
    Result result;

    @Autowired
    BrokerTemplateService brokerTemplateService;

    /**
     * 根据类型获取模板数据
     *
     * @param types
     * @param brokerId
     * @return
     */
    @RequestMapping(value = "/listbytyps", method = RequestMethod.GET)
    public Result GetListByTypes(
            @Check(value = "type") List<Short> types,
            @Check(value = "broker_id", required = true) Long brokerId
    ) {
        List<BrokerTemplate> temptes = brokerTemplateService.GetListByTypes(types, brokerId);
        List<BrokerTemplateExt> list = Lists.newArrayList();
        temptes.forEach((t) -> {
            boolean isadd = true;
            for (BrokerTemplateExt te : list) {
                if (Objects.equals(te.getType(), t.getType())) {
                    te.getBrokerTemplate().add(t);
                    isadd = false;
                }
            }
            if (isadd) {
                list.add(new BrokerTemplateExt(t.getType(), t));
            }
        });
        return result.format(ResultCode.Success, list);
    }

    /**
     * 获取经纪人短信、微聊模板 25
     *
     * @param brokerId 经纪人id
     * @param type 类别 1短信2微聊
     * @param max
     * @param limit
     * @param
     * @return
     * @throws java.lang.Exception @:broker_id 经纪人ID
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result getList(
            @Check(value = "broker_id", required = true) long brokerId,
            @Check(value = "type") short type,
            @Check(value = "max", required = false, defaultValue = "0") String max,
            @Check(value = "limit", required = false, defaultValue = "20") int limit
    ) throws Exception {
        FlowPagedList<BrokerTemplate> pageList = new FlowPagedList<>();
        List<Long> ids = brokerTemplateService.getTemplateIdsByPage(brokerId, type, max, limit);
        List<BrokerTemplate> list = Lists.newArrayList();
        if (ids.size() > 0) {
            list = brokerTemplateService.getTemplateListByIds(ids,brokerId);
            pageList.setNext_max(list.get(list.size() - 1).getSort());
        }
        pageList.setList(list);
        int total = 0;
        int pagedCount = 0;
        pageList.setCount(total);
        pageList.setHas_more(pagedCount > limit ? 1 : 0);

        pageList.setLimit(limit);
        return result.format(ResultCode.Success, pageList);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result add(
            @Check(value = "broker_id", required = true) Long brokerId,
            @Check(value = "type", defaultValue = "2", required = true) Short type,
            @Check(value = "content", required = true, defaultValue = "") String content,
            @Check(value = "is_default", defaultValue = "0") Short isDefault,
            @Check(value = "name", defaultValue = "") String name
    ) {
        BrokerTemplate template = new BrokerTemplate();
        template.setBrokerId(brokerId);
        template.setType(type);
        template.setContent(content);
        template.setIsDefault(isDefault);
        template.setName(name);
        int suc = 0;
        suc = brokerTemplateService.create(template);
        if (suc > 0) {
            return result.format(ResultCode.Success, template);
        }
        return result.format(ResultCode.UnKnownError);
    }

    /**
     * 更新短信/微聊模版
     *
     * @author liucx 2018-02-07
     * @param templateId 模版id
     * @param brokerId 经纪人id
     * @param type 类别 1 短信 2 微聊
     * @param content 内容
     * @param isDefault 默认 0否1是
     * @param name 短信时是title
     * @return
     * @throws java.rmi.server.ExportException
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Result update(@Check(value = "template_id", required = true) Long templateId,
            @Check(value = "broker_id", required = true) Long brokerId,
            @Check(value = "type", defaultValue = "2") Short type,
            @Check(value = "content", required = true, defaultValue = "") String content,
            @Check(value = "is_default", defaultValue = "0") Short isDefault,
            @Check(value = "name", defaultValue = "") String name
    ) throws ExportException {
        //短信，name必填  微聊 name可不传
        if (type == 1 && name == null) {
            return result.format(ResultCode.MustName);
        }
        BrokerTemplatePersistant temp = new BrokerTemplatePersistant();
        temp.setBrokerTemplateId(templateId);
        temp.setBrokerId(brokerId);
        temp.setType(type);
        temp.setContent(content);
        temp.setIsDefault(isDefault);
        temp.setName(name);
        try {
            //设置默认模板是先将其他模板修改为非默认模板
            if (temp.getIsDefault() == Short.valueOf("1")) {
                //修改默认模板
                brokerTemplateService.ResetDefaultByType(temp.getType(), brokerId);
            }
            int suc = brokerTemplateService.update(temp);
        } catch (Exception e) {
            return result.format(ResultCode.UnKnownError);
        }

        return result.format(ResultCode.Success);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Result delete(@Check(value = "template_id", required = true, number = true) long templateId,
            @Check(value = "broker_id", required = true, number = true) long brokerId) {
        BrokerTemplatePersistant temp = new BrokerTemplatePersistant();
        BrokerTemplate template = brokerTemplateService.GetModelByTemplateId(templateId);
        temp.setBrokerTemplateId(templateId);
        temp.setIsDeleted((short) 1);
        temp.setBrokerId(brokerId);
        temp.setType(template.getType());
        int suc = 0;
        try {
            suc = brokerTemplateService.delete(temp);
        } catch (Exception e) {
            return result.format(ResultCode.UnKnownError);
        }
        return result.format(ResultCode.Success);
    }

}
