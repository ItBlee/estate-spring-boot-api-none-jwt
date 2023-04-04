package com.itblee.service.impl;

import com.itblee.entity.Building;
import com.itblee.mapper.BuildingMapper;
import com.itblee.model.dto.BuildingDTO;
import com.itblee.model.request.BuildingSearchRequest;
import com.itblee.model.response.BuildingSearchResponse;
import com.itblee.repository.BuildingRepository;
import com.itblee.repository.conditions.ConditionKey;
import com.itblee.repository.conditions.impl.SqlConditionsImpl;
import com.itblee.repository.conditions.impl.SqlConditionsImpl.RangeValue;
import com.itblee.service.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BuildingServiceImpl implements BuildingService {

    @Autowired
    private BuildingRepository buildingRepository;

    @Autowired
    private BuildingMapper buildingMapper;

    @Override
    public BuildingDTO findOne(Long id) {
        Building building = buildingRepository.findOne(id);
        return buildingMapper.toDto(building);
    }

    @Override
    public List<BuildingSearchResponse> findAll(BuildingSearchRequest request) {
        List<Building> buildings;
        SqlConditionsImpl conditions = new SqlConditionsImpl();
        conditions.put(ConditionKey.BUILDING_NAME, request.getName());
        conditions.put(ConditionKey.BUILDING_FLOOR_AREA, request.getFloorarea());
        conditions.put(ConditionKey.BUILDING_DISTRICT, request.getDistrictcode());
        conditions.put(ConditionKey.BUILDING_WARD, request.getWard());
        conditions.put(ConditionKey.BUILDING_STREET, request.getStreet());
        conditions.put(ConditionKey.BUILDING_NUMBER_OF_BASEMENT, request.getNumberofbasement());
        conditions.put(ConditionKey.BUILDING_DIRECTION, request.getDirection());
        conditions.put(ConditionKey.BUILDING_LEVEL, request.getLevel());
        conditions.put(ConditionKey.BUILDING_AREA, RangeValue.newRange(request.getAreafrom(), request.getAreato()));
        conditions.put(ConditionKey.BUILDING_RENT_PRICE, RangeValue.newRange(request.getRentpricefrom(), request.getRentpriceto()));
        conditions.put(ConditionKey.BUILDING_MANAGER_NAME, request.getManagerName());
        conditions.put(ConditionKey.BUILDING_MANAGER_PHONE, request.getManagerPhone());
        conditions.put(ConditionKey.BUILDING_STAFF, request.getStaffid());
        conditions.put(ConditionKey.BUILDING_RENT_TYPES, request.getRenttypes());
        if (!conditions.getMap().isEmpty())
            buildings = buildingRepository.findByConditions(conditions);
        else buildings = buildingRepository.findAll();
        return buildingMapper.toResponse(buildings);
    }

    @Override
    public Long save(BuildingDTO buildingDTO) {
        return null;
    }

    @Override
    public void update(BuildingDTO buildingDTO) {

    }

    @Override
    public void delete(Long id) {

    }

}
