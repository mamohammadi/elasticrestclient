package com.github.mamfj.elasticrestclient.entity;

import java.util.List;

public interface ElasticRawResult<T> {
    List<T> getItems();
}
