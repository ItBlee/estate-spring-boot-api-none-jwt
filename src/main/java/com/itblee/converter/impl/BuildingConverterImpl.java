package com.itblee.converter.impl;

import com.itblee.converter.BuildingConverter;
import com.itblee.model.*;
import com.itblee.model.response.BuildingSearchResponse;
import com.itblee.repository.entity.*;
import com.itblee.util.StringUtils;
import com.itblee.util.ValidateUtils;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class BuildingConverterImpl extends AbstractConverter<Building> implements BuildingConverter {

    @Override
    public BuildingSearchResponse toSearchResponse(Building entity) {
        ValidateUtils.requireNonNull(entity);
        BuildingSearchResponse response = convert(entity, BuildingSearchResponse.class);
        List<RentAreaModel> rentAreas = entity.getRentAreas().stream()
                .map(rentArea -> convert(rentArea, RentAreaModel.class))
                .collect(Collectors.toList());
        List<AssignUserModel> assignUsers = entity.getAssignUsers().stream()
                .map(assignUser -> convert(assignUser.getUser(), AssignUserModel.class))
                .collect(Collectors.toList());
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
    public BuildingModel toModel(Building entity) {
        ValidateUtils.requireNonNull(entity);
        BuildingModel dto = convert(entity, BuildingModel.class);
        {
            DistrictModel district = convert(entity.getDistrict(), DistrictModel.class);
            List<RentAreaModel> rentAreas = entity.getRentAreas().stream()
                    .map(rentArea -> convert(rentArea, RentAreaModel.class))
                    .collect(Collectors.toList());
            List<RentTypeModel> rentTypes = entity.getRentTypes().stream()
                    .map(rentType -> convert(rentType.getRentType(), RentTypeModel.class))
                    .collect(Collectors.toList());
            List<AssignUserModel> assignUsers = entity.getAssignUsers().stream()
                    .map(assignUser -> convert(assignUser.getUser(), AssignUserModel.class))
                    .collect(Collectors.toList());
            dto.setDistrict(district);
            dto.setRentAreas(rentAreas);
            dto.setRentTypes(rentTypes);
            dto.setAssignUsers(assignUsers);
        }
        return dto;
    }

    @Override
    public Building toEntity(BuildingModel model) {
        throw new UnsupportedOperationException();
        /*ValidateUtils.requireNonNull(model);
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
        return entity;*/
    }

}
