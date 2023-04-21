package com.itblee.service.impl;

import com.itblee.mapper.BuildingMapper;
import com.itblee.model.dto.BuildingDTO;
import com.itblee.model.response.BuildingSearchResponse;
import com.itblee.repository.BuildingRepository;
import com.itblee.repository.entity.Building;
import com.itblee.repository.query.SqlMap;
import com.itblee.repository.query.impl.SqlConditionMap;
import com.itblee.repository.query.key.BuildingKey;
import com.itblee.service.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class BuildingServiceImpl implements BuildingService {

    @Autowired
    private BuildingRepository buildingRepository;

    @Autowired
    private BuildingMapper buildingMapper;

    @Override
    public Optional<BuildingDTO> findOne(Long id) {
        SqlMap<BuildingKey> conditions = new SqlConditionMap<>(BuildingKey.class);
        conditions.put(BuildingKey.ALL);
        conditions.put(Collections.singletonMap("id", id));
        List<Building> results = buildingRepository.findByCondition(conditions);
        if (results.isEmpty())
            return Optional.empty();
        return Optional.of(buildingMapper.toDto(results.get(0)));
    }

    @Override
    public List<BuildingSearchResponse> findByCondition(Map<String, Object> params) {
        SqlMap<BuildingKey> conditions = new SqlConditionMap<>(BuildingKey.class);
        conditions.put(BuildingKey.ALL);
        conditions.put(params);
        List<Building> results = buildingRepository.findByCondition(conditions);
        return buildingMapper.toResponse(results);
    }

    @Override
    public Long save(BuildingDTO dto) {
        return null;
    }

    @Override
    public void update(BuildingDTO dto) {

    }

    @Override
    public void delete(Long id) {

    }

}
