package com.itblee.sqlbuilder;

import com.itblee.sqlbuilder.model.Range;

import java.util.Map;

public interface SqlMap<K extends SqlKey> extends Map<SqlStatement, Object> {
    void addScope(K key);
    Object put(K key, Object value);
    Range put(K key, Object from, Object to);
    void putAll(Map<?, ?> params, Class<K> kClass);
}
