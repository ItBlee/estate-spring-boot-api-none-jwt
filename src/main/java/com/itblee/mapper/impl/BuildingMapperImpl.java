package com.itblee.mapper.impl;

import com.itblee.repository.entity.*;
import com.itblee.mapper.BuildingMapper;
import com.itblee.model.dto.*;
import com.itblee.model.response.BuildingSearchResponse;
import com.itblee.repository.query.key.BuildingKey;
import com.itblee.utils.StringUtils;
import org.springframework.stereotype.Component;

import java.util.*;

import static com.itblee.utils.MapUtils.get;

@Component
public class BuildingMapperImpl extends AbstractMapper<Building> implements BuildingMapper {

    @Override
    public Building mapRow(Map<String, Object> row) {
        Optional<Building> building = quickMap(row, Building.class, BuildingKey.class);
        building.ifPresent(b -> {
            if (b.getDistrictID() != null) {
                District district = new District();
                district.setId(b.getDistrictID());
                district.setName(get(row, "districtName", String.class));
                district.setCode(get(row, "districtCode", String.class));
                b.setDistrict(district);
            }
        });
        return building.orElse(null);
    }

    @Override
    public Building mergeRow(Map<String, Object> row, Building building) {
        if (building == null)
            throw new IllegalStateException();
        if (row.containsKey("rentareaID")) {
            RentArea rentArea = new RentArea();
            rentArea.setId(get(row, "rentareaID", Long.class));
            if (rentArea.getId() != null) {
                if (row.containsKey("rentareaValue")) {
                    rentArea.setValue(get(row, "rentareaValue", Integer.class));
                    rentArea.setBuildingID(building.getId());
                    building.getRentAreas().add(rentArea);
                }
            }
        }
        if (row.containsKey("renttypeID")) {
            RentType rentType = new RentType();
            rentType.setId(get(row, "renttypeID", Long.class));
            if (rentType.getId() != null) {
                if (row.containsKey("renttypeCode") && row.containsKey("renttypeName")) {
                    rentType.setCode(get(row, "renttypeCode", String.class));
                    rentType.setName(get(row, "renttypeName", String.class));
                    building.getRentTypes().add(rentType);
                }
            }
        }
        if (row.containsKey("userID")) {
            User user = new User();
            user.setId(get(row, "userID", Long.class));
            if (user.getId() != null) {
                if (row.containsKey("userFullName")) {
                    user.setFullName(get(row, "userFullName", String.class));
                    building.getAssignUsers().add(user);
                }
            }
        }
        return building;
    }

    @Override
    public BuildingSearchResponse toResponse(Building entity) {
        if (entity == null)
            throw new IllegalArgumentException();
        BuildingSearchResponse response = convert(entity, BuildingSearchResponse.class);
        List<RentAreaDTO> rentAreas = convert(entity.getRentAreas(), RentAreaDTO.class);
        List<AssignUserDTO> assignUsers = convert(entity.getAssignUsers(), AssignUserDTO.class);
        List<String> address = new LinkedList<>();
        if (!StringUtils.isBlank(entity.getStreet()))
            address.add(entity.getStreet());
        if (!StringUtils.isBlank(entity.getWard()))
            address.add(entity.getWard());
        if (entity.getDistrict() != null && !StringUtils.isBlank(entity.getDistrict().getName()))
            address.add(entity.getDistrict().getName());
        response.setAddress(String.join(", ", address));
        response.setRentAreas(rentAreas);
        response.setAssignUsers(assignUsers);
        return response;
    }

    @Override
    public List<BuildingSearchResponse> toResponse(Collection<Building> entities) {
        List<BuildingSearchResponse> list = new ArrayList<>();
        entities.forEach(entity -> list.add(toResponse(entity)));
        return list;
    }

    @Override
    public BuildingDTO toDto(Building entity) {
        if (entity == null)
            throw new IllegalArgumentException();
        BuildingDTO dto = convert(entity, BuildingDTO.class);
        {
            DistrictDTO district = convert(entity.getDistrict(), DistrictDTO.class);
            List<RentAreaDTO> rentAreas = convert(entity.getRentAreas(), RentAreaDTO.class);
            List<RentTypeDTO> rentTypes = convert(entity.getRentTypes(), RentTypeDTO.class);
            List<AssignUserDTO> assignUsers = convert(entity.getAssignUsers(), AssignUserDTO.class);
            dto.setDistrict(district);
            dto.setRentAreas(rentAreas);
            dto.setRentTypes(rentTypes);
            dto.setAssignUsers(assignUsers);
        }
        return dto;
    }

    @Override
    public List<BuildingDTO> toDto(Collection<Building> entities) {
        List<BuildingDTO> list = new ArrayList<>();
        entities.forEach(entity -> list.add(toDto(entity)));
        return list;
    }

    @Override
    public Building toEntity(BuildingDTO dto) {
        if (dto == null)
            throw new IllegalArgumentException();
        Building entity = convert(dto, Building.class);
        {
            District district = convert(dto.getDistrict(), District.class);
            List<User> assignUsers = convert(dto.getAssignUsers(), User.class);
            List<RentType> rentTypes = convert(dto.getRentTypes(), RentType.class);
            List<RentArea> rentAreas = convert(dto.getRentAreas(), RentArea.class);
                rentAreas.forEach(area -> area.setBuildingID(dto.getId()));
            entity.setDistrictID(district.getId());
            entity.setDistrict(district);
            entity.setRentAreas(new HashSet<>(rentAreas));
            entity.setRentTypes(new HashSet<>(rentTypes));
            entity.setAssignUsers(new HashSet<>(assignUsers));
        }
        return entity;
    }

    @Override
    public List<Building> toEntity(Collection<BuildingDTO> dtos) {
        List<Building> list = new ArrayList<>();
        dtos.forEach(dto -> list.add(toEntity(dto)));
        return list;
    }
}
