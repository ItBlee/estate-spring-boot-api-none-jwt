package com.itblee.repository.query.impl;

import com.itblee.exception.BadRequestException;
import com.itblee.repository.query.ConditionKey;
import com.itblee.repository.query.SqlMap;
import com.itblee.utils.CastUtils;
import com.itblee.utils.MapUtils;
import com.itblee.utils.StringUtils;

import java.util.*;

public class SqlConditionMap<K extends ConditionKey> extends LinkedHashMap<K, Object> implements SqlMap<K> {

    protected Class<K> typeFlag;

    public SqlConditionMap(Class<K> type) {
        if (type == null)
            throw new IllegalArgumentException("Type required");
        if (type.getEnumConstants().length == 0)
            throw new IllegalStateException("Invalid key with no enum.");
        this.typeFlag = type;
    }

    private void check(K key) {
        if (key == null)
            throw new IllegalArgumentException("Key required !");
    }

    @Override
    public Object put(K key) {
        check(key);
        if (StringUtils.isBlank(key.props().getWhereColumn()))
            return super.put(key, null);
        return null;
    }

    @Override
    public Object put(K key, Object value) {
        if (value == null)
            return null;
        check(key);
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
        return super.put(key, o);
    }

    @Override
    public Object put(K key, Object fromValue, Object toValue) {
        if (fromValue == null && toValue == null)
            return null;
        check(key);
        try {
            Number from = (Number) CastUtils.cast(fromValue, key.props().getType()).orElse(null);
            Number to = (Number) CastUtils.cast(toValue, key.props().getType()).orElse(null);
            return put(key, AbstractSqlBuilder.Range.newRange(from, to));
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException("Value of " + key.getName() + " invalid: " + e.getMessage(), e);
        } catch (IllegalStateException ignored) {
            return null;
        }
    }

    @Override
    public Object put(Map<String, Object> params) {
        if (params == null)
            throw new IllegalArgumentException();
        if (params.isEmpty())
            return null;
        Map<String, ConditionKey> keyMap = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        for (ConditionKey key : typeFlag.getEnumConstants()) {
            if (key.getParam() != null)
                keyMap.put(key.getParam(), key);
        }
        for (Map.Entry<String, Object> param : params.entrySet()) {
            K key = MapUtils.get(keyMap, param.getKey(), typeFlag);
            if (key == null)
                continue;
            try {
                if (key.isRange())
                    return put(key, MapUtils.get(params, key.getParam() + "from"),
                            MapUtils.get(params, key.getParam() + "to"));
                else return put(key, MapUtils.get(params, key.getParam()));
            } catch (IllegalStateException e) {
                throw new BadRequestException(e);
            }
        }
        return null;
    }

    @Override
    public void putAll(Map<? extends K, ?> m) {
        m.forEach(this::put);
    }

    @Override
    public Map<K, Object> getMap() {
        return Collections.unmodifiableMap(this);
    }

    @Override
    public Class<K> getType() {
        return typeFlag;
    }

}
