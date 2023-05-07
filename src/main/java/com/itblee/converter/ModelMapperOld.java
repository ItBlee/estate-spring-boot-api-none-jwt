package com.itblee.converter;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ModelMapperOld {
    private final ObjectMapper mapper;

    private ModelMapperOld() {
        this.mapper = new ObjectMapper();
        this.mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        //this.mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    private static final class InstanceHolder {
        static final ModelMapperOld instance = new ModelMapperOld();
    }

    public static ModelMapperOld getInstance() {
        return InstanceHolder.instance;
    }

    public <T> T mapModel(Object object, Class<T> mapTo) {
        return this.mapper.convertValue(object, mapTo);
    }

    public <T, E> List<T> mapModel(Collection<E> objects, Class<T> mapTo) {
        List<T> list = new ArrayList<>();
        objects.forEach(o -> list.add(mapModel(o, mapTo)));
        return list;
    }

}
