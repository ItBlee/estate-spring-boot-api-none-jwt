package com.itblee.converter;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public abstract class ObjectConverter {
    private final ObjectMapper mapper;

    protected ObjectConverter() {
        this.mapper = new ObjectMapper();
        this.mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    public <T> T convertObject(Object object, Class<T> convertTo) {
        return mapper.convertValue(object, convertTo);
    }

    public <T, V> List<T> convertObject(List<V> objects, Class<T> convertTo) {
        List<T> list = new ArrayList<>();
        for (V obj : objects)
            list.add(convertObject(obj, convertTo));
        return list;
    }

}
