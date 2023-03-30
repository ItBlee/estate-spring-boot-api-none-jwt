package com.itblee.converter;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public abstract class ObjectConverter {

    public static <K, V> K convertObject(V object, Class<K> convertTo) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        return mapper.convertValue(object, convertTo);
    }

    public static <K, V> List<K> convertObject(List<V> objects, Class<K> convertTo) {
        List<K> list = new ArrayList<>();
        for (V obj : objects)
            list.add(convertObject(obj, convertTo));
        return list;
    }

}
