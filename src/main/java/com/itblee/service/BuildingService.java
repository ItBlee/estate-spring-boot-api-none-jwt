package com.itblee.service;

import com.itblee.filter.BuildingFilter;
import com.itblee.model.dto.BuildingDTO;
import com.itblee.model.response.BuildingSearchResponse;

import java.util.List;

public interface BuildingService {
    BuildingFilter findOne(Long id);
    List<BuildingFilter> findAll();
}
