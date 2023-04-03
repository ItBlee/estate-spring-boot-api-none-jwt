package com.itblee.service.impl;

import com.itblee.entity.Building;
import com.itblee.mapper.BuildingMapper;
import com.itblee.model.dto.BuildingDTO;
import com.itblee.model.response.BuildingSearchResponse;
import com.itblee.repository.BuildingRepository;
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
    public List<BuildingSearchResponse> findAll() {
        List<Building> buildings = buildingRepository.findAll();
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
