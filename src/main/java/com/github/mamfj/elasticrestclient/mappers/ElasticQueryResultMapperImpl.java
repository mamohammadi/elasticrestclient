package com.github.mamfj.elasticrestclient.mappers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mamfj.elasticrestclient.converters.StringToPrimitiveTypeConverter;
import com.github.mamfj.elasticrestclient.entity.ElasticProjectionObject;
import com.github.mamfj.elasticrestclient.entity.Pair;
import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.validator.internal.util.StringHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ElasticQueryResultMapperImpl<T> implements ElasticQueryResultMapper<T> {

    @Override
    public List<T> map(String elasticJsonResult, Class<T> tClass) {
        List<T> result = new ArrayList<>();
        if(!StringHelper.isNullOrEmptyString(elasticJsonResult)){
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                ElasticProjectionObject obj = objectMapper.readValue(elasticJsonResult, ElasticProjectionObject.class);
                for (int row=0;row<obj.getRows().stream().count();row++) {
                    T model = tClass.getDeclaredConstructor().newInstance();
                    Integer i = 0;
                    for (Pair p : obj.getColumns()) {
                        String valueStr = obj.getRows().get(row).get(i++);
                        Class<?> propertyType = PropertyUtils.getPropertyType(model, p.getName());
                        if(propertyType != null) {
                            if(propertyType.isPrimitive() || propertyType == Date.class || propertyType == String.class) {
                                PropertyUtils.setSimpleProperty(model, p.getName(), StringToPrimitiveTypeConverter.convert(valueStr, propertyType));
                            }
                            else if(propertyType.isArray()){
                                Class<?> elementType = propertyType.getComponentType();

                            }
                            else{

                            }
                        }
                    }
                    result.add(model);
                }
            }catch (Exception ex){

            }
        }
        return result;
    }
}
