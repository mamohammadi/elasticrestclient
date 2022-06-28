package com.github.mamfj.elasticrestclient.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Field;

import java.util.List;

@Data
@NoArgsConstructor
public class ElasticProjectionObject {
    @Field(name = "columns")
    private List<Pair> columns;

    @Field(name = "rows")
    private List<List<String>> rows;

    @Field(name = "cursor")
    private String cursor;
}
