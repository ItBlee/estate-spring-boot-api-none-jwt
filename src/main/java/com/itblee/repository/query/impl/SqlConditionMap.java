package com.itblee.repository.query.impl;

import com.itblee.exception.BadRequestException;
import com.itblee.repository.query.ConditionKey;
import com.itblee.repository.query.bean.Range;
import com.itblee.repository.query.SqlMap;
import com.itblee.utils.CastUtils;
import com.itblee.utils.MapUtils;
import com.itblee.utils.StringUtils;

import java.util.*;

import static com.itblee.repository.query.bean.Range.RANGE_FROM;
import static com.itblee.repository.query.bean.Range.RANGE_TO;

public class SqlConditionMap<K extends ConditionKey> extends LinkedHashMap<K, Object> implements SqlMap<K> {

    private void check(K key) {
        if (key == null)
            throw new IllegalArgumentException("Key required !");
        if (key.queryProps() == null)
            throw new IllegalStateException("Missing SqlQuery from Key.");
    }

    @Override
    public Object put(K key) {
        check(key);
        if (StringUtils.isNotBlank(key.queryProps().getWhereColumn()))
            throw new IllegalStateException("Key without value, require query not contains where clause.");
        return super.put(key, null);
    }

    @Override
    public Object put(K key, Object value) {
        check(key);
        try {
            if (StringUtils.isBlank(key.queryProps().getWhereColumn()))
                throw new IllegalStateException("Unsupported.");
            Optional<?> cast = CastUtils.cast(value, key.getType());
            if (!cast.isPresent())
                throw new IllegalArgumentException("Wrong value type.");
            boolean isBlank = cast.get() instanceof CharSequence
                    && StringUtils.isBlank(cast.get().toString());
            boolean isContainsBlank = cast.get() instanceof CharSequence[]
                    && StringUtils.containsBlank((CharSequence[]) cast.get());
            if (isBlank || isContainsBlank)
                throw new IllegalArgumentException("Contain empty value.");
            return super.put(key, cast.get());
        } catch (IllegalArgumentException | IllegalStateException e) {
            throw new BadRequestException(key.getParamName() + " invalid: " + e.getMessage());
        }
    }

    @Override
    public Object put(K key, Object fromValue, Object toValue) {
        check(key);
        try {
            if (StringUtils.isBlank(key.queryProps().getWhereColumn()))
                throw new IllegalStateException("Unsupported.");
            Number from = (Number) CastUtils.cast(fromValue, key.getType()).orElse(null);
            Number to = (Number) CastUtils.cast(toValue, key.getType()).orElse(null);
            return super.put(key, Range.newRange(from, to));
        } catch (IllegalArgumentException | IllegalStateException e) {
            throw new BadRequestException(key.getParamName() + " invalid: " + e.getMessage());
        }
    }

    @Override
    public void putAll(Map<String, Object> params, Class<K> type) {
        if (params == null || type == null)
            throw new IllegalArgumentException();
        if (params.isEmpty())
            return;
        Map<String, ConditionKey> keyMap = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        for (ConditionKey key : type.getEnumConstants()) {
            keyMap.put(key.getParamName(), key);
        }
        for (Map.Entry<String, Object> param : params.entrySet()) {
            String keyName = StringUtils.removeIfLast(param.getKey(), RANGE_FROM, RANGE_TO);
            K key = MapUtils.get(keyMap, keyName, type);
            if (key == null || key.queryProps() == null)
                throw new BadRequestException(param.getKey() + " invalid: " + "Unsupported.");
            if (!key.isRange())
                put(key, param.getValue());
            else put(key, params.getOrDefault(key.getParamName() + RANGE_FROM, null),
                        params.getOrDefault(key.getParamName() + RANGE_TO, null));
        }
    }

    @Override
    public void putAll(Map<? extends K, ?> m) {
        m.forEach(this::put);
    }

}
