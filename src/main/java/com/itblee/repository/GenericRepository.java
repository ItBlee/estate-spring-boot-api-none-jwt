package com.itblee.repository;

import com.itblee.entity.BaseEntity;

import java.util.List;
import java.util.Map;

public interface GenericRepository<T extends BaseEntity> {
    List<T> findByCondition(Map<?, ?> conditions);
    Long save(T entity);
    void update(T entity);
    void delete(Long id);
}
