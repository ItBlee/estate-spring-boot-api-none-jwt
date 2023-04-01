package com.itblee.converter;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public abstract class ModelMapper {
    private final ObjectMapper mapper;

    protected ModelMapper() {
        this.mapper = new ObjectMapper();
        this.mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    public <T> T convertModel(Object object, Class<T> convertTo) {
        return mapper.convertValue(object, convertTo);
    }

    public <T, V> List<T> convertModel(List<V> objects, Class<T> convertTo) {
        List<T> list = new ArrayList<>();
        objects.forEach(o -> list.add(convertModel(o, convertTo)));
        return list;
    }
}
