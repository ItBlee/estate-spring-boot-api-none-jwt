package com.itblee.repository;

import com.itblee.repository.entity.Building;

import java.util.List;
import java.util.Map;

public interface BuildingRepository extends GenericRepository<Building, Long> {

    List<Building> findByCondition(Map<?, ?> conditions);

}
