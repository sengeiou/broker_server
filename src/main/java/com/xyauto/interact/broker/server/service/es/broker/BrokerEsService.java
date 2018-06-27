package com.xyauto.interact.broker.server.service.es.broker;

import com.google.common.collect.Lists;
import com.xyauto.interact.broker.server.enums.BrokerSortTypeEnum;
import com.xyauto.interact.broker.server.model.po.BrokerSearchParameters;
import com.xyauto.interact.broker.server.model.vo.Broker;
import com.xyauto.interact.broker.server.model.vo.Car;
import com.xyauto.interact.broker.server.model.vo.Dealer;
import com.xyauto.interact.broker.server.service.BrokerService;
import com.xyauto.interact.broker.server.service.DealerService;
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
public class BrokerEsService implements ILogger {

    private final static String TypeName = "broker";
    
    @Value("${xyauto.broker.search.index}")
    private String indexName;

    @Autowired
    private EsUtil esUtil;

    @Autowired
    @Lazy
    private BrokerService brokerService;

    @Autowired
    DealerService dealerService;

    public void createIndex() throws IOException {
        esUtil.createIndex(indexName);
    }

    public void update(long brokerId) throws IOException {
        Broker broker = brokerService.getAlways(brokerId);
        if (broker == null) {
            return;
        }
        this.add(Lists.newArrayList(broker));
    }

    public void add(List<Broker> list) throws IOException {
        List<BrokerEsEntity> eslist = Lists.newArrayList();
        for (int j = 0; j < list.size(); j++) {
            Broker broker = brokerService.getAlways(list.get(j).getBrokerId());
            Dealer dealer = dealerService.getAlways(list.get(j).getDealerId());
            List<Car> dealerCars = dealerService.getDealerCars(broker.getDealerId());
            List<Integer> brandIds = Lists.newArrayList();
            List<Integer> subBrandIds = Lists.newArrayList();
            List<Integer> seriesIds = Lists.newArrayList();
            List<Integer> carIds = Lists.newArrayList();
            for (int k = 0; k < dealerCars.size(); k++) {
                Car car = dealerCars.get(k);
                if (brandIds.contains(car.getBrandId()) == false) {
                    brandIds.add(car.getBrandId());
                }
                if (subBrandIds.contains(car.getSubBrandId()) == false) {
                    subBrandIds.add(car.getSubBrandId());
                }
                if (seriesIds.contains(car.getSeriesId()) == false) {
                    seriesIds.add(car.getSeriesId());
                }
                if (carIds.contains(car.getCarId()) == false) {
                    carIds.add(car.getCarId());
                }
            }
            BrokerEsEntity entity = new BrokerEsEntity();
            entity.setBrokerId(broker.getBrokerId());
            entity.setDealerId(broker.getDealerId());
            entity.setDealerName(dealer.getName());
            entity.setDealerStatus(dealer.getStatus());
            entity.setDealerType(dealer.getType());
            entity.setName(broker.getName());
            entity.setCityId(dealer.getCityId());
            entity.setProvinceId(dealer.getProvinceId());
            entity.setLatitude(dealer.getLatitude());
            entity.setLongitude(dealer.getLongitude());
            entity.setBrandIds(brandIds);
            entity.setSubBrandIds(subBrandIds);
            entity.setCarIds(carIds);
            entity.setSeriesIds(seriesIds);
            entity.setCreateTime(broker.getCreateTime().getTime());
            entity.setGrade(broker.getLevel());
            entity.setDistrictId(dealer.getDistrictId());
            entity.setMobile(broker.getMobile());
            eslist.add(entity);
        }
        try {
            esUtil.addBatch(eslist, indexName, TypeName);
        } catch (IOException e) {
            this.error("经纪人更新失败:" + e.getMessage());
        }
    }

