package com.itblee.mapper;

import com.itblee.entity.Building;
import com.itblee.model.dto.BuildingDTO;
import com.itblee.model.response.BuildingSearchResponse;

import java.util.Collection;
import java.util.List;

public interface BuildingMapper extends ResultSetExtractor<List<Building>>, RowMapper<Building> {

	BuildingSearchResponse toResponse(Building entity);
	List<BuildingSearchResponse> toResponse(Collection<Building> entities);

	BuildingDTO toDto(Building entity);
	List<BuildingDTO> toDto(Collection<Building> entities);

	Building toEntity(BuildingDTO dto);
	List<Building> toEntity(Collection<BuildingDTO> dtos);

}

