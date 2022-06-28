package com.github.mamfj.elasticrestclient.mappers;

public interface ElasticNativeQueryResultMapper<T> {

    Object mapAggregations(String elasticJsonResult, Class<T> tClass) throws Exception;
}
