package com.itblee.service;

import com.itblee.model.dto.BuildingDTO;
import com.itblee.model.response.BuildingSearchResponse;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface BuildingService {
    Optional<BuildingDTO> findOne(Long id);
    List<BuildingSearchResponse> findByCondition(Map<String, Object> params);
    Long save(BuildingDTO dto);
    void update(BuildingDTO dto);
    void delete(Long id);
}
