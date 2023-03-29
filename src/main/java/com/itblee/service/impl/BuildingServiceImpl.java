package com.itblee.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itblee.entity.BuildingEntity;
import com.itblee.mapper.BuildingMapper;
import com.itblee.model.dto.BuildingDTO;
import com.itblee.repository.BuildingRepository;
import com.itblee.service.BuildingService;

@Service
public class BuildingServiceImpl implements BuildingService {

    @Autowired
    private BuildingRepository buildingRepository;

    @Override
    public BuildingDTO findOne(Long id) {
        BuildingEntity result = buildingRepository.findOne(id);
        return BuildingMapper.mapDto(result);
    }

    @Override
    public List<BuildingDTO> findAll() {
        List<BuildingEntity> results = buildingRepository.findAll();
        return BuildingMapper.mapDto(results);
    }

}
