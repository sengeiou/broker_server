package com.xyauto.interact.broker.server.service.es.clue;

import com.google.common.collect.Lists;
import com.xyauto.interact.broker.server.model.po.BrokerClueSearchParameters;
import com.xyauto.interact.broker.server.model.vo.BrokerClue;
import com.xyauto.interact.broker.server.model.vo.BrokerCustomer;
import com.xyauto.interact.broker.server.model.vo.Dealer;
import com.xyauto.interact.broker.server.service.BrokerClueService;
import com.xyauto.interact.broker.server.service.DealerService;
import com.xyauto.interact.broker.server.util.DateUtil;
import com.xyauto.interact.broker.server.util.EsUtil;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.searchbox.core.search.aggregation.MetricAggregation;
import io.searchbox.core.search.aggregation.TermsAggregation;
import io.searchbox.core.search.aggregation.TermsAggregation.Entry;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.script.Script;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms.Order;
import org.elasticsearch.search.aggregations.bucket.terms.TermsBuilder;
import org.elasticsearch.search.aggregations.metrics.MetricsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.max.MaxBuilder;
import org.elasticsearch.search.aggregations.pipeline.having.BucketSelectorBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import com.xyauto.interact.broker.server.service.BrokerCustomerService;
import com.xyauto.interact.broker.server.util.ILogger;
import org.elasticsearch.search.aggregations.Aggregator;

@Service
public class BrokerClueEsService implements ILogger {

    private final static String TypeName = "broker_clue";

    @Autowired
    EsUtil esUtil;

    @Value("${xyauto.broker.search.index}")
    private String indexName;

    @Autowired
    DealerService dealerService;

    @Autowired
    @Lazy
    BrokerClueService brokerClueService;
    
    @Autowired
    @Lazy
    BrokerCustomerService brokerCustomerService;

    public void createIndex() throws IOException {
        esUtil.createIndex(indexName);
    }

    public void update(long brokerClueId) {
        try {
            BrokerClue brokerClue = brokerClueService.get(brokerClueId, false);
            this.add(Lists.newArrayList(brokerClue));
        } catch (IOException ex) {
            this.error("线索索引更新失败");
        }
    }

    public void add(List<BrokerClue> list) throws IOException {
        List<BrokerClueEsEntity> eslist = Lists.newArrayList();
        for (int j = 0; j < list.size(); j++) {

            BrokerClue clue = list.get(j);
            if (clue == null) {
                this.error("更新索引:线索是空");
                continue;
            }
            Dealer dealer = dealerService.getAlways(clue.getDealerId());
            if (dealer == null) {
                dealer = new Dealer();
                this.error("更新索引:经销商为空:" + clue.getDeviceId()+",需要等待同步经销商id"+dealer.getName());
            }
            BrokerClueEsEntity entity = new BrokerClueEsEntity();
            entity.setBrandId(clue.getBrandId());
            entity.setBrokerClueId(clue.getBrokerClueId());
            entity.setBrokerCustomerId(clue.getBrokerCustomerId());
            if (clue.getBrokerCustomerId() == 0) {
                //确认线索是否已有客户,补充处理
                BrokerCustomer brokerCustomer = brokerCustomerService.existsCustomer(
                        entity.getMobile(), entity.getDealerId());
                if (brokerCustomer != null) {
                    // 设置线索绑定客户
                    entity.setBrokerCustomerId(brokerCustomer.getBrokerCustomerId());
                }
            }
            entity.setBrokerId(clue.getBrokerId());
            entity.setDealerName(dealer.getName());
            entity.setCategory(clue.getCategory());
            entity.setCityId(clue.getCityId());
            entity.setClueId(clue.getClueId());
            entity.setDealerId(clue.getDealerId());
            entity.setDeviceId(clue.getDeviceId());
            entity.setIsHandled(clue.getIsHandled());
            entity.setMobile(clue.getMobile());
            entity.setProvinceId(clue.getProvinceId());
            entity.setSeriesId(clue.getSeriesId());
            entity.setSource(clue.getSource());
            entity.setSubBrandId(clue.getSubBrandId());
            entity.setCarId(clue.getCarId());
            entity.setType(clue.getType());
            entity.setUsername(clue.getUsername());
            entity.setCreateTime(clue.getCreateTime().getTime());
            entity.setIsDelete(clue.getIsDeleted());
            entity.setDeleteTime(clue.getDeleteTime().getTime());
            entity.setDistributeTime(clue.getDistributeTime().getTime());
            entity.setIsDistributed(clue.getIsDistributed());
            entity.setMobileCityId(clue.getMobileCityId());
            entity.setMobileProvinceId(clue.getMobileProvinceId());
            entity.setLocationCityId(clue.getLocationCityId());
            entity.setLocationProvinceId(clue.getLocationProvinceId());
            entity.setCallstatusname(clue.getCallInfo() == null ? "" : clue
                    .getCallInfo().getCallstatusname());
            entity.setCalleeNumber(clue.getCallInfo() == null ? "" : clue
                    .getCallInfo().getCalleenumber());
            entity.setCalleeRealNumber(clue.getCallInfo() == null ? "" : clue
                    .getCallInfo().getCalleerealnumber());
            entity.setCallerPhoneNumber(clue.getCallInfo() == null ? "" : clue
                    .getCallInfo().getCallerphonenumber());
            entity.setCallbegintime(clue.getCallInfo() == null ? 0 : clue
                    .getCallInfo().getCallbegintime());
            eslist.add(entity);
        }
        try {
            esUtil.addBatch(eslist, indexName, TypeName);
        } catch (IOException e) {
            this.error("经纪人线索更新失败:" + e.getMessage());
        }
    }

