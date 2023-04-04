package com.itblee.repository.conditions.impl;

import com.itblee.repository.conditions.ConditionKey;
import com.itblee.repository.conditions.SqlConditions;
import com.itblee.utils.StringUtils;

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
    public StringBuilder generateWhereClauseSQL() {
        return generateWhereClauseSQL(null);
    }

    @Override
    public StringBuilder generateWhereClauseSQL(StringBuilder builder) {
        if (builder == null)
            builder = new StringBuilder();
        //check if WHERE clause existed.
        if (StringUtils.containsIgnoreCase(builder.toString(), " WHERE ")) {
            return new StringBuilder();
        } else
            builder.append(" WHERE ");

        Iterator<Map.Entry<ConditionKey, Object>> iterator = getIterator();
        List<String> clauses = new ArrayList<>();

        //convert elements of Map(ConditionKey, value) to query clause after WHERE.
        while (iterator.hasNext()) {
            Map.Entry<ConditionKey, Object> entry = iterator.next();
            StringBuilder clause = new StringBuilder();
            //add column name (example: building.name)
            clause.append(entry.getKey().getValue());
            clause.append(" ");

            //add operator and values
            if (entry.getValue() instanceof String) {
                //"building.name LIKE CONCAT('%','name','%')"
                clause.append("LIKE CONCAT('%','").append(entry.getValue()).append("','%')");
            }
            else if (entry.getValue() instanceof Integer) {
                //"building.floorarea = 5"
                clause.append("= ").append(entry.getValue());
            }
            else if (entry.getValue() instanceof RangeValue) {
                RangeValue range = (RangeValue) entry.getValue();
                if (range.from == null || range.from <= 0) {
                    //"building.floorarea <= 5"
                    clause.append("<= ").append(range.to);
                } else if (range.to == null || range.to <= 0) {
                    //"building.floorarea >= 5"
                    clause.append(">= ").append(range.from);
                } else
                    //"building.floorarea BETWEEN 5 AND 10"
                    clause.append("BETWEEN ").append(range.from).append(" AND ").append(range.to);
            }
            else if (entry.getValue() instanceof String[]) {
                String inList = String.join("','", (String[]) entry.getValue());
                //"renttype IN ('a','b','c')"
                clause.append("IN ('").append(inList).append("')");
            }
            clauses.add(clause + " ");
        }

        builder.append(String.join(" AND ", clauses));
        return builder;
    }

}
