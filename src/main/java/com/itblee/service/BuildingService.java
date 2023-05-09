package com.itblee.service;

import com.itblee.model.BuildingModel;
import com.itblee.model.response.BuildingSearchResponse;

import java.util.List;
import java.util.Map;

public interface BuildingService extends GenericService<BuildingModel> {
    List<BuildingSearchResponse> findByCondition(Map<String, ?> params);
}
