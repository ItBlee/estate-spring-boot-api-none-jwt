package com.itblee.converter;

import com.itblee.entity.DistrictEntity;
import com.itblee.filter.DistrictFilter;
import com.itblee.model.dto.DistrictDTO;

import java.util.List;

public interface DistrictConverter extends RowConverter<DistrictEntity>{
	DistrictDTO convertToDto(DistrictFilter filter);
	List<DistrictDTO> convertToDto(List<DistrictFilter> filters);

	<T> DistrictFilter convertToFilter(T o);
	<T> List<DistrictFilter> convertToFilter(List<T> os);

	DistrictEntity convertToEntity(DistrictFilter filter);
	List<DistrictEntity> convertToEntity(List<DistrictFilter> filters);
}
