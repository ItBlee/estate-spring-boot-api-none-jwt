package com.itblee.repository.builder.impl;

import com.itblee.exception.BadRequestException;
import com.itblee.repository.builder.SqlMap;
import com.itblee.repository.builder.SqlKey;
import com.itblee.repository.builder.util.Range;
import com.itblee.repository.builder.util.ForwardingMap;
import com.itblee.utils.CastUtils;
import com.itblee.utils.MapUtils;
import com.itblee.utils.StringUtils;

import java.util.*;

import static com.itblee.repository.builder.util.Range.RANGE_FROM;
import static com.itblee.repository.builder.util.Range.RANGE_TO;

public class LinkedSqlMap<K extends SqlKey> extends ForwardingMap<K, Object> implements SqlMap<K> {

	private static final long serialVersionUID = -6418551830238036585L;

	public LinkedSqlMap() {
        super(new LinkedHashMap<>());
    }

    private void check(K key) {
        Objects.requireNonNull(key, "Key required !");
        Objects.requireNonNull(key.getStatement(), "Missing SqlQuery from Key.");
    }

    @Override
    public Object addScope(K key) {
        check(key);
        if (!key.isScope())
            throw new IllegalArgumentException("Required Scope not key.");
        return super.put(key, null);
    }

    @Override
    public Object put(K key, Object value) {
        check(key);
        try {
            if (key.isMarker())
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
            if (key.isMarker())
                throw new IllegalStateException("Unsupported.");
            Number from = (Number) CastUtils.cast(fromValue, key.getType()).orElse(null);
            Number to = (Number) CastUtils.cast(toValue, key.getType()).orElse(null);
            return super.put(key, Range.valueOf(from, to));
        } catch (IllegalArgumentException | IllegalStateException e) {
            throw new BadRequestException(key.getParamName() + " invalid: " + e.getMessage());
        }
    }

    @Override
    public void putAll(Map<String, ?> params, Class<K> type) {
        Objects.requireNonNull(params);
        Objects.requireNonNull(type);
        if (params.isEmpty())
            return;
        Map<String, SqlKey> keyMap = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        for (SqlKey key : type.getEnumConstants()) {
            keyMap.put(key.getParamName(), key);
        }
        for (Map.Entry<String, ?> param : params.entrySet()) {
            String keyName = StringUtils.removeIfLast(param.getKey(), RANGE_FROM, RANGE_TO);
            K key = MapUtils.get(keyMap, keyName, type);
            if (key == null || key.getStatement() == null)
                throw new BadRequestException(param.getKey() + " invalid: Unsupported.");
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