    public List<Long> search(BrokerClueSearchParameters params, String max,
            int limit) throws IOException {
        BoolQueryBuilder query = QueryBuilders.boolQuery();
        if (params.getClueId() != 0) {
            query.must(QueryBuilders.termQuery("clueId", params.getClueId()));
        }
        if (params.getDealerIds().isEmpty() == false) {
            query.must(QueryBuilders.termsQuery("dealerId",
                    params.getDealerIds()));
        }
        if (params.getBrokerIds().isEmpty() == false) {
            query.must(QueryBuilders.termsQuery("brokerId",
                    params.getBrokerIds()));
        }
        if (params.getDealerName() != null
                && params.getDealerName().length() > 0) {
            query.must(QueryBuilders.matchPhraseQuery("dealerName",
                    params.getDealerName()));
        }
        if (params.getMobile() != null && params.getMobile().length() > 0) {
            query.must(QueryBuilders.wildcardQuery("mobile", params.getMobile()
                    + "*"));
        }
        if (params.getUsername() != null && params.getUsername().length() > 0) {
            query.must(QueryBuilders.matchPhraseQuery("username",
                    params.getUsername()));
        }
        if (params.getBrandId() != 0) {
            query.must(QueryBuilders.termQuery("brandId", params.getBrandId()));
        }
        if (params.getSeriesId() != 0) {
            query.must(QueryBuilders.termQuery("seriesId", params.getSeriesId()));
        }
        if (params.getCarId() != 0) {
            query.must(QueryBuilders.termQuery("carId", params.getCarId()));
        }
        if (params.getCityId() != 0) {
            query.must(QueryBuilders.termQuery("cityId", params.getCityId()));
        }
        if (params.getProvinceId() != 0) {
            query.must(QueryBuilders.termQuery("provinceId",
                    params.getProvinceId()));
        }
        if (params.getMobileCityId() != 0) {
            query.must(QueryBuilders.termQuery("mobileCityId",
                    params.getMobileCityId()));
        }
        if (params.getMobileProvinceId() != 0) {
            query.must(QueryBuilders.termQuery("mobileProvinceId",
                    params.getMobileProvinceId()));
        }
        if (params.getLocationCityId() != 0) {
            query.must(QueryBuilders.termQuery("locationCityId",
                    params.getLocationCityId()));
        }
        if (params.getLocationProvinceId() != 0) {
            query.must(QueryBuilders.termQuery("locationProvinceId",
                    params.getLocationProvinceId()));
        }
        if (max.equals("0") == false) {
            query.must(QueryBuilders.scriptQuery(new Script(
                    "doc[\"createTime\"].value<" + max)));
        }
        if (params.getIsHandled() >= 0) {
            query.must(QueryBuilders.termQuery("isHandled",
                    params.getIsHandled()));
        }
        if (params.getIsCustomer() == 0) {
            query.must(QueryBuilders.termQuery("brokerCustomerId", 0));
        }
        if (params.getIsCustomer() == 1) {
            query.must(QueryBuilders.rangeQuery("brokerCustomerId").gt(0));
        }
        if (params.getCategories() != null
                && params.getCategories().isEmpty() == false) {
            query.must(QueryBuilders.termsQuery("category",
                    params.getCategories()));
        }
        if (params.getType() != 0) {
            query.must(QueryBuilders.termQuery("type", params.getType()));
        }
        if (params.getIsdelete() == 1) {
            query.must(QueryBuilders.termQuery("isDelete", params.getIsdelete()));
        }

        if (params.getIsdelete() <= 0) {
            query.must(QueryBuilders.termQuery("isDelete", 0));
            if (params.getBegin() != 0) {
                query.must(QueryBuilders.rangeQuery("createTime")
                        .from(params.getBegin()).includeLower(true));
            }
            if (params.getEnd() != 0) {
                query.must(QueryBuilders.rangeQuery("createTime")
                        .to(params.getEnd()).includeUpper(true));
            }
        } else if (params.getIsdelete() == 1) {
            query.must(QueryBuilders.termQuery("isDelete", 1));
            if (params.getBegin() != 0) {
                query.must(QueryBuilders.rangeQuery("deleteTime")
                        .from(params.getBegin()).includeLower(true));
            }
            if (params.getEnd() != 0) {
                query.must(QueryBuilders.rangeQuery("deleteTime")
                        .to(params.getEnd()).includeUpper(true));
            }
        }
        if (params.getDistributeBegin() > 0) {
            query.must(QueryBuilders.termQuery("isDistribute", 1));
            query.must(QueryBuilders.rangeQuery("distributeTime")
                    .from(params.getDistributeBegin()).includeLower(true));
        }
        if (params.getDistirbuteEnd() > 0) {
            query.must(QueryBuilders.termQuery("isDistribute", 1));
            query.must(QueryBuilders.rangeQuery("distributeEnd")
                    .from(params.getDistirbuteEnd()).includeLower(true));
        }
        SearchSourceBuilder search = new SearchSourceBuilder();
        search.query(query);
        search.sort(SortBuilders.fieldSort("createTime").order(SortOrder.DESC));
        search.size(limit);
        List<BrokerClueEsEntity> list = esUtil.search(BrokerClueEsEntity.class,
                indexName, TypeName, search);
        List<Long> ids = Lists.newArrayList();
        list.stream().forEach(clue -> {
            ids.add(clue.getBrokerClueId());
        });
        return ids;
    }

