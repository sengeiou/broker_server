package com.xyauto.interact.broker.server.controller;

import com.mcp.validate.annotation.Check;
import com.xyauto.interact.broker.server.enums.ResultCode;
import com.xyauto.interact.broker.server.exceptions.ResultException;
import com.xyauto.interact.broker.server.model.po.BrokerPo;
import com.xyauto.interact.broker.server.model.vo.Broker;
import com.xyauto.interact.broker.server.model.vo.EnumEntity;
import com.xyauto.interact.broker.server.service.BrokerService;
import com.xyauto.interact.broker.server.service.EnumEntityService;
import com.xyauto.interact.broker.server.util.Result;

import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 经纪人标签设置
 */

@RestController
@RequestMapping(value = "/broker/tags")
public class BrokerTagsController extends BaseController {

    @Autowired
    EnumEntityService enumEntityService;

    @Autowired
    BrokerService brokerService;

    /**
     * 获取所有标签
     * @param type
     * @return
     */
    @RequestMapping(value = "/getlist", method = RequestMethod.GET)
    public Result getTagsList(@Check(value = "type",required = true) short type){
        List<EnumEntity> model = enumEntityService.getAllByType(type);
        return  result.format(ResultCode.Success,model);
    }

    /**
     * 获取经纪人标签数据
     * @param BrokerId
     * @return
     */
    @RequestMapping(value = "/brokertags", method = RequestMethod.GET)
    public Result getTagsByBroker(@Check(value = "broker_id")long BrokerId) throws ResultException {
        Broker broker = brokerService.get(BrokerId);
        List<Short> ids = new ArrayList<>();
        if(broker.getTags()==null || broker.getTags().equals("") ){
            return  result.format(ResultCode.Success,"经纪人无标签数据");
        }else {
            for (String id : broker.getTags().split(",")) {
                if(!id.equals("")) {
                    ids.add(Short.valueOf(id));
                }
            }
            List<EnumEntity> model = enumEntityService.getBrokerTagsListByType(ids);
            return result.format(ResultCode.Success, model);
        }
    }

    /**
     * 修改经纪人标签
     * @param brokerId
     * @param tags
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result saveTags(@Check(value="broker_id",required = true)long brokerId,
                           @Check(value = "tags",required = true) String tags){
        BrokerPo broker = new BrokerPo();
        broker.setBrokerId(brokerId);
        broker.setTags(tags);
        int suc = brokerService.updateByParam(broker);
        if(suc>0) {
            return result.format(ResultCode.Success);
        }
        return result.format(ResultCode.UnKnownError);
    }


}
