package com.itblee.service;

import com.itblee.model.dto.BuildingDTO;

import java.util.List;

public interface BuildingService {
    BuildingDTO findOne(Long id);
    List<BuildingDTO> findAll();
}
