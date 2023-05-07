package com.itblee.sqlbuilder.impl;

import com.itblee.exception.BadRequestException;
import com.itblee.sqlbuilder.SqlKey;
import com.itblee.sqlbuilder.SqlMap;
import com.itblee.sqlbuilder.SqlStatement;
import com.itblee.sqlbuilder.model.ForwardingMap;
import com.itblee.sqlbuilder.model.Range;
import com.itblee.util.*;

import java.util.LinkedHashMap;
import java.util.Map;

import static com.itblee.sqlbuilder.model.Range.RANGE_FROM;
import static com.itblee.sqlbuilder.model.Range.RANGE_TO;

public class LinkedSqlMap<K extends SqlKey> extends ForwardingMap<SqlStatement, Object> implements SqlMap<K> {

	private static final long serialVersionUID = -6418551830238036585L;

    public LinkedSqlMap() {
        super(new LinkedHashMap<>());
    }

    @Override
    public void addScope(K key) {
        ValidateUtils.requireNonNull(key);
        ValidateUtils.requireNonNull(key.getStatement());
        if (!key.isScope())
            throw new IllegalArgumentException("Required Scope not key.");
        super.put(key.getStatement(), null);
    }

    @Override
    public Object put(SqlStatement statement, Object value) {
        ValidateUtils.requireNonNull(statement);
        ValidateUtils.requireValid(value);
        return super.put(statement, value);
    }

    @Override
    public Object put(K key, Object value) {
        ValidateUtils.requireNonNull(key);
        ValidateUtils.requireNonNull(value);
        try {
            if (key.isMarker() || key.isScope())
                throw new IllegalArgumentException("Unsupported param.");
            Object cast = CastUtils.cast(value, key.getType())
                    .orElseThrow(() -> new IllegalArgumentException("Unsupported type."));
            return put(key.getStatement(), cast);
        } catch (IllegalArgumentException e) {
            throw new BadRequestException(key.getParamName() + ": " + e.getMessage());
        }
    }

    @Override
    public Range put(K key, Object from, Object to) {
        ValidateUtils.requireNonNull(key);
        try {
            if (key.isMarker() || key.isScope())
                throw new IllegalStateException("Unsupported param.");
            Number fromNum = (Number) CastUtils.cast(from, key.getType()).orElse(null);
            Number toNum = (Number) CastUtils.cast(to, key.getType()).orElse(null);
            return (Range) super.put(key.getStatement(), Range.valueOf(fromNum, toNum));
        } catch (IllegalArgumentException | IllegalStateException e) {
            throw new BadRequestException(key.getParamName() + " invalid: " + e.getMessage());
        }
    }

    @Override
    public void putAll(Map<? extends SqlStatement, ?> m) {
        m.forEach(this::put);
    }

    @Override
    public void putAll(Map<?, ?> params, Class<K> kClass) {
        ValidateUtils.requireNonNull(params);
        ValidateUtils.requireNonNull(kClass);
        if (params.isEmpty())
            return;
        params.forEach((param, val) -> {
            String keyName = StringUtils.removeIfLast(param.toString(), RANGE_FROM, RANGE_TO);
            K key = MapUtils.get(SqlKeyUtils.getInstance(kClass), keyName, kClass);
            if (key == null)
                throw new BadRequestException(param + ": Unsupported.");
            if (!key.isRange())
                put(key, val);
            else put(key, params.getOrDefault(key.getParamName() + RANGE_FROM, null),
                    params.getOrDefault(key.getParamName() + RANGE_TO, null));
        });
    }

    @Override
    public Object putIfAbsent(SqlStatement statement, Object value) {
        Object stmt = getOrDefault(statement, null);
        if (stmt == null)
            stmt = this.put(statement, value);
        return stmt;
    }

}
