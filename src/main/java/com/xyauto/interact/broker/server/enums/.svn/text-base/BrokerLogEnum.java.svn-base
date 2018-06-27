package com.xyauto.interact.broker.server.enums;

import com.google.common.collect.Maps;
import java.util.Map;

public enum BrokerLogEnum {

    BrokerUpdate("broker_update", "修改经纪人信息"),
    BrokerClueHandle("broker_clue_handle", "经纪人处理线索"),
    BrokerClueCustomerAdd("broker_customer_add", "【%s】创建询价客户卡片"),
    BrokerCustomerAdd("broker_customer_add", "【%s】创建客户卡片"),
    BrokerCustomerStepUpdate("broker_customer_step_update", "【%s】将跟进状态更改为%s"),
    BrokerCustomerContactTimeUpdate("broker_customer_contacttime_update", "【%s】更改下次联系时间为%s"),
    BrokerCustomerLevelUpdate("broker_customer_level_update", "【%s】将意向等级更改为%s"),
    BrokerCustomerExchangeUpdate("broker_customer_exchange_update", "经纪人更新客户是否置换"),
    BrokerCustomerGenderUpdate("broker_customer_gender_update", "经纪人更新客户性别"),
    BrokerCustomerAllopatryUpdate("broker_customer_allopatry_update", "经纪人更新客户是否异地上牌"),
    BrokerCustomerPayTypeUpdate("broker_customer_paytype_update", "经纪人更新客户支付类型"),
    BrokerCustomerRemarkUpdate("broker_customer_remark_update", "【%s】更改备注为%s"),
    BrokerCustomerUserNameUpdate("broker_customer_username_update", "经纪人更新客户姓名"),
    BrokerCustomerCarsWillUpdate("broker_customer_carwill_update", "【%s】将客户意向车型更改为%s"),
    BrokerCustomerPhoneContact("broker_customer_contact_phone", "【%s】电话联系%s"),
    BrokerCustomerSmsContact("broker_customer_contact_sms", "【%s】发送短信给%s"),
    BrokerCustomerContact("broker_customer_contact", "经纪人联系客户"),
    BrokerClueMiss("broker_clue_miss", "经纪人线索流失"),
    BrokerClueDealerAdd("broker_clue_add", "线索自动分配给%s"),
    BrokerCluePersonAdd("broker_clue_add", "线索自动分配给%s"),
    BrokerClueAllotAdd("broker_clue_manual_handle", "【%s】线索手动分配给%s"),
    BrokerCluePickUpAdd("broker_clue_add", "【%s】认领了线索"),
    BrokerReceiptAdd("broker_receipt_add", "【%s】上传购车发票成功"),
    BrokerWebSiteView("broker_website_view", "经纪人微店被浏览"),
    BrokerCustomerAllot("broker_customer_allot", "【%s】将客户从%s名下分配给%s")
    ;
        
        
    private String name;
    private String desc;

    private BrokerLogEnum(String name, String desc) {
        this.desc = desc;
        this.name = name;
    }

    public static BrokerLogEnum format(BrokerLogEnum logType, Object... replacement) {
        logType.setDesc(String.format(logType.getDesc(), replacement));
        return logType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
    public static Map<String, String> resultMap = BrokerLogEnum.getMap();

    public static Map<String, String> getMap() {
        Map<String, String> map = Maps.newConcurrentMap();
        for (BrokerLogEnum itme : BrokerLogEnum.values()) {
            map.put(itme.getName(), itme.getDesc());
        }
        return map;
    }

}
