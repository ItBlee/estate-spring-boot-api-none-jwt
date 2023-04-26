package com.itblee.converter;

import com.itblee.entity.Building;
import com.itblee.model.BuildingModel;
import com.itblee.model.response.BuildingSearchResponse;

import java.util.Collection;
import java.util.List;

public interface BuildingConverter extends ResultSetExtractor<List<Building>>, RowConverter<Building> {

	BuildingSearchResponse toResponse(Building entity);
	List<BuildingSearchResponse> toResponse(Collection<Building> entities);

	BuildingModel toModel(Building entity);
	List<BuildingModel> toModel(Collection<Building> entities);

	Building toEntity(BuildingModel model);
	List<Building> toEntity(Collection<BuildingModel> models);

}

