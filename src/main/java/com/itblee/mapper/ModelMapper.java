package com.itblee.mapper;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ModelMapper {
    private final ObjectMapper mapper;

    private ModelMapper() {
        this.mapper = new ObjectMapper();
        this.mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    private static final class InstanceHolder {
        static final ModelMapper instance = new ModelMapper();
    }

    public static ModelMapper getInstance() {
        return InstanceHolder.instance;
    }

    public <T> T mapModel(Object object, Class<T> mapTo) {
        return this.mapper.convertValue(object, mapTo);
    }

    public <T, V> List<T> mapModel(Collection<V> objects, Class<T> mapTo) {
        List<T> list = new ArrayList<>();
        objects.forEach(o -> list.add(mapModel(o, mapTo)));
        return list;
    }

}
