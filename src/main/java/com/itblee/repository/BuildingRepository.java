package com.itblee.repository;

import com.itblee.entity.Building;

import java.util.List;

public interface BuildingRepository extends GenericRepository<Building> {
    Building findOne(Long id);
    List<Building> findAll();
    Long save(Building building);
    void update(Building building);
    void delete(Long id);
}
