package com.itblee.repository.sqlbuilder;

import com.itblee.repository.entity.BaseEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface SqlExtractor {
    <T extends BaseEntity> List<T> extractData(ResultSet resultSet, Class<T> entityClass) throws SQLException, ReflectiveOperationException;
    List<Map<String, Object>> extractRows(ResultSet resultSet) throws SQLException;
}
