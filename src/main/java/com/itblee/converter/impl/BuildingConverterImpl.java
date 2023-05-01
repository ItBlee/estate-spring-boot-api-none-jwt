package com.itblee.converter.impl;

import com.itblee.converter.BuildingConverter;
import com.itblee.entity.*;
import com.itblee.model.*;
import com.itblee.model.response.BuildingSearchResponse;
import com.itblee.sqlbuilder.key.BuildingKey;
import com.itblee.util.SqlKeyUtils;
import com.itblee.util.StringUtils;
import com.itblee.util.ValidateUtils;
import org.springframework.stereotype.Component;

import java.util.*;

import static com.itblee.util.MapUtils.get;

@Component
public class BuildingConverterImpl extends AbstractConverter<Building> implements BuildingConverter {

    @Override
    public Optional<Building> mapRow(Map<String, ?> row) {
        Optional<Building> building = SqlKeyUtils.mapByKey(row, Building.class, BuildingKey.class);
        building.ifPresent(b -> {
            if (b.getDistrictId() != null) {
                District district = new District();
                district.setId(b.getDistrictId());
                district.setName(get(row, "districtName", String.class));
                district.setCode(get(row, "districtCode", String.class));
                b.setDistrict(district);
            }
        });
        return building;
    }

    @Override
    public Optional<Building> groupRow(Map<String, ?> row, Building building) {
        if (building == null)
            return Optional.empty();
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
        return Optional.of(building);
    }

    @Override
    public BuildingSearchResponse toResponse(Building entity) {
        ValidateUtils.requireNonNull(entity);
        BuildingSearchResponse response = convert(entity, BuildingSearchResponse.class);
        List<RentAreaModel> rentAreas = convert(entity.getRentAreas(), RentAreaModel.class);
        List<AssignUserModel> assignUsers = convert(entity.getAssignUsers(), AssignUserModel.class);
        List<String> address = new LinkedList<>();
        if (StringUtils.isNotBlank(entity.getStreet()))
            address.add(entity.getStreet());
        if (StringUtils.isNotBlank(entity.getWard()))
            address.add(entity.getWard());
        if (entity.getDistrict() != null && StringUtils.isNotBlank(entity.getDistrict().getName()))
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
    public BuildingModel toModel(Building entity) {
        ValidateUtils.requireNonNull(entity);
        BuildingModel dto = convert(entity, BuildingModel.class);
        {
            DistrictModel district = convert(entity.getDistrict(), DistrictModel.class);
            List<RentAreaModel> rentAreas = convert(entity.getRentAreas(), RentAreaModel.class);
            List<RentTypeModel> rentTypes = convert(entity.getRentTypes(), RentTypeModel.class);
            List<AssignUserModel> assignUsers = convert(entity.getAssignUsers(), AssignUserModel.class);
            dto.setDistrict(district);
            dto.setRentAreas(rentAreas);
            dto.setRentTypes(rentTypes);
            dto.setAssignUsers(assignUsers);
        }
        return dto;
    }

    @Override
    public List<BuildingModel> toModel(Collection<Building> entities) {
        List<BuildingModel> list = new ArrayList<>();
        entities.forEach(entity -> list.add(toModel(entity)));
        return list;
    }

    @Override
    public Building toEntity(BuildingModel model) {
        ValidateUtils.requireNonNull(model);
        Building entity = convert(model, Building.class);
        {
            District district = convert(model.getDistrict(), District.class);
            List<User> assignUsers = convert(model.getAssignUsers(), User.class);
            List<RentType> rentTypes = convert(model.getRentTypes(), RentType.class);
            List<RentArea> rentAreas = convert(model.getRentAreas(), RentArea.class);
                rentAreas.forEach(area -> area.setBuildingID(model.getId()));
            entity.setDistrictId(district.getId());
            entity.setDistrict(district);
            entity.setRentAreas(new HashSet<>(rentAreas));
            entity.setRentTypes(new HashSet<>(rentTypes));
            entity.setAssignUsers(new HashSet<>(assignUsers));
        }
        return entity;
    }

    @Override
    public List<Building> toEntity(Collection<BuildingModel> models) {
        List<Building> list = new ArrayList<>();
        models.forEach(model -> list.add(toEntity(model)));
        return list;
    }
}
