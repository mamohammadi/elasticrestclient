package com.github.mamfj.elasticrestclient.mappers;

import java.util.List;

public interface ElasticQueryResultMapper<T> {

    public List<T> map(String elasticJsonResult, Class<T> tClass);
}
