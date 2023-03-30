package com.itblee.service.impl;

import com.itblee.converter.BuildingConverter;
import com.itblee.converter.DistrictConverter;
import com.itblee.entity.BuildingEntity;
import com.itblee.entity.DistrictEntity;
import com.itblee.filter.BuildingFilter;
import com.itblee.filter.DistrictFilter;
import com.itblee.model.dto.DistrictDTO;
import com.itblee.repository.BuildingRepository;
import com.itblee.repository.DistrictRepository;
import com.itblee.service.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BuildingServiceImpl implements BuildingService {

    @Autowired
    private BuildingRepository buildingRepository;

    @Autowired
    private DistrictRepository districtRepository;

    @Autowired
    private BuildingConverter buildingConverter;

    @Autowired
    private DistrictConverter districtConverter;

    @Override
    public BuildingFilter findOne(Long id) {
        BuildingEntity result = buildingRepository.findOne(id);
        if (result == null)
            return null;
        BuildingFilter building = buildingConverter.convertToFilter(result);
        DistrictEntity districtEntity = districtRepository.findOne(result.getDistrictID());
        DistrictFilter districtFilter = districtConverter.convertToFilter(districtEntity);
        building.setDistrict(districtFilter);
        return building;
    }

    @Override
    public List<BuildingFilter> findAll() {
        List<BuildingFilter> list = new ArrayList<>();
        List<BuildingEntity> results = buildingRepository.findAll();
        for (BuildingEntity result : results) {
            BuildingFilter building = buildingConverter.convertToFilter(result);
            DistrictEntity districtEntity = districtRepository.findOne(result.getDistrictID());
            DistrictFilter districtFilter = districtConverter.convertToFilter(districtEntity);
            building.setDistrict(districtFilter);
            list.add(building);
        }
        return list;
    }

    @Override
    public Long save(BuildingFilter buildingFilter) {
        return null;
    }

    @Override
    public void update(BuildingFilter buildingFilter) {

    }

    @Override
    public void delete(Long id) {

    }

}
