package com.itblee.converter;

import com.itblee.entity.BuildingEntity;
import com.itblee.filter.BuildingFilter;
import com.itblee.model.dto.BuildingDTO;
import com.itblee.model.response.BuildingSearchResponse;

import java.util.List;

public interface BuildingMapper extends RowMapper<BuildingEntity> {
	BuildingSearchResponse mapToResponse(BuildingFilter filter);
	List<BuildingSearchResponse> mapToResponse(List<BuildingFilter> filters);

	BuildingDTO mapToDto(BuildingFilter filter);
	List<BuildingDTO> mapToDto(List<BuildingFilter> filters);

	<T> BuildingFilter mapToFilter(T o);
	<T> List<BuildingFilter> mapToFilter(List<T> os);

	BuildingEntity mapToEntity(BuildingFilter filter);
	List<BuildingEntity> mapToEntity(List<BuildingFilter> filters);
}
