package com.itblee.repository;

import com.itblee.entity.Building;
import com.itblee.repository.conditions.SqlConditions;

import java.util.List;

public interface BuildingRepository extends GenericRepository<Building> {
    Building findOne(Long id);
    List<Building> findAll();
    List<Building> findByConditions(SqlConditions conditions);
    Long save(Building building);
    void update(Building building);
    void delete(Long id);
}
