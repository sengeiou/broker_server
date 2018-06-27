package com.xyauto.interact.broker.server.dao;

import com.xyauto.interact.broker.server.model.po.BrokerTemplatePersistant;
import com.xyauto.interact.broker.server.model.vo.BrokerTemplate;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author liucx
 * @date 2018-02-07
 */
@Mapper
public interface IBrokerTemplateDao {

    BrokerTemplate GetModelByTemplateId(@Param("templateid") long templateId);

    short CheckTypeDefault(@Param(value = "type") short type);

    int ResetDefaultByType(@Param(value = "type") short type,@Param(value = "brokerId") long brokerId);

    int DelteTemplate(@Param(value = "templateId")long templateId,@Param(value = "brokerId") long brokerId);

    List<BrokerTemplate> GetListByTypeAndBrokerId(@Param(value = "brokerId")long brokerId , @Param(value = "type")short type, @Param(value="pageIndex") int pageIndex,@Param(value = "pageSize") int pageSize );

    List<Long> GetTemplateIdsByPage(@Param(value = "brokerId") long brokerId,@Param(value = "type") short type,@Param(value = "max") String max ,@Param(value = "limit") int limit);

    List<BrokerTemplate>  GetTemplateListByIds(@Param(value = "ids") List<Long> ids);

    Long GetCountByType( @Param(value = "brokerId")long brokerId , @Param(value = "type")short type);

    Long GetLockedId( @Param(value = "brokerId")long brokerId , @Param(value = "type")short type);

    Integer UpdateTemplate(@Param("temp") BrokerTemplatePersistant temp);

    Integer setDefaultByType( @Param(value = "brokerId")long brokerId , @Param(value = "type")short type);

    /**
     * 根据类型获取数据
     * @param types
     * @return
     */
    List<BrokerTemplate> GetListByTypes(@Param("types") List<Short> types,@Param("brokerId") Long brokerId);


    Integer Create(BrokerTemplate template);
}