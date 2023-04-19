package com.itblee.repository.impl;

import com.itblee.exception.BadRequestException;
import com.itblee.repository.ConditionKey;
import com.itblee.utils.CastUtils;
import com.itblee.utils.MapUtils;
import com.itblee.utils.StringUtils;

import java.util.*;

public class SqlConditionMap<T extends ConditionKey> extends AbstractSqlBuilder<T> implements Map<T, Object> {

    private final Map<T, Object> map = new LinkedHashMap<>();

    public SqlConditionMap(Class<T> type) {
        super(type);
    }

    @Override
    public Object put(T key, Object value) {
        if (value == null)
            return null;
        if (key == null)
            throw new IllegalArgumentException("Key required !");
        Optional<?> cast = CastUtils.cast(value, key.props().getType());
        if (!cast.isPresent())
            return null;
        Object o = cast.get();
        if (o instanceof CharSequence && StringUtils.isBlank((String) value))
            throw new IllegalStateException("Value of " + key.getName() + " invalid !");
        if (o instanceof CharSequence[]) {
            String[] notBlankArr = Arrays.stream((String[]) o)
                    .filter(StringUtils::isNotBlank)
                    .toArray(String[]::new);
            if (notBlankArr.length == 0)
                throw new IllegalStateException("Value of " + key.getName() + " invalid !");
            o = notBlankArr;
        }
        return map.put(key, o);
    }

    public Object put(T key, Object fromValue, Object toValue) {
        if (fromValue == null && toValue == null)
            return null;
        if (key == null)
            throw new IllegalArgumentException("Key required !");
        try {
            Number from = (Number) CastUtils.cast(fromValue, key.props().getType()).orElse(null);
            Number to = (Number) CastUtils.cast(toValue, key.props().getType()).orElse(null);
            return map.put(key, Range.newRange(from, to));
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException("Value of " + key.getName() + " invalid: " + e.getMessage(), e);
        } catch (IllegalStateException ignored) {
            return null;
        }
    }

    public Object put(T key) {
        if (key == null)
            throw new IllegalArgumentException("Key required !");
        if (StringUtils.isBlank(key.props().getWhereColumn()))
            return map.put(key, null);
        return null;
    }

    public void put(Map<String, Object> params) {
        if (params == null)
            throw new IllegalArgumentException();
        if (params.isEmpty())
            return;
        Map<String, ConditionKey> keyMap = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        for (ConditionKey key : typeFlag.getEnumConstants()) {
            if (key.getParam() != null)
                keyMap.put(key.getParam(), key);
        }
        for (Map.Entry<String, Object> param : params.entrySet()) {
            T key = MapUtils.get(keyMap, param.getKey(), typeFlag);
            if (key == null)
                continue;
            try {
                if (key.isRange())
                    put(key, MapUtils.get(params, key.getParam() + "from"),
                            MapUtils.get(params, key.getParam() + "to"));
                else put(key, MapUtils.get(params, key.getParam()));
            } catch (IllegalStateException e) {
                throw new BadRequestException(e);
            }
        }
    }

    @Override
    public void putAll(Map<? extends T, ?> m) {
        m.forEach(this::put);
    }

    public Map<T, Object> getMap() {
        return Collections.unmodifiableMap(map);
    }

    @Override
    public StringBuilder buildSelectQuery() {
        return buildSelectQuery(getMap().keySet());
    }

    @Override
    public StringBuilder buildJoinQuery() {
        return buildJoinQuery(getMap().keySet());
    }

    @Override
    public StringBuilder buildWhereQuery() {
        return buildWhereQuery(getMap());
    }

    @Override
    public Object get(Object key) {
        return map.get(key);
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return map.containsValue(value);
    }

    @Override
    public Object remove(Object key) {
        return map.remove(key);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public Set<T> keySet() {
        return map.keySet();
    }

    @Override
    public Collection<Object> values() {
        return map.values();
    }

    @Override
    public Set<Entry<T, Object>> entrySet() {
        return map.entrySet();
    }
}
