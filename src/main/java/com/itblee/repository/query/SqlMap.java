package com.itblee.repository.query;

import java.util.Map;

public interface SqlMap<K extends ConditionKey> extends Map<K, Object> {
    Object put(K key);
    Object put(K key, Object fromValue, Object toValue);
    void put(Map<String, Object> params);
    Map<K, Object> getMap();
    Class<K> getType();
}
