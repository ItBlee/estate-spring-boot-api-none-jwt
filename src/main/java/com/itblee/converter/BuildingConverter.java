package com.itblee.converter;

import com.itblee.repository.entity.Building;
import com.itblee.model.BuildingModel;
import com.itblee.model.response.BuildingSearchResponse;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public interface BuildingConverter {

	BuildingSearchResponse toSearchResponse(Building entity);
	default List<BuildingSearchResponse> toSearchResponse(Collection<Building> entities) {
		List<BuildingSearchResponse> list = new ArrayList<>();
		entities.forEach(entity -> list.add(toSearchResponse(entity)));
		return list;
	}

	BuildingModel toModel(Building entity);
	default List<BuildingModel> toModel(Collection<Building> entities) {
		List<BuildingModel> list = new ArrayList<>();
		entities.forEach(entity -> list.add(toModel(entity)));
		return list;
	}

	Building toEntity(BuildingModel model);
	default List<Building> toEntity(Collection<BuildingModel> models) {
		List<Building> list = new ArrayList<>();
		models.forEach(model -> list.add(toEntity(model)));
		return list;
	}

}

