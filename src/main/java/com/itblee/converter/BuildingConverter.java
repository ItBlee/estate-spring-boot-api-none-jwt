package com.itblee.converter;

import com.itblee.model.BuildingModel;
import com.itblee.model.response.BuildingSearchResponse;
import com.itblee.repository.entity.Building;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public interface BuildingConverter {

	BuildingSearchResponse toSearchResponse(Building entity);
	default List<BuildingSearchResponse> toSearchResponse(Collection<Building> entities) {
		return entities.stream()
				.map(this::toSearchResponse)
				.collect(Collectors.toList());
	}

	BuildingModel toModel(Building entity);
	default List<BuildingModel> toModel(Collection<Building> entities) {
		return entities.stream()
				.map(this::toModel)
				.collect(Collectors.toList());
	}

	Building toEntity(BuildingModel model);
	default List<Building> toEntity(Collection<BuildingModel> models) {
		return models.stream()
				.map(this::toEntity)
				.collect(Collectors.toList());
	}

}

