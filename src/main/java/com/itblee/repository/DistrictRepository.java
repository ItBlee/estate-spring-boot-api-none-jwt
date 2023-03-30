package com.itblee.repository;

import com.itblee.entity.DistrictEntity;

import java.util.List;

public interface DistrictRepository extends GenericRepository<DistrictEntity> {
    DistrictEntity findOne(Long id);
    List<DistrictEntity> findAll();
}
