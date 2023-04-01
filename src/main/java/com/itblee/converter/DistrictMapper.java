package com.itblee.converter;

import com.itblee.entity.DistrictEntity;
import com.itblee.filter.DistrictFilter;
import com.itblee.model.dto.DistrictDTO;

import java.util.List;

public interface DistrictMapper extends RowMapper<DistrictEntity> {
	DistrictDTO mapToDto(DistrictFilter filter);
	List<DistrictDTO> mapToDto(List<DistrictFilter> filters);

	<T> DistrictFilter mapToFilter(T o);
	<T> List<DistrictFilter> mapToFilter(List<T> os);

	DistrictEntity mapToEntity(DistrictFilter filter);
	List<DistrictEntity> mapToEntity(List<DistrictFilter> filters);
}
