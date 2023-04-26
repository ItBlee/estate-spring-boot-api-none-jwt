package com.itblee.repository.builder;

import java.util.Map;

public interface SqlMap<K extends SqlKey> extends Map<K, Object> {
    Object addScope(K key);
    Object put(K key, Object fromValue, Object toValue);
    void putAll(Map<String, ?> params, Class<K> type);
}
