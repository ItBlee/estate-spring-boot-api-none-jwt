package com.itblee.converter.impl;

import com.itblee.converter.BuildingConverter;
import com.itblee.repository.entity.*;
import com.itblee.model.*;
import com.itblee.model.response.BuildingSearchResponse;
import com.itblee.util.StringUtils;
import com.itblee.util.ValidateUtils;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

@Component
public class BuildingConverterImpl extends AbstractConverter implements BuildingConverter {

    @Override
    public BuildingSearchResponse toSearchResponse(Building entity) {
        ValidateUtils.requireNonNull(entity);
        BuildingSearchResponse response = convert(entity, BuildingSearchResponse.class);
        List<RentAreaModel> rentAreas = convert(entity.getRentArea(), RentAreaModel.class);
        List<AssignUserModel> assignUsers = convert(entity.getUser(), AssignUserModel.class);
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
        BuildingModel model = convert(entity, BuildingModel.class);
        {
            DistrictModel district = convert(entity.getDistrict(), DistrictModel.class);
            List<RentAreaModel> rentAreas = convert(entity.getRentArea(), RentAreaModel.class);
            List<RentTypeModel> rentTypes = convert(entity.getRentType(), RentTypeModel.class);
            List<AssignUserModel> assignUsers = convert(entity.getUser(), AssignUserModel.class);
            model.setDistrict(district);
            model.setRentAreas(rentAreas);
            model.setRentTypes(rentTypes);
            model.setAssignUsers(assignUsers);
        }
        return model;
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
            entity.setRentArea(new HashSet<>(rentAreas));
            entity.setRentType(new HashSet<>(rentTypes));
            entity.setUser(new HashSet<>(assignUsers));
        }
        return entity;
    }

}
