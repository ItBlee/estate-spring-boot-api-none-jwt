package com.itblee.repository.conditions.impl;

import com.itblee.repository.conditions.ConditionKey;
import com.itblee.repository.conditions.SqlConditions;

import java.util.*;

public class SqlConditionsImpl implements SqlConditions {

    public static class RangeValue {
        public Integer from;
        public Integer to;

        private RangeValue(Integer from, Integer to) {
            this.from = from;
            this.to = to;
        }

        public static RangeValue newRange(Integer from, Integer to) {
            if ((from == null || from <= 0) && (to == null || to <= 0))
                return null;
            return new RangeValue(from, to);
        }
    }

    private final EnumMap<ConditionKey, Object> map = new EnumMap<>(ConditionKey.class);


    public EnumMap<ConditionKey, Object> getMap() {
        return map.clone();
    }

    public Iterator<Map.Entry<ConditionKey, Object>> getIterator() {
        return getMap().entrySet().iterator();
    }

    public void put(ConditionKey key, Object value) {
        if (value != null)
            map.put(key, value);
    }

    public void remove(ConditionKey key) {
        map.remove(key);
    }

    @Override
    public StringBuilder generateSQL() {
        return generateSQL(null);
    }

    @Override
    public StringBuilder generateSQL(StringBuilder builder) {
        if (builder == null)
            builder = new StringBuilder();
        builder.append("WHERE ");
        Iterator<Map.Entry<ConditionKey, Object>> iterator = getIterator();
        List<String> clauses = new ArrayList<>();

        while (iterator.hasNext()) {
            Map.Entry<ConditionKey, Object> entry = iterator.next();
            StringBuilder clause = new StringBuilder();
            clause.append(entry.getKey().getValue());
            clause.append(" ");

            if (entry.getValue() instanceof String) {
                clause.append("LIKE CONCAT('%','").append(entry.getValue()).append("','%')");
            }
            else if (entry.getValue() instanceof Integer) {
                clause.append("= ").append(entry.getValue());
            }
            else if (entry.getValue() instanceof RangeValue) {
                RangeValue range = (RangeValue) entry.getValue();
                if (range.from == null || range.from <= 0) {
                    clause.append("<= ").append(range.to);
                } else if (range.to == null || range.to <= 0) {
                    clause.append(">= ").append(range.from);
                } else
                    clause.append("BETWEEN ").append(range.from).append(" AND ").append(range.to);
            }
            else if (entry.getValue() instanceof Object[]) {
                String inList = String.join("','", (String[]) entry.getValue());
                clause.append("IN ('").append(inList).append("')");
            }
            clauses.add(clause + " ");
        }

        builder.append(String.join(" AND ", clauses));
        return builder;
    }

}
