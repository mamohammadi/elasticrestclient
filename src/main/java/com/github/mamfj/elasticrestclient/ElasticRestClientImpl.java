package com.github.mamfj.elasticrestclient;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mamfj.elasticrestclient.entity.ElasticRawResult;
import com.github.mamfj.elasticrestclient.mappers.ElasticQueryResultMapper;
import com.github.mamfj.elasticrestclient.mappers.ElasticQueryResultMapperImpl;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;

import java.util.ArrayList;
import java.util.List;

public class ElasticRestClientImpl implements ElasticRestClient {
    protected final ElasticsearchOperations elasticsearchOperations;
    protected final RestHighLevelClient client;

    public ElasticRestClientImpl(ElasticsearchOperations elasticsearchOperations, RestHighLevelClient client) {
        this.elasticsearchOperations = elasticsearchOperations;
        this.client = client;
    }

    @Override
    public <T> Page<T> ExecuteSqlQuery(String query, Class<T> tClass, Pageable pageable) {
        RestClient low = client.getLowLevelClient();
        Request request = new Request(
                "POST",
                "/_sql?format=json");
        request.setJsonEntity("{\"query\":\"" + query + "\"}");
        try {
            Response response = low.performRequest(request);
            String json = EntityUtils.toString(response.getEntity());
            ElasticQueryResultMapper<T> mapper = new ElasticQueryResultMapperImpl<>();
            List<T> resultList = mapper.map(json, tClass);
            int start = (int) pageable.getOffset();
            int end = Math.min((start + pageable.getPageSize()), resultList.size());
            return new PageImpl<>(resultList.subList(start, end), pageable, resultList.size());

        } catch (Exception ex) {

        }
        return Page.empty();
    }
    
    public <ElasticRawResultT extends ElasticRawResult, T> List<T> ExecuteNativeQuery(String index, String query, Class<ElasticRawResultT> elasticRawResultTClass, Class<T> tClass) {
        RestClient low = client.getLowLevelClient();
        Request request = new Request(
                "POST",
                index + "/_search");
        request.setJsonEntity(query);
        try {
            Response response = low.performRequest(request);
            String json = EntityUtils.toString(response.getEntity());
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
            objectMapper.enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_VALUES);
            objectMapper.enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS);
            objectMapper.enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES);
            ElasticRawResultT projection = objectMapper.readValue(json, elasticRawResultTClass);
            return projection.getItems();
        } catch (Exception ex) {
            return new ArrayList<>();
        }
    }
    
    @Override
    public <ElasticRawResultT extends ElasticRawResult, T> Page<T> ExecuteNativeQuery(String index, String query, Class<ElasticRawResultT> elasticRawResultTClass, Class<T> tClass, Pageable pageable) {
        RestClient low = client.getLowLevelClient();
        Request request = new Request(
                "POST",
                index + "/_search");
        request.setJsonEntity(query);
        try {
            Response response = low.performRequest(request);
            String json = EntityUtils.toString(response.getEntity());
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
            objectMapper.enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_VALUES);
            objectMapper.enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS);
            objectMapper.enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES);
            ElasticRawResultT projection = objectMapper.readValue(json, elasticRawResultTClass);
            List<T> resultList = projection.getItems();
            return new PageImpl<>(resultList, pageable, 0);
        } catch (Exception ex) {
            return Page.empty();
        }
    }
}
