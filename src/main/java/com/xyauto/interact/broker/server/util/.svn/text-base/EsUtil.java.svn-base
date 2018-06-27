package com.xyauto.interact.broker.server.util;

import com.mysql.jdbc.StringUtils;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.JestResult;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.core.Bulk;
import io.searchbox.core.BulkResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import io.searchbox.core.search.aggregation.MetricAggregation;
import io.searchbox.core.search.aggregation.TermsAggregation;
import io.searchbox.indices.CreateIndex;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.List;

import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class EsUtil {

    @Value("${xyauto.broker.search.host}")
    private String host;

    @Autowired
    private JestClient jestClient;

    public HttpClientConfig clientConfig() {
        List<String> hosts = StringUtils.split(this.getHost(),",",true);
        LinkedHashSet servers = new LinkedHashSet();
        hosts.forEach(item->{
            String connectionUrl = "http://" + item;
            servers.add(connectionUrl);
        });
        HttpClientConfig clientConfig = new HttpClientConfig.Builder(servers).multiThreaded(true).build();
        return clientConfig;
    }

    @Bean
    public JestClient jestClient() {
        JestClientFactory factory = new JestClientFactory();
        factory.setHttpClientConfig(clientConfig());
        return factory.getObject();
    }

    public void createIndex(String indexName) throws IOException {
        CreateIndex createIndex = new CreateIndex.Builder(indexName).build();
        getJestClient().execute(createIndex);
    }

    public <T> void add(String indexName, String typeName, List<T> list) throws IOException {
        for (T obj : list) {
            Index index = new Index.Builder(obj).index(indexName).type(typeName).build();
            JestResult result = getJestClient().execute(index);
        }
    }

    public <T> void addBatch(List<T> t, String indexName, String typeName)
            throws IOException {
        Bulk.Builder bulk = new Bulk.Builder().defaultIndex(indexName).defaultType(typeName);
        for (T item : t) {
            Index index = new Index.Builder(item).index(indexName).type(typeName).build();
            bulk.addAction(index);
        }
        BulkResult br = getJestClient().execute(bulk.build());
    }

    public <T> List<T> search(Class<T> clazz, String indexName, String typeName, SearchSourceBuilder queryBuilder) throws IOException {
        Search search = new Search.Builder(queryBuilder.toString()).addIndex(indexName).addType(typeName).build();
        SearchResult result = getJestClient().execute(search);
        return result.getSourceAsObjectList(clazz);
    }

    public MetricAggregation search(String indexName, String typeName, SearchSourceBuilder queryBuilder) throws IOException {
        Search search = new Search.Builder(queryBuilder.toString()).addIndex(indexName).addType(typeName).build();
        SearchResult result = getJestClient().execute(search);
        return result.getAggregations();
    }

    public int countGroupMobile(String indexName, String typeName, SearchSourceBuilder queryBuilder) throws IOException {
        Search search = new Search.Builder(queryBuilder.toString()).addIndex(indexName).addType(typeName).build();
        SearchResult result = getJestClient().execute(search);
//        Object count = JSONObject.parseObject(JSONObject.parseObject(((SearchResult) result).getJsonObject().get("aggregations").toString()).get("group").toString()).get("sum_other_doc_count");
        TermsAggregation term = result.getAggregations().getTermsAggregation("group");
        int count = term.getBuckets().size();
        return count;
    }

    public int count(String indexName, String typeName, SearchSourceBuilder queryBuilder) throws IOException {
        Search search = new Search.Builder(queryBuilder.toString()).addIndex(indexName).addType(typeName).build();
        SearchResult result = getJestClient().execute(search);
        return result.getTotal();
    }

    public SearchResult result(String indexName, String typeName, SearchSourceBuilder queryBuilder) throws IOException {
        Search search = new Search.Builder(queryBuilder.toString()).addIndex(indexName).addType(typeName).build();
        SearchResult result = getJestClient().execute(search);
        return result;
    }

    /**
     * @return the host
     */
    public String getHost() {
        return host;
    }

    /**
     * @param host the host to set
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     * @return the jestClient
     */
    public JestClient getJestClient() {
        return jestClient;
    }

    /**
     * @param jestClient the jestClient to set
     */
    public void setJestClient(JestClient jestClient) {
        this.jestClient = jestClient;
    }

}
