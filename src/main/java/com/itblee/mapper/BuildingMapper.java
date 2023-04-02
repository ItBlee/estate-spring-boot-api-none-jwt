package com.itblee.mapper;

import com.itblee.entity.Building;
import com.itblee.model.dto.BuildingDTO;
import com.itblee.model.response.BuildingSearchResponse;

import java.util.Collection;
import java.util.List;

public interface BuildingMapper extends RowMapper<Building> {
	BuildingSearchResponse mapToResponse(Building entity);
	List<BuildingSearchResponse> mapToResponse(Collection<Building> entities);

	BuildingDTO mapToDto(Building entity);
	List<BuildingDTO> mapToDto(Collection<Building> entities);

	Building mapToEntity(BuildingDTO filter);
	List<Building> mapToEntity(Collection<BuildingDTO> filters);
}
