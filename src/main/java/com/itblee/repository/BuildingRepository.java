package com.itblee.repository;

import com.itblee.entity.Building;
import com.itblee.repository.query.SqlMap;

import java.util.List;

public interface BuildingRepository extends GenericRepository<Building> {
    List<Building> findByCondition(SqlMap<?> conditions);
    Long save(Building entity);
    void update(Building entity);
    void delete(Long id);
}
