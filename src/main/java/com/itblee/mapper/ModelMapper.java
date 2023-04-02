package com.itblee.mapper;

import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ModelMapper {
    private static ModelMapper instance;
    private final ObjectMapper mapper;

    private ModelMapper() {
        this.mapper = new ObjectMapper();
        this.mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    public static ModelMapper getInstance() {
        if (instance == null) {
            instance = new ModelMapper();
        }
        return instance;
    }

    public <T> T convertModel(Object object, Class<T> convertTo) {
        return mapper.convertValue(object, convertTo);
    }

    public <T, V> List<T> convertModel(Collection<V> objects, Class<T> convertTo) {
        List<T> list = new ArrayList<>();
        objects.forEach(o -> list.add(convertModel(o, convertTo)));
        return list;
    }

}
