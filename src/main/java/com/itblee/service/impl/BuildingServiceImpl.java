package com.itblee.service.impl;

import com.itblee.entity.Building;
import com.itblee.mapper.BuildingMapper;
import com.itblee.model.dto.BuildingDTO;
import com.itblee.model.response.BuildingSearchResponse;
import com.itblee.repository.BuildingRepository;
import com.itblee.repository.condition.SqlConditionMap;
import com.itblee.repository.condition.key.BuildingKey;
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
        Map<String, Object> params = Collections.singletonMap("id", id);
        SqlConditionMap condition = buildingMapper.toCondition(params);
        condition.put(BuildingKey.ALL);
        List<Building> result = buildingRepository.findByCondition(condition);
        if (result.isEmpty())
            return Optional.empty();
        return Optional.of(buildingMapper.toDto(result.get(0)));
    }

    @Override
    public List<BuildingSearchResponse> findByCondition(Map<String, Object> params) {
        SqlConditionMap condition = buildingMapper.toCondition(params);
        condition.put(BuildingKey.ALL);
        List<Building> results = buildingRepository.findByCondition(condition);
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