    public List<Long> search(BrokerClueSearchParameters params, int page,
            int limit) throws IOException {
        BoolQueryBuilder query = new BoolQueryBuilder();
        if (params.getClueId() != 0) {
            query.must(QueryBuilders.termQuery("clueId", params.getClueId()));
        }
        if (params.getDealerIds().isEmpty() == false) {
            query.must(QueryBuilders.termsQuery("dealerId",
                    params.getDealerIds()));
        }
        if (params.getBrokerIds().isEmpty() == false) {
            query.must(QueryBuilders.termsQuery("brokerId",
                    params.getBrokerIds()));
        }
        if (params.getDealerName() != null
                && params.getDealerName().length() > 0) {
            query.must(QueryBuilders.matchPhraseQuery("dealerName",
                    params.getDealerName()));
        }
        if(params.getMobileList().isEmpty()==false){
            query.must(QueryBuilders.termsQuery("mobile",
                    params.getMobileList()));
        }
        if (params.getMobile() != null && params.getMobile().length() > 0) {
            query.must(QueryBuilders.wildcardQuery("mobile", params.getMobile()
                    + "*"));
        }
        if (params.getUsername() != null && params.getUsername().length() > 0) {
            query.must(QueryBuilders.matchPhraseQuery("username",
                    params.getUsername()));
        }
        if (params.getBrandId() != 0) {
            query.must(QueryBuilders.termQuery("brandId", params.getBrandId()));
        }
        if (params.getSeriesId() != 0) {
            query.must(QueryBuilders.termQuery("seriesId", params.getSeriesId()));
        }
        if (params.getCarId() != 0) {
            query.must(QueryBuilders.termQuery("carId", params.getCarId()));
        }
        if (params.getCityId() != 0) {
            query.must(QueryBuilders.termQuery("cityId", params.getCityId()));
        }
        if (params.getProvinceId() != 0) {
            query.must(QueryBuilders.termQuery("provinceId",
                    params.getProvinceId()));
        }
        if (params.getMobileCityId() != 0) {
            query.must(QueryBuilders.termQuery("mobileCityId",
                    params.getMobileCityId()));
        }
        if (params.getMobileProvinceId() != 0) {
            query.must(QueryBuilders.termQuery("mobileProvinceId",
                    params.getMobileProvinceId()));
        }
        if (params.getLocationCityId() != 0) {
            query.must(QueryBuilders.termQuery("locationCityId",
                    params.getLocationCityId()));
        }
        if (params.getLocationProvinceId() != 0) {
            query.must(QueryBuilders.termQuery("locationProvinceId",
                    params.getLocationProvinceId()));
        }
        if (params.getIsHandled() >= 0) {
            query.must(QueryBuilders.termQuery("isHandled",
                    params.getIsHandled()));
        }
        if (params.getIsCustomer() == 0) {
            query.must(QueryBuilders.termQuery("brokerCustomerId", 0));
        }
        if (params.getIsCustomer() == 1) {
            query.must(QueryBuilders.rangeQuery("brokerCustomerId").gt(0));
        }
        if (params.getType() != 0) {
            query.must(QueryBuilders.termQuery("type", params.getType()));
        }
        if (params.getCategories().isEmpty() == false) {
            query.must(QueryBuilders.termsQuery("category",
                    params.getCategories()));
        }
        if (params.getIsdelete() == 1) {
            query.must(QueryBuilders.termQuery("isDelete", params.getIsdelete()));
        }
        if (params.getIsdelete() <= 0) {
            query.must(QueryBuilders.termQuery("isDelete", 0));
            if (params.getBegin() != 0) {
                query.must(QueryBuilders.rangeQuery("createTime")
                        .from(params.getBegin()).includeLower(true));
            }
            if (params.getEnd() != 0) {
                query.must(QueryBuilders.rangeQuery("createTime")
                        .to(params.getEnd()).includeUpper(true));
            }
        } else if (params.getIsdelete() == 1) {
            query.must(QueryBuilders.termQuery("isDelete", 1));
            if (params.getBegin() != 0) {
                query.must(QueryBuilders.rangeQuery("deleteTime")
                        .from(params.getBegin()).includeLower(true));
            }
            if (params.getEnd() != 0) {
                query.must(QueryBuilders.rangeQuery("deleteTime")
                        .to(params.getEnd()).includeUpper(true));
            }
        }
        if (params.getCallstatus() != 0) {
            if (params.getCallstatus() == 1) {
                query.must(QueryBuilders.matchQuery("callstatusname", "ANSWER"));
            } else if (params.getCallstatus() == -1) {
                query.mustNot(QueryBuilders.matchQuery("callstatusname",
                        "ANSWER"));
            }
        }
        if (params.getCallerPhoneNumber().isEmpty() == false) {
            query.must(QueryBuilders.wildcardQuery("callerPhoneNumber",
                    params.getCallerPhoneNumber() + "*"));
        }
        if (params.getCalleeRealNumber().isEmpty() == false) {
            query.must(QueryBuilders.wildcardQuery("calleeNumber",
                    params.getCalleeRealNumber() + "*"));
        }
        if (params.getDistributeBegin() > 0) {
            query.must(QueryBuilders.termQuery("isDistribute", 1));
            query.must(QueryBuilders.rangeQuery("distributeTime")
                    .from(params.getDistributeBegin()).includeLower(true));
        }
        if (params.getDistirbuteEnd() > 0) {
            query.must(QueryBuilders.termQuery("isDistribute", 1));
            query.must(QueryBuilders.rangeQuery("distributeEnd")
                    .from(params.getDistirbuteEnd()).includeLower(true));
        }
        SearchSourceBuilder search = new SearchSourceBuilder();
        search.query(query);
        search.sort(SortBuilders.fieldSort("createTime").order(SortOrder.DESC));
        search.from((page - 1) * limit);
        search.size(limit);
        List<BrokerClueEsEntity> list = esUtil.search(BrokerClueEsEntity.class,
                indexName, TypeName, search);
        List<Long> ids = Lists.newArrayList();
        list.stream().forEach(clue -> {
            ids.add(clue.getBrokerClueId());
        });
        return ids;
    }

