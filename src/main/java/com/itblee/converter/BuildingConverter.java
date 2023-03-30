package com.itblee.converter;

import com.itblee.entity.BuildingEntity;
import com.itblee.filter.BuildingFilter;
import com.itblee.model.dto.BuildingDTO;
import com.itblee.model.response.BuildingSearchResponse;

import java.util.List;

public interface BuildingConverter extends RowConverter<BuildingEntity>{
	BuildingSearchResponse convertToResponse(BuildingFilter filter);
	List<BuildingSearchResponse> convertToResponse(List<BuildingFilter> filters);

	BuildingDTO convertToDto(BuildingFilter filter);
	List<BuildingDTO> convertToDto(List<BuildingFilter> filters);

	<T> BuildingFilter convertToFilter(T o);
	<T> List<BuildingFilter> convertToFilter(List<T> os);

	BuildingEntity convertToEntity(BuildingFilter filter);
	List<BuildingEntity> convertToEntity(List<BuildingFilter> filters);
}