    public List<Long> search(BrokerSearchParameters params, String max, int limit) throws IOException {
        BoolQueryBuilder query = new BoolQueryBuilder();
        if (params.getDealerId() != 0) {
            query.must(QueryBuilders.termQuery("dealerId", params.getDealerId()));
        }
        if (params.getDealerName().length() > 0) {
            query.must(QueryBuilders.matchQuery("dealerName", params.getDealerName()));
        }
        if (params.getDealerType() != 0) {
            query.must(QueryBuilders.termQuery("dealerType", params.getDealerType()));
        }
        if (params.getDealerStatus() != 0) {
            query.must(QueryBuilders.termQuery("dealerStatus", params.getDealerStatus()));
        }
        if (params.getBrokerId() != 0) {
            query.must(QueryBuilders.termQuery("brokerId", params.getBrokerId()));
        }
        if (params.getBrokerName().length() > 0) {
            query.must(QueryBuilders.matchQuery("name", params.getBrokerName()));
        }
        if (params.getBrandId() != 0) {
            query.must(QueryBuilders.termsQuery("brandIds", new int[]{params.getBrandId()}));
        }
        if (params.getSeriesId() != 0) {
            query.must(QueryBuilders.termsQuery("seriesIds", new int[]{params.getSeriesId()}));
        }
        if (params.getCarId() != 0) {
            query.must(QueryBuilders.termsQuery("carId", new int[]{params.getCarId()}));
        }
        if (params.getProvinceId() != 0) {
            query.must(QueryBuilders.termQuery("provinceId", params.getProvinceId()));
        }
        if (params.getCityId() != 0) {
            query.must(QueryBuilders.termQuery("cityId", params.getCityId()));
        }
        if (params.getDistrictId() != 0) {
            query.must(QueryBuilders.termQuery("districtId", params.getDistrictId()));
        }
        if (params.getMobile().length() > 0) {
            query.must(QueryBuilders.matchQuery("mobile", params.getMobile()));
        }
        if (max.equals("0") == false) {
            if (params.getSort().equals(BrokerSortTypeEnum.LevelTimeDesc)) {
                query.must(QueryBuilders.scriptQuery(new Script("doc[\"grade\"].value*10000000000+doc[\"createTime\"].value<" + max)));
            }
            if (params.getSort().equals(BrokerSortTypeEnum.LevelDistanceDesc)) {
                //todo:distance
            }
        }
        SearchSourceBuilder search = new SearchSourceBuilder();
        search.query(query);
        if (params.getSort().equals(BrokerSortTypeEnum.LevelTimeDesc)) {
            search.sort(SortBuilders.scriptSort(new Script("doc[\"grade\"].value*10000000000+doc[\"createTime\"].value"), "number").order(SortOrder.DESC));
        }
        if (params.getSort().equals(BrokerSortTypeEnum.LevelDistanceDesc)) {
            //todo:distance
        }
        search.size(limit);
        //termsBuilder = AggregationBuilders.terms(termsName).field(fieldName).order(Terms.Order.term(asc)).size(0);

        List<BrokerEsEntity> list = esUtil.search(BrokerEsEntity.class, indexName, TypeName, search);
        List<Long> ids = Lists.newArrayList();
        list.stream().forEach(broker -> {
            ids.add(broker.getBrokerId());
        });
        return ids;
    }