    public int searchListCount(BrokerClueSearchParameters params, String max)
            throws IOException {
        BoolQueryBuilder query = new BoolQueryBuilder();
        if (params.getClueId() != 0) {
            query.must(QueryBuilders.termQuery("clueId", params.getClueId()));
        }
        if (params.getDealerIds().isEmpty() == false) {
            query.must(QueryBuilders.termsQuery("dealerId",
                    params.getDealerIds()));
        }
        if (params.getBrokerIds().isEmpty() == false) {
            query.must(QueryBuilders.termsQuery("brokerId",
                    params.getBrokerIds()));
        }
        if (params.getDealerName() != null
                && params.getDealerName().length() > 0) {
            query.must(QueryBuilders.matchPhraseQuery("dealerName",
                    params.getDealerName()));
        }
        if (params.getMobile() != null && params.getMobile().length() > 0) {
            query.must(QueryBuilders.wildcardQuery("mobile", params.getMobile()
                    + "*"));
        }
        if (params.getUsername() != null && params.getUsername().length() > 0) {
            query.must(QueryBuilders.matchPhraseQuery("username",
                    params.getUsername()));
        }
        if (params.getBrandId() != 0) {
            query.must(QueryBuilders.termQuery("brandId", params.getBrandId()));
        }
        if (params.getSeriesId() != 0) {
            query.must(QueryBuilders.termQuery("seriesId", params.getSeriesId()));
        }
        if (params.getCarId() != 0) {
            query.must(QueryBuilders.termQuery("carId", params.getCarId()));
        }
        if (params.getCityId() != 0) {
            query.must(QueryBuilders.termQuery("cityId", params.getCityId()));
        }
        if (params.getProvinceId() != 0) {
            query.must(QueryBuilders.termQuery("provinceId",
                    params.getProvinceId()));
        }
        if (params.getMobileCityId() != 0) {
            query.must(QueryBuilders.termQuery("mobileCityId",
                    params.getMobileCityId()));
        }
        if (params.getMobileProvinceId() != 0) {
            query.must(QueryBuilders.termQuery("mobileProvinceId",
                    params.getMobileProvinceId()));
        }
        if (params.getLocationCityId() != 0) {
            query.must(QueryBuilders.termQuery("locationCityId",
                    params.getLocationCityId()));
        }
        if (params.getLocationProvinceId() != 0) {
            query.must(QueryBuilders.termQuery("locationProvinceId",
                    params.getLocationProvinceId()));
        }
        if (max.equals("0") == false) {
            query.must(QueryBuilders.scriptQuery(new Script(
                    "doc[\"createTime\"].value<" + max)));
        }
        if (params.getIsHandled() >= 0) {
            query.must(QueryBuilders.termQuery("isHandled",
                    params.getIsHandled()));
        }
        if (params.getIsCustomer() == 0) {
            query.must(QueryBuilders.termQuery("brokerCustomerId", 0));
        }
        if (params.getIsCustomer() == 1) {
            query.must(QueryBuilders.rangeQuery("brokerCustomerId").gt(0));
        }
        if (params.getType() != 0) {
            query.must(QueryBuilders.termQuery("type", params.getType()));
        }
        if (params.getCategories().isEmpty() == false) {
            query.must(QueryBuilders.termsQuery("category",
                    params.getCategories()));
        }
        if (params.getIsdelete() == 1) {
            query.must(QueryBuilders.termQuery("isDelete", params.getIsdelete()));
        }
        if (params.getIsdelete() <= 0) {
            query.must(QueryBuilders.termQuery("isDelete", 0));
            if (params.getBegin() != 0) {
                query.must(QueryBuilders.rangeQuery("createTime")
                        .from(params.getBegin()).includeLower(true));
            }
            if (params.getEnd() != 0) {
                query.must(QueryBuilders.rangeQuery("createTime")
                        .to(params.getEnd()).includeUpper(true));
            }
        } else if (params.getIsdelete() == 1) {
            query.must(QueryBuilders.termQuery("isDelete", 1));
            if (params.getBegin() != 0) {
                query.must(QueryBuilders.rangeQuery("deleteTime")
                        .from(params.getBegin()).includeLower(true));
            }
            if (params.getEnd() != 0) {
                query.must(QueryBuilders.rangeQuery("deleteTime")
                        .to(params.getEnd()).includeUpper(true));
            }
        }
        if (params.getCallerPhoneNumber().isEmpty() == false) {
            query.must(QueryBuilders.wildcardQuery("callerPhoneNumber",
                    params.getCallerPhoneNumber() + "*"));
        }
        if (params.getCalleeRealNumber().isEmpty() == false) {
            query.must(QueryBuilders.wildcardQuery("calleeNumber",
                    params.getCalleeRealNumber() + "*"));
        }
        if (params.getCallstatus() != 0) {
            if (params.getCallstatus() == 1) {
                query.must(QueryBuilders.matchQuery("callstatusname", "ANSWER"));
            } else if (params.getCallstatus() == -1) {
                query.mustNot(QueryBuilders.matchQuery("callstatusname",
                        "ANSWER"));
            }
        }
        if (params.getDistributeBegin() > 0) {
            query.must(QueryBuilders.termQuery("isDistribute", 1));
            query.must(QueryBuilders.rangeQuery("distributeTime")
                    .from(params.getDistributeBegin()).includeLower(true));
        }
        if (params.getDistirbuteEnd() > 0) {
            query.must(QueryBuilders.termQuery("isDistribute", 1));
            query.must(QueryBuilders.rangeQuery("distributeEnd")
                    .from(params.getDistirbuteEnd()).includeLower(true));
        }
        SearchSourceBuilder search = new SearchSourceBuilder();
        search.query(query);
        return esUtil.count(indexName, TypeName, search);
    }

