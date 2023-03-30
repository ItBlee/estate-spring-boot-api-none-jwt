package com.itblee.service.impl;

import com.itblee.converter.BuildingConverter;
import com.itblee.converter.ObjectConverter;
import com.itblee.entity.BuildingEntity;
import com.itblee.filter.BuildingFilter;
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
    private BuildingConverter buildingConverter;

    @Override
    public BuildingFilter findOne(Long id) {
        BuildingEntity result = buildingRepository.findOne(id);
        return buildingConverter.convertToFilter(result);
    }

    @Override
    public List<BuildingFilter> findAll() {
        List<BuildingEntity> results = buildingRepository.findAll();
        return buildingConverter.convertToFilter(results);
    }

}