    public List<Long> search(BrokerSearchParameters params, int page, int limit) throws IOException {
        BoolQueryBuilder query = new BoolQueryBuilder();
        if (params.getDealerId() != 0) {
            query.must(QueryBuilders.termQuery("dealerId", params.getDealerId()));
        }
        if (params.getDealerName().length() > 0) {
            query.must(QueryBuilders.matchQuery("dealerName", params.getDealerName()));
        }
        if (params.getDealerType() != 0) {
            query.must(QueryBuilders.termQuery("dealerType", params.getDealerType()));
        }
        if (params.getDealerStatus() != 0) {
            query.must(QueryBuilders.termQuery("dealerStatus", params.getDealerStatus()));
        }
        if (params.getBrokerId() != 0) {
            query.must(QueryBuilders.termQuery("brokerId", params.getBrokerId()));
        }
        if (params.getBrokerName().length() > 0) {
            query.must(QueryBuilders.matchQuery("name", params.getBrokerName()));
        }
        if (params.getBrandId() != 0) {
            query.must(QueryBuilders.termsQuery("brandIds", new int[]{params.getBrandId()}));
        }
        if (params.getSeriesId() != 0) {
            query.must(QueryBuilders.termsQuery("seriesIds", new int[]{params.getSeriesId()}));
        }
        if (params.getCarId() != 0) {
            query.must(QueryBuilders.termsQuery("carId", new int[]{params.getCarId()}));
        }
        if (params.getProvinceId() != 0) {
            query.must(QueryBuilders.termQuery("provinceId", params.getProvinceId()));
        }
        if (params.getCityId() != 0) {
            query.must(QueryBuilders.termQuery("cityId", params.getCityId()));
        }
        if (params.getDistrictId() != 0) {
            query.must(QueryBuilders.termQuery("districtId", params.getDistrictId()));
        }
        if (params.getMobile().length() > 0) {
            query.must(QueryBuilders.matchQuery("mobile", params.getMobile()));
        }
        SearchSourceBuilder search = new SearchSourceBuilder();
        search.query(query);
        if (params.getSort().equals(BrokerSortTypeEnum.LevelTimeDesc)) {
            search.sort(SortBuilders.scriptSort(new Script("doc[\"grade\"].value*10000000000+doc[\"createTime\"].value"), "number").order(SortOrder.DESC));
        }
        if (params.getSort().equals(BrokerSortTypeEnum.LevelDistanceDesc)) {
            //todo:distance
        }
        search.from((page - 1) * limit);
        search.size(limit);
        List<BrokerEsEntity> list = esUtil.search(BrokerEsEntity.class, indexName, TypeName, search);
        List<Long> ids = Lists.newArrayList();
        list.stream().forEach(broker -> {
            ids.add(broker.getBrokerId());
        });
        return ids;
    }

    public int searchListCount(BrokerSearchParameters params, String max) throws IOException {
        BoolQueryBuilder query = new BoolQueryBuilder();
        if (params.getDealerId() != 0) {
            query.must(QueryBuilders.termQuery("dealerId", params.getDealerId()));
        }
        if (params.getDealerName().length() > 0) {
            query.must(QueryBuilders.matchQuery("dealerName", params.getDealerName()));
        }
        if (params.getDealerType() != 0) {
            query.must(QueryBuilders.termQuery("dealerType", params.getDealerType()));
        }
        if (params.getDealerStatus() != 0) {
            query.must(QueryBuilders.termQuery("dealerStatus", params.getDealerStatus()));
        }
        if (params.getBrokerId() != 0) {
            query.must(QueryBuilders.termQuery("brokerId", params.getBrokerId()));
        }
        if (params.getBrokerName().length() > 0) {
            query.must(QueryBuilders.matchQuery("name", params.getBrokerName()));
        }
        if (params.getBrandId() != 0) {
            query.must(QueryBuilders.termsQuery("brandIds", new int[]{params.getBrandId()}));
        }
        if (params.getSeriesId() != 0) {
            query.must(QueryBuilders.termsQuery("seriesIds", new int[]{params.getSeriesId()}));
        }
        if (params.getCarId() != 0) {
            query.must(QueryBuilders.termsQuery("carId", new int[]{params.getCarId()}));
        }
        if (params.getProvinceId() != 0) {
            query.must(QueryBuilders.termQuery("provinceId", params.getProvinceId()));
        }
        if (params.getCityId() != 0) {
            query.must(QueryBuilders.termQuery("cityId", params.getCityId()));
        }
        if (params.getDistrictId() != 0) {
            query.must(QueryBuilders.termQuery("districtId", params.getDistrictId()));
        }
        if (params.getMobile().length() > 0) {
            query.must(QueryBuilders.matchQuery("mobile", params.getMobile()));
        }
        SearchSourceBuilder search = new SearchSourceBuilder();
        search.query(query);
        if (max.equals("0") == false) {
            if (params.getSort().equals(BrokerSortTypeEnum.LevelTimeDesc)) {
                search.query(QueryBuilders.scriptQuery(new Script("doc[\"grade\"].value*10000000000+doc[\"createTime\"].value<" + max)));
            }
            if (params.getSort().equals(BrokerSortTypeEnum.LevelDistanceDesc)) {
                //todo:distance
            }
        }
        return esUtil.count(indexName, TypeName, search);
    }

    /**
     * @return the indexName
     */
    public String getIndexName() {
        return indexName;
    }

    /**
     * @param indexName the indexName to set
     */
    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

}