    public Map<String, Object> serarchGroupByMobile(BrokerClueSearchParameters params)
            throws IOException {
        BoolQueryBuilder query = new BoolQueryBuilder();
        if (params.getClueId() != 0) {
            query.must(QueryBuilders.termQuery("clueId", params.getClueId()));
        }
        if (params.getBrokerClueId() != 0) {
            query.must(QueryBuilders.termQuery("brokerClueId", params.getBrokerClueId()));
        }
        if (params.getDealerIds().isEmpty() == false) {
            query.must(QueryBuilders.termsQuery("dealerId",
                    params.getDealerIds()));
        }
        if (params.getBrokerIds().isEmpty() == false) {
            query.must(QueryBuilders.termsQuery("brokerId",
                    params.getBrokerIds()));
        }
        if (params.getDealerName() != null
                && params.getDealerName().length() > 0) {
            query.must(QueryBuilders.matchPhraseQuery("dealerName", params.getDealerName()));
        }
        if (params.getMobile() != null && params.getMobile().length() > 0) {
            query.must(QueryBuilders.wildcardQuery("mobile", params.getMobile()
                    + "*"));
        }
        if (params.getUsername() != null && params.getUsername().length() > 0) {
            query.must(QueryBuilders.matchPhraseQuery("username",
                    params.getUsername()));
        }
        if (params.getBrandId() != 0) {
            query.must(QueryBuilders.termQuery("brandId", params.getBrandId()));
        }
        if (params.getSeriesId() != 0) {
            query.must(QueryBuilders.termQuery("seriesId", params.getSeriesId()));
        }
        if (params.getCarId() != 0) {
            query.must(QueryBuilders.termQuery("carId", params.getCarId()));
        }
        if (params.getCityId() != 0) {
            query.must(QueryBuilders.termQuery("cityId", params.getCityId()));
        }
        if (params.getProvinceId() != 0) {
            query.must(QueryBuilders.termQuery("provinceId",
                    params.getProvinceId()));
        }
        if (params.getMobileCityId() != 0) {
            query.must(QueryBuilders.termQuery("mobileCityId",
                    params.getMobileCityId()));
        }
        if (params.getMobileProvinceId() != 0) {
            query.must(QueryBuilders.termQuery("mobileProvinceId",
                    params.getMobileProvinceId()));
        }
        if (params.getLocationCityId() != 0) {
            query.must(QueryBuilders.termQuery("locationCityId",
                    params.getLocationCityId()));
        }
        if (params.getLocationProvinceId() != 0) {
            query.must(QueryBuilders.termQuery("locationProvinceId",
                    params.getLocationProvinceId()));
        }
        if (params.getIsHandled() >= 0) {
            query.must(QueryBuilders.termQuery("isHandled",
                    params.getIsHandled()));
        }
        if (params.getIsCustomer() == 0) {
            query.must(QueryBuilders.termQuery("brokerCustomerId", 0));
        }
        if (params.getIsCustomer() == 1) {
            query.must(QueryBuilders.rangeQuery("brokerCustomerId").gt(0));
        }
        if (params.getType() != 0) {
            query.must(QueryBuilders.termQuery("type", params.getType()));
        }
        if (params.getCategories().isEmpty() == false) {
            query.must(QueryBuilders.termsQuery("category",
                    params.getCategories()));
        }
        if (params.getIsdelete() <= 0) {
            query.must(QueryBuilders.termQuery("isDelete", 0));
            if (params.getBegin() != 0) {
                query.must(QueryBuilders.rangeQuery("createTime")
                        .from(params.getBegin()).includeLower(true));
            }
            if (params.getEnd() != 0) {
                query.must(QueryBuilders.rangeQuery("createTime")
                        .to(params.getEnd()).includeUpper(true));
            }
        } else if (params.getIsdelete() == 1) {
            query.must(QueryBuilders.termQuery("isDelete", 1));
            if (params.getBegin() != 0) {
                query.must(QueryBuilders.rangeQuery("deleteTime")
                        .from(params.getBegin()).includeLower(true));
            }
            if (params.getEnd() != 0) {
                query.must(QueryBuilders.rangeQuery("deleteTime")
                        .to(params.getEnd()).includeUpper(true));
            }
        }
        if (params.getDistributeBegin() > 0) {
            query.must(QueryBuilders.termQuery("isDistribute", 1));
            query.must(QueryBuilders.rangeQuery("distributeTime")
                    .from(params.getDistributeBegin()).includeLower(true));
        }
        if (params.getDistirbuteEnd() > 0) {
            query.must(QueryBuilders.termQuery("isDistribute", 1));
            query.must(QueryBuilders.rangeQuery("distributeTime")
                    .from(params.getDistirbuteEnd()).includeLower(true));
        }
        if (params.getCallerPhoneNumber().isEmpty() == false) {
            query.must(QueryBuilders.wildcardQuery("callerPhoneNumber",
                    params.getCallerPhoneNumber() + "*"));
        }
        if (params.getCalleeRealNumber().isEmpty() == false) {
            query.must(QueryBuilders.wildcardQuery("calleeNumber",
                    params.getCalleeRealNumber() + "*"));
        }
        if (params.getCallstatus() != 0) {
            if (params.getCallstatus() == 1) {
                query.must(QueryBuilders.matchQuery("callstatusname", "ANSWER"));
            } else if (params.getCallstatus() == -1) {
                query.mustNot(QueryBuilders.matchQuery("callstatusname",
                        "ANSWER"));
            }
        }
        if (params.getBrandId() != 0) {
            query.must(QueryBuilders.termQuery("brandId", params.getBrandId()));
        }
        if (params.getSeriesId() != 0) {
            query.must(QueryBuilders.termQuery("seriesId", params.getSeriesId()));
        }
        if (params.getCarId() != 0) {
            query.must(QueryBuilders.termQuery("carId", params.getCarId()));
        }
        if (params.getCityId() != 0) {
            query.must(QueryBuilders.termQuery("cityId", params.getCityId()));
        }
        if (params.getProvinceId() != 0) {
            query.must(QueryBuilders.termQuery("provinceId",
                    params.getProvinceId()));
        }
        if (params.getSources().isEmpty() == false) {
            query.must(QueryBuilders.termsQuery("source", params.getSources()));
        }

        SearchSourceBuilder search = new SearchSourceBuilder();
        // 按手机号分组---
        TermsBuilder termsBuilder = AggregationBuilders.terms("group")
                .field("mobile")
                .order(Order.aggregation("max_time", false));
        MaxBuilder maxBuilder = AggregationBuilders.max("max_bcId").field(
                "brokerClueId");
        MaxBuilder maxBuilderTime = AggregationBuilders.max("max_time").field(
                "createTime");
        termsBuilder.subAggregation(maxBuilder);
        termsBuilder.subAggregation(maxBuilderTime);
        //termsBuilder.collectMode(Aggregator.SubAggCollectionMode.BREADTH_FIRST);
        //termsBuilder.executionHint("map");
        if (StringUtils.isNotBlank(params.getMax())
                && Long.parseLong(params.getMax()) > 0) {
            BucketSelectorBuilder bucketSelectorBuilder = new BucketSelectorBuilder("having");

            Script script = new Script("max_time<" + Long.parseLong(params.getMax()), Script.DEFAULT_TYPE, "expression", null);
            Map<String, String> map = new HashMap<String, String>();
            map.put("max_time", "max_time");
            bucketSelectorBuilder.setBucketsPathsMap(map);
            bucketSelectorBuilder.script(script);
            termsBuilder.subAggregation(bucketSelectorBuilder);
            if (params.getDealerIds() == null) {
                return null;
            }
            termsBuilder.size(0);
        } else {
            termsBuilder.size(params.getLimit() + (int) params.getOffset());
        }
        search.aggregation(termsBuilder);
        search.query(query);
        search.size(0);
        List<Long> ids = Lists.newArrayList();
        /*List<BrokerClueEsEntity> list = esUtil.search(BrokerClueEsEntity.class, indexName, TypeName,
                search);
        for (BrokerClueEsEntity brokerClueEsEntity : list) {
            ids.add(brokerClueEsEntity.getBrokerClueId());
        }*/
        MetricAggregation aggregation = esUtil.search(indexName, TypeName,
                search);
        TermsAggregation terms = aggregation.getTermsAggregation("group");
        List<Entry> list = terms.getBuckets();
        int size = (list.size() > params.getLimit()) ? params.getLimit() : list.size();
        int total = 0;
        //在分组时间过滤后，size用法有问题
        if (Long.parseLong(params.getMax()) > 0) {
            total = list.size();
            for (int i = 0; i < size; i++) {
                Entry entry = list.get(i);
                ids.add(entry.getMaxAggregation("max_bcId").getMax().longValue());
            }
        } else {
            for (int i = (int) params.getOffset(); i < list.size(); i++) {
                Entry entry = list.get(i);
                ids.add(entry.getMaxAggregation("max_bcId").getMax().longValue());
            }
        }
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("ids", ids);
        resultMap.put("total", total);
        return resultMap;
    }

