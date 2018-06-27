package com.xyauto.interact.broker.server.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mcp.validate.annotation.Check;
import com.xyauto.interact.broker.server.enums.BrokerNoticeSettingEnum;
import com.xyauto.interact.broker.server.enums.ResultCode;
import com.xyauto.interact.broker.server.model.vo.BrokerNoticeSetting;
import com.xyauto.interact.broker.server.service.BrokerNoticeSettingService;
import com.xyauto.interact.broker.server.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;

/**
 * 经纪人相关设置
 *
 * @author liucx
 * @date 2018-01-31
 */
@RestController
@RequestMapping(value = "/broker/setting/")
public class BrokerSettingController extends BaseController {


    @Autowired
    BrokerNoticeSettingService brokerNoticeSettingService;

    /**
     * 27.修改消息提醒开关
     *
     * @param brokerId 顾问id
     * @param swichId 开关id
     * @param setType 开关设置 0关闭 1打开
     * @return setSuccess 设置结果标识 0失败 1成功
     * @return msg 设置结果描述信息
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Result setMsgRemind(@Check(value = "broker_id") long brokerId, @Check(value = "swich_id") int swichId,@Check(value = "set_type") Short setType) {
        BrokerNoticeSetting setting = brokerNoticeSettingService.getSettingByBrokerId(brokerId);
        if(setting==null){
            setting  = new BrokerNoticeSetting();
            setting.setBrokerId(brokerId);
            setting.setPromit(31);
        }
        // 开启状态下设置开启
        if((setting.getPromit() & swichId) > 0 && setType == 1){
            return result.format(ResultCode.Success);
        }
        if((setting.getPromit() & swichId) == 0 && setType == 0){
            return result.format(ResultCode.Success);
        }
        setting.setPromit(setType>0?(setting.getPromit()+swichId):(setting.getPromit()-swichId));
        int suc = brokerNoticeSettingService.updateByParam(setting);
        if(suc>0) {
            return result.format(ResultCode.Success);
        }else {
            return result.format(ResultCode.Success);
        }
    }

    /**
     * 26.获取消息提醒管理
     *
     * @param brokerId 顾问id
     * @return 消息列表list
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public Result getMsgSetting(@Check(value = "broker_id",number = true) long brokerId) {
        BrokerNoticeSetting setting = brokerNoticeSettingService.getSettingByBrokerId(brokerId);

        JSONArray jsonArray = new JSONArray();
        JSONObject  object = new JSONObject();
        object.put("swichId", BrokerNoticeSettingEnum.ClueSetting.getName());
        object.put("swichDescription",BrokerNoticeSettingEnum.ClueSetting.getDesc());
        object.put("isOpen","1");
        jsonArray.add(object);
        object = (JSONObject) object.clone();
        object.put("swichId", BrokerNoticeSettingEnum.TalkSetting.getName());
        object.put("swichDescription",BrokerNoticeSettingEnum.TalkSetting.getDesc());
        jsonArray.add(object);
        object = (JSONObject) object.clone();
        object.put("swichId", BrokerNoticeSettingEnum.QASetting.getName());
        object.put("swichDescription",BrokerNoticeSettingEnum.QASetting.getDesc());
        jsonArray.add(object);
        object = (JSONObject) object.clone();
        object.put("swichId", BrokerNoticeSettingEnum.DealSetting.getName());
        object.put("swichDescription",BrokerNoticeSettingEnum.DealSetting.getDesc());
        jsonArray.add(object);
        object = (JSONObject) object.clone();
        object.put("swichId", BrokerNoticeSettingEnum.TaskSetting.getName());
        object.put("swichDescription",BrokerNoticeSettingEnum.TaskSetting.getDesc());
        jsonArray.add(object);

        if(setting!=null){
            for(Object obj : jsonArray){
                JSONObject t = (JSONObject) obj;
                if(t.get("swichId").equals(BrokerNoticeSettingEnum.ClueSetting.getName())){
                    t.put("isOpen",(setting.getPromit() & 16) > 0 ? "1" : "0");
                }
                if(t.get("swichId").equals(BrokerNoticeSettingEnum.TalkSetting.getName())){
                    t.put("isOpen",(setting.getPromit() & 8) > 0 ? "1" : "0");
                }
                if(t.get("swichId").equals(BrokerNoticeSettingEnum.QASetting.getName())){
                    t.put("isOpen",(setting.getPromit() & 4) > 0 ? "1" : "0");
                }
                if(t.get("swichId").equals(BrokerNoticeSettingEnum.DealSetting.getName())){
                    t.put("isOpen",(setting.getPromit() & 2) > 0 ? "1" : "0");
                }
                if(t.get("swichId").equals(BrokerNoticeSettingEnum.TaskSetting.getName())){
                    t.put("isOpen",(setting.getPromit() & 1) > 0 ? "1" : "0");
                }
            }
        }
        return result.format(ResultCode.Success,jsonArray);
    }
    
    
    
}
