package com.itblee.service.impl;

import com.itblee.converter.BuildingConverter;
import com.itblee.model.BuildingModel;
import com.itblee.model.response.BuildingSearchResponse;
import com.itblee.repository.BuildingRepository;
import com.itblee.repository.entity.Building;
import com.itblee.repository.sqlbuilder.SqlMap;
import com.itblee.repository.sqlbuilder.impl.LinkedSqlMap;
import com.itblee.repository.sqlbuilder.key.BuildingKey;
import com.itblee.service.BuildingService;
import com.itblee.util.ValidateUtils;
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

    public Optional<BuildingModel> findOne(Long id) {
        ValidateUtils.requireNonNull(id);
        return buildingRepository.findById(id).map(buildingConverter::toModel);
    }

    public List<BuildingSearchResponse> findByCondition(Map<String, ?> params) {
        ValidateUtils.requireValidParams(params);
        SqlMap<BuildingKey> statements = new LinkedSqlMap<>();
        statements.addScope(BuildingKey.SHORTEN);
        statements.putAll(params, BuildingKey.class);
        List<Building> results = buildingRepository.findByCondition(statements);
        return buildingConverter.toSearchResponse(results);
    }

}
