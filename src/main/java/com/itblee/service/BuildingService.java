package com.itblee.service;

import com.itblee.model.dto.BuildingDTO;
import com.itblee.model.response.BuildingSearchResponse;

import java.util.List;

public interface BuildingService {
    BuildingDTO findOne(Long id);
    List<BuildingSearchResponse> findAll();
    Long save(BuildingDTO buildingDTO);
    void update(BuildingDTO buildingDTO);
    void delete(Long id);
}
