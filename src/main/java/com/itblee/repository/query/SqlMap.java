package com.itblee.repository.query;

import java.util.Map;

public interface SqlMap<K extends ConditionKey> extends Map<K, Object> {
    Object put(K key);
    Object put(K key, Object fromValue, Object toValue);
    void putAll(Map<String, Object> params, Class<K> type);
}
