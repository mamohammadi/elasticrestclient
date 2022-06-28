package com.github.mamfj.elasticrestclient;

import com.github.mamfj.elasticrestclient.entity.ElasticRawResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

interface ElasticRestClient {
    public <T> Page<T> ExecuteSqlQuery(String query, Class<T> tClass, Pageable pageable);
        public <ElasticRawResultT extends ElasticRawResult, T> List<T> ExecuteNativeQuery(String index, String query, Class<ElasticRawResultT> elasticRawResultTClass, Class<T> tClass);
}
