package com.itblee.repository;

import com.itblee.entity.BuildingEntity;

import java.util.List;

public interface BuildingRepository extends GenericRepository<BuildingEntity> {
    BuildingEntity findOne(Long id);
    List<BuildingEntity> findAll();
}