    /**
     * 获取近三个月用户线索覆盖多少经销商
     *
     * @param mobile
     * @return
     * @throws java.io.IOException
     */
    public long getFollowDealerCount(String mobile) throws IOException {
        SearchSourceBuilder search = new SearchSourceBuilder();
        BoolQueryBuilder query = new BoolQueryBuilder();
        query.must(QueryBuilders.termQuery("mobile", mobile));
        query.must(QueryBuilders.rangeQuery("distributeTime")
                .from(DateUtil.addDay(new Date(), -90).getTime() / 1000)
                .includeLower(true));
        MetricsAggregationBuilder aggregation = AggregationBuilders
                .cardinality("agg").field("dealerId");
        search.aggregation(aggregation);
        search.query(query);
        return esUtil.result(indexName, TypeName, search).getAggregations()
                .getValueCountAggregation("agg").getValueCount();
    }

    public int serarchCountGroupByMobile(BrokerClueSearchParameters params)
            throws IOException {
        BoolQueryBuilder query = new BoolQueryBuilder();
        if (params.getClueId() != 0) {
            query.must(QueryBuilders.termQuery("clueId", params.getClueId()));
        }
        if (params.getBrokerClueId() != 0) {
            query.must(QueryBuilders.termQuery("brokerClueId", params.getBrokerClueId()));
        }
        if (params.getDealerIds().isEmpty() == false) {
            query.must(QueryBuilders.termsQuery("dealerId",
                    params.getDealerIds()));
        }
        if (params.getBrokerIds().isEmpty() == false) {
            query.must(QueryBuilders.termsQuery("brokerId",
                    params.getBrokerIds()));
        }
        if (params.getDealerName() != null
                && params.getDealerName().length() > 0) {
            query.must(QueryBuilders.matchPhraseQuery("dealerName", params.getDealerName()));
        }
        if (params.getMobile() != null && params.getMobile().length() > 0) {
            query.must(QueryBuilders.wildcardQuery("mobile", params.getMobile()
                    + "*"));
        }
        if (params.getUsername() != null && params.getUsername().length() > 0) {
            query.must(QueryBuilders.matchPhraseQuery("username",
                    params.getUsername()));
        }
        if (params.getBrandId() != 0) {
            query.must(QueryBuilders.termQuery("brandId", params.getBrandId()));
        }
        if (params.getSeriesId() != 0) {
            query.must(QueryBuilders.termQuery("seriesId", params.getSeriesId()));
        }
        if (params.getCarId() != 0) {
            query.must(QueryBuilders.termQuery("carId", params.getCarId()));
        }
        if (params.getCityId() != 0) {
            query.must(QueryBuilders.termQuery("cityId", params.getCityId()));
        }
        if (params.getProvinceId() != 0) {
            query.must(QueryBuilders.termQuery("provinceId",
                    params.getProvinceId()));
        }
        if (params.getMobileCityId() != 0) {
            query.must(QueryBuilders.termQuery("mobileCityId",
                    params.getMobileCityId()));
        }
        if (params.getMobileProvinceId() != 0) {
            query.must(QueryBuilders.termQuery("mobileProvinceId",
                    params.getMobileProvinceId()));
        }
        if (params.getLocationCityId() != 0) {
            query.must(QueryBuilders.termQuery("locationCityId",
                    params.getLocationCityId()));
        }
        if (params.getLocationProvinceId() != 0) {
            query.must(QueryBuilders.termQuery("locationProvinceId",
                    params.getLocationProvinceId()));
        }
        if (params.getIsHandled() >= 0) {
            query.must(QueryBuilders.termQuery("isHandled",
                    params.getIsHandled()));
        }
        if (params.getIsCustomer() == 0) {
            query.must(QueryBuilders.termQuery("brokerCustomerId", 0));
        }
        if (params.getIsCustomer() == 1) {
            query.must(QueryBuilders.rangeQuery("brokerCustomerId").gt(0));
        }
        if (params.getType() != 0) {
            query.must(QueryBuilders.termQuery("type", params.getType()));
        }
        if (params.getCategories().isEmpty() == false) {
            query.must(QueryBuilders.termsQuery("category",
                    params.getCategories()));
        }
        if (params.getIsdelete() <= 0) {
            query.must(QueryBuilders.termQuery("isDelete", 0));
            if (params.getBegin() != 0) {
                query.must(QueryBuilders.rangeQuery("createTime")
                        .from(params.getBegin()).includeLower(true));
            }
            if (params.getEnd() != 0) {
                query.must(QueryBuilders.rangeQuery("createTime")
                        .to(params.getEnd()).includeUpper(true));
            }
        } else if (params.getIsdelete() == 1) {
            query.must(QueryBuilders.termQuery("isDelete", 1));
            if (params.getBegin() != 0) {
                query.must(QueryBuilders.rangeQuery("deleteTime")
                        .from(params.getBegin()).includeLower(true));
            }
            if (params.getEnd() != 0) {
                query.must(QueryBuilders.rangeQuery("deleteTime")
                        .to(params.getEnd()).includeUpper(true));
            }
        }
        if (params.getDistributeBegin() > 0) {
            query.must(QueryBuilders.termQuery("isDistribute", 1));
            query.must(QueryBuilders.rangeQuery("distributeTime")
                    .from(params.getDistributeBegin()).includeLower(true));
        }
        if (params.getDistirbuteEnd() > 0) {
            query.must(QueryBuilders.termQuery("isDistribute", 1));
            query.must(QueryBuilders.rangeQuery("distributeTime")
                    .from(params.getDistirbuteEnd()).includeLower(true));
        }
        if (params.getCallerPhoneNumber().isEmpty() == false) {
            query.must(QueryBuilders.wildcardQuery("callerPhoneNumber",
                    params.getCallerPhoneNumber() + "*"));
        }
        if (params.getCalleeRealNumber().isEmpty() == false) {
            query.must(QueryBuilders.wildcardQuery("calleeNumber",
                    params.getCalleeRealNumber() + "*"));
        }
        if (params.getCallstatus() != 0) {
            if (params.getCallstatus() == 1) {
                query.must(QueryBuilders.matchQuery("callstatusname", "ANSWER"));
            } else if (params.getCallstatus() == -1) {
                query.mustNot(QueryBuilders.matchQuery("callstatusname",
                        "ANSWER"));
            }
        }
        if (params.getBrandId() != 0) {
            query.must(QueryBuilders.termQuery("brandId", params.getBrandId()));
        }
        if (params.getSeriesId() != 0) {
            query.must(QueryBuilders.termQuery("seriesId", params.getSeriesId()));
        }
        if (params.getCarId() != 0) {
            query.must(QueryBuilders.termQuery("carId", params.getCarId()));
        }
        if (params.getCityId() != 0) {
            query.must(QueryBuilders.termQuery("cityId", params.getCityId()));
        }
        if (params.getProvinceId() != 0) {
            query.must(QueryBuilders.termQuery("provinceId",
                    params.getProvinceId()));
        }
        if (params.getSources().isEmpty() == false) {
            query.must(QueryBuilders.termsQuery("source", params.getSources()));
        }

        SearchSourceBuilder search = new SearchSourceBuilder();
        // 按手机号分组---
        TermsBuilder termsBuilder = AggregationBuilders.terms("group")
                .field("mobile").size(0);
        //termsBuilder.collectMode(Aggregator.SubAggCollectionMode.BREADTH_FIRST);
        //termsBuilder.executionHint("map");
        search.aggregation(termsBuilder);
        search.query(query);
        search.size(0);
//        return esUtil.count(indexName, TypeName, search);
        return esUtil.countGroupMobile(indexName, TypeName, search);
    }
}
