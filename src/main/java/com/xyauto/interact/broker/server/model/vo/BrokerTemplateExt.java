package com.xyauto.interact.broker.server.model.vo;

import com.xyauto.interact.broker.server.config.DataBaseConfig;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class BrokerTemplateExt {


    public BrokerTemplateExt(short Type,BrokerTemplate t){
        this.type=Type;
        this.BrokerTemplate = new ArrayList<BrokerTemplate>();
        this.BrokerTemplate.add(t);
    }
    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public List<com.xyauto.interact.broker.server.model.vo.BrokerTemplate> getBrokerTemplate() {
        return BrokerTemplate;
    }

    public void setBrokerTemplate(List<com.xyauto.interact.broker.server.model.vo.BrokerTemplate> brokerTemplate) {
        BrokerTemplate = brokerTemplate;
    }
    /**
     * 类型   1短信，2微聊  3 微店标题导语
     * @return
     */
    private Short type;

    /**
     * 模板信息
     */
    private List<BrokerTemplate>  BrokerTemplate;
}
