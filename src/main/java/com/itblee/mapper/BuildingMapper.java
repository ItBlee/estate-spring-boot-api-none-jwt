package com.itblee.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.itblee.entity.BuildingEntity;
import com.itblee.model.dto.BuildingDTO;

public class BuildingMapper implements RowMapper<BuildingEntity> {

	@Override
	public BuildingEntity mapRow(ResultSet resultSet) {
		try {
			BuildingEntity building = new BuildingEntity();
			building.setId(resultSet.getLong("id"));
			building.setName(resultSet.getString("name"));
			building.setStreet(resultSet.getString("street"));
			building.setWard(resultSet.getString("ward"));
			return building;
		} catch (SQLException e) {
			return null;
		}	
	}

	public static BuildingDTO mapDto(BuildingEntity entity) {
		if (entity == null)
			return null;
		BuildingDTO dto = new BuildingDTO();
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		dto.setAddress(entity.getStreet() + " " + entity.getWard());
		return dto;
	}

	public static List<BuildingDTO> mapDto(List<BuildingEntity> entities) {
		List<BuildingDTO> list = new ArrayList<>();
		for (BuildingEntity entity: entities) {
			BuildingDTO dto = new BuildingDTO();
			dto.setId(entity.getId());
			dto.setName(entity.getName());
			dto.setAddress(entity.getStreet() + " " + entity.getWard());
			list.add(dto);
		}
		return list;
	}

}
