package com.itblee.service.impl;

import com.itblee.converter.BuildingConverter;
import com.itblee.entity.Building;
import com.itblee.model.BuildingModel;
import com.itblee.model.response.BuildingSearchResponse;
import com.itblee.repository.BuildingRepository;
import com.itblee.repository.builder.SqlMap;
import com.itblee.repository.builder.impl.LinkedSqlMap;
import com.itblee.repository.builder.key.BuildingKey;
import com.itblee.service.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class BuildingServiceImpl implements BuildingService {

    @Autowired
    private BuildingRepository buildingRepository;

    @Autowired
    private BuildingConverter buildingConverter;

    @Override
    public Optional<BuildingModel> findOne(Long id) {
        SqlMap<BuildingKey> conditions = new LinkedSqlMap<>();
        conditions.addScope(BuildingKey.ALL);
        conditions.put(BuildingKey.ID, id);
        List<Building> results = buildingRepository.findByCondition(conditions);
        if (results.isEmpty())
            return Optional.empty();
        return Optional.of(buildingConverter.toModel(results.get(0)));
    }

    @Override
    public List<BuildingSearchResponse> findByCondition(Map<String, Object> params) {
        SqlMap<BuildingKey> conditions = new LinkedSqlMap<>();
        conditions.addScope(BuildingKey.SHORTEN);
        conditions.putAll(params, BuildingKey.class);
        List<Building> results = buildingRepository.findByCondition(conditions);
        return buildingConverter.toResponse(results);
    }

    /*@Override
    public List<BuildingSearchResponse> findAll(BuildingSearchRequest request) {
        SqlConditions conditions = new SqlConditionsImpl();
        conditions.put(ConditionKey.BUILDING.NAME, request.getName());
        conditions.put(ConditionKey.BUILDING.FLOOR_AREA, request.getFloorarea());
        conditions.put(ConditionKey.BUILDING.DISTRICT, request.getDistrictcode());
        conditions.put(ConditionKey.BUILDING.WARD, request.getWard());
        conditions.put(ConditionKey.BUILDING.STREET, request.getStreet());
        conditions.put(ConditionKey.BUILDING.NUMBER_OF_BASEMENT, request.getNumberofbasement());
        conditions.put(ConditionKey.BUILDING.DIRECTION, request.getDirection());
        conditions.put(ConditionKey.BUILDING.LEVEL, request.getLevel());
        conditions.put(ConditionKey.BUILDING.AREA, request.getAreafrom(), request.getAreato());
        conditions.put(ConditionKey.BUILDING.RENT_PRICE, request.getRentpricefrom(), request.getRentpriceto());
        conditions.put(ConditionKey.BUILDING.MANAGER_NAME, request.getManagerName());
        conditions.put(ConditionKey.BUILDING.MANAGER_PHONE, request.getManagerPhone());
        conditions.put(ConditionKey.BUILDING.STAFF, request.getStaffid());
        conditions.put(ConditionKey.BUILDING.RENT_TYPES, request.getRenttypes());

        List<Building> buildings = buildingRepository.findByConditions(conditions);
        return buildingMapper.toResponse(buildings);
    }*/

    @Override
    public Long save(BuildingModel dto) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void update(BuildingModel dto) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(Long id) {
        throw new UnsupportedOperationException();
    }

}
