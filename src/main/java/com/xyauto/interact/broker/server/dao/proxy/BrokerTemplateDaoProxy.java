package com.xyauto.interact.broker.server.dao.proxy;

import com.xyauto.interact.broker.server.dao.IBrokerTemplateDao;
import com.xyauto.interact.broker.server.model.po.BrokerTemplatePersistant;
import com.xyauto.interact.broker.server.model.vo.BrokerTemplate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BrokerTemplateDaoProxy {
    @Autowired
    IBrokerTemplateDao dao;

    public List<Integer> getListIds(long brokerId, String max, int limit) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<Integer> getListIdsByPage(long brokerId, int page, int limit) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<BrokerTemplate> getListByIds(List<Long> ids) {
        throw new UnsupportedOperationException("Not supported yet.");
    }


    public BrokerTemplate GetModelByTemplateId(long templateId){
        return dao.GetModelByTemplateId(templateId);
    }

    /**
     * 根据brokerid和type取id集合
     *
     * @author liucx
     * @param brokerId
     * @param type
     * @param max
     * @param limit
     * @return 
     */
    public List<Long> getTemplateIdsByPage(long brokerId, short type, String max, int limit) {
        return dao.GetTemplateIdsByPage(brokerId, type, max, limit);
    }

    /**
     * 根据id集合取实体集合
     *
     * @param ids
     * @return
     * @author liucx
     */
    public List<BrokerTemplate> getTemplateListByIds(List<Long> ids) {
        return dao.GetTemplateListByIds(ids);
    }

    /**
     * 根据类别取模版条数
     * @param brokerId
     * @param type
     * @return
     */
    public Long getCountByType(long brokerId, short type) {
        return dao.GetCountByType(brokerId, type);
    }

    /**
     * 取系统模版的id
     * @param brokerId
     * @param type
     * @return
     */
    public Long getLockedId(long brokerId,  short type) {
        return dao.GetLockedId(brokerId, type);
    }

    /**
     * 删除后设置默认模板
     * @param brokerId
     * @param type
     * @return
     */
    public Integer setDefaultByType(long brokerId , short type){
        return dao.setDefaultByType(brokerId, type);
    }

    public Integer update(BrokerTemplatePersistant temp){
        return  dao.UpdateTemplate(temp);
    }


    /**
     * 根据类型获取模板数据
     * @param types
     * @return
     */
    public List<BrokerTemplate> GetListByTypes(List<Short> types, Long brokerId){
        return  dao.GetListByTypes(types,brokerId);
    }

    public Integer Create(BrokerTemplate template){
        return dao.Create(template);
    }

    public int ResetDefaultByType(short type,long brokerId){
        return dao.ResetDefaultByType(type,brokerId);
    }
}