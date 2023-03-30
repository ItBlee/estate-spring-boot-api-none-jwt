package com.itblee.service;

import com.itblee.filter.BuildingFilter;

import java.util.List;

public interface BuildingService {
    BuildingFilter findOne(Long id);
    List<BuildingFilter> findAll();
    Long save(BuildingFilter buildingFilter);
    void update(BuildingFilter buildingFilter);
    void delete(Long id);
}
