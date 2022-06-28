package com.github.mamfj.elasticrestclient.mappers;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class ElasticNativeQueryResultMapperImpl<T> implements ElasticNativeQueryResultMapper<T> {

    @Override
    public Object mapAggregations(String elasticJsonResult, Class<T> tClass) throws Exception {
        //Only mapping result of json to class
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        objectMapper.enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_VALUES);
        objectMapper.enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS);
        objectMapper.enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES);
        return objectMapper.readValue(elasticJsonResult,tClass);

    }
}
