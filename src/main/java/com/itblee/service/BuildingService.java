package com.itblee.service;

import com.itblee.model.BuildingModel;
import com.itblee.model.response.BuildingSearchResponse;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface BuildingService {
    Optional<BuildingModel> findOne(Long id);
    List<BuildingSearchResponse> findByCondition(Map<String, ?> params);
    Long save(BuildingModel model);
    void update(BuildingModel model);
    void delete(Long id);
}
