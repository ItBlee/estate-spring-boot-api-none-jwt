package com.itblee.repository.condition;

import com.itblee.utils.CastUtils;
import com.itblee.utils.StringUtils;

import java.util.*;

public class SqlConditionMap extends AbstractSqlConditionBuilder implements SqlConditionBuilder {

    private final Map<ConditionKey, Object> map = new LinkedHashMap<>();
    private Class<? extends ConditionKey> typeFlag;

    private void checkKey(ConditionKey key) {
        if (key == null)
            throw new NullPointerException("Key required !");
        if (typeFlag == null) {
            if (key.getClass().getEnumConstants().length == 0)
                throw new IllegalStateException("Invalid key with no enum.");
            typeFlag = key.getClass();
        } else if (typeFlag != key.getClass())
            throw new IllegalStateException("Key doesn't match this map");
    }

    public void put(ConditionKey key) {
        checkKey(key);
        if (StringUtils.isBlank(key.props().getWhereColumn()))
            map.put(key, null);
    }

    public void put(ConditionKey key, Object value) {
        checkKey(key);
        Optional<?> cast = CastUtils.cast(value, key.props().getType());
        if (!cast.isPresent())
            return;
        Object o = cast.get();
        if (o instanceof CharSequence && StringUtils.isBlank((String) value))
            throw new IllegalStateException("Value of " + key.getName() + " invalid !");
        if (o instanceof CharSequence[]) {
            String[] notBlankArr = Arrays.stream((String[]) o)
                    .filter(s -> s != null && !StringUtils.isBlank(s))
                    .toArray(String[]::new);
            if (notBlankArr.length == 0)
                throw new IllegalStateException("Value of " + key.getName() + " invalid !");
            o = notBlankArr;
        }
        map.put(key, o);
    }

    public void put(ConditionKey key, Object fromValue, Object toValue) {
        checkKey(key);
        try {
            Number from = (Number) CastUtils.cast(fromValue, key.props().getType()).orElse(null);
            Number to = (Number) CastUtils.cast(toValue, key.props().getType()).orElse(null);
            map.put(key, Range.newRange(from, to));
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException("Value of " + key.getName() + " invalid: " + e.getMessage(), e);
        } catch (IllegalStateException ignored) {}
    }

    public void remove(ConditionKey key) {
        checkKey(key);
        map.remove(key);
    }

    public Map<ConditionKey, Object> getMap() {
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

}
