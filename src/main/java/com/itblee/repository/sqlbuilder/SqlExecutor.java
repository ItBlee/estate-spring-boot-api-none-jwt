package com.itblee.repository.sqlbuilder;

import com.itblee.repository.entity.BaseEntity;

import java.util.List;

public interface SqlExecutor {
    <T extends BaseEntity> List<T> executeQuery(String sql, Class<T> entityClass, Object... params);
    Object executeInsert(String sql, Object... params);
    void executeUpdate(String sql, Object... params);
}
