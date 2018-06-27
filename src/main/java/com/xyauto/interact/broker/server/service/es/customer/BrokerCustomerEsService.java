package com.xyauto.interact.broker.server.service.es.customer;

import com.google.common.collect.Lists;
import com.xyauto.interact.broker.server.dao.proxy.BrokerCustomerCarsWillDaoProxy;
import com.xyauto.interact.broker.server.model.po.BrokerCustomerSearchParameters;
import com.xyauto.interact.broker.server.model.vo.BrokerCustomer;
import com.xyauto.interact.broker.server.model.vo.BrokerCustomerCarsWill;
import com.xyauto.interact.broker.server.service.BrokerCustomerCarsWillService;
import com.xyauto.interact.broker.server.service.BrokerCustomerService;
import com.xyauto.interact.broker.server.util.EsUtil;
import com.xyauto.interact.broker.server.util.ILogger;
import java.io.IOException;
import java.util.List;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.script.Script;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class BrokerCustomerEsService implements ILogger {

    private final static String TypeName = "broker_customer";

    @Autowired
    private EsUtil esUtil;

    @Value("${xyauto.broker.search.index}")
    private String indexName;

    @Autowired
    BrokerCustomerCarsWillService carsWillDao;

    @Autowired
    @Lazy
    private BrokerCustomerService brokerCustomerService;

    public void createIndex() throws IOException {
        esUtil.createIndex(indexName);
    }

    public void update(long brokerCustomerId) {
        try {
            BrokerCustomer brokerCustomer = brokerCustomerService.get(brokerCustomerId);
            if (brokerCustomer == null) {
                return;
            }
            this.add(Lists.newArrayList(brokerCustomer));
        } catch (IOException ex) {
            this.error("更新经纪人客户索引失败:" + ex.getMessage());
        }
    }

    public void add(List<BrokerCustomer> list) throws IOException {
        List<BrokerCustomerEsEntity> eslist = Lists.newArrayList();
        for (int j = 0; j < list.size(); j++) {
            BrokerCustomer customer = list.get(j);
            if (customer == null) {
                continue;
            }
            BrokerCustomerEsEntity entity = new BrokerCustomerEsEntity();
            entity.setBrokerCustomerId(customer.getBrokerCustomerId());
            entity.setBrokerId(customer.getBrokerId());

            //默认是新车
            entity.setCategory(1);

            BrokerCustomerCarsWill carsWill = carsWillDao.getByBrokerCustomerId(customer.getBrokerId(), customer.getBrokerCustomerId());
            if (carsWill != null && carsWill.getCarId() > 0) {
                entity.setCarId(carsWill.getCarId());
                entity.setSeriesId(carsWill.getSeriesId());
                entity.setCategory(carsWill.getCategory());
            }
            entity.setProvinceId(customer.getProvinceId());
            entity.setCityId(customer.getCityId());
            entity.setCreateTime(customer.getCreateTime().getTime());
            entity.setDealerId(customer.getDealerId());
            entity.setLevel(customer.getLevel());
            entity.setStep(customer.getStep());
            entity.setUsername(customer.getUserName());
            entity.setMobile(customer.getMobile());
            eslist.add(entity);
        }
        try {
            esUtil.addBatch(eslist, indexName, TypeName);
        } catch (IOException e) {
            this.error("经纪人客户更新失败:" + e.getMessage());
        }
    }

    public List<Long> search(BrokerCustomerSearchParameters params, String max, int limit) throws IOException {
        BoolQueryBuilder query = new BoolQueryBuilder();
        if (params.getBrokerIds().isEmpty() == false) {
            query.must(QueryBuilders.termsQuery("brokerId", params.getBrokerIds()));
        }
        if (params.getDealerIds().isEmpty() == false) {
            query.must(QueryBuilders.termsQuery("dealerId", params.getDealerIds()));
        }
        if (params.getCarId() > 0) {
            query.must(QueryBuilders.termQuery("carId", params.getCarId()));
        }
        if (params.getSeriesId() > 0) {
            query.must(QueryBuilders.termQuery("seriesId", params.getSeriesId()));
        }
        if (params.getCityId() > 0) {
            query.must(QueryBuilders.termQuery("cityId", params.getCityId()));
        }
        if (params.getProvinceId() > 0) {
            query.must(QueryBuilders.termQuery("provinceId", params.getProvinceId()));
        }
        if (params.getMobile().isEmpty() == false) {
            query.must(QueryBuilders.wildcardQuery("mobile", params.getMobile() + "*"));
        }
        if (params.getLevel().isEmpty() == false) {
            query.must(QueryBuilders.termsQuery("level", params.getLevel()));
        }
        if (params.getStep().isEmpty() == false) {
            query.must(QueryBuilders.termsQuery("step", params.getStep()));
        }
        if (params.getCategories().isEmpty() == false) {
            query.must(QueryBuilders.termsQuery("category", params.getCategories()));
        }
        if (params.getUsername().isEmpty() == false) {
            query.must(QueryBuilders.matchPhraseQuery("username", params.getUsername()));
        }
        if (params.getBegin() != 0) {
            query.must(QueryBuilders.rangeQuery("createTime").from(params.getBegin()).includeLower(true));
        }
        if (params.getEnd() != 0) {
            query.must(QueryBuilders.rangeQuery("createTime").to(params.getEnd()).includeUpper(true));
        }
        if (max.equals("0") == false) {
            query.must(QueryBuilders.scriptQuery(new Script("((6-doc[\"level\"].value)*10000000000+doc[\"createTime\"].value/1000)<" + max)));
        }
        SearchSourceBuilder search = new SearchSourceBuilder();
        search.sort(SortBuilders.scriptSort(new Script("(6-doc[\"level\"].value)*10000000000+doc[\"createTime\"].value/1000"), "number").order(SortOrder.DESC));
        search.query(query);
        if (limit > 0) {
            search.size(limit);
        }
        List<BrokerCustomerEsEntity> list = esUtil.search(BrokerCustomerEsEntity.class, indexName, TypeName, search);
        List<Long> ids = Lists.newArrayList();
        list.stream().forEach(customer -> {
            ids.add(customer.getBrokerCustomerId());
        });
        return ids;
    }

    public List<Long> search(BrokerCustomerSearchParameters params, int page, int limit) throws IOException {
        BoolQueryBuilder query = new BoolQueryBuilder();
        if (params.getBrokerIds().isEmpty() == false) {
            query.must(QueryBuilders.termsQuery("brokerId", params.getBrokerIds()));
        }
        if (params.getDealerIds().isEmpty() == false) {
            query.must(QueryBuilders.termsQuery("dealerId", params.getDealerIds()));
        }
        if (params.getCarId() > 0) {
            query.must(QueryBuilders.termQuery("carId", params.getCarId()));
        }
        if (params.getSeriesId() > 0) {
            query.must(QueryBuilders.termQuery("seriesId", params.getSeriesId()));
        }
        if (params.getCityId() > 0) {
            query.must(QueryBuilders.termQuery("cityId", params.getCityId()));
        }
        if (params.getProvinceId() > 0) {
            query.must(QueryBuilders.termQuery("provinceId", params.getProvinceId()));
        }
        if (params.getMobile().isEmpty() == false) {
            query.must(QueryBuilders.wildcardQuery("mobile", params.getMobile() + "*"));
        }
        if (params.getLevel().isEmpty() == false) {
            query.must(QueryBuilders.termsQuery("level", params.getLevel()));
        }
        if (params.getStep().isEmpty() == false) {
            query.must(QueryBuilders.termsQuery("step", params.getStep()));
        }
        if (params.getCategories().isEmpty() == false) {
            query.must(QueryBuilders.termsQuery("category", params.getCategories()));
        }
        if (params.getUsername().isEmpty() == false) {
            query.must(QueryBuilders.matchPhraseQuery("username", params.getUsername()));
        }
        if (params.getBegin() != 0) {
            query.must(QueryBuilders.rangeQuery("createTime").from(params.getBegin()).includeLower(true));
        }
        if (params.getEnd() != 0) {
            query.must(QueryBuilders.rangeQuery("createTime").to(params.getEnd()).includeUpper(true));
        }
        SearchSourceBuilder search = new SearchSourceBuilder();
        search.query(query);
        search.from((page - 1) * limit);
        search.size(limit);
        search.sort(SortBuilders.scriptSort(new Script("(6-doc[\"level\"].value)*10000000000+doc[\"createTime\"].value/1000"), "number").order(SortOrder.DESC));
        List<BrokerCustomerEsEntity> list = esUtil.search(BrokerCustomerEsEntity.class, indexName, TypeName, search);
        List<Long> ids = Lists.newArrayList();
        list.stream().forEach(customer -> {
            ids.add(customer.getBrokerCustomerId());
        });
        return ids;
    }

    public int searchListCount(BrokerCustomerSearchParameters params, String max) throws IOException {
        BoolQueryBuilder query = new BoolQueryBuilder();
        if (params.getBrokerIds().isEmpty() == false) {
            query.must(QueryBuilders.termsQuery("brokerId", params.getBrokerIds()));
        }
        if (params.getDealerIds().isEmpty() == false) {
            query.must(QueryBuilders.termsQuery("dealerId", params.getDealerIds()));
        }
        if (params.getCarId() > 0) {
            query.must(QueryBuilders.termQuery("carId", params.getCarId()));
        }
        if (params.getSeriesId() > 0) {
            query.must(QueryBuilders.termQuery("seriesId", params.getSeriesId()));
        }
        if (params.getCityId() > 0) {
            query.must(QueryBuilders.termQuery("cityId", params.getCityId()));
        }
        if (params.getProvinceId() > 0) {
            query.must(QueryBuilders.termQuery("provinceId", params.getProvinceId()));
        }
        if (params.getMobile().isEmpty() == false) {
            query.must(QueryBuilders.wildcardQuery("mobile", params.getMobile() + "*"));
        }
        if (params.getLevel().isEmpty() == false) {
            query.must(QueryBuilders.termsQuery("level", params.getLevel()));
        }
        if (params.getStep().isEmpty() == false) {
            query.must(QueryBuilders.termsQuery("step", params.getStep()));
        }
        if (params.getCategories().isEmpty() == false) {
            query.must(QueryBuilders.termsQuery("category", params.getCategories()));
        }
        if (params.getUsername().isEmpty() == false) {
            query.must(QueryBuilders.matchPhraseQuery("username", params.getUsername()));
        }
        if (params.getBegin() != 0) {
            query.must(QueryBuilders.rangeQuery("createTime").from(params.getBegin()).includeLower(true));
        }
        if (params.getEnd() != 0) {
            query.must(QueryBuilders.rangeQuery("createTime").to(params.getEnd()).includeUpper(true));
        }
        if (max.equals("0") == false) {
            query.must(QueryBuilders.scriptQuery(new Script("(6-doc[\"level\"].value)*10000000000+doc[\"createTime\"].value<" + max)));
        }
        SearchSourceBuilder search = new SearchSourceBuilder();
        search.query(query);
        return esUtil.count(indexName, TypeName, search);
    }

    public long getExistsCustomerId(String mobile, long dealerId) throws IOException {
        BoolQueryBuilder query = new BoolQueryBuilder();
        query.must(QueryBuilders.termQuery("dealerId", dealerId));
        query.must(QueryBuilders.termQuery("mobile", mobile));
        SearchSourceBuilder search = new SearchSourceBuilder();
        search.query(query);
        search.size(1);
        List<BrokerCustomerEsEntity> list = esUtil.search(BrokerCustomerEsEntity.class, indexName, TypeName, search);
        long brokerCustomerId = 0;
        if (list.isEmpty()==false) {
            brokerCustomerId = list.get(0).getBrokerCustomerId();
        }
        return brokerCustomerId;

    }

}
