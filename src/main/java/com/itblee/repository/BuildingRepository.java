package com.itblee.repository;

import com.itblee.entity.Building;
import com.itblee.repository.key.BuildingKey;

import java.util.List;

public interface BuildingRepository extends GenericRepository<Building> {
    List<Building> findByCondition(SqlBuilder<BuildingKey> conditions);
    Long save(Building entity);
    void update(Building entity);
    void delete(Long id);
}
