package com.itblee.repository.custom;

import com.itblee.repository.entity.Building;

import java.util.List;
import java.util.Map;

public interface BuildingRepositoryCustom {
    List<Building> findByCondition(Map<?, ?> conditions);
}
